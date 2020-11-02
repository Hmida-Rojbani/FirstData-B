package de.tekup.rest.data.services;

import java.util.List;

import de.tekup.rest.data.models.AddressEntity;
import de.tekup.rest.data.models.PersonEntity;

public interface PersonService {
	
	List<PersonEntity> getAllPersonEntity();
	PersonEntity getPersonEntityById(long id);
	PersonEntity createPersonEntity(PersonEntity entity);
	PersonEntity modifyPersonEntity(long id, PersonEntity newEntity);
	PersonEntity deletePersonEntityById(long id);
	List<AddressEntity> getAllAddressEntity();
	public List<PersonEntity> getAllByOperator(String operator);
	public double averageAgesPersons();
}
