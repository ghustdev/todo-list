package todolist.repository;

import todolist.model.Task;

import java.util.List;

public interface ITaskRepository {
	void saveTask(List<Task> tasks);
	List<Task> getTasks();
}
