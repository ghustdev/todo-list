package services;

import model.Task;
import model.TaskStatus;
import repository.TaskRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class TaskService {
	TaskRepository repository = new TaskRepository();
	
	public void addTask(String name, String description, LocalDate dateFinished, Integer priorityLevel, String category, TaskStatus status) {
		List<Task> listTasks = repository.getTasks();
		
		int maxId = listTasks.stream().mapToInt(Task::getId).max().orElse(0) + 1;
		
		Task newTask = new Task(maxId, name, description, dateFinished, priorityLevel, category, status);
		listTasks.add(newTask);
		repository.saveTask(listTasks);
	}
	
	public List<Task> listTasks() {
		if (repository.getTasks() == null) return null;
		return repository.getTasks();
	}
	
	public List<Task> searchIdTask(int id) {
		List<Task> listTasks = repository.getTasks();
		if (listTasks == null) return null;
		return listTasks.stream()
				.filter(task -> task.getId() == id)
				.collect(Collectors.toList());
	}
	
	public List<Task> listTasksPerCategory(String category) {
		List<Task> listTasks = repository.getTasks();
		return listTasks.stream()
				.filter(task -> task.getCategory().equalsIgnoreCase(category))
				.collect(Collectors.toList());
	}
	
	public List<Task> listTasksPerPriority(int priorityLevel) {
		List<Task> listTasks = repository.getTasks();
		return listTasks.stream()
				.filter(task -> task.getPriorityLevel() == priorityLevel)
				.collect(Collectors.toList());
	}
	
	public List<Task> listTasksPerStatus(TaskStatus status) {
		List<Task> listTasks = repository.getTasks();
		return listTasks.stream()
				.filter(task -> task.getStatus() == status)
				.collect(Collectors.toList());
	}
	
	public List<Task> filterTasksPerDate(LocalDate dateFinished) {
		List<Task> listTasks = repository.getTasks();
		return listTasks.stream()
				.filter(task -> task.getDateFinished().equals(dateFinished))
				.collect(Collectors.toList());
	}
	
	public boolean updateTask(int id, String name, String description, LocalDate dateFinished, Integer priorityLevel, String category, TaskStatus status) {
		List<Task> listTasks = repository.getTasks();
		
		for (int i = 0; i < listTasks.size(); i++) {
			Task task = listTasks.get(i);
			if (task.getId() == id) {
				Task updatedTask = new Task(id, name, description, dateFinished, priorityLevel, category, status);
				listTasks.set(i, updatedTask);
				repository.saveTask(listTasks);
				return true;
			}
		}
		
		return false;
	}
	
	public boolean deleteTask(int id) {
		List<Task> listTasks = repository.getTasks();
		
		boolean removedId = listTasks.removeIf(t -> t.getId() == id);
		
		if (removedId) {
			repository.saveTask(listTasks);
			return true;
		}
		return false;
	}
}
