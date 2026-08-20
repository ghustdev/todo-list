package todolist.services;

import todolist.model.Task;
import todolist.model.TaskStatus;
import todolist.repository.TaskRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class TaskService implements ITaskService {
	private final TaskRepository repository;
	
	public TaskService(TaskRepository repository) {
		this.repository = repository;
	}
	
	public void addTask(String name, String description, LocalDateTime dateTimeFinished, Integer priorityLevel, String category, TaskStatus status) {
		List<Task> listTasks = repository.getTasks();
		
		int maxId = listTasks.stream().mapToInt(Task::getId).max().orElse(0) + 1;
		
		Task newTask = new Task(maxId, name, description, dateTimeFinished, priorityLevel, category, status);
		listTasks.add(newTask);
		repository.saveTask(listTasks);
	}
	
	public List<Task> listTasks() {
		return repository.getTasks();
	}
	
	public List<Task> searchTaskById(int id) {
		List<Task> listTasks = repository.getTasks();
		if (listTasks == null) return null;
		return listTasks.stream().filter(task -> task.getId() == id).collect(Collectors.toList());
	}
	
	public List<Task> filterTasksByCategory(String category) {
		List<Task> listTasks = repository.getTasks();
		return listTasks.stream().filter(task -> task.getCategory().equalsIgnoreCase(category)).collect(Collectors.toList());
	}
	
	public List<Task> filterTasksByPriority(int priorityLevel) {
		List<Task> listTasks = repository.getTasks();
		return listTasks.stream().filter(task -> task.getPriorityLevel() == priorityLevel).collect(Collectors.toList());
	}
	
	public List<Task> filterTasksByStatus(TaskStatus status) {
		List<Task> listTasks = repository.getTasks();
		return listTasks.stream().filter(task -> task.getStatus() == status).collect(Collectors.toList());
	}
	
	public List<Task> filterTasksByDate(LocalDate dateFinished) {
		List<Task> listTasks = repository.getTasks();
		return listTasks.stream().filter(task -> task.getDateTimeFinished().toLocalDate().equals(dateFinished)).collect(Collectors.toList());
	}
	
	public List<Task> filterTasksByTerm(String term) {
		List<Task> listTasks = repository.getTasks();
		if (listTasks == null) return java.util.Collections.emptyList();
		if (term == null || term.trim().isEmpty()) {
			return listTasks;
		}
		String lowerTerm = term.toLowerCase().trim();
		return listTasks.stream().filter(task -> (task.getName() != null && task.getName().toLowerCase().contains(lowerTerm)) || (task.getDescription() != null && task.getDescription().toLowerCase().contains(lowerTerm))).collect(Collectors.toList());
	}
	
	public boolean updateTask(int id, String name, String description, LocalDateTime dateTimeFinished, Integer priorityLevel, String category, TaskStatus status) {
		List<Task> listTasks = repository.getTasks();
		
		for (int i = 0; i < listTasks.size(); i++) {
			Task task = listTasks.get(i);
			if (task.getId() == id) {
				Task updatedTask = new Task(id, name, description, dateTimeFinished, priorityLevel, category, status);
				listTasks.set(i, updatedTask);
				repository.saveTask(listTasks);
				return true;
			}
		}
		
		return false;
	}
	
	public boolean deleteTask(int id) {
		List<Task> listTasks = repository.getTasks();
		if (listTasks == null) return false;
		
		boolean removedId = listTasks.removeIf(t -> t.getId() == id);
		
		if (removedId) {
			repository.saveTask(listTasks);
			return true;
		}
		return false;
	}
	
	public boolean updateTaskStatus(int id, TaskStatus newStatus) {
		List<Task> listTasks = repository.getTasks();
		if (listTasks == null) return false;
		
		for (int i = 0; i < listTasks.size(); i++) {
			Task task = listTasks.get(i);
			if (task.getId() == id) {
				Task updatedTask = new Task(task.getId(), task.getName(), task.getDescription(), task.getDateTimeFinished(), task.getPriorityLevel(), task.getCategory(), newStatus);
				listTasks.set(i, updatedTask);
				repository.saveTask(listTasks);
				return true;
			}
		}
		return false;
	}
}
