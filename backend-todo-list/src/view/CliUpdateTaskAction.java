package view;

import model.TaskStatus;
import services.TaskService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CliUpdateTaskAction {
	static void cliUpdateTask(Cli cli) {
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
			
			System.out.println("+================================================+");
			System.out.println("|               Atualizar Tarefa                 |");
			System.out.println("+================================================+");
			System.out.print("ID da tarefa: ");
			int id = Integer.parseInt(cli.scanner.nextLine());
			
			System.out.print("Nome: ");
			String name = cli.scanner.nextLine();
			
			System.out.print("Descrição: ");
			String description = cli.scanner.nextLine();
			
			System.out.print("Data e hora final (dd/MM/yyyy HH:mm): ");
			LocalDateTime dateTimeFinished = LocalDateTime.parse(cli.scanner.nextLine(), dtf);
			while (dateTimeFinished.isBefore(LocalDateTime.now())) {
				System.out.print("\nErro: Insira uma data/hora final correta (futuro e dd/MM/yyyy HH:mm): ");
				dateTimeFinished = LocalDateTime.parse(cli.scanner.nextLine(), dtf);
			}
			
			System.out.print("Nível de prioridade (1 à 5): ");
			int priorityLevel = Integer.parseInt(cli.scanner.nextLine());
			while (priorityLevel < 1 || priorityLevel > 5) {
				System.out.print("\nErro: Insira um nível de prioridade correto (1 à 5): ");
				priorityLevel = Integer.parseInt(cli.scanner.nextLine());
			}
			
			System.out.print("Categoria: ");
			String category = cli.scanner.nextLine();
			
			System.out.print("Status (1-TODO, 2-DOING, 3-DONE): ");
			int optionStatus = Integer.parseInt(cli.scanner.nextLine());
			while (optionStatus < 1 || optionStatus > 3) {
				System.out.print("Erro: Insira um status correto (1-TODO, 2-DOING, 3-DONE): ");
				optionStatus = Integer.parseInt(cli.scanner.nextLine());
			}
			TaskStatus status = (optionStatus == 2) ? TaskStatus.DOING : (optionStatus == 3) ? TaskStatus.DONE : TaskStatus.TODO;
			
			TaskService taskService = new TaskService();
			boolean updatedTask = taskService.updateTask(
					id,
					name,
					description,
					dateTimeFinished,
					priorityLevel,
					category,
					status
			);
			
			System.out.println("+================================================+");
			if (updatedTask) {
				System.out.println("Terafa atualizada com sucesso!");
			}
			else {
				System.out.println("Erro: ID inexistente!");
			}
			System.out.println("+================================================+");
			cli.pause();
		}
		catch (Exception e) {
			System.out.println("+================================================+");
			System.out.println("Erro: Adicione a entrada correta (Atualizar Tarefa)");
			System.out.println("+================================================+");
			cli.pause();
		}
	}
}
