package Milestone1;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

@DisplayName("Contact Service Testing Class")
class ContactServiceTest {
	// Test variables
	private String[] goodFirstNames = new String[] {"FirstName", "First"};
	
	private String[] goodLastNames = new String[] {"LastName", "Last"};
	
	private String[] goodPhoneNumbers = new String[] {"5555555555",  "6666666666"};
	
	private String[] goodAddresses = new String[] {"Address", "Another Address"};
	
	ContactService testService = ContactService.getInstance();
	
	// placeholder variables
	private String goodRandomFirstName;
	private String goodRandomLastName;
	private String goodRandomPhoneNumber;
	private String goodRandomAddress;
	
	
	@BeforeEach
	void setupTest() {
		goodRandomFirstName = goodFirstNames[ThreadLocalRandom.current().nextInt(0, goodFirstNames.length)];
		goodRandomLastName = goodLastNames[ThreadLocalRandom.current().nextInt(0, goodLastNames.length)];
		goodRandomPhoneNumber = goodPhoneNumbers[ThreadLocalRandom.current().nextInt(0, goodPhoneNumbers.length)];
		goodRandomAddress = goodAddresses[ThreadLocalRandom.current().nextInt(0, goodAddresses.length)];
		testService.createNewContact(goodRandomFirstName, goodRandomLastName, goodRandomPhoneNumber, goodRandomAddress);
	}
	
	// Clean up each test by resetting the testService
	@AfterEach
	void cleanUpTest() {
		testService.resetContactList();
	}
	
	
	
	
	/*
	 *  Test that contact service is able to add a new contact
	 */
	
	// Tests adding one contact
	@Test
	@DisplayName("Tests adding one contact")
	void testAddingContact() {
		assertDoesNotThrow(() -> {testService.createNewContact(goodRandomFirstName, goodRandomLastName, goodRandomPhoneNumber, goodRandomAddress);});
	}
	
	// Tests adding two different contacts
	@Test
	@DisplayName("Tests adding two different contacts")
	void testAddingMultipleContacts() {
		assertDoesNotThrow(() -> {
			testService.createNewContact(goodRandomFirstName, goodRandomLastName, goodRandomPhoneNumber, goodRandomAddress);
			testService.createNewContact(goodRandomFirstName, goodRandomLastName, goodRandomPhoneNumber, goodRandomAddress);
			});
	}
	
	// Tests adding the same contact 50 times
	@RepeatedTest(50)
	@DisplayName("Tests adding the same contact 50 times")
	void testAddingManyContacts() {
		assertDoesNotThrow(() -> {testService.createNewContact(goodRandomFirstName, goodRandomLastName, goodRandomPhoneNumber, goodRandomAddress);});
	}
	
	
	/*
	 * Test that contact service is able to delete a contact by id
	 */
	
	// Tests deleting the first contact from the before each function
	@Test
	@DisplayName("Tests deleting the first contact from the before each function")
	void testRemovingOnlyContact() {
		assertDoesNotThrow(() -> {testService.deleteContact("1");});
		assertEquals(testService.getContactList().size(), 0);	
	}
	
	// Tests re-adding the same contact after deleting it
	@Test
	@DisplayName("Tests re-adding the same contact after deleting it")
	void testRemovingAndAddingOnlyContact() {
		int sizeBeforeAdding = testService.getContactList().size();
		assertDoesNotThrow(() -> {
			testService.deleteContact("1");
			testService.createNewContact(goodRandomFirstName, goodRandomLastName, goodRandomPhoneNumber, goodRandomAddress);
			});
		assertEquals(testService.getContactList().size(), sizeBeforeAdding);
	}
	
	// Tests that adding several contacts, removing one and replacing it will set it back to a previously used id
	@Test
	@DisplayName("Tests that adding several contacts, removing one and replacing it will set it back to a previously used id")
	void testRemovingAndAddingOnPreviousId() {
		// Add three contacts
		testService.createNewContact(goodRandomFirstName, goodRandomLastName, goodRandomPhoneNumber, goodRandomAddress);
		testService.createNewContact(goodRandomFirstName, goodRandomLastName, goodRandomPhoneNumber, goodRandomAddress);
		testService.createNewContact(goodRandomFirstName, goodRandomLastName, goodRandomPhoneNumber, goodRandomAddress);
		int sizeAfterAddingContacts = testService.getContactList().size();
		
		// Delete the second contact and assert the size change
		testService.deleteContact("2");
		assertEquals(testService.getContactList().size(), sizeAfterAddingContacts - 1);
		
		// Add back the contact - which should be on id 2 - assert the contacts id
		testService.createNewContact(goodRandomFirstName, goodRandomLastName, goodRandomPhoneNumber, goodRandomAddress);
		assertEquals(testService.getContact("2").getId(), "2");
	}
	
	
	
	/*
	 * Test that contact service is able to modify contacts
	 */
	
	// Tests that contact service can update the first name
	@Test
	@DisplayName("Tests that contact service can update the first name")
	void testUpdateFirstName() {
		goodRandomFirstName = goodFirstNames[ThreadLocalRandom.current().nextInt(0, goodFirstNames.length)];
		testService.updateFirstName("1", goodRandomFirstName);
		assertEquals(testService.getContact("1").getFirstName(), goodRandomFirstName);
	}
	
	// Tests that contact service can update the last name
	@Test
	@DisplayName("Tests that contact service can update the last name")
	void testUpdateLastName() {
		goodRandomLastName = goodLastNames[ThreadLocalRandom.current().nextInt(0, goodLastNames.length)];
		testService.updateLastName("1", goodRandomLastName);
		assertEquals(testService.getContact("1").getLastName(), goodRandomLastName);
	}
	
	// Tests that contact service can update the phone number
	@Test
	@DisplayName("Tests that contact service can update the phone number")
	void testUpdatePhoneNumber() {
		goodRandomPhoneNumber = goodPhoneNumbers[ThreadLocalRandom.current().nextInt(0, goodPhoneNumbers.length)];
		testService.updatePhoneNumber("1", goodRandomPhoneNumber);
		assertEquals(testService.getContact("1").getPhoneNumber(), goodRandomPhoneNumber);
	}
	
	// Tests that contact service can update the last name
	@Test
	@DisplayName("Tests that contact service can update the last name")
	void testUpdateAddress() {
		goodRandomAddress = goodAddresses[ThreadLocalRandom.current().nextInt(0, goodAddresses.length)];
		testService.updateAddress("1", goodRandomAddress);
		assertEquals(testService.getContact("1").getAddress(), goodRandomAddress);
	}
}
