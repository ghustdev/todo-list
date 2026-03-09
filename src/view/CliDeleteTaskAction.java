package view;

import services.TaskService;

public class CliDeleteTaskAction {
	static void cliDeleteTask(Cli cli) {
		try {
			TaskService taskService = new TaskService();
			
			System.out.println("+================================================+");
			System.out.println("|                Deletar Tarefa                  |");
			System.out.println("+================================================+");
			System.out.print("Insira o ID para deletar: ");
			int id = cli.scanner.nextInt();
			cli.scanner.nextLine();
			
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
			cli.pause();
		}
		catch (Exception e) {
			System.out.println("+================================================+");
			System.out.println("Erro: Algum erro em deletar tarefa (Deletar Tarefa)");
			System.out.println("+================================================+");
			cli.pause();
		}
	}
}
