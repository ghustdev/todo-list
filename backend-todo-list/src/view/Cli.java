package view;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Cli {
	Scanner scanner = new Scanner(System.in);
	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	
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
		CliListAllTasksAction.cliListAllTasks(this);
	}
	
	public void cliListPerCategory() {
		CliListPerCategoryAction.cliListPerCategory(this);
	}
	
	public void cliListPerPriority() {
		CliListPerPriorityAction.cliListPerPriority(this);
	}
	
	public void cliListPerStatus() {
		CliListPerStatusAction.cliListPerStatus(this);
	}
	
	public void cliFilterPerDate() {
		CliFilterPerDateAction.cliFilterPerDate(this);
	}
	
	public void cliDeleteTask() {
		CliDeleteTaskAction.cliDeleteTask(this);
	}
	
	public void pause() {
		System.out.println("Aperte \"Enter\" para continuar");
		scanner.nextLine();
	}
}
