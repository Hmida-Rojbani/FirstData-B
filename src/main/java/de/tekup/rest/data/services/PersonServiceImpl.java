package de.tekup.rest.data.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.tekup.rest.data.models.AddressEntity;
import de.tekup.rest.data.models.PersonEntity;
import de.tekup.rest.data.repositories.AddressRepository;
import de.tekup.rest.data.repositories.PersonRepository;
@Service
public class PersonServiceImpl implements PersonService {

	private PersonRepository reposPerson;
	private AddressRepository reposAddress;

	@Autowired
	public PersonServiceImpl(PersonRepository reposPerson, AddressRepository reposAddress) {
		super();
		this.reposPerson = reposPerson;
		this.reposAddress = reposAddress;
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
		AddressEntity addressEntity = entity.getAddress();
		AddressEntity addressInBase = reposAddress.save(addressEntity);
		entity.setAddress(addressInBase);
		return reposPerson.save(entity);
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
