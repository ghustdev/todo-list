package view;

import model.Task;
import model.TaskStatus;
import services.TaskService;

import java.util.List;

public class CliListPerStatusAction {
	static void cliListPerStatus(Cli cli) {
		try {
			TaskService taskService = new TaskService();
			
			System.out.println("+================================================+");
			System.out.println("|           Listar Tarefas por Status            |");
			System.out.println("+================================================+");
			System.out.print("Status (1-TODO, 2-DOING, 3-DONE): ");
			int optionStatus = Integer.parseInt(cli.scanner.nextLine());
			while (optionStatus < 1 || optionStatus > 3) {
				System.out.print("Erro: Insira um status correto (1-TODO, 2-DOING, 3-DONE): ");
				optionStatus = Integer.parseInt(cli.scanner.nextLine());
			}
			TaskStatus status = (optionStatus == 2) ? TaskStatus.DOING : (optionStatus == 3) ? TaskStatus.DONE : TaskStatus.TODO;
			
			List<Task> tasks = taskService.listTasksPerStatus(status);
			
			if (tasks == null || tasks.isEmpty()) {
				System.out.println("Nenhuma tarefa encontrada para esse status");
				System.out.println("+================================================+");
				cli.pause();
				return;
			}
			
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
			System.out.println("Erro: Algum erro em listar tarefas por status");
			System.out.println("+================================================+");
			cli.pause();
		}
	}
}
