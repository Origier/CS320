package Milestone1;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Appointment {
	// Private fields to avoid modification
	private String id;
	private GregorianCalendar appointmentDate;
	private String description;
	
	// Constants to determine the size parameters of the appointment fields
	private final int MAXDESCRIPTIONSIZE = 50;
	private final int MAXIDSIZE = 10;
	
	// Static field to ensure that the ids are all unique
	static private ArrayList<String> usedIds = new ArrayList<String>();
	
	// Constructor to require the given attributes for an appointment
	public Appointment(String id, GregorianCalendar appointmentDate, String description) {
		// Setting all of the attributes through the appropriate setters
		setId(id);
		setDate(appointmentDate);
		setDescription(description);
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
	
	// Requires an appointment date field that cannot be in the past
	public void setDate(GregorianCalendar appointmentDate) {
		if (appointmentDate == null) {
			throw new IllegalArgumentException("The date cannot be null");
		} else if (appointmentDate.before(new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_YEAR))) { // New GregorianCalendar is defaulted to the current date and time
			throw new IllegalArgumentException("The appointment date must be in the future");
		} else {
			this.appointmentDate = appointmentDate;
		}
	}
	
	public GregorianCalendar getDate() {
		return appointmentDate;
	}
	
	// Requires a description field that cannot be longer than 50 characters and cannot be NULL but is mutable
	public void setDescription(String description) {
		if (description == null) {
			throw new IllegalArgumentException("Description may not be null");
		} else if (description.length() > MAXDESCRIPTIONSIZE) {
			throw new IllegalArgumentException("Description is too long, must be " + MAXDESCRIPTIONSIZE + " or fewer characters");
		} else {
			this.description = description;
		}
	}
	
	public String getDescription() {
		return description;
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
