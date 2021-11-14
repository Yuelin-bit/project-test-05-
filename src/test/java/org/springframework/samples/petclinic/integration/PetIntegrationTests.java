package org.springframework.samples.petclinic.integration;

/**
 * Class for integration testing of the Pet repository with the
 * persistence layer.
 *
 * @author joycetan
 */

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.persistence.OwnerRepository;
import org.springframework.samples.petclinic.persistence.PetRepository;

@SpringBootTest
public class PetIntegrationTests {

	@Autowired
	private PetRepository petRepository;

	@Autowired
	private OwnerRepository ownerRepository;

	@Test
	void testFindPetTypes() {
		List<PetType> listPetTypes = petRepository.findPetTypes();

		assertNotNull(listPetTypes);
		assertTrue(listPetTypes.size() > 0);
	}

	@Test
	void testFindById() {
		// Create an owner
		Owner owner = Utils.createOwner("Dummy", "Dummy1", "rue Ipsum", "City", "2957560", null);
		ownerRepository.save(owner);

		// Retrieve a random pet type
		PetType type = petRepository.findPetTypes().get(0);

		// Create a pet
		LocalDate birthDate = LocalDate.of(2020, Month.APRIL, 12);
		int valid_id = 12;
		String name = "Money";
		Pet pet = Utils.createPet(name, birthDate, type, valid_id, owner);
		petRepository.save(pet);

		Pet found_pet = petRepository.findById(valid_id);
		assertNotNull(found_pet);
		assertEquals(pet.getId(), found_pet.getId());
		assertEquals(pet.getName(), found_pet.getName());
	}

	@Test
	void testFindByNonExistentId() {
		int invalid_id = -1;

		Pet pet = petRepository.findById(invalid_id);
		assertNull(pet);
	}

	@Test
	void testUpdatePet() {
		// Create an owner
		Owner owner = Utils.createOwner("Dummy", "Dummy1", "rue Ipsum", "City", "2957560", null);
		ownerRepository.save(owner);

		// Retrieve a random pet type
		PetType type = petRepository.findPetTypes().get(0);

		// Create a pet
		LocalDate birthDate = LocalDate.of(2020, Month.APRIL, 13);
		int id = 14;
		String oldName = "Money", newName = "Coco";
		Pet pet = Utils.createPet(oldName, birthDate, type, id, owner);
		petRepository.save(pet);

		// Update pet and save it
		Pet updated_pet = pet;
		updated_pet.setName(newName);
		petRepository.save(updated_pet);

		Pet retrieved_pet = petRepository.findById(id);
		assertNotNull(retrieved_pet);
		assertEquals(newName, retrieved_pet.getName());
		assertEquals(birthDate, retrieved_pet.getBirthDate());
		assertEquals(id, retrieved_pet.getId());

	}

}
