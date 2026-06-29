package todolist.services

import spock.lang.Specification
import spock.lang.Unroll
import todolist.model.Task
import todolist.model.TaskStatus
import todolist.repository.TaskRepository

import java.time.LocalDateTime

class TaskServiceTest extends Specification {
    TaskService taskService
    TaskRepository repository

    def setup() {
        repository = Mock(TaskRepository)
        taskService = new TaskService(repository)
    }



    @Unroll
    def "Teste para AddTask"() {
        given: "Setup dosdados de cadastro"
        def tasks = [
                new Task(1, "Tarefa 1", "Descrição 1", LocalDateTime.now(), 1, "Estudos", TaskStatus.TODO),
                new Task(2, "Tarefa 2", "Descrição 2", LocalDateTime.now(), 2, "Trabalho", TaskStatus.DONE)
        ]
        repository.getTasks() >> tasks

        when: "O serviço é chamado para adicionar a tarefa"
        taskService.addTask("Estudar", "Estudar Spock", LocalDateTime.now(), 5, "Estudos", TaskStatus.TODO)
        def result = repository.getTasks()

        then: "Validação"
        result.size() == 3
        result[2].id == 3
        result[2].name == "Estudar"
    }



    @Unroll
    def "Teste para ListTasks"() {
        given:
        def tasks = [
                new Task(1, "Tarefa 1", "Descrição 1", LocalDateTime.now(), 1, "Estudos", TaskStatus.TODO),
                new Task(2, "Tarefa 2", "Descrição 2", LocalDateTime.now(), 2, "Trabalho", TaskStatus.DONE)
        ]
        repository.getTasks() >> tasks

        when:
        def result = taskService.listTasks()

        then:
        result.size() == 2
        result[0].id == 1
        result[0].name == "Tarefa 1"
        result[1].id == 2
        result[1].name == "Tarefa 2"
    }
}
