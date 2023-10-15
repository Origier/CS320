package Milestone1;

import java.nio.BufferOverflowException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.GregorianCalendar;
import java.util.Hashtable;

public class AppointmentService {
	// Fields
	private int nextId = 1;
	private final long MAXID = 10000000000L;
	private ArrayList<Integer> removedIds = new ArrayList<Integer>();
	private Dictionary<String, Appointment> appointmentList = new Hashtable<>();
	
	// Singleton design pattern - there is no need for more than once instance of Appointment Service on a mobile device
	private static AppointmentService instance;
	
	private AppointmentService() {}
	
	public static AppointmentService getInstance() {
		if (instance == null) {
			instance = new AppointmentService();
		}
		return instance;
	}
	
	// Can add a new appointment with a unique id
	public void createNewAppointment(GregorianCalendar appointmentDate, String description) {
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
		// Attempting to generate the new appointment
		try {
			appointmentList.put(id, new Appointment(id, appointmentDate, description));
			
			// If no errors occurred creating the appointment then we can increment / remove the id for availability
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
	
	public Dictionary<String, Appointment> getAppointmentList() {
		return appointmentList;
	}
	
	// Can delete appointments based on the unique id
	public void deleteAppointment(String id) {
		appointmentList.remove(id);
		Appointment.removeId(id);
		// Adds the opened Id to the list of possible ids to be used again
		removedIds.add(Integer.parseInt(id));
	}
	
	// Deletes all appointments and resets the id counter
	public void resetAppointmentList() {
		ArrayList<String> idList = Collections.list(appointmentList.keys());
		
		// Removing all appointments
		for (int i = 0; i < idList.size(); i++) {
			appointmentList.remove(idList.get(i));
		}
		
		// Clearing the cache of removed ids
		removedIds = new ArrayList<Integer>();
		
		// Clearing the appointment object of unique ids
		Appointment.clearAllIds();
		nextId = 1;
	}
	
	// appointment getter
	public Appointment getAppointment(String id) {
		if (isValidAppointment(id)) {
			return appointmentList.get(id);
		} else {
			return null;
		}
	}
	
	// Determines if an id is a currently valid appointment
	private Boolean isValidAppointment(String id) {
		ArrayList<String> idList = Collections.list(appointmentList.keys());
		return idList.contains(id);
	}
}
