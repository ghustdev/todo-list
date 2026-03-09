package view;

import model.TaskStatus;
import services.TaskService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CliAddTaskAction {
	static void cliAddTask(Cli cli) {
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			
			System.out.println("+================================================+");
			System.out.println("|               Adicionar Tarefa                 |");
			System.out.println("+================================================+");
			System.out.print("Nome: ");
			String name = cli.scanner.nextLine();
			
			System.out.print("Descrição: ");
			String description = cli.scanner.nextLine();
			
			System.out.print("Data final (dd/MM/yyyy): ");
			LocalDate dateFinished = LocalDate.parse(cli.scanner.nextLine(), dtf);
			while (dateFinished.isBefore(LocalDate.now())) {
				System.out.print("\nErro: Insira uma data final correta (após hoje e dd/MM/yyyy): ");
				dateFinished = LocalDate.parse(cli.scanner.nextLine(), dtf);
			}
			
			System.out.print("Nível de prioridade (1 à 5): ");
			int priorityLevel = cli.scanner.nextInt();
			while (priorityLevel < 1 || priorityLevel > 5) {
				System.out.print("\nErro: Insira um nível de prioridade correto (1 à 5): ");
				priorityLevel = cli.scanner.nextInt();
			}
			cli.scanner.nextLine();
			
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
			taskService.addTask(name, description,  dateFinished, priorityLevel, category, status);
			
			System.out.println("+================================================+");
			System.out.println("Terafa adicionada com sucesso!");
			System.out.println("+================================================+");
			cli.pause();
		}
		catch (Exception e) {
			System.out.println("+================================================+");
			System.out.println("Erro: Adicione a entrada correta (Adicionar Tarefas)");
			System.out.println("+================================================+");
			cli.pause();
		}
	}
}
