package de.tekup.rest.data.models;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.modelmapper.ModelMapper;

import de.tekup.rest.data.dto.AddressDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "Person")
@ToString(exclude = {"address","phones","games"})
@EqualsAndHashCode(exclude = "address")
public class PersonEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "personName", length = 50, unique = true, nullable = false)
	private String name;
	private LocalDate dateOfBirth;
	
	@OneToOne(cascade = CascadeType.REMOVE)
	private AddressEntity address;
	
	@OneToMany(mappedBy = "person", cascade = CascadeType.REMOVE)
	private List<TelephoneNumberEntity> phones;
	
	@ManyToMany(mappedBy = "persons", cascade = CascadeType.REMOVE)
	private List<GamesEntity> games;
	
	public AddressDTO getAddressDTO() {
		ModelMapper mapper = new ModelMapper();
		return mapper.map(address,AddressDTO.class);
	}


}
