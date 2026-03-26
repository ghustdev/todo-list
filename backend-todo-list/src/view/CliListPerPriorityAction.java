package view;

import model.Task;
import services.TaskService;

import java.util.List;

public class CliListPerPriorityAction {
	static void cliListPerPriority(Cli cli) {
		try {
			TaskService taskService = new TaskService();
			
			System.out.println("+================================================+");
			System.out.println("|         Listar Tarefas por Prioridade          |");
			System.out.println("+================================================+");
			System.out.print("Insira a prioridade (1 a 5): ");
			int priorityLevel = Integer.parseInt(cli.scanner.nextLine());
			while (priorityLevel < 1 || priorityLevel > 5) {
				System.out.print("Erro: Insira um nível de prioridade correto (1 à 5): ");
				priorityLevel = Integer.parseInt(cli.scanner.nextLine());
			}
			
			List<Task> tasks = taskService.listTasksPerPriority(priorityLevel);
			
			if (tasks == null || tasks.isEmpty()) {
				System.out.println("Nenhuma tarefa encontrada para essa prioridade");
				System.out.println("+================================================+");
				cli.pause();
				return;
			}
			
			for (Task task : tasks) {
				System.out.println("Id: " + task.getId());
				System.out.println("Nome: " + task.getName());
				System.out.println("Descricao: " + task.getDescription());
				System.out.println("Data final (dd/MM/yyyy HH:mm): " + task.getDateTimeFinished().format(cli.dateTimeFormatter));
				System.out.println("Prioridade: " + task.getPriorityLevel());
				System.out.println("Status: " + task.getStatus());
				System.out.println("+================================================+");
			}
			cli.pause();
		}
		catch (Exception e) {
			System.out.println("+================================================+");
			System.out.println("Erro: Algum erro em listar tarefas por prioridade");
			System.out.println("+================================================+");
			cli.pause();
		}
	}
}
