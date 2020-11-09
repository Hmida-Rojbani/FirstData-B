package de.tekup.rest.data.endpoints;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.tekup.rest.data.models.TelephoneNumberEntity;
import de.tekup.rest.data.repositories.TelephoneNumberRepository;

@RestController
@RequestMapping("/api/phones")
public class PhoneRestTest {
		@Autowired
		private TelephoneNumberRepository repos;
		
		@GetMapping("/{operator}")
		public List<TelephoneNumberEntity> getByOpt(@PathVariable("operator") String operator){
			return repos.getByOperator(operator);
		}
}
