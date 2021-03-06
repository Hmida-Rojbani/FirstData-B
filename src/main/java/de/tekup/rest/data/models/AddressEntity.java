package de.tekup.rest.data.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.tekup.rest.data.dto.AddressRequest;
import lombok.Data;

@Entity
@Data
public class AddressEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private int number;
	private String street;
	private String city;
	
	
	
	@OneToOne(mappedBy = "address")
	@JsonIgnore
	private PersonEntity person;
	
}
