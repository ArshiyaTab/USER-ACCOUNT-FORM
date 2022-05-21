package in.ashokit.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
@Entity
@Table(name="CITY_MASTER")
@Data
public class CitiesMasterEntity {
	@Id
	@Column(name="CITY_ID")
	private Integer cityid;
	
	@Column(name="CITY_NAME")
	private String cityName;
	
	@Column(name="STATE_ID")
	private Integer stateid;

}
