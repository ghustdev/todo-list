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
				System.out.println("[7] - Filtro Tarefas por data");
				System.out.println("[0] - Encerrar programa");
				System.out.println("\nSelecione uma ação:");
				System.out.println("+================================================+");
				
				int optionMenu = cli.scanner.nextInt();
				cli.scanner.nextLine();
				
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
					cli.cliFilterPerData();
				}
				else if (optionMenu == 0) {
					cli.scanner.close();
					break;
				}
				else {
					System.out.println("+================================================+");
					System.out.println("Erro: Insira uma opção válida");
					System.out.println("+================================================+");
					System.out.println("Aperte \"Enter\" para continuar");
					cli.scanner.nextLine();
				}
			} catch (Exception e) {
				System.out.println("+================================================+");
				System.out.println("Erro: Adicione a entrada correta (números de 0 - 6)");
				System.out.println("+================================================+");
				System.out.println("Aperte \"Enter\" para continuar");
				cli.scanner.nextLine();
			}
		}
	}
}
