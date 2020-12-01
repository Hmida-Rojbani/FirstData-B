package de.tekup.rest.data.endpoints;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.tekup.rest.data.dto.GameType;
import de.tekup.rest.data.dto.PersonDTO;
import de.tekup.rest.data.dto.PersonRequest;
import de.tekup.rest.data.models.AddressEntity;
import de.tekup.rest.data.models.PersonEntity;
import de.tekup.rest.data.services.PersonService;

@RestController
@RequestMapping("/api/persons")
public class PersonRest {
	
	private PersonService service;
	private ModelMapper mapper;
	
	
	@Autowired
	public PersonRest(PersonService service, ModelMapper mapper) {
		super();
		this.service = service;
		this.mapper = mapper;
	}

	@GetMapping
	public List<PersonEntity> getAllPersonEntities(){
		return service.getAllPersonEntity();
	}
	
	@GetMapping("/address")
	public List<AddressEntity> getAllAddressEntities(){
		return service.getAllAddressEntity();
	}
	
	@PostMapping
	public PersonDTO createPersonEntity(@Valid @RequestBody PersonRequest person) {
		return service.createPersonEntity(person);
	}
	
	@GetMapping("/{id}")
	public PersonDTO getPersonEntityWithId(@PathVariable("id")long id){
		return mapper.map(service.getPersonEntityById(id), PersonDTO.class);
	}
	
	@GetMapping("operator/{operator}")
	public List<PersonEntity> getPersonsEntityWithoperator(@PathVariable("operator")String operator){
		return service.getAllByOperator(operator);
	}
	@GetMapping("average/age")
	public double getAverageAge(){
		return service.averageAgesPersons();
	}
	
	@PutMapping("/{id}")
	public PersonEntity updatePersonEntity(@PathVariable("id")long id, @RequestBody PersonEntity newPerson) {
		return service.modifyPersonEntity(id, newPerson);
	}

	@DeleteMapping("/{id}")
	public PersonEntity deletePersonEntityWithId(@PathVariable("id")long id){
		return service.deletePersonEntityById(id);
	}
	
	@GetMapping("/type/number")
	public List<GameType> getTypeAndNumber(){
		return service.getGameTypeAndNumber();
	}
	
	@GetMapping("/type/most")
	public List<PersonEntity> getMostPlayed(){
		return service.getMaxPlayed();
	}
	
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException e) {
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		 // To Return 1 validation error
		//return new ResponseEntity<String>(e.getBindingResult().getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
		StringBuilder errors = new StringBuilder();
		for (FieldError error : e.getBindingResult().getFieldErrors()) {
			errors.append(error.getField() + ": "+ error.getDefaultMessage()+".\n");
		}
		return new ResponseEntity<String>(errors.toString(), HttpStatus.BAD_REQUEST);
	}
	

}
