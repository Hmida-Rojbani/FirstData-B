package de.tekup.rest.data.dto;

import java.time.LocalDate;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonRequest {
	
	@NotBlank(message = "Person name must contain Characters")
	@Size(min = 8, max=50)
	private String name;
	
	@Past
	private LocalDate dateOfBirth;
	@Valid
	private AddressRequest addressReq;
}
