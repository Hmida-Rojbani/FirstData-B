package de.tekup.rest.data.services;

import java.util.List;

import de.tekup.rest.data.dto.GameType;
import de.tekup.rest.data.dto.PersonDTO;
import de.tekup.rest.data.dto.PersonRequest;
import de.tekup.rest.data.models.AddressEntity;
import de.tekup.rest.data.models.PersonEntity;

public interface PersonService {
	
	List<PersonEntity> getAllPersonEntity();
	PersonEntity getPersonEntityById(long id);
	PersonDTO createPersonEntity(PersonRequest entity);
	PersonEntity modifyPersonEntity(long id, PersonEntity newEntity);
	PersonEntity deletePersonEntityById(long id);
	List<AddressEntity> getAllAddressEntity();
	public List<PersonEntity> getAllByOperator(String operator);
	public double averageAgesPersons();
	public List<GameType> getGameTypeAndNumber();
	public List<PersonEntity> getMaxPlayed();
}
