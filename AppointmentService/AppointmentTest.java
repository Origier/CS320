package Milestone1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.ThreadLocalRandom;

@DisplayName("Appointment Testing Class")
class AppointmentTest {

	// Test Ids
	private String[] goodTestIds = new String[]{"12345", "678910"};
	private String id11Char = "12345678910";
	private String id10Char = "1234567890";
	
	// Test dates - all set dynamically to ensure the tests will always work as intended
	private GregorianCalendar[] goodTestDates = new GregorianCalendar[] {new GregorianCalendar(Calendar.YEAR + 5, 1, 1), new GregorianCalendar(Calendar.YEAR + 10, 1, 1)};
	private GregorianCalendar testDateOneDayAfter = new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH + 1);
	private GregorianCalendar testDateOneDayBefore = new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH - 1);
	
	// Test descriptions
	private String[] goodTestDescriptions = new String[] {"Description here", "Description is also here"};
	private String description50Char = "ABCDEFGHIJKLMNOPQRSTABCDEFGHIJKLMNOPQRSTabcdefghij";
	private String description51Char = "ABCDEFGHIJKLMNOPQRSTABCDEFGHIJKLMNOPQRSTabcdefghij1";
	
	// Placeholder variables
	private String randomGoodId;
	private GregorianCalendar randomGoodDate;
	private String randomGoodDesc;
	
	/*
	 * Test Setups and clean ups
	 */
	@BeforeEach
	void setupTest() {
		// Choosing a random valid values for the appointment
		randomGoodId = goodTestIds[ThreadLocalRandom.current().nextInt(0, goodTestIds.length)];
		randomGoodDate = goodTestDates[ThreadLocalRandom.current().nextInt(0, goodTestDates.length)];
		randomGoodDesc = goodTestDescriptions[ThreadLocalRandom.current().nextInt(0, goodTestDescriptions.length)];
	}
	
	// Clean up each test by resetting the testAppointment to null
	@AfterEach
	void cleanupTest() {
		Appointment.clearAllIds();
	}
	
	
	
	
	/* 
	 * Test that the ID is required, must be unique and cannot be longer than 10 characters, the ID cannot be NULL and must not be a mutable attribute 
	 */
	
	// Testing that the ID must be unique (does not match the test appointment)
	@Test
	@DisplayName("Testing that each appointment must have a unique ID")
	void testMustBeUniqueId() {
		Appointment testAppointment = new Appointment(randomGoodId, randomGoodDate, randomGoodDesc); 
		assertThrows(IllegalArgumentException.class, () -> {new Appointment(randomGoodId, randomGoodDate, randomGoodDesc);});		
	}
	
	// Test that a random good ID will be instantiated fine
	@Test
	@DisplayName("Testing that a unique ID will instantiate fine")
	void testInstantiateUniqueId() {
		assertDoesNotThrow(() -> {
			Appointment appointment2 = new Appointment(randomGoodId, randomGoodDate, randomGoodDesc);
			
			// Additionally asserting that the instance variables all set correctly
			if (appointment2.getId() != randomGoodId) {
				throw new RuntimeException("The id did not set correctly");
			} else if(appointment2.getDate() != randomGoodDate) {
				throw new RuntimeException("The appointment date did not set correctly");
			} else if(appointment2.getDescription() != randomGoodDesc) {
				throw new RuntimeException("The description did not set correctly");
			}
		});
	}
	
	// Testing that the ID cannot be longer than 10 characters
	@Test
	@DisplayName("Testing that the ID cannot be longer than 10 characters")
	void testIdLengthBad() {
		assertThrows(IllegalArgumentException.class, () -> {new Appointment(id11Char, randomGoodDate, randomGoodDesc);});	
	}
	
	// Testing that the ID can be 10 characters
	@Test
	@DisplayName("Testing that the ID can be 10 characters")
	void testIdLengthGood() {
		assertDoesNotThrow(() -> {
			Appointment appointment2 = new Appointment(id10Char, randomGoodDate, randomGoodDesc);
			
			// Additionally asserting that the instance variables all set correctly
			if (appointment2.getId() != id10Char) {
				throw new RuntimeException("The id did not set correctly");
			} else if(appointment2.getDate() != randomGoodDate) {
				throw new RuntimeException("The appointment date did not set correctly");
			} else if(appointment2.getDescription() != randomGoodDesc) {
				throw new RuntimeException("The description did not set correctly");
			}
		});
	}
	
	// Testing that the ID cannot be null
	@Test
	@DisplayName("Testing that the ID cannot be null")
	void testIdCannotBeNull() {
		assertThrows(IllegalArgumentException.class, () -> {new Appointment(null, randomGoodDate, randomGoodDesc);});
	}
	
	
	
	
	/*
	 *  Test that the date field is required and cannot be in the past
	 */
	
	// Testing that the date field can be in the future
	@Test
	@DisplayName("Testing that the date field can be in the future")
	void testDateGood() {
		assertDoesNotThrow(() -> {
			Appointment appointment2 = new Appointment(randomGoodId, randomGoodDate, randomGoodDesc);
			
			// Additionally asserting that the instance variables all set correctly
			if (appointment2.getId() != randomGoodId) {
				throw new RuntimeException("The id did not set correctly");
			} else if(appointment2.getDate() != randomGoodDate) {
				throw new RuntimeException("The appointment date did not set correctly");
			} else if(appointment2.getDescription() != randomGoodDesc) {
				throw new RuntimeException("The description did not set correctly");
			}
		});
	}
	
	// Testing that the date field cannot be in the past - even by one day
	@Test
	@DisplayName("Testing that the date field cannot be in the past - even by one day")
	void testDateBad() {
		assertThrows(IllegalArgumentException.class, () -> {new Appointment(randomGoodId, testDateOneDayBefore, randomGoodDesc);});
	}
	
	// Testing that the date cannot be null
	@Test
	@DisplayName("Testing that the date cannot be null")
	void testDateCannotBeNull() {
		assertThrows(IllegalArgumentException.class, () -> {new Appointment(randomGoodId, null, randomGoodDesc);});
	}
	
	// Testing that the date can be set on the next day
	@Test
	@DisplayName("Testing that the date can be set on the next day")
	void testDateNextDay() {
		assertDoesNotThrow(() -> {
			Appointment appointment2 = new Appointment(randomGoodId, testDateOneDayAfter, randomGoodDesc);
			
			// Additionally asserting that the instance variables all set correctly
			if (appointment2.getId() != randomGoodId) {
				throw new RuntimeException("The id did not set correctly");
			} else if(appointment2.getDate() != testDateOneDayAfter) {
				throw new RuntimeException("The appointment date did not set correctly");
			} else if(appointment2.getDescription() != randomGoodDesc) {
				throw new RuntimeException("The description did not set correctly");
			}
		});
	}
	
	
	/*
	 *  Test that the description field is required, cannot be longer than 50 characters and cannot be NULL
	 */
	// Testing that the description field can be 50 characters
	@Test
	@DisplayName("Testing that the description field can be 50 characters")
	void testDescriptionLengthGood() {
		assertDoesNotThrow(() -> {
			Appointment appointment2 = new Appointment(randomGoodId, randomGoodDate, description50Char);
			
			// Additionally asserting that the instance variables all set correctly
			if (appointment2.getId() != randomGoodId) {
				throw new RuntimeException("The id did not set correctly");
			} else if(appointment2.getDate() != randomGoodDate) {
				throw new RuntimeException("The appointment date did not set correctly");
			} else if(appointment2.getDescription() != description50Char) {
				throw new RuntimeException("The description did not set correctly");
			}
		});
	}
	
	// Testing that the description field cannot be more than 50 characters
	@Test
	@DisplayName("Testing that the description field cannot be more than 50 characters")
	void testDescriptionLengthBad() {
		assertThrows(IllegalArgumentException.class, () -> {new Appointment(randomGoodId, randomGoodDate, description51Char);});
	}
	
	// Testing that the description cannot be null
	@Test
	@DisplayName("Testing that the description cannot be null")
	void testDescriptionCannotBeNull() {
		assertThrows(IllegalArgumentException.class, () -> {new Appointment(randomGoodId, randomGoodDate, null);});
	}
}
