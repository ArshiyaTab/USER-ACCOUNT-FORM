package in.ashokit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entities.CitiesMasterEntity;


public interface CityRepository extends JpaRepository<CitiesMasterEntity , Integer>  {
	
	//select * from Cities_Master where state_Id=?
	public List<CitiesMasterEntity> findByStateId(Integer stateId);

}
