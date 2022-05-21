package in.ashokit.service;

import java.util.Map;

import in.ashokit.bindings.LoginForm;
import in.ashokit.bindings.UnlockForm;
import in.ashokit.bindings.UserForm;

public interface UserManagementService {
	
public  String login(LoginForm loginForm);

public  String emailCheck(String emailId);

public Map<Integer,String> loadCountries();

public Map<Integer,String> loadStates(Integer CountryId);

public Map<Integer,String> loadCities(Integer StateId);

public String registerUser(UserForm userForm);


//Unlock Account Functionality Method
public String unlockAccount(UnlockForm unlockAccForm);

//Forgot password Functionality method
public String forgotPwd(String emailId);



	

}
