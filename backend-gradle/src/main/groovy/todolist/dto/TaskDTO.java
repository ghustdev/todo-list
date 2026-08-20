package todolist.dto;

import todolist.model.TaskStatus;

import java.time.LocalDateTime;

public class TaskDTO {
	public String name;
	public String description;
	public LocalDateTime dateTimeFinished;
	public int priorityLevel;
	public TaskStatus status;
	public String category;
	
	public TaskDTO(String name, String description, LocalDateTime dateTimeFinished, int priorityLevel, String category, TaskStatus status) {
	}
}
