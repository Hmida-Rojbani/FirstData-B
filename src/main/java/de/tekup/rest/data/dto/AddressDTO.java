package de.tekup.rest.data.dto;

import lombok.Data;

@Data
public class AddressDTO {
	
	private int id;
	
	private int number;
	private String street;
	private String city;

}
