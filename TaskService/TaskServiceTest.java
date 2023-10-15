package Milestone1;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

@DisplayName("Task Service Testing Class")
class TaskServiceTest {

	// Test variables
	private String[] testTaskNames = new String[] {"Task name here", "Another task name"};
	
	private String[] testDescriptions = new String[] {"Wow a description", "OMG another description..."};
	
	TaskService testService = TaskService.getInstance();
	
	// placeholder variables
	private String goodRandomTaskName;
	private String goodRandomDescription;
	
	@BeforeEach
	void setupTest() {
		goodRandomTaskName = testTaskNames[ThreadLocalRandom.current().nextInt(0, testTaskNames.length)];
		goodRandomDescription = testDescriptions[ThreadLocalRandom.current().nextInt(0, testDescriptions.length)];
		testService.createNewTask(goodRandomTaskName, goodRandomDescription);
	}
	
	// Clean up each test by resetting the testService
	@AfterEach
	void cleanUpTest() {
		testService.resetTaskList();
	}
	
	
	
	
	/*
	 *  Test that task service is able to add a new task
	 */
	
	// Tests adding one task
	@Test
	@DisplayName("Tests adding one task")
	void testAddingTask() {
		assertDoesNotThrow(() -> {
			testService.createNewTask(goodRandomTaskName, goodRandomDescription);
			
			// Additionally asserting that the new task was created with the given instance variables
			if (testService.getTask("2").getName() != goodRandomTaskName) {
				throw new RuntimeException("The new task was not created with the correct name");
			} else if (testService.getTask("2").getDescription() != goodRandomDescription) {
				throw new RuntimeException("The new task was not created with the correct description");
			}
		});
	}
	
	// Tests adding two different tasks
	@Test
	@DisplayName("Tests adding two different tasks")
	void testAddingMultipleTasks() {
		assertDoesNotThrow(() -> {
			testService.createNewTask(goodRandomTaskName, goodRandomDescription);
			testService.createNewTask(goodRandomTaskName, goodRandomDescription);
			});
	}
	
	// Tests adding the same task 50 times
	@RepeatedTest(50)
	@DisplayName("Tests adding the same task 50 times")
	void testAddingManyTasks() {
		assertDoesNotThrow(() -> {testService.createNewTask(goodRandomTaskName, goodRandomDescription);});
	}
	
	
	/*
	 * Test that task service is able to delete a task by id
	 */
	
	// Tests deleting the first task from the before each function
	@Test
	@DisplayName("Tests deleting the first task from the before each function")
	void testRemovingOnlyTask() {
		int sizeBeforeRemoving = testService.getTaskList().size();
		assertDoesNotThrow(() -> {testService.deleteTask("1");});
		assertEquals(testService.getTaskList().size(), sizeBeforeRemoving - 1);	
	}
	
	// Tests re-adding the same task after deleting it
	@Test
	@DisplayName("Tests re-adding the same task after deleting it")
	void testRemovingAndAddingOnlyTask() {
		int sizeBeforeRemoving = testService.getTaskList().size();
		assertDoesNotThrow(() -> {
			testService.deleteTask("1");
			testService.createNewTask(goodRandomTaskName, goodRandomDescription);
			});
		assertEquals(testService.getTaskList().size(), sizeBeforeRemoving);
	}
	
	// Tests that adding several tasks, removing one and replacing it will set it back to a previously used id
	@Test
	@DisplayName("Tests that adding several tasks, removing one and replacing it will set it back to a previously used id")
	void testRemovingAndAddingOnPreviousId() {
		// Add three tasks
		testService.createNewTask(goodRandomTaskName, goodRandomDescription);
		testService.createNewTask(goodRandomTaskName, goodRandomDescription);
		testService.createNewTask(goodRandomTaskName, goodRandomDescription);
		int sizeBeforeRemoving = testService.getTaskList().size();
		
		// Delete the second task and assert the size change
		testService.deleteTask("2");
		assertEquals(testService.getTaskList().size(), sizeBeforeRemoving - 1);
		
		// Add back the task - which should be on id 2 - assert the tasks id
		testService.createNewTask(goodRandomTaskName, goodRandomDescription);
		assertEquals(testService.getTask("2").getId(), "2");
	}
	
	
	
	/*
	 * Test that task service is able to modify tasks
	 */
	
	// Tests that task service can update the name
	@Test
	@DisplayName("Tests that task service can update the name")
	void testUpdateName() {
		goodRandomTaskName = testTaskNames[ThreadLocalRandom.current().nextInt(0, testTaskNames.length)];
		testService.updateName("1", goodRandomTaskName);
		assertEquals(testService.getTask("1").getName(), goodRandomTaskName);
	}
	
	// Tests that task service can update the description
	@Test
	@DisplayName("Tests that task service can update the description")
	void testUpdateDescription() {
		goodRandomDescription = testDescriptions[ThreadLocalRandom.current().nextInt(0, testDescriptions.length)];
		testService.updateDescription("1", goodRandomDescription);
		assertEquals(testService.getTask("1").getDescription(), goodRandomDescription);
	}

}
