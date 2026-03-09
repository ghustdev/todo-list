package repository;

import model.Task;
import model.TaskStatus;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository {
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	String path = "tasks.csv";
	
	// Adicionar tarefas no arquivo
	public void saveTask(List<Task> tasks) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
			for (Task task : tasks) {
				bw.write(
						task.getId() + ";" +
						task.getName() + ";" +
						task.getDescription() + ";" +
						task.getDateFinished().format(dtf) + ";" +
						task.getPriorityLevel() + ";" +
						task.getCategory() + ";" +
						task.getStatus()
				);
				bw.newLine();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Ler tarefas do arquivo
	public List<Task> getTasks() {
		List<Task> tasks = new ArrayList<>();
		File file = new File(path);
		
		// Caso a lista seja vazia
		if (!file.exists()) {
			return tasks;
		}
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] data = line.split(";");

				int  id = Integer.parseInt(data[0]);
				String name = data[1];
				String description =  data[2];
				LocalDate dateFinished  = LocalDate.parse(data[3], dtf);
				int priorityLevel =  Integer.parseInt(data[4]);
				String category =  data[5];
				TaskStatus status =  TaskStatus.valueOf(data[6]);
				
				Task task = new Task(id, name, description, dateFinished, priorityLevel, category, status);
				tasks.add(task);
			}
		}
		catch (IOException e) {
			System.out.println("+================================================+");
			System.out.println("Erro ao ler dados no arquivo - " + e.getMessage());
			System.out.println("+================================================+");
			System.out.println("Aperte \"Enter\" para continuar");
		}
		catch (NumberFormatException e) {
			System.out.println("+================================================+");
			System.out.println("Erro ao processar dados no arquivo - " + e.getMessage());
			System.out.println("+================================================+");
			System.out.println("Aperte \"Enter\" para continuar");
		}
		
		return tasks;
	}
}
