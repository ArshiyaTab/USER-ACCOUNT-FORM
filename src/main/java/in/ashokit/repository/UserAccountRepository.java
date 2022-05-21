package in.ashokit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entities.UserAccountEntity;

public interface UserAccountRepository extends JpaRepository<UserAccountEntity , Integer> {
	
	//select * from user_accounts where user_email=? and user_pwd=?
	public UserAccountEntity findByEmailAndPazzword(String email,String pwd);
	
	//select * from user_account where user_email=?
	public UserAccountEntity findByEmail(String emailId);

}
