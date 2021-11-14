package org.springframework.samples.petclinic.integration;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Visit;

/**
 * Class to hold util methods used in integration testing
 *
 * @author joycetan
 *
 */
public class Utils {

	public static Owner createOwner(String fName, String lName, String address, String city, String phone,
			Set<Pet> pets) {
		Owner newOwner = new Owner();
		newOwner.setFirstName(fName);
		newOwner.setLastName(lName);
		newOwner.setAddress(address);
		newOwner.setCity(city);
		newOwner.setTelephone(phone);
		newOwner.setPetsInternal(pets);
		return newOwner;
	}

	public static Pet createPet(String name, LocalDate birthDate, PetType type, int id, Owner owner) {
		Pet newPet = new Pet();
		newPet.setName(name);
		newPet.setBirthDate(birthDate);
		newPet.setType(type);
		newPet.setOwner(owner);
		newPet.setId(id);
		return newPet;
	}

	public static Visit createVisit(LocalDate date, String description, int petId) {
		Visit newVisit = new Visit();
		newVisit.setDate(date);
		newVisit.setDescription(description);
		newVisit.setPetId(petId);
		return newVisit;
	}

}
