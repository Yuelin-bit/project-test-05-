package org.springframework.samples.petclinic.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.persistence.VisitRepository;

/**
 * Class to perform integration testing between Visit repository and the persistence layer
 *
 * @author joycetan
 *
 */
@SpringBootTest
public class VisitIntegrationTests {

	@Autowired
	private VisitRepository visitRepository;

	@Test
	void testFindByPetId() {
		// Create visits
		int petId = 2;
		Visit visit1 = Utils.createVisit(LocalDate.of(2020, Month.DECEMBER, 1), "Description1", petId);
		Visit visit2 = Utils.createVisit(LocalDate.of(2020, Month.DECEMBER, 7), "Description2", petId);
		visitRepository.save(visit1);
		visitRepository.save(visit2);

		// Retrieve list of visits for given pet
		List<Visit> visits = visitRepository.findByPetId(petId);
		assertNotNull(visits);
		assertEquals(2, visits.size());
	}

	@Test
	void testFindByNonValidId() {
		int nonValidPetId = -1;
		List<Visit> visits = visitRepository.findByPetId(nonValidPetId);
		assertNotNull(visits);
		assertTrue(visits.isEmpty());
	}

	@Test
	void testUpdateVisit() throws Exception {
		// Create Visit
		LocalDate date = LocalDate.of(2021, Month.DECEMBER, 5);
		int petId = 1;
		String oldDescription = "Old description", newDescription = "New description";
		Visit oldVisit = Utils.createVisit(date, oldDescription, petId);
		visitRepository.save(oldVisit);

		// Update visit
		Visit newVisit = oldVisit;
		newVisit.setDescription(newDescription);
		visitRepository.save(newVisit);

		// Retrieve visit object and check if updated
		List<Visit> visits = visitRepository.findByPetId(petId);
		assertEquals(1, visits.size());
		Visit visit = visits.get(0);

		assertEquals(newVisit.getDescription(), visit.getDescription());
		assertEquals(newVisit.getDate(), visit.getDate());
		assertEquals(newVisit.getPetId(), visit.getPetId());
	}

}
