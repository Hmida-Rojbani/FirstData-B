package de.tekup.rest.data.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.tekup.rest.data.models.AddressEntity;
import de.tekup.rest.data.models.PersonEntity;
import de.tekup.rest.data.models.TelephoneNumberEntity;
import de.tekup.rest.data.repositories.AddressRepository;
import de.tekup.rest.data.repositories.PersonRepository;
import de.tekup.rest.data.repositories.TelephoneNumberRepository;
@Service
public class PersonServiceImpl implements PersonService {

	private PersonRepository reposPerson;
	private AddressRepository reposAddress;
	private TelephoneNumberRepository reposPhone;

	@Autowired
	public PersonServiceImpl(PersonRepository reposPerson, AddressRepository reposAddress,
			TelephoneNumberRepository reposPhone) {
		super();
		this.reposPerson = reposPerson;
		this.reposAddress = reposAddress;
		this.reposPhone = reposPhone;
	}



	@Override
	public List<PersonEntity> getAllPersonEntity() {
		return reposPerson.findAll();
	}

	

	@Override
	public PersonEntity getPersonEntityById(long id) {
		Optional<PersonEntity> opt = reposPerson.findById(id);

		PersonEntity entity;

		if (opt.isPresent())
			entity = opt.get();
		else
			throw new NoSuchElementException("Person with this id is not found.");

		return entity;
	}

	@Override
	public PersonEntity createPersonEntity(PersonEntity entity) {
		// extract Address
		AddressEntity addressEntity = entity.getAddress();
		// save Address first
		AddressEntity addressInBase = reposAddress.save(addressEntity);
		// set Address with Id in person
		entity.setAddress(addressInBase);
		// save person
		PersonEntity person =  reposPerson.save(entity);
		
		for (TelephoneNumberEntity phone : entity.getPhones()) {
			phone.setPerson(person);
			reposPhone.save(phone);
		}
		
		return person;
	}

	@Override
	public PersonEntity modifyPersonEntity(long id, PersonEntity newEntity) {
		PersonEntity entity = this.getPersonEntityById(id);
		if (newEntity.getName() != null) 
			entity.setName(newEntity.getName());
		if (newEntity.getDateOfBirth() != null) 
			entity.setDateOfBirth(newEntity.getDateOfBirth());
		if (newEntity.getAddress() != null) 
			entity.setAddress(newEntity.getAddress());
		return reposPerson.save(entity);
	}

	@Override
	public PersonEntity deletePersonEntityById(long id) {
		PersonEntity entity = this.getPersonEntityById(id);
		reposPerson.deleteById(id);
		return entity;
	}

	@Override
	public List<AddressEntity> getAllAddressEntity() {
;
		return null;
	}

}
