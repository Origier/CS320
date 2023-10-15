package Milestone1;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Contact Testing Class")
class ContactTest {
	private Contact testContact;
	// Test Ids
	private String[] goodIds = new String[] {"12345", "678910"};
	private String id11Char = "12345678910";
	private String id10Char = "1234567890";
	
	// Test first names
	private String[] goodFirstNames = new String[] {"FirstName", "First"};
	private String firstName10Char = "ABCDEFGHIJ";
	private String firstName11Char = "ABCDEFGHIJK";
	
	// Test last names
	private String[] goodLastNames = new String[] {"LastName", "Last"};
	private String lastName10Char = "ABCDEFGHIJ";
	private String lastName11Char = "ABCDEFGHIJK";
	
	// Test phone numbers
	private String[] goodPhoneNumbers = new String[] {"5555555555",  "6666666666"};
	private String badPhoneNumberNoDigit = "ABCDEFGHIJ"; // 10 Characters but no digits
	private String badPhoneNumberOneNonDigit = "123456789A"; // 10 Characters with one non-digit
	private String badPhoneNumberOneDigit = "1BCDEFGHIJ"; // 10 Characters but the first character is a digit
	private String phoneNumberToShort = "999999999"; // 9 digits long
	private String phoneNumberToLong = "11111111111"; // 11 digits long
	
	// Test addresses
	private String[] goodAddresses = new String[] {"Address", "Another Address"};
	private String address30Char = "An address once lived here for";
	private String address31Char = "An address once lived here fore";
	
	// Variable placeholders
	private String goodRandomId;
	private String goodOtherRandomId;
	private String goodRandomFirstName;
	private String goodRandomLastName;
	private String goodRandomPhoneNumber;
	private String goodRandomAddress;
	
	/*
	 * Test Setups and clean ups
	 */
	
	// Initialize each test with a contact object
	@BeforeEach
	void setupTest() {
		goodRandomId = goodIds[ThreadLocalRandom.current().nextInt(0, goodIds.length)];
		goodRandomFirstName = goodFirstNames[ThreadLocalRandom.current().nextInt(0, goodFirstNames.length)];
		goodRandomLastName = goodLastNames[ThreadLocalRandom.current().nextInt(0, goodLastNames.length)];
		goodRandomPhoneNumber = goodPhoneNumbers[ThreadLocalRandom.current().nextInt(0, goodPhoneNumbers.length)];
		goodRandomAddress = goodAddresses[ThreadLocalRandom.current().nextInt(0, goodAddresses.length)];
		testContact = new Contact(goodRandomId, goodRandomFirstName, goodRandomLastName, goodRandomPhoneNumber, goodRandomAddress); 
		
		// Getting another random ID that isn't the same as the first
		goodOtherRandomId = goodIds[ThreadLocalRandom.current().nextInt(0, goodIds.length)];
		while (goodOtherRandomId == goodRandomId) {
			goodOtherRandomId = goodIds[ThreadLocalRandom.current().nextInt(0, goodIds.length)];
		}
	}
	
	// Clean up each test by resetting the testContact to null
	@AfterEach
	void cleanupTest() {
		Contact.clearAllIds();
		testContact = null;
	}
	
	
	
	
	/* 
	 * Test that the ID is required, must be unique and cannot be longer than 10 characters, the ID cannot be NULL and must not be a mutable attribute 
	 */
	
	// Testing that the ID must be unique (does not match the test contact)
	@Test
	@DisplayName("Testing that the ID must be unique (does not match the test contact)")
	void testMustBeUniqueId() {
		assertThrows(IllegalArgumentException.class, () -> {new Contact(goodRandomId, goodRandomFirstName, goodRandomLastName, goodRandomPhoneNumber, goodRandomAddress);});		
	}
	
	// Testing that a different ID will be instantiated fine
	@Test
	@DisplayName("Testing that a different ID will be instantiated fine")
	void testInstantiateUniqueId() {
		assertDoesNotThrow(() -> {new Contact(goodOtherRandomId, goodRandomFirstName, goodRandomLastName, goodRandomPhoneNumber, goodRandomAddress);});
	}
	
