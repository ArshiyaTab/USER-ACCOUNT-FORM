package in.ashokit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entities.CountryMasterEntity;

public interface CountryRepository extends JpaRepository<CountryMasterEntity, Integer> {
	
	

}
