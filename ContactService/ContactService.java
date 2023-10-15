package Milestone1;

import java.util.Dictionary;
import java.util.Hashtable;
import java.nio.BufferOverflowException;
import java.util.ArrayList;
import java.util.Collections;

/*
 * The contact service class manages all of the contacts on the phone. This allows the user to add new contacts, delete contacts or update current contacts
 */

public class ContactService {
	
	// Fields
	private int nextId = 1;
	private final long MAXID = 10000000000L;
	private ArrayList<Integer> removedIds = new ArrayList<Integer>();
	private Dictionary<String, Contact> contactList = new Hashtable<>();
	
	// Singleton design pattern - there is no need for more than once instance of Contact Service on a mobile device
	private static ContactService instance;
	
	private ContactService() {}
	
	public static ContactService getInstance() {
		if (instance == null) {
			instance = new ContactService();
		}
		return instance;
	}
	
	// Can add a new contact with a unique id
	public void createNewContact(String firstName, String lastName, String phoneNumber, String address) {
		// If there are removed ids then use those first before creating new ones
		String id = "";
		if (removedIds.isEmpty()) {
			id = Integer.toString(nextId);
		} else {
			id = Integer.toString(removedIds.get(0));
		}
		
		if (nextId == MAXID) {
			throw new BufferOverflowException();
		}
		// Attempting to generate the new contact
		try {
			contactList.put(id, new Contact(id, firstName, lastName, phoneNumber, address));
			
			// If no errors occured creating the contact then we can increment / remove the id for availability
			if (removedIds.isEmpty()) {
				nextId += 1;
			} else {
				removedIds.remove(0);
			}
		} catch (IllegalArgumentException e) {
		// If it fails then reset the next id number and print out the reason
			System.out.print(e.getMessage());
		}
	}
	
	public Dictionary<String, Contact> getContactList() {
		return contactList;
	}
	
	// Can delete contacts based on the unique id
	public void deleteContact(String id) {
		contactList.remove(id);
		Contact.removeId(id);
		// Adds the opened Id to the list of possible ids to be used again
		removedIds.add(Integer.parseInt(id));
	}
	
	// Can update a contacts fields based on the unique id
	
	// Updating first name
	public void updateFirstName(String id, String firstName) {
		// Ensures that the id is in the contact list and then attempts to update the first name
		if (isValidContact(id)) {
			try {
				contactList.get(id).setFirstName(firstName);
			} catch (IllegalArgumentException e) {
				System.out.print(e.getMessage());
			}
		}
	}
	
	// Updating last name
	public void updateLastName(String id, String lastName) {
		// Ensures that the id is in the contact list and then attempts to update the last name
		if (isValidContact(id)) {
			try {
				contactList.get(id).setLastName(lastName);
			} catch (IllegalArgumentException e) {
				System.out.print(e.getMessage());
			}
		}
	}
	
	// Updating phone number
	public void updatePhoneNumber(String id, String phoneNumber) {
		// Ensures that the id is in the contact list and then attempts to update the phone number
		if (isValidContact(id)) {
			try {
				contactList.get(id).setPhoneNumber(phoneNumber);
			} catch (IllegalArgumentException e) {
				System.out.print(e.getMessage());
			}
		}
	}
	
	// Updating address
	public void updateAddress(String id, String address) {
		// Ensures that the id is in the contact list and then attempts to update the phone number
		if (isValidContact(id)) {
			try {
				contactList.get(id).setAddress(address);
			} catch (IllegalArgumentException e) {
				System.out.print(e.getMessage());
			}
		}
	}
	
	// Deletes all contacts and resets the id counter
	public void resetContactList() {
		ArrayList<String> idList = Collections.list(contactList.keys());
		
		// Removing all contacts
		for (int i = 0; i < idList.size(); i++) {
			contactList.remove(idList.get(i));
		}
		
		// Resetting the cache of removed ids
		removedIds = new ArrayList<Integer>();
		
		// Clearing the contact object of unique ids
		Contact.clearAllIds();
		nextId = 1;
	}
	
	// Contact getter
	public Contact getContact(String id) {
		if (isValidContact(id)) {
			return contactList.get(id);
		} else {
			return null;
		}
	}
	
	// Determines if an id is a currently valid contact
	private Boolean isValidContact(String id) {
		ArrayList<String> idList = Collections.list(contactList.keys());
		return idList.contains(id);
	}
}
