package view;

import model.Task;
import services.TaskService;

import java.util.List;

public class CliListPerCategoryAction {
	static void cliListPerCategory(Cli cli) {
		try {
			TaskService taskService = new TaskService();
			
			System.out.println("+================================================+");
			System.out.println("|          Listar Tarefas por Categoria          |");
			System.out.println("+================================================+");
			System.out.print("Insira a categoria: ");
			String category = cli.scanner.nextLine();
			
			List<Task> tasks = taskService.listTasksPerCategory(category);
			
			if (tasks == null || tasks.isEmpty()) {
				System.out.println("Nenhuma tarefa encontrada para essa categoria");
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
			System.out.println("Erro: Algum erro em listar tarefas por categoria");
			System.out.println("+================================================+");
			cli.pause();
		}
	}
}
