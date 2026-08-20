package todolist;

import todolist.repository.TaskRepository;
import todolist.services.TaskService;
import todolist.view.Cli;

public class Main {
	public static void main(String[] args) {
		
		TaskRepository repository = new TaskRepository();
		TaskService taskService = new TaskService(repository);
		Cli cli = new Cli(taskService);
		
		cli.cliMainMenu();
	}
}
