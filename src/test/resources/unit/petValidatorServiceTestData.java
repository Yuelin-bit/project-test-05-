package unit;
import java.time.LocalDate;

import org.springframework.samples.petclinic.model.*;

public class petValidatorServiceTestData {
	public static final String petName = "Gumball Watterson";
	public static final Integer id = 1;
	public static final LocalDate birthday = LocalDate.of(2021, 12, 19);
	
	public static PetType createCatType() {
		PetType kind = new PetType();
		kind.setName("cat");
		return kind;
	}

}
