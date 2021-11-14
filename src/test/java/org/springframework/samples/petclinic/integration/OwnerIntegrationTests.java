package org.springframework.samples.petclinic.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.persistence.OwnerRepository;
import org.springframework.samples.petclinic.integration.Utils;

/**
 * Class for integration testing of the Owner repository with the persistence layer.
 *
 * @author joycetan
 */
@SpringBootTest
public class OwnerIntegrationTests {

	@Autowired
	private OwnerRepository ownerRepository;

	@Test
	void testFindByLastName() {
		String lastName = "Long";
		Owner owner_1 = Utils.createOwner("George", lastName, "123 rue Example", "Paris", "111111111", null);
		Owner owner_2 = Utils.createOwner("Alicia", lastName, "123 rue Example", "Madrid", "123456789", null);
		Owner owner_3 = Utils.createOwner("Jae Hwan", "Kim", "1234 rue Test", "Seoul", "34664357", null);
		ownerRepository.save(owner_1);
		ownerRepository.save(owner_2);
		ownerRepository.save(owner_3);

		Collection<Owner> owners = ownerRepository.findByLastName(lastName);
		assertNotNull(owners);
		assertEquals(2, owners.size());
	}

	@Test
	void testFindNonExistentOwnerByLastName() {
		String nonExistentLastName = "None";

		Collection<Owner> owners = ownerRepository.findByLastName(nonExistentLastName);
		assertNotNull(owners);
		assertTrue(owners.isEmpty());
	}

	@Test
	void testFindByEmptyLastName() {
		String emptyLastName = "";

		Collection<Owner> owners = ownerRepository.findByLastName(emptyLastName);
		assertNotNull(owners);
		assertTrue(owners.size() > 0);
	}

	@Test
	void testFindById() {
		// Find the id of an existing owner in database
		String lastName = "unique";
		Owner owner = Utils.createOwner("firstName", lastName, "999 rue Tavish", "Montreal", "098765432", null);
		ownerRepository.save(owner);
		int valid_id = ownerRepository.findByLastName(lastName).iterator().next().getId();

		Owner valid_owner = ownerRepository.findById(valid_id);
		assertNotNull(valid_owner);
		assertEquals(owner.getId(), valid_owner.getId());
		assertEquals(owner.getLastName(), valid_owner.getLastName());
	}

	@Test
	void testFindByNonExistentId() {
		int nonValidId = -1;
		Owner nonValidOwner = ownerRepository.findById(nonValidId);

		assertNull(nonValidOwner);
	}

	@Test
	void testUpdateOwner() {
		String oldLastName = "David", newLastName = "James";
		String fName = "Aaron", address = "346 rue Hibernate", city = "Boston", phone = "4874038505";
		Owner owner = Utils.createOwner(fName, oldLastName, address, city, phone, null);
		ownerRepository.save(owner);

		// Update owner
		Owner updatedOwner = owner;
		updatedOwner.setLastName(newLastName);
		ownerRepository.save(updatedOwner);

		// Retrieve owner object to check if it was updated
		Collection<Owner> retrieved_owners_old = ownerRepository.findByLastName(oldLastName);
		Collection<Owner> retrieved_owners_new = ownerRepository.findByLastName(newLastName);
		assertTrue(retrieved_owners_old.size() == 0);
		assertTrue(retrieved_owners_new.size() == 1);

		Owner retrieved_owner = retrieved_owners_new.iterator().next();

		assertNotNull(retrieved_owner);
		assertEquals(updatedOwner.getFirstName(), retrieved_owner.getFirstName());
		assertEquals(updatedOwner.getLastName(), retrieved_owner.getLastName());
		assertEquals(updatedOwner.getAddress(), retrieved_owner.getAddress());
		assertEquals(updatedOwner.getCity(), retrieved_owner.getCity());
		assertEquals(updatedOwner.getTelephone(), retrieved_owner.getTelephone());
		assertEquals(updatedOwner.getId(), retrieved_owner.getId());

	}

}
