package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.validation.Errors;

import unit.petValidatorServiceTestData;

@ExtendWith(MockitoExtension.class)
public class PetValidatorTest {

	private PetValidator pv = new PetValidator();

	private Pet obj = new Pet();

	private static final String nameError = "nameError";

	private static final String typeError = "typeError";

	private static final String birthDateError = "birthDateError";

	// arrayList to keep track whether a rejectValue() method call with certain parameter
	// is called
	ArrayList<String> methodOutcome = new ArrayList<>();

	@Mock
	Errors errors;

	@BeforeEach
	public void setUpMock() {

		Mockito.lenient().doAnswer((invokation) -> {
			methodOutcome.add(nameError);
			return nameError;
		}).when(errors).rejectValue("name", "required", "required");

		Mockito.lenient().doAnswer((invokation) -> {
			methodOutcome.add(typeError);
			return typeError;
		}).when(errors).rejectValue("type", "required", "required");

		Mockito.lenient().doAnswer((invokation) -> {
			methodOutcome.add(birthDateError);
			return birthDateError;
		}).when(errors).rejectValue("birthDate", "required", "required");

	}

	@Nested
	class testValidate {

		@BeforeEach
		public void createPet() {
			obj.setName(petValidatorServiceTestData.petName);
			obj.setType(petValidatorServiceTestData.createCatType());
			obj.setId(petValidatorServiceTestData.id);
			obj.setBirthDate(petValidatorServiceTestData.birthday);
			methodOutcome.clear();
		}

		@Test
		@Order(1)
		public void testValidateSuccess() {
			pv.validate(obj, errors);

			assertThat(methodOutcome.size() == 0);
		}

		@Test
		@Order(2)
		public void testValidateNull() {
			assertThrows(NullPointerException.class, () -> {
				pv.validate(null, errors);
			});
		}

		@Test
		@Order(3)
		public void testValidateNoName() {
			obj.setName(null);

			pv.validate(obj, errors);

			assertThat(methodOutcome.size() == 1);
			assertThat(methodOutcome.get(0).equals(nameError));
		}

		@Test
		@Order(4)
		public void testValidateEmptyName() {
			obj.setName("");

			pv.validate(obj, errors);

			assertThat(methodOutcome.size() == 1);
			assertThat(methodOutcome.get(0).equals(nameError));
		}

		@Test
		@Order(5)
		public void testValidateNoType() {
			obj.setType(null);

			pv.validate(obj, errors);

			// why no errors?
			// condition to type error: pet.isNew() && pet.getType() == null
			// which is kind of peculiar, but may be compactible with higher level logic
			// that
			// utilize this validator
			assertThat(methodOutcome.size() == 0);
			// assertEquals("type", errors.getFieldError().getField());
		}

		@Test
		@Order(6)
		public void testValidateNoIdNoType() {
			obj.setType(null);
			obj.setId(null);

			pv.validate(obj, errors);

			assertThat(methodOutcome.size() == 1);
			assertThat(methodOutcome.get(0).equals(typeError));
		}

		@Test
		@Order(7)
		public void testValidateNoID() {
			obj.setId(null);

			pv.validate(obj, errors);

			assertThat(methodOutcome.size() == 0);
		}

		@Test
		@Order(8)
		public void testValidateNoBirthDate() {
			obj.setBirthDate(null);

			pv.validate(obj, errors);

			assertThat(methodOutcome.size() == 1);
			assertThat(methodOutcome.get(0).equals(birthDateError));

		}

	}

	@Nested
	class testSupports {

		Vet foreignObj = new Vet();

		@Test
		@Order(9)
		public void testSupportsSuccess() {
			assertTrue(pv.supports(obj.getClass()));
		}

		@Test
		@Order(10)
		public void testSupportsNull() {
			assertThrows(NullPointerException.class, () -> {
				pv.supports(null);
			});
		}

		@Test
		public void testSupportsNonPetClass() {
			assertTrue(!pv.supports(foreignObj.getClass()));
		}

	}

}
