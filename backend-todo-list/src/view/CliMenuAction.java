package view;

public class CliMenuAction {
	static void cliManu(Cli cli) {
		while (true) {
			try {
				System.out.println("+================================================+");
				System.out.println("|            Gerenciador de Tarefas              |");
				System.out.println("+================================================+");
				System.out.println("[1] - Adicionar Tarefa");
				System.out.println("[2] - Deletar Tarefa");
				System.out.println("[3] - Listar todas as Tarefas");
				System.out.println("[4] - Listar Tarefas por Categoria");
				System.out.println("[5] - Listar Tarefas por Prioridade");
				System.out.println("[6] - Listar Tarefas por Status");
				System.out.println("[7] - Filtrar Tarefas por Data");
				System.out.println("[8] - Atualizar Tarefa");
				System.out.println("[0] - Encerrar programa");
				System.out.println("+================================================+");
				System.out.print("Selecione uma ação: ");
				
				int optionMenu = Integer.parseInt(cli.scanner.nextLine());
				
				if (optionMenu == 1) {
					cli.cliAddTask();
				}
				else if (optionMenu == 2) {
					cli.cliDeleteTask();
				}
				else if (optionMenu == 3) {
					cli.cliListAllTasks();
				}
				else if (optionMenu == 4) {
					cli.cliListPerCategory();
				}
				else if (optionMenu == 5) {
					cli.cliListPerPriority();
				}
				else if (optionMenu == 6) {
					cli.cliListPerStatus();
				}
				else if (optionMenu == 7) {
					cli.cliFilterPerDate();
				}
				else if (optionMenu == 8) {
					cli.cliUpdateTask();
				}
				else if (optionMenu == 0) {
					cli.scanner.close();
					break;
				}
				else {
					System.out.println("+================================================+");
					System.out.println("Erro: Insira uma opção válida");
					System.out.println("+================================================+");
					cli.pause();
				}
			}
			catch (Exception e) {
				System.out.println("+================================================+");
				System.out.println("Erro: Adicione a entrada correta (números de 0 - 8)");
				System.out.println("+================================================+");
				cli.pause();
			}
		}
	}
}
