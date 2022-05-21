package in.ashokit.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ashokit.bindings.LoginForm;
import in.ashokit.bindings.UnlockForm;
import in.ashokit.bindings.UserForm;
import in.ashokit.entities.CitiesMasterEntity;
import in.ashokit.entities.CountryMasterEntity;
import in.ashokit.entities.StateMasterEntity;
import in.ashokit.entities.UserAccountEntity;
import in.ashokit.repository.CityRepository;
import in.ashokit.repository.CountryRepository;
import in.ashokit.repository.StateRepository;
import in.ashokit.repository.UserAccountRepository;
import in.ashokit.util.EmailUtils;


@Service

public class UserManagementServiceImpl implements UserManagementService {



@Autowired	
private UserAccountRepository userRepo;
@Autowired
private CountryRepository countryRepo;
@Autowired
private StateRepository stateRepo;
@Autowired
private CityRepository cityRepo;
@Autowired
private EmailUtils emailUtils;

	
    @Override
	public String login(LoginForm loginForm) {
		UserAccountEntity entity=userRepo.findByEmailAndPazzword(loginForm.getEmail(),loginForm.getPwd());
	
		if(entity==null) {
		return "Invalid Credentials";
	    }
	
	
		if(entity!= null && entity.getAccStatus().equals("LOCKED")){
		return "Your Account Locked";
	    }
		
		return "SUCCESS";
	}


	@Override
	public String emailCheck(String emailId) {
		UserAccountEntity entity=userRepo.findByEmail(emailId);
		if (entity == null) {
			return "UNIQUE";
			
		}
		
		return "DUPLICATE";
	}

	@Override
	public Map<Integer, String> loadCountries() {
		List<CountryMasterEntity> countries=countryRepo.findAll();
		Map<Integer,String>CountryMap= new HashMap<>();
		for(CountryMasterEntity entity : countries) {
			CountryMap.put(entity.getCountryId(),entity.getCountryName());
			
		}
		
		return null;
	}

	@Override
	public Map<Integer, String> loadStates(Integer CountryId) {
		List<StateMasterEntity> states = stateRepo.findByCountryId(CountryId);
		Map<Integer,String> statesMap= new HashMap<>();
		for(StateMasterEntity state : states) {
			statesMap.put(state.getStateid(), state.getStateName());
		}
		return null;
	}

	@Override
	public Map<Integer, String> loadCities(Integer StateId) {
		Map<Integer,String> citiesMap= new HashMap<>();
		List<CitiesMasterEntity> cities= cityRepo.findByStateId(StateId);
		for(CitiesMasterEntity entity : cities) {
			citiesMap.put(entity.getCityid(),entity.getCityName());
			
		}
		
		return citiesMap;
	}

	@Override
	public String registerUser(UserForm userForm) {
	   
		UserAccountEntity entity= new UserAccountEntity();
	   
		BeanUtils.copyProperties(userForm, entity);
	   
		entity.setAccStatus("LOCKED");
	   // Generate Random PASSWORD
	   entity.setPazzword(generateRandomPwd());
	   
	   UserAccountEntity saveEntity = userRepo.save(entity);
	   
	   String email = userForm.getEmail();
	   String subject ="User Registration-Ashok IT";
	   String fileName = "UNLOCK-ACC-EMAIL-BODY-TEMPLATE.txt";
	   String body = readMailBodyContent(fileName , entity);
	   
	   boolean isSent = emailUtils.sendEmail(email , subject ,body);
	   
	   if (saveEntity.getUserid()!= null && isSent) {
		
		   return "SUCCESS";
		  
	   }
	   return "FAIL";
	
	}

	@Override
	public String unlockAccount(UnlockForm unlockAccForm){
		if(!(unlockAccForm.getNewPwd().equals(unlockAccForm.getConfirmNewPwd()))) {
			return "Password and Confirm Password should be same";
		}
		UserAccountEntity entity= 
				userRepo.findByEmailAndPazzword(unlockAccForm.getEmail(),unlockAccForm.getTempPwd());
		if (entity==null) {
		return "Incorrect Temporary Password";
		}
		entity.setPazzword(unlockAccForm.getNewPwd());
		entity.setAccStatus("UNLOCKED");
		userRepo.save(entity);
		
		return "Account Unlocked";
	}
		
	@Override
	public String forgotPwd(String emailId) {
		UserAccountEntity entity= userRepo.findByEmail(emailId);
		if (entity == null) {
			return "Invalid Email Id";
		}
		return null;
	}
	//If record available send Password to user mail
	// Email Sending

private String generateRandomPwd() {
    int leftLimit = 48; // numeral '0'
    int rightLimit = 122; // letter 'z'
    int targetStringLength = 6;
    Random random = new Random();

    String generatedString = random.ints(leftLimit, rightLimit + 1)
      .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
      .limit(targetStringLength)
      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
      .toString();

    System.out.println(generatedString);
	return generatedString;
}



private String readMailBodyContent(String fileName, UserAccountEntity entity) {
	
	String mailBody = null;
	try {
		
		StringBuffer sb = new StringBuffer();
		
		FileReader fr = new FileReader(fileName);
		BufferedReader br = new BufferedReader(fr);
		String line = br.readLine();// reading first line data
		
		while(line != null) {
			sb.append(line);//appending line data to buffer Object
			line = br.readLine();//reading next line data
		}
		mailBody = sb.toString();
			
		mailBody = mailBody.replace("{FNAME}", entity.getFname());
		mailBody = mailBody.replace("{LNAME}", entity.getLname());
		mailBody = mailBody.replace("{TEMP-PWD}", entity.getPazzword());
		mailBody = mailBody.replace("{EMAIL}", entity.getEmail());
		mailBody = mailBody.replace("{PWD}", entity.getPazzword());
		
		br.close();
		
		
	}catch (Exception e) {
		e.printStackTrace();
	}
	return mailBody;
}

		
			
		
	}

