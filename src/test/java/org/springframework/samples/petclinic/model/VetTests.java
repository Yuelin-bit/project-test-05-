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
package org.springframework.samples.petclinic.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.util.SerializationUtils;
import unit.VetModelTestData;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Dave Syer
 */
class VetTests {

	@Test
	void testSerialization() {
		Vet vet = new Vet();
		vet.setFirstName("Zaphod");
		vet.setLastName("Beeblebrox");
		vet.setId(123);
		Vet other = (Vet) SerializationUtils.deserialize(SerializationUtils.serialize(vet));
		assertThat(other.getFirstName()).isEqualTo(vet.getFirstName());
		assertThat(other.getLastName()).isEqualTo(vet.getLastName());
		assertThat(other.getId()).isEqualTo(vet.getId());
	}

	@Test
	void createVet() {
		Vet vet = new Vet();
		vet.setFirstName(VetModelTestData.vetFirstName1);
		vet.setLastName(VetModelTestData.vetLastName1);
		vet.setId(VetModelTestData.id1);
		assertEquals(VetModelTestData.vetFirstName1, vet.getFirstName());
		assertEquals(VetModelTestData.vetLastName1, vet.getLastName());
		assertEquals(VetModelTestData.id1, vet.getId());
		assertEquals(0, vet.getSpecialties().size());
		// This feels pretty useless...testing the model is a horrible idea
		// It's like testing the language and not the actual code
		// Done for the sake of test coverage - Bokun Zhao
	}

}