	// Testing that the ID cannot be longer than 10 characters
	@Test
	@DisplayName("Testing that the ID cannot be longer than 10 characters")
	void testIdLengthBad() {
		assertThrows(IllegalArgumentException.class, () -> {new Contact(id11Char, goodRandomFirstName, goodRandomLastName, goodRandomPhoneNumber, goodRandomAddress);});	
	}
	
	// Testing that the ID can be 10 characters
	@Test
	@DisplayName("Testing that the ID can be 10 characters")
	void testIdLengthGood() {
		assertDoesNotThrow(() -> {new Contact(id10Char, goodRandomFirstName, goodRandomLastName, goodRandomPhoneNumber, goodRandomAddress);});
	}
	
	// Testing that the ID cannot be null
	@Test
	@DisplayName("Testing that the ID cannot be null")
	void testIdCannotBeNull() {
		assertThrows(IllegalArgumentException.class, () -> {new Contact(null, goodRandomFirstName, goodRandomLastName, goodRandomPhoneNumber, goodRandomAddress);});
	}
	
	
	
	
	/*
	 *  Test that the first name field is required, cannot be longer than 10 characters and cannot be NULL but can be changed
	 */
	
	// Testing that the first name field can be 10 characters
	@Test
	@DisplayName("Testing that the first name field can be 10 characters")
	void testFirstNameLengthGood() {
		assertDoesNotThrow(() -> {new Contact(goodOtherRandomId, firstName10Char, goodRandomLastName, goodRandomPhoneNumber, goodRandomAddress);});
	}
	
	// Testing that the first name field cannot be more than 10 characters
	@Test
	@DisplayName("Testing that the first name field cannot be more than 10 characters")
	void testFirstNameLengthBad() {
		assertThrows(IllegalArgumentException.class, () -> {new Contact(goodOtherRandomId, firstName11Char, goodRandomLastName, goodRandomPhoneNumber, goodRandomAddress);});
	}
	
	// Testing that the first name cannot be null
	@Test
	@DisplayName("Testing that the first name cannot be null")
	void testFirstNameCannotBeNull() {
		assertThrows(IllegalArgumentException.class, () -> {new Contact(goodOtherRandomId, null, goodRandomLastName, goodRandomPhoneNumber, goodRandomAddress);});
	}
	
	// Testing that the first name field can be modified
	@Test
	@DisplayName("Testing that the first name field can be modified")
	void testFirstNameMutable() {
		goodRandomFirstName = goodFirstNames[ThreadLocalRandom.current().nextInt(0, goodFirstNames.length)];
		assertDoesNotThrow(() -> {testContact.setFirstName(goodRandomFirstName);});
	}
	
	// Testing that the first name cannot be changed to something longer than 10 characters
	@Test
	@DisplayName("Testing that the first name cannot be changed to something longer than 10 characters")
	void testFirstNameChangeLengthBad() {
		assertThrows(IllegalArgumentException.class, () -> {testContact.setFirstName(firstName11Char);});
	}
	
	// Testing that the first name can be changed to something that is 10 characters
	@Test
	@DisplayName("Testing that the first name can be changed to something that is 10 characters")
	void testFirstNameChangeLengthGood() {
		assertDoesNotThrow(() -> {testContact.setFirstName(firstName10Char);});
	}
	
	// Testing that changing the first name actually changes the contacts variable
	@Test
	@DisplayName("Testing that changing the first name actually changes the contacts variable")
	void testFirstNameChangeSet() {
		goodRandomFirstName = goodFirstNames[ThreadLocalRandom.current().nextInt(0, goodFirstNames.length)];
		testContact.setFirstName(goodRandomFirstName);
		assertEquals(testContact.getFirstName(), goodRandomFirstName);
	}
	
	
	
	
	/*
	 *  Test that the last name field is required, cannot be longer than 10 characters and cannot be NULL but can be changed
	 */
	// Testing that the first name field can be 10 characters
	@Test
	@DisplayName("Testing that the first name field can be 10 characters")
	void testLastNameLengthGood() {
		assertDoesNotThrow(() -> {new Contact(goodOtherRandomId, goodRandomFirstName, lastName10Char, goodRandomPhoneNumber, goodRandomAddress);});
	}
	
