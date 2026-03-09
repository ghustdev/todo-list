package services;

import model.Task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AlarmNotifier implements Runnable {
	private static final long DEFAULT_POLL_INTERVAL_MILLIS = 10_000;

	private final TaskService taskService;
	private final DateTimeFormatter formatter;
	private final Set<Integer> notifiedTaskIds;
	private final long pollIntervalMillis;
	private volatile boolean running;

	public AlarmNotifier() {
		this.taskService = new TaskService();
		this.formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		this.notifiedTaskIds = new HashSet<>();
		this.pollIntervalMillis = DEFAULT_POLL_INTERVAL_MILLIS;
		this.running = true;
	}

	public void stop() {
		running = false;
	}

	@Override
	public void run() {
		while (running) {
			try {
				checkAndNotifyAlarms();
				Thread.sleep(pollIntervalMillis);
			}
			catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				break;
			}
		}
	}

	private void checkAndNotifyAlarms() {
		List<Task> tasksWithAlarmDue = taskService.listTasksWithAlarmDue(LocalDateTime.now());
		Set<Integer> currentTaskIds = new HashSet<>();

		for (Task task : tasksWithAlarmDue) {
			currentTaskIds.add(task.getId());
			if (!notifiedTaskIds.contains(task.getId())) {
				printAlarm(task);
				notifiedTaskIds.add(task.getId());
			}
		}

		// Remove tarefas que saíram da janela para poder notificar novamente no futuro.
		notifiedTaskIds.retainAll(currentTaskIds);
	}

	private void printAlarm(Task task) {
		synchronized (System.out) {
			System.out.println();
			System.out.println("+================================================+");
			System.out.println("|                 NOTIFICACAO                    |");
			System.out.println("+================================================+");
			System.out.println("Tarefa: " + task.getName());
			System.out.println("Termino previsto: " + task.getDateTimeFinished().format(formatter));
			System.out.println("Disparo com antecedencia de: " + task.getAlarmAdvanceMinutes() + " minutos");
			System.out.println("+================================================+");
			System.out.print("Selecione uma ação: ");
		}
	}
}
