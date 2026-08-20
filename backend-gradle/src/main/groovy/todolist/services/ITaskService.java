package todolist.services;

import todolist.model.Task;
import todolist.model.TaskStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ITaskService {
	void addTask(String name, String description, LocalDateTime dateTimeFinished, Integer priorityLevel, String category, TaskStatus status);
	
	List<Task> listTasks();
	
	List<Task> searchTaskById(int id);
	
	List<Task> filterTasksByCategory(String category);
	
	List<Task> filterTasksByPriority(int priorityLevel);
	
	List<Task> filterTasksByStatus(TaskStatus status);
	
	List<Task> filterTasksByDate(LocalDate dateFinished);
	
	List<Task> filterTasksByTerm(String term);
	
	boolean updateTask(int id, String name, String description, LocalDateTime dateTimeFinished, Integer priorityLevel, String category, TaskStatus status);
	
	boolean deleteTask(int id);
	
	boolean updateTaskStatus(int id, TaskStatus newStatus);
}