	// Testing that the first name field cannot be more than 10 characters
	@Test
	@DisplayName("Testing that the first name field cannot be more than 10 characters")
	void testLastNameLengthBad() {
		assertThrows(IllegalArgumentException.class, () -> {new Contact(goodOtherRandomId, goodRandomFirstName, lastName11Char, goodRandomPhoneNumber, goodRandomAddress);});
	}
	
	// Testing that the first name cannot be null
	@Test
	@DisplayName("Testing that the first name cannot be null")
	void testLastNameCannotBeNull() {
		assertThrows(IllegalArgumentException.class, () -> {new Contact(goodOtherRandomId, goodRandomFirstName, null, goodRandomPhoneNumber, goodRandomAddress);});
	}
	
	// Testing that the first name field can be modified
	@Test
	@DisplayName("Testing that the first name field can be modified")
	void testLastNameMutable() {
		goodRandomLastName = goodLastNames[ThreadLocalRandom.current().nextInt(0, goodLastNames.length)];
		assertDoesNotThrow(() -> {testContact.setLastName(goodRandomLastName);});
	}
	
	// Testing that the first name cannot be changed to something longer than 10 characters
	@Test
	@DisplayName("Testing that the first name cannot be changed to something longer than 10 characters")
	void testLastNameChangeLengthBad() {
		assertThrows(IllegalArgumentException.class, () -> {testContact.setLastName(lastName11Char);});
	}
	
	// Testing that the first name can be changed to something that is 10 characters
	@Test
	@DisplayName("Testing that the first name can be changed to something that is 10 characters")
	void testLastNameChangeLengthGood() {
		assertDoesNotThrow(() -> {testContact.setLastName(lastName10Char);});
	}
	
	// Testing that changing the last name actually changes the contacts variable
	@Test
	@DisplayName("Testing that changing the last name actually changes the contacts variable")
	void testLastNameChangeSet() {
		goodRandomLastName = goodLastNames[ThreadLocalRandom.current().nextInt(0, goodLastNames.length)];
		testContact.setLastName(goodRandomLastName);
		assertEquals(testContact.getLastName(), goodRandomLastName);
	}

	
	
	
	/*
	 *  Test that the phone string field is required, must be exactly 10 digits and cannot be NULL but can be changed
	 */
	
	// Testing that the phone number can't be less than 10 digits
	@Test
	@DisplayName("Testing that the phone number can't be less than 10 digits")
	void testPhoneNumberToShort() {
		assertThrows(IllegalArgumentException.class, () -> {new Contact(goodOtherRandomId, goodRandomFirstName, goodRandomLastName, phoneNumberToShort, goodRandomAddress);});
	}
	
	// Testing that the phone number can't be more than 10 digits
	@Test
	@DisplayName("Testing that the phone number can't be more than 10 digits")
	void testPhoneNumberToLong() {
		assertThrows(IllegalArgumentException.class, () -> {new Contact(goodOtherRandomId, goodRandomFirstName, goodRandomLastName, phoneNumberToLong, goodRandomAddress);});
	}
	
	// Testing that the phone number will only accept digit based strings
	@Test
	@DisplayName("Testing that the phone number will only accept digit based strings")
	void testPhoneNumberMustBeDigits() {
		assertThrows(IllegalArgumentException.class, () -> {new Contact(goodOtherRandomId, goodRandomFirstName, goodRandomLastName, badPhoneNumberNoDigit, goodRandomAddress);});
	}
	
	// Testing that the phone number checks to make sure all characters are digits
	@Test
	@DisplayName("Testing that the phone number checks to make sure all characters are digits")
	void testPhoneNumberMustBeDigitsOneChar() {
		assertThrows(IllegalArgumentException.class, () -> {new Contact(goodOtherRandomId, goodRandomFirstName, goodRandomLastName, badPhoneNumberOneNonDigit, goodRandomAddress);});
	}
	
