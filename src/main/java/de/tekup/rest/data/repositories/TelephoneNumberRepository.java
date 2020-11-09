package de.tekup.rest.data.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import de.tekup.rest.data.models.PersonEntity;
import de.tekup.rest.data.models.TelephoneNumberEntity;

public interface TelephoneNumberRepository extends JpaRepository<TelephoneNumberEntity, Integer>{

	// List<TelephoneNumberEntity> findByOperator(String operator);
	List<TelephoneNumberEntity> findByOperatorIgnoreCase(String operator);
	
	//@Query(value = "select * from TelephoneNumberEntity t "
	//		+ "where t.operator = ?1" , nativeQuery = true)
	@Query("select t from TelephoneNumberEntity t "
			+ "where lower(t.operator) = lower(:operator)" )
	List<TelephoneNumberEntity> getByOperator(@Param("operator") String operator);

	// All person with a given operator
	@Query("select distinct(t.person) from TelephoneNumberEntity t "
			+ "where lower(t.operator) = lower(:operator)" )
	List<PersonEntity> getPersonWithOperator(@Param("operator") String operator);

}
