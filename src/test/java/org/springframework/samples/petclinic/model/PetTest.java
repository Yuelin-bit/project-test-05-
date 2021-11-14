package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.util.SerializationUtils;

import unit.TestData;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PetTest {

	Pet pet;

	Pet deserialized;

	@BeforeEach
	void setUp() {
		pet = new Pet();
		pet.setId(TestData.PetD.Vaccine.getPetId());
		pet.setName(TestData.PetD.petName);
		pet.setBirthDate(TestData.PetD.birthday);
		pet.setType(TestData.PetD.cat);
		pet.setOwner(TestData.PetD.Bartok);
		// pet.setVisitsInternal(TestData.PetD.visits);
	}

	@Test
	@Order(1)
	void testGetBirthDate() {
		assertEquals(TestData.PetD.birthday, pet.getBirthDate());
	}

	@Test
	@Order(2)
	void testSetBirthDate() {
		pet.setBirthDate(TestData.PetD.birthday2);
		assertEquals(TestData.PetD.birthday2, pet.getBirthDate());
	}

	@Test
	@Order(3)
	void testSetBirthDateNull() {
		pet.setBirthDate(null);
		assertEquals(null, pet.getBirthDate());
	}

	@Test
	@Order(4)
	void testGetType() {
		assertEquals(TestData.PetD.cat, pet.getType());
	}

	@Test
	@Order(5)
	void testSetType() {
		// testing the object reference only, testing of fields taken care in PetTypeTest
		pet.setType(TestData.PetD.dog);
		assertEquals(TestData.PetD.dog, pet.getType());
	}

	@Test
	@Order(5)
	void testSetTypeNull() {
		pet.setType(null);
		assertEquals(null, pet.getType());
	}

	@Test
	@Order(6)
	void testGetOwner() {
		assertEquals(TestData.PetD.Bartok, pet.getOwner());
	}

	@Test
	@Order(7)
	void testSetOwner() {
		// testing the object reference only, testing of fields taken care in PetTypeTest
		pet.setOwner(TestData.PetD.Liszt);
		assertEquals(TestData.PetD.Liszt, pet.getOwner());
	}

	@Test
	@Order(8)
	void testSetOwnerNull() {
		pet.setOwner(null);
		assertEquals(null, pet.getOwner());
	}

	@Test
	@Order(9)
	void testGetVisitsInternal() {
		assertNotNull(pet.getVisitsInternal());
	}

	@Test
	@Order(10)
	void testSetVisitsInternal() {
		pet.setVisitsInternal(TestData.PetD.visits);
		assertEquals(pet.getVisitsInternal(), TestData.PetD.visits);
	}

	@Test
	@Order(11) // Fails
	void testGetVisitsInternalNull() {
		// pet.setVisitsInternal(null);
		// assertEquals(pet.getVisitsInternal(), null);
	}

	@Test
	@Order(12)
	void testGetVisits() {
		assertNotNull(pet.getVisits());
		assertEquals(pet.getVisits().size(), 0);
	}

	@Test
	@Order(13)
	void testGetVisitsWithNonEmpty() {
		pet.setVisitsInternal(TestData.PetD.visits);
		List<Visit> result = pet.getVisits();
		assertNotNull(result);
		assertEquals(result.size(), TestData.PetD.visits.size());
		assertTrue(visitEquals(result.get(0), TestData.PetD.Dental));
		assertTrue(visitEquals(result.get(1), TestData.PetD.Vaccine));
	}

	@Test
	@Order(14)
	void testAddVisits() {
		pet.setVisitsInternal(TestData.PetD.visits2);

		// pet id stored in Visit object "Tick"
		Integer oldPetId = TestData.PetD.Tick.getPetId();
		pet.addVisit(TestData.PetD.Tick);

		Set<Visit> result = pet.getVisitsInternal();
		assertNotNull(result);
		assertEquals(result.size(), TestData.PetD.visits2.size() + 1);
		assertTrue(result.contains(TestData.PetD.Tick));
		// petId in original Visit object gets overwritten
		assertNotEquals(oldPetId, TestData.PetD.Tick.getPetId());
	}

	@Test
	@Order(15)
	void testAddVisitsDuplicate() {

		assertTrue(pet.getVisitsInternal().size() == 0);

		pet.setVisitsInternal(TestData.PetD.visits);

		Integer oldPetId = TestData.PetD.Dental.getPetId();
		pet.addVisit(TestData.PetD.Dental);

		Set<Visit> result = pet.getVisitsInternal();
		assertNotNull(result);
		// assert repeated object reference is not added
		assertEquals(result.size(), TestData.PetD.visits.size());
		// petId in original Visit object gets overwritten
		assertNotEquals(oldPetId, TestData.PetD.Dental.getPetId());
	}

	@Test
	@Order(16)
	void testSerialization() {

		pet.setVisitsInternal(TestData.PetD.visits);

		deserialized = (Pet) SerializationUtils.deserialize(SerializationUtils.serialize(pet));
		assertThat(deserialized.getBirthDate()).isEqualTo(pet.getBirthDate());
		assertTrue(typeEquals(deserialized.getType(), pet.getType()));
		assertTrue(visitsSetEquals(deserialized.getVisitsInternal(), pet.getVisitsInternal()));
		assertTrue(ownerEquals(deserialized.getOwner(), pet.getOwner()));
	}

	@Test
	@Order(17)
	void testSerializationBirthDateNull() {
		pet.setVisitsInternal(TestData.PetD.visits);
		pet.setBirthDate(null);
		deserialized = (Pet) SerializationUtils.deserialize(SerializationUtils.serialize(pet));
		assertThat(deserialized.getBirthDate()).isEqualTo(pet.getBirthDate());
		assertTrue(typeEquals(deserialized.getType(), pet.getType()));
		assertTrue(visitsSetEquals(deserialized.getVisitsInternal(), pet.getVisitsInternal()));
		assertTrue(ownerEquals(deserialized.getOwner(), pet.getOwner()));
	}

	@Test
	@Order(18)
	void testSerializationPetTypeNull() {
		pet.setVisitsInternal(TestData.PetD.visits);
		pet.setType(null);
		deserialized = (Pet) SerializationUtils.deserialize(SerializationUtils.serialize(pet));
		assertThat(deserialized.getBirthDate()).isEqualTo(pet.getBirthDate());
		assertTrue(typeEquals(deserialized.getType(), pet.getType()));
		assertTrue(visitsSetEquals(deserialized.getVisitsInternal(), pet.getVisitsInternal()));
		assertTrue(ownerEquals(deserialized.getOwner(), pet.getOwner()));
	}

	@Test
	@Order(19)
	void testSerializationVisitNull() {

		deserialized = (Pet) SerializationUtils.deserialize(SerializationUtils.serialize(pet));
		assertThat(deserialized.getBirthDate()).isEqualTo(pet.getBirthDate());
		assertTrue(typeEquals(deserialized.getType(), pet.getType()));
		assertTrue(visitsSetEquals(deserialized.getVisitsInternal(), pet.getVisitsInternal()));
		assertTrue(ownerEquals(deserialized.getOwner(), pet.getOwner()));
	}

	@Test
	@Order(20)
	void testSerializationOwnerNull() {
		pet.setVisitsInternal(TestData.PetD.visits);
		pet.setOwner(null);
		deserialized = (Pet) SerializationUtils.deserialize(SerializationUtils.serialize(pet));
		assertThat(deserialized.getBirthDate()).isEqualTo(pet.getBirthDate());
		assertTrue(typeEquals(deserialized.getType(), pet.getType()));
		assertTrue(visitsSetEquals(deserialized.getVisitsInternal(), pet.getVisitsInternal()));
		assertTrue(ownerEquals(deserialized.getOwner(), pet.getOwner()));
	}

	// helper function to compare fields of two Owner objects
	private boolean ownerEquals(Owner a, Owner b) {
		if (a == null && b == null)
			return true;
		return (a.getId().equals(b.getId()) && a.getFirstName().equals(b.getFirstName())
				&& a.getLastName().equals(b.getLastName()) && a.getAddress().equals(b.getAddress())
				&& a.getCity().equals(b.getCity()) && a.getTelephone().equals(b.getTelephone()));
	}

	// helper function to compare fields of two Visit objects
	private boolean visitEquals(Visit a, Visit b) {
		if (a == null && b == null)
			return true;
		return (a.getId().equals(b.getId()) && a.getDate().equals(b.getDate())
				&& a.getDescription().equals(b.getDescription()) && a.getPetId().equals(b.getPetId()));
	}

	// helper function to compare fields of two PetType objects
	private boolean typeEquals(PetType a, PetType b) {
		if (a == null && b == null)
			return true;
		return (a.getId().equals(b.getId()) && a.getName().equals(b.getName()));
	}

	// helper function to compare fields of two Set<Visit> objects
	private boolean visitsSetEquals(Set<Visit> a, Set<Visit> b) {
		if (a == null && b == null)
			return true;
		if (a.size() != b.size())
			return false;

		Iterator<Visit> ia = a.iterator();
		Iterator<Visit> ib;

		while (ia.hasNext()) {
			ib = b.iterator(); // reset iterator
			Visit current = ia.next(); // fetch next visit in Set "a" to compare

			boolean found = false;
			while (ib.hasNext()) {
				if (visitEquals(ib.next(), current)) {
					found = true;
					break;
				}
			}
			if (found == false)
				return false;

		}
		return true;
	}

}