	// Testing that the phone number checks to make sure not just the first character needs to be a digit
	@Test
	@DisplayName("Testing that the phone number checks to make sure not just the first character needs to be a digit")
	void testPhoneNumberMustBeDigitsOneDigit() {
		assertThrows(IllegalArgumentException.class, () -> {new Contact(goodOtherRandomId, goodRandomFirstName, goodRandomLastName, badPhoneNumberOneDigit, goodRandomAddress);});
	}
	
	// Testing that the phone number cannot be null
	@Test
	@DisplayName("Testing that the phone number cannot be null")
	void testPhoneNumberCannotBeNull() {
		assertThrows(IllegalArgumentException.class, () -> {new Contact(goodOtherRandomId, goodRandomFirstName, goodRandomLastName, null, goodRandomAddress);});
	}
	
	// Testing that the phone number can be changed
	@Test
	@DisplayName("Testing that the phone number can be changed")
	void testPhoneNumberMutable() {
		goodRandomPhoneNumber = goodPhoneNumbers[ThreadLocalRandom.current().nextInt(0, goodPhoneNumbers.length)];
		assertDoesNotThrow(() -> {testContact.setPhoneNumber(goodRandomPhoneNumber);});
	}
	
	// Testing that the phone number change actually changed the contacts variable
	@Test
	@DisplayName("Testing that the phone number change actually changed the contacts variable")
	void testPhoneNumberMutableSets() {
		goodRandomPhoneNumber = goodPhoneNumbers[ThreadLocalRandom.current().nextInt(0, goodPhoneNumbers.length)];
		testContact.setPhoneNumber(goodRandomPhoneNumber);
		assertEquals(testContact.getPhoneNumber(), goodRandomPhoneNumber);
	}
	
	// Testing that changing the phone number still does not except invalid phone numbers
	@Test
	@DisplayName("Testing that changing the phone number still does not except invalid phone numbers")
	void testPhoneNumberChangeMustBeValid() {
		assertThrows(IllegalArgumentException.class, () -> {testContact.setPhoneNumber(badPhoneNumberNoDigit);});
	}

	
	

	/*
	 *  Test that the address field is required, cannot be longer than 30 characters and may not be NULL but is mutable
	 */
	
	// Testing that the address can be 30 characters long
	@Test
	@DisplayName("Testing that the address can be 30 characters long")
	void testAddressLengthGood() {
		assertDoesNotThrow(() -> {new Contact(goodOtherRandomId, goodRandomFirstName, goodRandomLastName, goodRandomPhoneNumber, address30Char);});
	}
	
	// Testing that the address cannot be more than 30 characters long
	@Test
	@DisplayName("Testing that the address cannot be more than 30 characters long")
	void testAddressLengthBad() {
		assertThrows(IllegalArgumentException.class, () -> {new Contact(goodOtherRandomId, goodRandomFirstName, goodRandomLastName, goodRandomPhoneNumber, address31Char);});
	}
	
	
	// Testing that the address cannot be null
	@Test
	@DisplayName("Testing that the address cannot be null")
	void testAddressCannotBeNull() {
		assertThrows(IllegalArgumentException.class, () -> {new Contact(goodOtherRandomId, goodRandomFirstName, goodRandomLastName, goodRandomPhoneNumber, null);});
	}
	
	// Testing that the address can be changed
	@Test
	@DisplayName("Testing that the address can be changed")
	void testAddressMutable() {
		goodRandomAddress = goodAddresses[ThreadLocalRandom.current().nextInt(0, goodAddresses.length)];
		assertDoesNotThrow(() -> {testContact.setAddress(goodRandomAddress);});
	}
	
	// Testing that the address cannot be changed to something invalid
	@Test
	@DisplayName("Testing that the address cannot be changed to something invalid")
	void testAddressChangeMustBeValid() {
		assertThrows(IllegalArgumentException.class, () -> {testContact.setAddress(address31Char);});
	}
	
	// Testing that the address being changed actually changes the contacts variable
	@Test
	@DisplayName("Testing that the address being changed actually changes the contacts variable")
	void testAddressMutableSets() {
		goodRandomAddress = goodAddresses[ThreadLocalRandom.current().nextInt(0, goodAddresses.length)];
		testContact.setAddress(goodRandomAddress);
		assertEquals(testContact.getAddress(), goodRandomAddress);
	}
}
