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
public class PersonTest {

	Person p;

	Person deserialized;

	@BeforeEach
	void setUp() {
		p = new Person();
		p.setFirstName(TestData.PersonD.firstName);
		p.setLastName(TestData.PersonD.lastName);
	}

	@Test
	@Order(1)
	void testGetFirstName() {
		assertEquals(TestData.PersonD.firstName, p.getFirstName());
	}

	@Test
	@Order(2)
	void testGetLastName() {
		assertEquals(TestData.PersonD.lastName, p.getLastName());
	}

	@Test
	@Order(3)
	void testSetFirstName() {
		p.setFirstName(TestData.PersonD.firstName2);
		assertEquals(TestData.PersonD.firstName2, p.getFirstName());
	}

	@Test
	@Order(4)
	void testSetLastName() {
		p.setLastName(TestData.PersonD.lastName2);
		assertEquals(TestData.PersonD.lastName2, p.getLastName());
	}

	@Test
	@Order(5)
	void testSetNameEmpty() {
		// first / last name has similar logic, test one is sufficient
		p.setLastName("");
		assertEquals("", p.getLastName());
	}

	@Test
	@Order(6)
	void testSerialization() {
		deserialized = (Person) SerializationUtils.deserialize(SerializationUtils.serialize(p));
		assertThat(deserialized.getFirstName()).isEqualTo(p.getFirstName());
		assertThat(deserialized.getLastName()).isEqualTo(p.getLastName());
	}

	@Test
	@Order(7)
	void testSerializationNullName() {
		// first / last name has similar logic, test one is sufficient
		p.setFirstName(null);
		deserialized = (Person) SerializationUtils.deserialize(SerializationUtils.serialize(p));
		assertThat(deserialized.getFirstName()).isEqualTo(p.getFirstName());
	}

}
