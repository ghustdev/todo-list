package view;

import model.Task;
import model.TaskStatus;
import services.TaskService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Cli {
	Scanner scanner = new Scanner(System.in);
	
	public void cliMainMenu() {
		while (true) {
			try {
				System.out.println("+================================================+");
				System.out.println("|            Gerenciador de Tarefas              |");
				System.out.println("+================================================+");
				System.out.println("\nSelecione uma ação:");
				System.out.println("[1] - Adicionar Tarefa");
				System.out.println("[2] - Listar Tarefa");
				System.out.println("[3] - Editar Tarefa");
				System.out.println("[4] - Deletar Tarefa");
				System.out.println("[5] - Quantidade por status");
				System.out.println("[6] - Filtro por data");
				System.out.println("[0] - Encerrar programa");
				System.out.println("+================================================+");
				System.out.println("Por enquanto, apenas Adicioner, Listar e Encerrar funcionam");
				System.out.println("+================================================+");
				
				int optionMenu = scanner.nextInt();
				scanner.nextLine();
				
				if (optionMenu == 1) {
					cliAddTask();
				}
				else if (optionMenu == 2) {
					cliListTasks();
				}
				else if (optionMenu == 3) {
					System.out.println("+================================================+");
					System.out.println("Editar ainda não funciona...");
					System.out.println("+================================================+");
					System.out.println("Aperte \"Enter\" para continuar");
					scanner.nextLine();
				}
				else if (optionMenu == 4) {
					cliDeleteTask();
				}
				else if (optionMenu == 5) {
					System.out.println("+================================================+");
					System.out.println("Quantidade por status ainda não funciona...");
					System.out.println("+================================================+");
					System.out.println("Aperte \"Enter\" para continuar");
					scanner.nextLine();
				}
				else if (optionMenu == 6) {
					System.out.println("+================================================+");
					System.out.println("Filtro por data ainda não funciona...");
					System.out.println("+================================================+");
					System.out.println("Aperte \"Enter\" para continuar");
					scanner.nextLine();
				}
				else if (optionMenu == 0) {
					scanner.close();
					break;
				}
				else {
					System.out.println("+================================================+");
					System.out.println("Erro: Insira uma opção válida");
					System.out.println("+================================================+");
					System.out.println("Aperte \"Enter\" para continuar");
					scanner.nextLine();
				}
			} catch (Exception e) {
				System.out.println("+================================================+");
				System.out.println("Erro: Adicione a entrada correta (números de 0 - 6)");
				System.out.println("+================================================+");
				System.out.println("Aperte \"Enter\" para continuar");
				scanner.nextLine();
			}
		}
	}
	
	public void cliAddTask() {
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			
			System.out.println("+================================================+");
			System.out.println("|               Adicionar Tarefa                 |");
			System.out.println("+================================================+");
			System.out.print("Nome: ");
			String name = scanner.nextLine();
			
			System.out.print("Descrição: ");
			String description = scanner.nextLine();
			
			System.out.print("Data final (dd/MM/yyyy): ");
			LocalDate dateFinished = LocalDate.parse(scanner.nextLine(), dtf);
			while (dateFinished.isBefore(LocalDate.now())) {
				System.out.print("\nErro: Insira uma data final correta (após hoje e dd/MM/yyyy): ");
				dateFinished = LocalDate.parse(scanner.nextLine(), dtf);
			}
			
			System.out.print("Nível de prioridade (1 à 5): ");
			int priorityLevel = scanner.nextInt();
			while (priorityLevel < 1 || priorityLevel > 5) {
				System.out.print("\nErro: Insira um nível de prioridade correto (1 à 5): ");
				priorityLevel = scanner.nextInt();
			}
			scanner.nextLine();
			
			System.out.print("Categoria: ");
			String category = scanner.nextLine();
			
			System.out.print("Status (1-TODO, 2-DOING, 3-DONE): ");
			int optionStatus = Integer.parseInt(scanner.nextLine());
			while (optionStatus < 1 || optionStatus > 3) {
				System.out.print("Erro: Insira um status correto (1-TODO, 2-DOING, 3-DONE): ");
				optionStatus = Integer.parseInt(scanner.nextLine());
			}
			TaskStatus status = (optionStatus == 2) ? TaskStatus.DOING : (optionStatus == 3) ? TaskStatus.DONE : TaskStatus.TODO;
			
			TaskService taskService = new TaskService();
			taskService.addTask(name, description,  dateFinished, priorityLevel, category, status);
			
			System.out.println("+================================================+");
			System.out.println("Terafa adicionada com sucesso!");
			System.out.println("+================================================+");
			System.out.println("Aperte \"Enter\" para continuar");
			scanner.nextLine();
		}
		catch (Exception e) {
			System.out.println("+================================================+");
			System.out.println("Erro: Adicione a entrada correta (Adicionar Tarefas)");
			System.out.println("+================================================+");
			System.out.println("Aperte \"Enter\" para continuar");
			scanner.nextLine();
		}
	}
	
	public void cliListTasks() {
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
			System.out.println("Aperte \"Enter\" para continuar");
			scanner.nextLine();
		}
		catch (Exception e) {
			System.out.println("+================================================+");
			System.out.println("Erro: Algum erro em listar tarefas (Listar Tarefas)");
			System.out.println("+================================================+");
			System.out.println("Aperte \"Enter\" para continuar");
		}
	}
	
	public void cliDeleteTask() {
		try {
			TaskService taskService = new TaskService();
			
			System.out.println("+================================================+");
			System.out.println("|                Deletar Tarefa                  |");
			System.out.println("+================================================+");
			System.out.print("Insira o ID para deletar: ");
			int id = scanner.nextInt();
			scanner.nextLine();
			
			boolean removedId = taskService.deleteTask(id);
			
			if (removedId) {
				System.out.println("+================================================+");
				System.out.println("Terafa excluída com sucesso!");
			}
			else {
				System.out.println("+================================================+");
				System.out.println("Erro: ID inexistente!");
			}
			
			System.out.println("+================================================+");
			System.out.println("Aperte \"Enter\" para continuar");
			scanner.nextLine();
		}
		catch (Exception e) {
			System.out.println("+================================================+");
			System.out.println("Erro: Algum erro em deletar tarefa (Deletar Tarefa)");
			System.out.println("+================================================+");
			System.out.println("Aperte \"Enter\" para continuar");
		}
	}
}
