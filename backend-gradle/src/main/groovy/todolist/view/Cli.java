package todolist.view;

import todolist.services.TaskService;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Cli {
	Scanner scanner;
	DateTimeFormatter dateTimeFormatter;
	private final TaskService taskService;
	
	public Cli(TaskService taskService) {
		this.scanner = new Scanner(System.in);
		this.dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		this.taskService = taskService;
	}
	
	public TaskService getTaskService() {
		return taskService;
	}
	
	public void cliMainMenu() {
		CliMenuAction.cliManu(this);
	}
	
	public void cliAddTask() {
		CliAddTaskAction.cliAddTask(this);
	}
	
	public void cliUpdateTask() {
		CliUpdateTaskAction.cliUpdateTask(this);
	}
	
	public void cliListAllTasks() {
		CliListTasksAction.cliListAllTasks(this);
	}
	
	public void cliListPerCategory() {
		CliFilterByCategoryAction.cliListPerCategory(this);
	}
	
	public void cliListPerPriority() {
		CliFilterByPriorityAction.cliListPerPriority(this);
	}
	
	public void cliListPerStatus() {
		CliFilterByStatusAction.cliListPerStatus(this);
	}
	
	public void cliFilterPerDate() {
		CliFilterByDateAction.cliFilterPerDate(this);
	}
	
	public void cliDeleteTask() {
		CliDeleteTaskAction.cliDeleteTask(this);
	}
	
	public void pause() {
		System.out.println("Aperte \"Enter\" para continuar");
		scanner.nextLine();
	}
}
