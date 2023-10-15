package Milestone1;

import java.nio.BufferOverflowException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;

public class TaskService {
	// Fields
	private int nextId = 1;
	private final long MAXID = 10000000000L;
	private ArrayList<Integer> removedIds = new ArrayList<Integer>();
	private Dictionary<String, Task> taskList = new Hashtable<>();
	
	// Singleton design pattern - there is no need for more than once instance of Task Service on a mobile device
	private static TaskService instance;
	
	private TaskService() {}
	
	public static TaskService getInstance() {
		if (instance == null) {
			instance = new TaskService();
		}
		return instance;
	}
	
	// Can add a new task with a unique id
	public void createNewTask(String name, String description) {
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
		// Attempting to generate the new task
		try {
			taskList.put(id, new Task(id, name, description));
			
			// If no errors occured creating the task then we can increment / remove the id for availability
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
	
	public Dictionary<String, Task> getTaskList() {
		return taskList;
	}
	
	// Can delete tasks based on the unique id
	public void deleteTask(String id) {
		taskList.remove(id);
		Task.removeId(id);
		// Adds the opened Id to the list of possible ids to be used again
		removedIds.add(Integer.parseInt(id));
	}
	
	// Can update a tasks fields based on the unique id
	
	// Updating task name
	public void updateName(String id, String name) {
		// Ensures that the id is in the task list and then attempts to update the name
		if (isValidTask(id)) {
			try {
				taskList.get(id).setName(name);
			} catch (IllegalArgumentException e) {
				System.out.print(e.getMessage());
			}
		}
	}
	
	// Updating description
	public void updateDescription(String id, String description) {
		// Ensures that the id is in the task list and then attempts to update the description
		if (isValidTask(id)) {
			try {
				taskList.get(id).setDescription(description);
			} catch (IllegalArgumentException e) {
				System.out.print(e.getMessage());
			}
		}
	}
	
	// Deletes all tasks and resets the id counter
	public void resetTaskList() {
		ArrayList<String> idList = Collections.list(taskList.keys());
		
		// Removing all tasks
		for (int i = 0; i < idList.size(); i++) {
			taskList.remove(idList.get(i));
		}
		
		// Clearing the cache of removed ids
		removedIds = new ArrayList<Integer>();
		
		// Clearing the task object of unique ids
		Task.clearAllIds();
		nextId = 1;
	}
	
	// task getter
	public Task getTask(String id) {
		if (isValidTask(id)) {
			return taskList.get(id);
		} else {
			return null;
		}
	}
	
	// Determines if an id is a currently valid task
	private Boolean isValidTask(String id) {
		ArrayList<String> idList = Collections.list(taskList.keys());
		return idList.contains(id);
	}
}
