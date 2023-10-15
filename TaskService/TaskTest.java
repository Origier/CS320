package Milestone1;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Task Testing Class")
class TaskTest {

	private Task testTask;
	// Test Ids
	private String[] goodTestIds = new String[]{"12345", "678910"};
	private String id11Char = "12345678910";
	private String id10Char = "1234567890";
	
	// Test names
	private String[] goodTestTaskNames = new String[] {"TaskName", "TaskNameNumber2"};
	private String taskName20Char = "ABCDEFGHIJKLMNOPQRST";
	private String taskName21Char = "ABCDEFGHIJKLMNOPQRSTU";
	
	// Test descriptions
	private String[] goodTestDescriptions = new String[] {"Description here", "Description is also here"};
	private String description50Char = "ABCDEFGHIJKLMNOPQRSTABCDEFGHIJKLMNOPQRSTabcdefghij";
	private String description51Char = "ABCDEFGHIJKLMNOPQRSTABCDEFGHIJKLMNOPQRSTabcdefghij1";
	
	// placeholder variables
	String goodRandomId;
	String goodRandomTaskName;
	String goodRandomDescription;
	String goodOtherRandomId;
	
	/*
	 * Test Setups and clean ups
	 */
	
	// Initialize each test with a task object
	@BeforeEach
	void setupTest() {
		goodRandomId = goodTestIds[ThreadLocalRandom.current().nextInt(0, goodTestIds.length)];
		goodRandomTaskName = goodTestTaskNames[ThreadLocalRandom.current().nextInt(0, goodTestTaskNames.length)];
		goodRandomDescription = goodTestDescriptions[ThreadLocalRandom.current().nextInt(0, goodTestDescriptions.length)];
		testTask = new Task(goodRandomId, goodRandomTaskName, goodRandomDescription); 
		
		// Setting another id that is not the same as the first random id
		goodOtherRandomId = goodTestIds[ThreadLocalRandom.current().nextInt(0, goodTestIds.length)];
		while (goodOtherRandomId == goodRandomId) {
			goodOtherRandomId = goodTestIds[ThreadLocalRandom.current().nextInt(0, goodTestIds.length)];
		}
	}
	
	// Clean up each test by resetting the testTask to null
	@AfterEach
	void cleanupTest() {
		Task.clearAllIds();
		testTask = null;
	}
	
	
	
	
	/* 
	 * Test that the ID is required, must be unique and cannot be longer than 10 characters, the ID cannot be NULL and must not be a mutable attribute 
	 */
	
	// Testing that the ID must be unique (does not match the test task)
	@Test
	@DisplayName("Testing that each task must have a unique ID")
	void testMustBeUniqueId() {
		assertThrows(IllegalArgumentException.class, () -> {new Task(goodRandomId, goodRandomTaskName, goodRandomDescription);});		
	}
	
	// Test that a different ID will be instantiated fine
	@Test
	@DisplayName("Testing that a unique ID will instantiate fine")
	void testInstantiateUniqueId() {
		assertDoesNotThrow(() -> {
			Task task2 = new Task(goodOtherRandomId, goodRandomTaskName, goodRandomDescription);
			
			// Additionally asserting that the instance variables all set correctly
			if (task2.getId() != goodOtherRandomId) {
				throw new RuntimeException("The id did not set correctly");
			} else if(task2.getName() != goodRandomTaskName) {
				throw new RuntimeException("The task name did not set correctly");
			} else if(task2.getDescription() != goodRandomDescription) {
				throw new RuntimeException("The description did not set correctly");
			}
		});
	}
	
	// Testing that the ID cannot be longer than 10 characters
	@Test
	@DisplayName("Testing that the ID cannot be longer than 10 characters")
	void testIdLengthBad() {
		assertThrows(IllegalArgumentException.class, () -> {new Task(id11Char, goodRandomTaskName, goodRandomDescription);});	
	}
	
	// Testing that the ID can be 10 characters
	@Test
	@DisplayName("Testing that the ID can be 10 characters")
	void testIdLengthGood() {
		assertDoesNotThrow(() -> {
			Task task2 = new Task(id10Char, goodRandomTaskName, goodRandomDescription);
			
			// Additionally asserting that the instance variables all set correctly
			if (task2.getId() != id10Char) {
				throw new RuntimeException("The id did not set correctly");
			} else if(task2.getName() != goodRandomTaskName) {
				throw new RuntimeException("The task name did not set correctly");
			} else if(task2.getDescription() != goodRandomDescription) {
				throw new RuntimeException("The description did not set correctly");
			}
		});
	}
	
	// Testing that the ID cannot be null
	@Test
	@DisplayName("Testing that the ID cannot be null")
	void testIdCannotBeNull() {
		assertThrows(IllegalArgumentException.class, () -> {new Task(null, goodRandomTaskName, goodRandomDescription);});
	}
	
	
	
	
	/*
	 *  Test that the name field is required, cannot be longer than 20 characters and cannot be NULL but can be changed
	 */
	
