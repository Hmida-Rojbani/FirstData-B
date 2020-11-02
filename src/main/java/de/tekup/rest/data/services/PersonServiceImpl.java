package de.tekup.rest.data.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.tekup.rest.data.models.AddressEntity;
import de.tekup.rest.data.models.GamesEntity;
import de.tekup.rest.data.models.PersonEntity;
import de.tekup.rest.data.models.TelephoneNumberEntity;
import de.tekup.rest.data.repositories.AddressRepository;
import de.tekup.rest.data.repositories.GamesRepository;
import de.tekup.rest.data.repositories.PersonRepository;
import de.tekup.rest.data.repositories.TelephoneNumberRepository;
@Service
public class PersonServiceImpl implements PersonService {

	private PersonRepository reposPerson;
	private AddressRepository reposAddress;
	private TelephoneNumberRepository reposPhone;
	private GamesRepository reposGames;

	@Autowired
	public PersonServiceImpl(PersonRepository reposPerson, AddressRepository reposAddress,
			TelephoneNumberRepository reposPhone, GamesRepository reposGames) {
		super();
		this.reposPerson = reposPerson;
		this.reposAddress = reposAddress;
		this.reposPhone = reposPhone;
		this.reposGames = reposGames;
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
		// set Person within address
		addressInBase.setPerson(entity);
		// save person
		PersonEntity person =  reposPerson.save(entity);
		
		for (TelephoneNumberEntity phone : entity.getPhones()) {
			// set person into phone
			phone.setPerson(person);
			// save phone
			reposPhone.save(phone);
		}
		
		// saving Games
		
		for (GamesEntity game : entity.getGames()) {
			//set person into game
			List<PersonEntity> persons;
			if(game.getPersons() != null) {
				persons = game.getPersons();
			}
		else {
				persons = new ArrayList<>();
			}
			persons.add(person);
			game.setPersons(persons);
			reposGames.save(game);
		}
		
		return person;
	}

	// TODO update this code to support all data modification
	@Override
	public PersonEntity modifyPersonEntity(long id, PersonEntity newPerson) {
		// is There a better (3 points bonus en DS)
		PersonEntity oldPerson = this.getPersonEntityById(id);
		if (newPerson.getName() != null) 
			oldPerson.setName(newPerson.getName());
		if (newPerson.getDateOfBirth() != null) 
			oldPerson.setDateOfBirth(newPerson.getDateOfBirth());
		
		AddressEntity oldAddress = oldPerson.getAddress();
		AddressEntity newAddress = newPerson.getAddress();
		
		// update (fusion de données) entre l'address existant et nouvelle address
		if(newAddress.getNumber() != 0)
			oldAddress.setNumber(newAddress.getNumber());
		if(newAddress.getStreet() != null)
			oldAddress.setStreet(newAddress.getStreet());
		if(newAddress.getCity() != null)
			oldAddress.setCity(newAddress.getCity());
		
		// update phones
		List<TelephoneNumberEntity> newPhones = newPerson.getPhones();
		List<TelephoneNumberEntity> oldPhones = oldPerson.getPhones();
		
		// loop over phones
		for (int i = 0; i < newPhones.size(); i++) {
			TelephoneNumberEntity newPhone = newPhones.get(i);
			for (int j = 0; j < oldPhones.size(); j++) {
				TelephoneNumberEntity oldPhone = oldPhones.get(j);
				if(newPhone.getId() == oldPhone.getId()) {
					if(newPhone.getNumber() != null)
						oldPhone.setNumber(newPhone.getNumber());
					if(newPhone.getOperator() != null)
						oldPhone.setOperator(newPhone.getOperator());
					// stop loop
					break;
				}
					
			}
		}
		
		// TODO loop over games 
		List<GamesEntity> oldGames = oldPerson.getGames();
		List<GamesEntity> newGames = newPerson.getGames();
		boolean found;
		for (GamesEntity newGame : newGames) {
			found = false;
			for (GamesEntity oldGame : oldGames) {
				if(oldGame.getId() == newGame.getId()) {
					if(newGame.getTitle() != null)
					oldGame.setTitle(newGame.getTitle());
					if(newGame.getType() != null)
						oldGame.setType(newGame.getType());
					// stop 
					found = true;
					break;
				}
				
			}
			if(found == false) {
				oldGames.add(newGame);
				List<PersonEntity> persons;
				if(newGame.getPersons() != null) {
					persons = newGame.getPersons();
				} 
			else {
					persons = new ArrayList<>();
				}
				persons.add(oldPerson);
				newGame.setPersons(persons);
				reposGames.save(newGame);
			}
				
		}
		
		return reposPerson.save(oldPerson);
	}

	@Override
	public PersonEntity deletePersonEntityById(long id) {
		PersonEntity entity = this.getPersonEntityById(id);
		reposPerson.deleteById(id);
		return entity;
	}
	
	// All person with a given operator
	public List<PersonEntity> getAllByOperator(String operator){
		/*List<PersonEntity> persons = reposPerson.findAll();
		List<PersonEntity> returnPersons = new ArrayList<>();
		for (PersonEntity person : persons) {
			//filtrage 
			for (TelephoneNumberEntity phone : person.getPhones()) {
				if(phone.getOperator().equalsIgnoreCase(operator)) {
					returnPersons.add(person);
					break;
				}
					
			}
			
		}*/
		
		/*List<TelephoneNumberEntity> phones = reposPhone.findAll();
		Set<PersonEntity> returnPersons = new HashSet<>();
		for (TelephoneNumberEntity phone : phones) {
			if(phone.getOperator().equalsIgnoreCase(operator)) {
				returnPersons.add(phone.getPerson());
			}
		}*/
		List<PersonEntity> returnPersons = reposPhone.findAll()
													.stream()
													.filter(phone->phone.getOperator().equalsIgnoreCase(operator))
													.map(phone -> phone.getPerson())
													.distinct()
													.collect(Collectors.toList());
		return returnPersons;
	}
	
	// Average age of all Persons
	
	public double averageAgesPersons() {
		/*double sum = 0;
		List<PersonEntity> persons = reposPerson.findAll();
		LocalDate now = LocalDate.now();
		for (PersonEntity person : persons) {
			 sum += now.getYear() - person.getDateOfBirth().getYear();
		}*/
		LocalDate now = LocalDate.now();
		double average = reposPerson.findAll()
								.stream()
								.mapToDouble(person -> now.getYear() - person.getDateOfBirth().getYear())
								.average()
								.orElse(0);
		
		return average;
	}
	
	// Persons whom playes the type of game the most played
	public List<PersonEntity> getMaxPlayed(){
		List<GamesEntity> games = reposGames.findAll();
		GamesEntity mostType = games.get(0);
		for (GamesEntity game : games) {
			if(mostType.getPersons().size() < game.getPersons().size())
				mostType = game;
		}
		// write it in Java8
		return mostType.getPersons();
	}

	@Override
	public List<AddressEntity> getAllAddressEntity() {
;
		return null;
	}

}
