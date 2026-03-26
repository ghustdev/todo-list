package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Task {
	
	private int id;
	private String name;
	private String description;
	private LocalDateTime dateTimeFinished;
	private int priorityLevel;
	private TaskStatus status;
	private String category;
	
	public Task() {}
	
	public Task(
			int id,
			String name,
			String description,
			LocalDateTime dateTimeFinished,
			Integer priorityLevel,
			String category,
			TaskStatus status
	) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.dateTimeFinished = dateTimeFinished;
		this.priorityLevel = priorityLevel;
		this.category = category;
		this.status = status;
	}

	public Task(int id, String name, String description, LocalDate dateFinished, Integer priorityLevel, String category, TaskStatus status) {
		this(id, name, description, dateFinished.atTime(23, 59), priorityLevel, category, status);
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
	
	public LocalDate getDateFinished() {
		return dateTimeFinished.toLocalDate();
	}

	public LocalDateTime getDateTimeFinished() {
		return dateTimeFinished;
	}
	
	public int getPriorityLevel() {
		return priorityLevel;
	}
	
	public String getCategory() {
		return category;
	}
	
	public TaskStatus getStatus() {
		return status;
	}
}
