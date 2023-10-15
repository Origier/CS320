package Milestone1;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

@DisplayName("Appointment Service Testing Class")
class AppointmentServiceTest {

	// Test variables
	private GregorianCalendar[] testDates = new GregorianCalendar[] {new GregorianCalendar(Calendar.YEAR + 5, 1, 1), new GregorianCalendar(Calendar.YEAR + 10, 1, 1)};
	
	private String[] testDescriptions = new String[] {"Wow a description", "OMG another description..."};
	
	AppointmentService testService = AppointmentService.getInstance();
	
	// placeholder variables
	private GregorianCalendar randomGoodDate;
	private String randomGoodDesc;
	
	@BeforeEach
	void setupTest() {
		// Choosing a random valid values for the appointment
		randomGoodDate = testDates[ThreadLocalRandom.current().nextInt(0, testDates.length)];
		randomGoodDesc = testDescriptions[ThreadLocalRandom.current().nextInt(0, testDescriptions.length)];
	}
	
	// Clean up each test by resetting the testService
	@AfterEach
	void cleanUpTest() {
		testService.resetAppointmentList();
	}
	
	
	
	
	/*
	 *  Test that appointment service is able to add a new appointment
	 */
	
	// Tests adding one appointment
	@Test
	@DisplayName("Tests adding one appointment")
	void testAddingAppointment() {
		assertDoesNotThrow(() -> {
			testService.createNewAppointment(randomGoodDate, randomGoodDesc);
			
			// Additionally asserting that the new appointment was created with the given instance variables
			if (testService.getAppointment("1").getDate() != randomGoodDate) {
				throw new RuntimeException("The new appointment was not created with the correct date");
			} else if (testService.getAppointment("1").getDescription() != randomGoodDesc) {
				throw new RuntimeException("The new appointment was not created with the correct description");
			}
		});
	}
	
	// Tests adding two different appointments
	@Test
	@DisplayName("Tests adding two different appointments")
	void testAddingMultipleAppointments() {
		assertDoesNotThrow(() -> {
			testService.createNewAppointment(randomGoodDate, randomGoodDesc);
			
			// Choosing a random valid values for the appointment
			randomGoodDate = testDates[ThreadLocalRandom.current().nextInt(0, testDates.length)];
			randomGoodDesc = testDescriptions[ThreadLocalRandom.current().nextInt(0, testDescriptions.length)];
			testService.createNewAppointment(randomGoodDate, randomGoodDesc);
			});
	}
	
	// Tests adding the same appointment 50 times
	@RepeatedTest(50)
	@DisplayName("Tests adding the same appointment 50 times")
	void testAddingManyAppointments() {
		assertDoesNotThrow(() -> {testService.createNewAppointment(randomGoodDate, randomGoodDesc);});
	}
	
	
	/*
	 * Test that appointment service is able to delete an appointment by id
	 */
	
	// Tests adding and deleting an appointment
	@Test
	@DisplayName("Tests adding and deleting an appointment")
	void testRemovingOnlyAppointment() {
		int startingSize = testService.getAppointmentList().size();
		testService.createNewAppointment(randomGoodDate, randomGoodDesc);
		assertDoesNotThrow(() -> {testService.deleteAppointment("1");});
		
		// Asserting that the number of appointments is now the same as before adding the contact
		assertEquals(testService.getAppointmentList().size(), startingSize);	
	}
	
	// Tests re-adding the same appointment after deleting it
	@Test
	@DisplayName("Tests re-adding the same appointment after deleting it")
	void testRemovingAndAddingOnlyAppointment() {
		assertDoesNotThrow(() -> {
			testService.createNewAppointment(randomGoodDate, randomGoodDesc);
			int sizeWithAppointment = testService.getAppointmentList().size();
			testService.deleteAppointment("1");
			testService.createNewAppointment(randomGoodDate, randomGoodDesc);
			assertEquals(testService.getAppointmentList().size(), sizeWithAppointment);
			});
	}
	
	// Tests that adding several appointments, removing one and replacing it will set it back to a previously used id
	@Test
	@DisplayName("Tests that adding several appointments, removing one and replacing it will set it back to a previously used id")
	void testRemovingAndAddingOnPreviousId() {
		// Add three appointments
		testService.createNewAppointment(randomGoodDate, randomGoodDesc);
		testService.createNewAppointment(randomGoodDate, randomGoodDesc);
		testService.createNewAppointment(randomGoodDate, randomGoodDesc);
		int currentSize = testService.getAppointmentList().size();
		
		// Delete the second appointmetn and assert the size change
		testService.deleteAppointment("2");
		assertEquals(testService.getAppointmentList().size(), currentSize - 1);
		
		// Add back the appointment - which should be on id 2 - assert the appointments id
		testService.createNewAppointment(randomGoodDate, randomGoodDesc);
		assertEquals(testService.getAppointment("2").getId(), "2");
	}
}
