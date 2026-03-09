package view;

import model.Task;
import services.TaskService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CliFilterPerDateAction {
	static void cliFilterPerDate(Cli cli) {
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			TaskService taskService = new TaskService();
			
			System.out.println("+================================================+");
			System.out.println("|            Filtrar Tarefas por Data            |");
			System.out.println("+================================================+");
			System.out.print("Insira a data final (dd/MM/yyyy): ");
			LocalDate dateFinished = LocalDate.parse(cli.scanner.nextLine(), dtf);
			
			List<Task> tasks = taskService.filterTasksPerDate(dateFinished);
			
			if (tasks == null || tasks.isEmpty()) {
				System.out.println("Nenhuma tarefa encontrada para essa data");
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
			System.out.println("Erro: Algum erro em filtrar tarefas por data");
			System.out.println("+================================================+");
			cli.pause();
		}
	}
}
