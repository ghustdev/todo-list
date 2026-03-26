# TODO List - Documentação Backend

Backend do sistema de gerenciamento de tarefas, desenvolvido em Java puro com arquitetura MVC e camada de Repository.

## Arquitetura

O backend segue o padrão MVC (Model-View-Controller) com uma camada adicional de Repository para separação de responsabilidades:

### 📦 Model (Modelo de Dados)
**Localização**: `src/model/`

Representa as entidades de domínio da aplicação.

- **Task.java**: Classe que define a estrutura de uma tarefa
  - Atributos: id, name, description, dateTimeFinished, priorityLevel, category, status
  
- **TaskStatus.java**: Enum com os possíveis status de uma tarefa
  - Valores: TODO, IN_PROGRESS, DONE

**Responsabilidade**: Definir apenas a estrutura dos dados, sem lógica de negócio ou persistência.

### 🎯 Service (Lógica de Negócio)
**Localização**: `src/services/`

Contém toda a lógica de negócio e regras da aplicação.

- **TaskService.java**: Gerencia operações de tarefas
  - `addTask()`: Adiciona nova tarefa com ID auto-incrementado
  - `listTasks()`: Lista todas as tarefas
  - `searchIdTask()`: Busca tarefa por ID
  - `listTasksPerCategory()`: Filtra por categoria
  - `listTasksPerPriority()`: Filtra por prioridade
  - `listTasksPerStatus()`: Filtra por status
  - `filterTasksPerDate()`: Filtra por data
  - `updateTask()`: Atualiza tarefa existente
  - `deleteTask()`: Remove tarefa por ID

**Responsabilidade**: Implementar regras de negócio, validações e coordenar operações entre View e Repository.

### 💾 Repository (Persistência)
**Localização**: `src/repository/`

Responsável pela comunicação com a camada de dados.

- **TaskRepository.java**: Gerencia leitura e escrita no arquivo CSV
  - `saveTask()`: Salva lista de tarefas no arquivo
  - `getTasks()`: Lê tarefas do arquivo

**Responsabilidade**: Isolar a lógica de persistência. Mudanças no formato de armazenamento (CSV → JSON → Banco de Dados) afetam apenas esta camada.

### 🖥️ View (Interface CLI)
**Localização**: `src/view/`

Interface de linha de comando para interação com o usuário.

- **Cli.java**: Menu principal e coordenação das ações
- **CliAddTaskAction.java**: Fluxo de adição de tarefa
- **CliListAllTasksAction.java**: Listagem de todas as tarefas
- **CliListPerCategoryAction.java**: Listagem por categoria
- **CliListPerPriorityAction.java**: Listagem por prioridade
- **CliListPerStatusAction.java**: Listagem por status
- **CliFilterPerDateAction.java**: Filtro por data
- **CliUpdateTaskAction.java**: Atualização de tarefa
- **CliDeleteTaskAction.java**: Exclusão de tarefa
- **CliMenuAction.java**: Interface para ações do menu

**Responsabilidade**: Exibir menus, capturar entrada do usuário e mostrar resultados. Não conhece detalhes de persistência.

### 🚀 Main
**Localização**: `src/Main.java`

Ponto de entrada da aplicação CLI.

## Estrutura de Arquivos

```
backend-todo-list/
├── src/
│   ├── model/
│   │   ├── Task.java
│   │   └── TaskStatus.java
│   ├── repository/
│   │   └── TaskRepository.java
│   ├── services/
│   │   └── TaskService.java
│   ├── view/
│   │   ├── Cli.java
│   │   ├── CliAddTaskAction.java
│   │   ├── CliDeleteTaskAction.java
│   │   ├── CliFilterPerDateAction.java
│   │   ├── CliListAllTasksAction.java
│   │   ├── CliListPerCategoryAction.java
│   │   ├── CliListPerPriorityAction.java
│   │   ├── CliListPerStatusAction.java
│   │   ├── CliMenuAction.java
│   │   └── CliUpdateTaskAction.java
│   └── Main.java
├── tasks.csv
├── TODO-list-java.iml
└── README.md
```

## Requisitos

- Java 8+ (recomendado Java 11+)
- IDE Java (IntelliJ IDEA, Eclipse, VS Code com extensões Java)

## Como Executar

1. Abra o projeto na sua IDE
2. Certifique-se de que o JDK está configurado
3. Execute a classe `Main.java`
4. Interaja com o menu CLI

## Formato de Persistência

As tarefas são armazenadas em `tasks.csv` com o seguinte formato:

```
id;nome;descricao;data_final;prioridade;categoria;status
```

**Exemplo**:
```
1;Estudar Java;Revisar conceitos de POO;25/12/2024 18:00;1;Estudos;TODO
2;Reunião;Apresentar projeto;26/12/2024 14:30;2;Trabalho;IN_PROGRESS
```

## Vantagens da Arquitetura

### Separação de Responsabilidades
Cada camada tem uma função específica, facilitando manutenção e testes.

### Facilidade de Mudança
- Trocar CSV por banco de dados? Altere apenas o Repository
- Criar interface web? Substitua apenas a View
- Adicionar regras de negócio? Modifique apenas o Service

### Testabilidade
Cada camada pode ser testada independentemente.

### Escalabilidade
Fácil adicionar novas funcionalidades sem impactar código existente.

## Melhorias Futuras

- [ ] Implementar API REST para comunicação com frontend web
- [ ] Migrar persistência para banco de dados (PostgreSQL/MySQL)
- [ ] Adicionar autenticação e autorização
- [ ] Implementar testes unitários e de integração
- [ ] Adicionar logging estruturado
- [ ] Criar documentação de API (Swagger/OpenAPI)

## Licença

Este projeto está sob a licença de `Gustavo Cardoso` e `Acelera ZG`.
