package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.util.SerializationUtils;

import unit.TestData;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VisitTest {

	Visit v;

	Visit deserialized;

	@BeforeEach
	void setUp() {
		v = new Visit();
		v.setDate(TestData.VisitD.independence);
		v.setDescription(TestData.VisitD.description);
		v.setPetId(TestData.VisitD.petId);
	}

	@Test
	@Order(1)
	void testGetDate() {
		assertEquals(TestData.VisitD.independence, v.getDate());
	}

	@Test
	@Order(2)
	void testGetDescription() {
		assertEquals(TestData.VisitD.description, v.getDescription());
	}

	@Test
	@Order(3)
	void testGetPetId() {
		assertEquals(TestData.VisitD.petId, v.getPetId());
	}

	@Test
	@Order(4)
	void testSetDate() {
		v.setDate(TestData.VisitD.rememberance);
		assertEquals(TestData.VisitD.rememberance, v.getDate());
	}

	@Test
	@Order(5)
	void testSetDateNull() {
		v.setDate(null);
		assertEquals(null, v.getDate());
	}

	@Test
	@Order(6)
	void testSetDescription() {
		v.setDescription(TestData.VisitD.description2);
		assertEquals(TestData.VisitD.description2, v.getDescription());
	}

	@Test
	@Order(7)
	void testSetDescriptionNull() {
		v.setDescription(null);
		assertEquals(null, v.getDescription());
	}

	@Test
	@Order(8)
	void testSetDescriptionEmpty() {
		v.setDescription("");
		assertEquals("", v.getDescription());
	}

	@Test
	@Order(9)
	void testSetPetId() {
		v.setPetId(TestData.VisitD.petId2);
		assertEquals(TestData.VisitD.petId2, v.getPetId());
	}

	@Test
	@Order(10)
	void testSetPetIdNull() {
		v.setPetId(null);
		assertEquals(null, v.getPetId());
	}

	@Test
	@Order(11)
	void testSetPetIdBoundaryValue() {
		// first / last name has similar logic, test one is sufficient
		v.setPetId(Integer.MAX_VALUE);
		assertEquals(Integer.MAX_VALUE, v.getPetId());
		v.setPetId(Integer.MIN_VALUE);
		assertEquals(Integer.MIN_VALUE, v.getPetId());
	}

	@Test
	@Order(12)
	void testSerialization() {
		deserialized = (Visit) SerializationUtils.deserialize(SerializationUtils.serialize(v));
		assertThat(deserialized.getDate()).isEqualTo(v.getDate());
		assertThat(deserialized.getDescription()).isEqualTo(v.getDescription());
		assertThat(deserialized.getPetId()).isEqualTo(v.getPetId());
	}

	@Test
	@Order(13)
	void testSerializationNullName() {
		v.setDate(null);
		deserialized = (Visit) SerializationUtils.deserialize(SerializationUtils.serialize(v));
		assertThat(deserialized.getDate()).isEqualTo(v.getDate());
		assertThat(deserialized.getDescription()).isEqualTo(v.getDescription());
		assertThat(deserialized.getPetId()).isEqualTo(v.getPetId());
	}

	@Test
	@Order(14)
	void testSerializationNullDescription() {
		v.setDescription(null);
		deserialized = (Visit) SerializationUtils.deserialize(SerializationUtils.serialize(v));
		assertThat(deserialized.getDate()).isEqualTo(v.getDate());
		assertThat(deserialized.getDescription()).isEqualTo(v.getDescription());
		assertThat(deserialized.getPetId()).isEqualTo(v.getPetId());
	}

	@Test
	@Order(15)
	void testSerializationNullPetId() {
		v.setPetId(null);
		deserialized = (Visit) SerializationUtils.deserialize(SerializationUtils.serialize(v));
		assertThat(deserialized.getDate()).isEqualTo(v.getDate());
		assertThat(deserialized.getDescription()).isEqualTo(v.getDescription());
		assertThat(deserialized.getPetId()).isEqualTo(v.getPetId());
	}

}
