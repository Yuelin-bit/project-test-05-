package unit;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.model.PetType;


public final class TestData {

	// TODO: Planning to group ALL unit test data into one composite class.
	/*
	 * ID categorization:
	 * 0-49: pets
	 * 50-99: owners
	 * 100-110: pet types
	 * 200-: visits
	 */

	public static class BaseEntityD {
		public static final Integer id = 123;
		public static final Integer id2 = 0;
	}

	public static class NamedEntityD {
		public static final String name = "item";
		public static final String name2 = "other";
	}

	public static class PersonD {
		public static final String firstName = "Bokun";
		public static final String lastName = "Zhao";
		public static final String firstName2 = "Sam";
		public static final String lastName2 = "Nguyen";
	}

	public static class VisitD {
		public static final LocalDate independence = LocalDate.of(1776, 7, 4);
		public static final LocalDate rememberance = LocalDate.of(1931, 11, 11);
		public static final String description = "dental check";
		public static final String description2 = "vaccination";
		public static final Integer petId = 10;
		public static final Integer petId2 = 20;
	}

	public static class PetD {
		public static final String petName = "Gumball T. Watterson";
		public static final String petName2 = "H. Brian Griffin";
		public static final LocalDate birthday = LocalDate.of(2011, 5, 3);
		public static final LocalDate birthday2 = LocalDate.of(1998, 12, 20);
		public static final PetType cat = new PetType() {
			{
				setId(101);
				setName("cat");
			}
		};
		public static final PetType dog = new PetType() {
			{
				setId(102);
				setName("dog");
			}
		};
		public static final Owner Bartok = new Owner() {
			{
				setId(51);
				setFirstName("Bela");
				setLastName("Bartok");
				setAddress("Banat");
				setCity("Sannicolau Mare");
				setTelephone("224-323-4985");
			}
		};
		public static final Owner Liszt = new Owner() {
			{
				setId(52);
				setFirstName("Franz");
				setLastName("Liszt");
				setAddress("Raiding");
				setCity("Sopron");
				setTelephone("432-307-6347");
			}
		};
		public static final Visit Tick = new Visit() {
			{
				setId(201);
				setDate(LocalDate.of(2015, 8, 1));
				setDescription("Tick Removal");
				setPetId(13);
			}
		};

		public static final Visit Vaccine = new Visit() {
			{
				setId(204);
				setDate(LocalDate.of(2012, 3, 12));
				setDescription("Vaccination");
				setPetId(14);
			}
		};
		
		public static final Visit Dental = new Visit() {
			{
				setId(207);
				setDate(LocalDate.of(2013, 10, 9));
				setDescription("Dental Check");
				setPetId(15);
			}
		};

		// the sort in Pet class is descending in date
		// construct Set so that the effect of sort is manifestable
		public static final LinkedHashSet<Visit> visits =
				new LinkedHashSet<Visit>(
						Set.of(TestData.PetD.Vaccine, TestData.PetD.Dental 
								));
		public static final LinkedHashSet<Visit> visits2 =
				new LinkedHashSet<Visit>(
						Set.of(TestData.PetD.Vaccine
								));

	}
	public static class PetTypeFormatter {

	}
}
