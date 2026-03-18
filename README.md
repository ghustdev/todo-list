# TODO List Java - Projeto Completo

Projeto de gerenciamento de tarefas com arquitetura separada em frontend e backend, desenvolvido em Java puro com persistência em arquivo CSV.

<img width="1910" height="940" alt="mvp todo list" src="https://github.com/user-attachments/assets/1d482761-c6d5-4e72-b362-87c020057dab" />

#### Acesse o frontend da aplicação:
[todolist.io](https://todo-list-java-snowy.vercel.app/)

## Estrutura do Projeto

O projeto está organizado em duas partes principais:

### 📁 Backend (`backend-todo-list/`)
Contém toda a lógica de negócio, persistência de dados e modelo de domínio em Java.
- **Arquitetura**: MVC com camada de Repository
- **Tecnologia**: Java 8+
- **Persistência**: Arquivo CSV

[📖 Documentação completa do Backend](./backend-todo-list/README.md)

### 📁 Frontend (`frontend-todo-list/`)
Interface web moderna para interação com o sistema de tarefas.
- **Tecnologia**: JavaScript vanilla
- **Interface**: Web (HTML/CSS/JS)

[📖 Documentação do Frontend](./frontend-todo-list/README.md)

## Funcionalidades Gerais

- ✅ Cadastro de tarefas com nome, descrição, data final, prioridade, categoria e status
- ✅ Listagem de tarefas armazenadas
- ✅ Edição de tarefas
- ✅ Filtragem de tarefas por categoria, prioridade, status e data
- ✅ Exclusão de tarefas por ID
- ✅ Sistema de alarmes para tarefas
- ✅ Persistência local em `tasks.csv`

## Requisitos

- **Backend**: Java 8+ (recomendado 11+)
- **Frontend**: Navegador web moderno

## Como Executar

### Backend (CLI)
1. Navegue até `backend-todo-list/`
2. Abra o projeto na sua IDE (ex.: IntelliJ)
3. Execute a classe `Main.java`
4. As informações são salvas em .csv

### Frontend (Web)
1. Navegue até `frontend-todo-list/`
2. Abra o arquivo `index.html` no navegador
3. Ou utilize um servidor local (ex.: Live Server)
4. As informações (states) são salvas em LocalStorage

## Persistência

As tarefas são salvas em `tasks.csv`, no formato:
```
id;nome;descricao;data_final;prioridade;categoria;status;alarme_ativo;minutos_antecedencia
```

## Licença

Este projeto está sob a licença de `Gustavo Cardoso` e `Acelera ZG`.
