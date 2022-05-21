package in.ashokit.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
@Entity
@Table(name="USER_ACCOUNTS")
@Data
public class UserAccountEntity {
	@Id
	@GeneratedValue
	@Column(name="USER_ID")
	private Integer userid;
	
	@Column(name="FIRST_NAME")
	private String fname;
	
	@Column(name="LAST_NAME")
	private String lname;
	
	@Column(name="USER_EMAIL", unique=true)
	private String email;
	
	@Column(name="USER_PWD")
	private String  pazzword;
	
	@Column(name="USER_MOBILE")
	private Long phno;
	
	@Column(name="DOB")
	private LocalDate dob;
	
	@Column(name="Gender")
	private String gender;
	
	@Column(name="CITY_ID")
	private Integer cityid;
	
	@Column(name="STATE_ID")
	private Integer stateid;
	
	@Column(name="COUNTRY_ID")
	private Integer countryid;
	
	@Column(name="ACCOUNT_STATUS")
	private String accStatus;
	
	@Column(name="CREATED_DATE", updatable = false)
	@CreationTimestamp
	private LocalDate createdDate;
	
	@Column(name="UPDATED_DATE", insertable = false)
	@UpdateTimestamp
	private LocalDate updatedDate;
	
	
	
	

}
