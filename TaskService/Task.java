package Milestone1;

import java.util.ArrayList;

public class Task {
	// Private fields to avoid modification
	private String id;
	private String name;
	private String description;
	
	// Constants to determine the size parameters of the task fields
	private final int MAXDESCRIPTIONSIZE = 50;
	private final int MAXNAMESIZE = 20;
	private final int MAXIDSIZE = 10;
	
	// Static field to ensure that the ids are all unique
	static private ArrayList<String> usedIds = new ArrayList<String>();
	
	// Constructor to require the given attributes for a task
	public Task(String id, String name, String description) {
		// Setting all of the attributes through the appropriate setters
		setId(id);
		setName(name);
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
	
	// Requires a task name field that cannot be longer than 20 characters and cannot be NULL but is mutable
	public void setName(String name) {
		if (name == null) {
			throw new IllegalArgumentException("The task name may not be null");
		} else if (name.length() > MAXNAMESIZE) {
			throw new IllegalArgumentException("The task name is too long, must be " + MAXNAMESIZE + " or fewer characters");
		} else {
			this.name = name;
		}
	}
	
	public String getName() {
		return name;
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
