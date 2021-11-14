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
public class BaseEntityTest {

	BaseEntity be;

	BaseEntity deserialized;

	@BeforeEach
	void setUp() {
		be = new BaseEntity();
		be.setId(TestData.BaseEntityD.id);
	}

	@Test
	@Order(1)
	void testGetId() {
		assertEquals(TestData.BaseEntityD.id, be.getId());
	}

	@Test
	@Order(2)
	void testSetId() {
		be.setId(TestData.BaseEntityD.id2);
		assertEquals(TestData.BaseEntityD.id2, be.getId());
	}

	@Test
	@Order(3)
	void testSetIdBoundaryValue() {
		be.setId(Integer.MAX_VALUE);
		assertEquals(Integer.MAX_VALUE, be.getId());
		be.setId(Integer.MIN_VALUE);
		assertEquals(Integer.MIN_VALUE, be.getId());
	}

	@Test
	@Order(4)
	void testSerialization() {
		deserialized = (BaseEntity) SerializationUtils.deserialize(SerializationUtils.serialize(be));
		assertThat(deserialized.getId()).isEqualTo(be.getId());
	}

	@Test
	@Order(5)
	void testSerializationNullID() {
		be.setId(null);
		deserialized = (BaseEntity) SerializationUtils.deserialize(SerializationUtils.serialize(be));
		assertThat(deserialized.getId()).isEqualTo(be.getId());
	}

	@Test
	@Order(6)
	void testIsNewTrue() {
		be.setId(null);
		assertTrue(be.isNew());
	}

	@Test
	@Order(7)
	void testIsNewFalse() {
		assertTrue(!be.isNew());
	}

	@Test // Sanity check
	@Order(8)
	void testOnVoidObject() {
		be = null;
		assertThrows(NullPointerException.class, () -> {
			be.getId();
		});
	}

}
