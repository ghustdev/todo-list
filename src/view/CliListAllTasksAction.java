package view;

import model.Task;
import services.TaskService;

import java.util.List;

public class CliListAllTasksAction {
	static void cliListAllTasks(Cli cli) {
		try {
			TaskService taskService = new TaskService();
			List<Task> tasks = taskService.listTasks();
			
			System.out.println("+================================================+");
			System.out.println("|                Listar Tarefas                  |");
			System.out.println("+================================================+");
			
			for (Task task : tasks) {
				System.out.println("Id: " + task.getId());
				System.out.println("Nome: " + task.getName());
				System.out.println("Descricao: " + task.getDescription());
				System.out.println("Data final (dd/MM/yyyy): " + task.getDateFinished());
				System.out.println("Categoria: " + task.getCategory());
				System.out.println("Status: " + task.getStatus());
				System.out.println("+================================================+");
			}
			cli.pause();
		}
		catch (Exception e) {
			System.out.println("+================================================+");
			System.out.println("Erro: Algum erro em listar tarefas (Listar Tarefas)");
			System.out.println("+================================================+");
			cli.pause();
		}
	}
}
