package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.util.SerializationUtils;

import unit.TestData;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NamedEntityTest {

	NamedEntity ne;

	NamedEntity deserialized;

	@BeforeEach
	void setUp() {
		ne = new NamedEntity();
		ne.setName(TestData.NamedEntityD.name);
	}

	@Test
	@Order(1)
	void testGetName() {
		assertEquals(TestData.NamedEntityD.name, ne.getName());
	}

	@Test
	@Order(2)
	void testSetName() {
		ne.setName(TestData.NamedEntityD.name2);
		assertEquals(TestData.NamedEntityD.name2, ne.getName());
	}

	@Test
	@Order(3)
	void testSetNameEmpty() {
		ne.setName("");
		assertEquals("", ne.getName());
	}

	@Test
	@Order(4)
	void testSerialization() {
		deserialized = (NamedEntity) SerializationUtils.deserialize(SerializationUtils.serialize(ne));
		assertThat(deserialized.getName()).isEqualTo(ne.getName());
	}

	@Test
	@Order(5)
	void testSerializationNullName() {
		ne.setName(null);
		deserialized = (NamedEntity) SerializationUtils.deserialize(SerializationUtils.serialize(ne));
		assertThat(deserialized.getName()).isEqualTo(ne.getName());
	}

	@Test
	@Order(6)
	void testToString() {
		assertEquals(ne.toString(), TestData.NamedEntityD.name);
		// mini-integration here: toString() & getName()
	}

	@Test
	@Order(7)
	void testToStringNull() {
		ne.setName(null);
		assertEquals(ne.toString(), null);
	}

}
