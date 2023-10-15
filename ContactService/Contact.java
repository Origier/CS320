package Milestone1;

import java.util.ArrayList;

/*
 * The contact class and data structure. Contains the necessary fields to use a contact within a persons phone such as
 * the first name, last name, phone number, address and the contact ID for identifying the specific contact
 */

public class Contact {
	// Private fields to avoid modification
	private String id;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String address;
	
	// Constants to determine the size parameters of the contact fields
	private final int PHONENUMBERSIZE = 10;
	private final int MAXFIRSTNAMESIZE = 10;
	private final int MAXLASTNAMESIZE = 10;
	private final int MAXADDRESSSIZE = 30;
	private final int MAXIDSIZE = 10;
	
	// Static field to ensure that the ids are all unique
	static private ArrayList<String> usedIds = new ArrayList<String>();
	
	// Constructor to require the given attributes for a contact
	public Contact(String id, String firstName, String lastName, String phoneNumber, String address) {
		// Setting all of the attributes through the appropriate setters
		setId(id);
		setFirstName(firstName);
		setLastName(lastName);
		setPhoneNumber(phoneNumber);
		setAddress(address);
	}
	
	// Requires a unique ID that cannot be longer than 10 characters, the ID cannot be NULL and must not be a mutable attribute
	private void setId(String id) {
		if (usedIds.contains(id)) {
			throw new IllegalArgumentException("ID has already been used");
		} else if (id == null) {
			throw new IllegalArgumentException("ID may not be null");
		} else if (id.length() > MAXIDSIZE) {
			throw new IllegalArgumentException("ID is too long, must be " + MAXIDSIZE + " or fewer characters");
		} else {
			usedIds.add(id);
			this.id = id;
		}
	}
	
	// Requires a first name field that cannot be longer than 10 characters and cannot be NULL but is mutable
	public void setFirstName(String firstName) {
		if (firstName == null) {
			throw new IllegalArgumentException("First Name may not be null");
		} else if (firstName.length() > MAXFIRSTNAMESIZE) {
			throw new IllegalArgumentException("First Name is too long, must be " + MAXFIRSTNAMESIZE + " or fewer characters");
		} else {
			this.firstName = firstName;
		}
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	// Requires a last name field that cannot be longer than 10 characters and cannot be NULL but is mutable
	public void setLastName(String lastName) {
		if (lastName == null) {
			throw new IllegalArgumentException("Last Name may not be null");
		} else if (lastName.length() > MAXLASTNAMESIZE) {
			throw new IllegalArgumentException("Last Name is too long, must be " + MAXLASTNAMESIZE + " or fewer characters");
		} else {
			this.lastName = lastName;
		}
	}
	
	public String getLastName() {
		return lastName;
	}
	
	
	// Requires a phone string field that must be exactly 10 digits and cannot be NULL but is mutable
	public void setPhoneNumber(String phoneNumber) {
		if (phoneNumber == null) {
			throw new IllegalArgumentException("Phone number may not be null");
		} else if(phoneNumber.length() != PHONENUMBERSIZE) {
			throw new IllegalArgumentException("Phone number must be " + PHONENUMBERSIZE + " digits");
		} else {
			for (int i = 0; i < phoneNumber.length(); i++) {
				if (!Character.isDigit(phoneNumber.charAt(i))) {
					throw new IllegalArgumentException("Phone numbers must be digits");
				}
			}
			
			// If the code gets here without an exception then set the phone number
			this.phoneNumber = phoneNumber;
		}
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	// Requires an address field that cannot be longer than 30 characters and may not be NULL but is mutable
	public void setAddress(String address) {
		if (address == null) {
			throw new IllegalArgumentException("Address may not be null");
		} else if (address.length() > MAXADDRESSSIZE) {
			throw new IllegalArgumentException("Address is too long, must be " + MAXADDRESSSIZE + " or fewer characters");
		} else {
			this.address = address;
		}
	}
	
	public String getAddress() {
		return address;
	}
	
	// Remove an Id from the list of unique ids to allow it to be used again
	static public void removeId(String id) {
		if (usedIds.contains(id)) {
			usedIds.remove(id);
		}
	}
	
	// Reset the list of currently used ids
	static public void clearAllIds() {
		usedIds.clear();
	}
	
	public String getId() {
		return id;
	}
}
