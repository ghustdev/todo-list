package view;

import model.Task;
import services.TaskService;

import java.util.List;

public class CliListAllTasksAction {
	static void cliListAllTasks(Cli cli) {
		try {
			TaskService taskService = new TaskService();
			
			System.out.println("+================================================+");
			System.out.println("|                Listar Tarefas                  |");
			System.out.println("+================================================+");
			
			List<Task> tasks = taskService.listTasks();
			
			if (tasks == null || tasks.isEmpty()) {
				System.out.println("Nenhuma tarefa cadastrada");
				System.out.println("+================================================+");
				cli.pause();
				return;
			}
			
			for (Task task : tasks) {
				System.out.println("Id: " + task.getId());
				System.out.println("Nome: " + task.getName());
				System.out.println("Descricao: " + task.getDescription());
				System.out.println("Data final (dd/MM/yyyy HH:mm): " + task.getDateTimeFinished().format(cli.dateTimeFormatter));
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
