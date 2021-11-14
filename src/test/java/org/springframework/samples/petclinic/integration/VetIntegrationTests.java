package org.springframework.samples.petclinic.integration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.persistence.VetRepository;

/**
 * Class for integration testing of the Vet repository with the persistence layer.
 *
 * @author joycetan
 *
 */

@SpringBootTest
public class VetIntegrationTests {

	@Autowired
	private VetRepository vetRepository;

	@Test
	void testFindAll() throws Exception {
		Collection<Vet> vets = vetRepository.findAll();
		assertNotNull(vets);
		assertTrue(vets.size() > 0);
	}

}
