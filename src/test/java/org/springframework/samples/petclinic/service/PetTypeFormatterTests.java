/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.persistence.PetRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * Test class for {@link PetTypeFormatter}
 *
 * @author Colin But
 */
@ExtendWith(MockitoExtension.class)
class PetTypeFormatterTests {

	@Mock
	private static PetRepository pets; // mocks the "pets" repository

	public static PetTypeFormatter petTypeFormatter; // use a real instance of
														// petTypeFormatter

	@BeforeEach
	void setup() {
		PetTypeFormatterTests.petTypeFormatter = new PetTypeFormatter(pets);
	}

	@Nested
	class testPrint {

		private PetType petType;

		@BeforeEach
		void testPrintSetUp() {
			petType = new PetType();
			petType.setName("Hamster");
		}

		@Test
		void testPrintSuccess() {
			String petTypeName = PetTypeFormatterTests.petTypeFormatter.print(petType, Locale.ENGLISH);
			assertThat(petTypeName).isEqualTo("Hamster");
		}

		@Test
		void testPrintNullPetType() {
			petType.setName(null);
			String petTypeName = PetTypeFormatterTests.petTypeFormatter.print(petType, Locale.ENGLISH);
			assertThat(petTypeName).isEqualTo(null);
		}

		@Test
		void testPrintEmptyPetType() {
			petType.setName("");
			String petTypeName = PetTypeFormatterTests.petTypeFormatter.print(petType, Locale.ENGLISH);
			assertThat(petTypeName).isEqualTo("");
		}

		@Test
		void testPrintNullLocale() {

			String petTypeName = PetTypeFormatterTests.petTypeFormatter.print(petType, null);
			assertThat(petTypeName).isEqualTo("Hamster");
		}

	}

	@Nested
	class testParse {

		@Test
		void shouldParse() throws ParseException {
			given(PetTypeFormatterTests.pets.findPetTypes()).willReturn(makePetTypes());
			PetType petType = PetTypeFormatterTests.petTypeFormatter.parse("Bird", Locale.ENGLISH);
			assertThat(petType.getName()).isEqualTo("Bird");
		}

		/**
		 * There are 6 petTypes in the database cat, dog, lizard, snake, bird, hamster,
		 * mocker "pets" mocks the existence of these types using makePetTypes() method.
		 * This class already provides enough statement/flow coverage, additional test
		 * cases are trivial.
		 * @author bokunzhao
		 */

		@Test
		void shouldThrowParseExceptionNull() {
			given(PetTypeFormatterTests.pets.findPetTypes()).willReturn(makePetTypes());
			Assertions.assertThrows(ParseException.class, () -> {
				petTypeFormatter.parse(null, Locale.ENGLISH);
			});
		}

		@ParameterizedTest
		@ValueSource(strings = { "", "Fish", "dog" })
		void shouldThrowParseExceptionNull(String typeName) {
			given(PetTypeFormatterTests.pets.findPetTypes()).willReturn(makePetTypes());
			Assertions.assertThrows(ParseException.class, () -> {
				petTypeFormatter.parse(typeName, Locale.ENGLISH);
			});
		}

		/**
		 * Helper method to produce some sample pet types just for test purpose
		 * @return {@link Collection} of {@link PetType}
		 */
		private List<PetType> makePetTypes() {
			List<PetType> petTypes = new ArrayList<>();
			petTypes.add(new PetType() {
				{
					setName("Dog");
				}
			});
			petTypes.add(new PetType() {
				{
					setName("Bird");
				}
			});
			return petTypes;
		}

	}

}