	// Testing that the name field can be 20 characters
	@Test
	@DisplayName("Testing that the name field can be 20 characters")
	void testNameLengthGood() {
		assertDoesNotThrow(() -> {
			Task task2 = new Task(goodOtherRandomId, taskName20Char, goodRandomDescription);
			
			// Additionally asserting that the instance variables all set correctly
			if (task2.getId() != goodOtherRandomId) {
				throw new RuntimeException("The id did not set correctly");
			} else if(task2.getName() != taskName20Char) {
				throw new RuntimeException("The task name did not set correctly");
			} else if(task2.getDescription() != goodRandomDescription) {
				throw new RuntimeException("The description did not set correctly");
			}
		});
	}
	
	// Testing that the name field cannot be more than 20 characters
	@Test
	@DisplayName("Testing that the name field cannot be more than 20 characters")
	void testNameLengthBad() {
		assertThrows(IllegalArgumentException.class, () -> {new Task(goodOtherRandomId, taskName21Char, goodRandomDescription);});
	}
	
	// Testing that the name cannot be null
	@Test
	@DisplayName("Testing that the name cannot be null")
	void testNameCannotBeNull() {
		assertThrows(IllegalArgumentException.class, () -> {new Task(goodOtherRandomId, null, goodRandomDescription);});
	}
	
	// Testing that the name field can be modified
	@Test
	@DisplayName("Testing that the name field can be modified")
	void testNameMutable() {
		assertDoesNotThrow(() -> {
			goodRandomTaskName = goodTestTaskNames[ThreadLocalRandom.current().nextInt(0, goodTestTaskNames.length)];
			testTask.setName(goodRandomTaskName);
			
			// Asserting that the name changed correctly
			if (testTask.getName() != goodRandomTaskName) {
				throw new RuntimeException("The task name did not change properly");
			}
		});
	}
	
	// Testing that the name cannot be changed to something longer than 20 characters
	@Test
	@DisplayName("Testing that the name cannot be changed to something longer than 20 characters")
	void testNameChangeLengthBad() {
		assertThrows(IllegalArgumentException.class, () -> {testTask.setName(taskName21Char);});
	}
	
	// Testing that the name can be changed to something that is 20 characters
	@Test
	@DisplayName("Testing that the name can be changed to something that is 20 characters")
	void testNameChangeLengthGood() {
		assertDoesNotThrow(() -> {
			testTask.setName(taskName20Char);
			
			// Asserting that the name changed correctly
			if (testTask.getName() != taskName20Char) {
				throw new RuntimeException("The task name did not change to the 20 char name properly");
			}
		});
	}
	
	
	
	
	/*
	 *  Test that the description field is required, cannot be longer than 50 characters and cannot be NULL but can be changed
	 */
	// Testing that the description field can be 50 characters
	@Test
	@DisplayName("Testing that the description field can be 50 characters")
	void testDescriptionLengthGood() {
		assertDoesNotThrow(() -> {
			Task task2 = new Task(goodOtherRandomId, goodRandomTaskName, description50Char);
			
			// Additionally asserting that the instance variables all set correctly
			if (task2.getId() != goodOtherRandomId) {
				throw new RuntimeException("The id did not set correctly");
			} else if(task2.getName() != goodRandomTaskName) {
				throw new RuntimeException("The task name did not set correctly");
			} else if(task2.getDescription() != description50Char) {
				throw new RuntimeException("The description did not set correctly");
			}
		});
	}
	
	// Testing that the description field cannot be more than 50 characters
	@Test
	@DisplayName("Testing that the description field cannot be more than 50 characters")
	void testDescriptionLengthBad() {
		assertThrows(IllegalArgumentException.class, () -> {new Task(goodOtherRandomId, goodRandomTaskName, description51Char);});
	}
	
	// Testing that the description cannot be null
	@Test
	@DisplayName("Testing that the description cannot be null")
	void testDescriptionCannotBeNull() {
		assertThrows(IllegalArgumentException.class, () -> {new Task(goodOtherRandomId, goodRandomTaskName, null);});
	}
	
	// Testing that the description field can be modified
	@Test
	@DisplayName("Testing that the description field can be modified")
	void testDescriptionMutable() {
		assertDoesNotThrow(() -> {
			goodRandomDescription = goodTestDescriptions[ThreadLocalRandom.current().nextInt(0, goodTestDescriptions.length)];
			testTask.setDescription(goodRandomDescription);
			
			// Additionally asserting that the description changed correctly
			if (testTask.getDescription() != goodRandomDescription) {
				throw new RuntimeException("The description did not change properly");
			}
		});
	}
	
	// Testing that the description cannot be changed to something longer than 50 characters
	@Test
	@DisplayName("Testing that the description cannot be changed to something longer than 50 characters")
	void testDescriptionChangeLengthBad() {
		assertThrows(IllegalArgumentException.class, () -> {testTask.setDescription(description51Char);});
	}
	
	// Testing that the description can be changed to something that is 50 characters
	@Test
	@DisplayName("Testing that the description can be changed to something that is 50 characters")
	void testDescriptionChangeLengthGood() {
		assertDoesNotThrow(() -> {
			testTask.setDescription(description50Char);
			
			// Additionally asserting that the description changed correctly
			if (testTask.getDescription() != description50Char) {
				throw new RuntimeException("The description did not change to the 50 char description properly");
			}
		});
	}
}
