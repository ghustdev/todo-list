# 📋 Todo List - Documentação Frontend

## 📖 Índice

1. [Visão Geral](#visão-geral)
2. [Tecnologias](#tecnologias)
3. [Estrutura do Projeto](#estrutura-do-projeto)
4. [Arquitetura](#arquitetura)
5. [Módulos](#módulos)
6. [Funcionalidades](#funcionalidades)
7. [Design System](#design-system)
8. [Como Executar](#como-executar)
9. [API Interna](#api-interna)

---

## 🎯 Visão Geral

Aplicação web de gerenciamento de tarefas (Todo List) com interface minimalista inspirada no Notion. Desenvolvida com JavaScript vanilla, sem frameworks, focando em simplicidade e performance.

### Características Principais

- ✅ CRUD completo de tarefas
- 💾 Persistência com localStorage
- 🔔 Sistema de alertas em tempo real
- 📱 Design responsivo (mobile-first)
- 🎨 Interface inspirada no Notion
- 🔄 Três views: Home, Today, Profile

---

## 🛠️ Tecnologias

- **HTML5** - Estrutura semântica
- **CSS3** - Estilização (variáveis CSS, Grid, Flexbox)
- **JavaScript ES6+** - Lógica da aplicação
- **Vite** - Build tool e dev server
- **Font Awesome 6.5.1** - Ícones
- **Google Fonts (Inter)** - Tipografia
- **LocalStorage API** - Persistência de dados

---

## 📁 Estrutura do Projeto

```
frontend-todo-list/
├── index.html              # Estrutura HTML completa
├── package.json            # Dependências e scripts
├── populate-tasks.js       # Script para popular dados de exemplo
├── src/
│   ├── main.js            # Ponto de entrada da aplicação
│   ├── model.js           # Gerenciamento de estado (state)
│   ├── view.js            # Renderização da interface
│   ├── controller.js      # Lógica dos modais
│   ├── alerts.js          # Sistema de notificações
│   └── style.css          # Estilos globais
└── README.md              # Esta documentação
```

---

## 🏗️ Arquitetura

### Padrão MVC Simplificado

```
┌─────────────┐
│   main.js   │ ← Inicialização
└──────┬──────┘
       │
   ┌───┴────┐
   │        │
┌──▼──┐  ┌─▼────────┐
│Model│  │Controller│
│     │  │          │
│State│◄─┤ Modals   │
└──┬──┘  └─────┬────┘
   │           │
   │      ┌────▼────┐
   └─────►│  View   │
          │         │
          │ Render  │
          └─────────┘
```

### Fluxo de Dados

```
User Action → Controller → Model (State) → View → DOM Update
                              ↓
                        LocalStorage
```

---

## 📦 Módulos

### 1. `main.js` - Inicialização

**Responsabilidade:** Ponto de entrada da aplicação

```javascript
import './style.css'
import { loadState } from './model.js'
import { render } from './view.js'
import { startAlertSystem } from './alerts.js'

loadState()           // Carrega dados do localStorage
render()              // Renderiza interface inicial
startAlertSystem()    // Inicia verificação de alertas
```

**Ordem de Execução:**
1. Carrega CSS
2. Restaura estado do localStorage
3. Renderiza interface
4. Ativa sistema de alertas

---

### 2. `model.js` - Estado da Aplicação

**Responsabilidade:** Gerenciar dados e persistência

#### Estado Global

```javascript
const state = {
  tasks: [],              // Array de tarefas
  currentTab: 'today',    // Aba ativa
  dateFilter: null,       // Filtro de data (Home)
  userName: 'Usuário',    // Nome do usuário
  alertedTasks: new Set() // IDs de tarefas já alertadas
}
```

#### Modelo de Tarefa

```javascript
{
  id: String,           // UUID único
  title: String,        // Título (obrigatório)
  description: String,  // Descrição (opcional)
  status: String,       // 'TODO' | 'DOING' | 'DONE'
  date: String,         // YYYY-MM-DD
  time: String,         // HH:MM
  category: String,     // Categoria da tarefa
  priority: Number,     // 1-5
  alert: Boolean,       // Alerta ativado?
  createdAt: String     // ISO timestamp
}
```

#### API Pública

| Função | Parâmetros | Retorno | Descrição |
|--------|-----------|---------|-----------|
| `loadState()` | - | void | Carrega dados do localStorage |
| `addTask(task)` | Object | void | Adiciona nova tarefa |
| `updateTask(id, updates)` | String, Object | void | Atualiza tarefa existente |
| `deleteTask(id)` | String | void | Remove tarefa |
| `getTask(id)` | String | Object | Busca tarefa por ID |
| `setUserName(name)` | String | void | Atualiza nome do usuário |

#### Persistência

- **Chave:** `todolist_data`
- **Formato:** JSON
- **Dados salvos:** `tasks`, `userName`
- **Trigger:** Automático após cada operação CRUD

---

### 3. `view.js` - Renderização

**Responsabilidade:** Manipular DOM e exibir dados

#### Função Principal

```javascript
render() // Re-renderiza toda a interface
```

#### Views Disponíveis

##### Home View
- Lista todas as tarefas
- Filtro por data
- Ordenação por prioridade

##### Today View
- Tarefas do dia atual
- Data formatada em português
- Separação pendentes/concluídas

##### Profile View
- Avatar com inicial do nome
- Estatísticas:
  - Total de tarefas
  - Concluídas hoje
  - Pendentes
  - Número de categorias
- Lista de tarefas por categoria

#### Funções Auxiliares

| Função | Descrição |
|--------|-----------|
| `showView(viewId)` | Exibe view específica |
| `updateNavigation()` | Atualiza abas ativas |
| `renderHome()` | Renderiza view Home |
| `renderToday()` | Renderiza view Today |
| `renderProfile()` | Renderiza view Profile |
| `renderTaskList(tasks, containerId)` | Renderiza lista de tarefas |
| `createTaskRow(task)` | Cria HTML de uma tarefa |
| `attachEventListeners()` | Conecta eventos do DOM |

---

### 4. `controller.js` - Modais

**Responsabilidade:** Gerenciar modais de criação/edição/detalhes

#### Modais Disponíveis

##### Task Modal (Criar/Editar)
```javascript
openTaskModal(taskId = null)
```
- `taskId = null` → Modo criação
- `taskId = String` → Modo edição

**Campos:**
- Título (obrigatório)
- Descrição
- Status (TODO/DOING/DONE)
- Data e hora (obrigatórios)
- Categoria (select)
- Prioridade (1-5)
- Alerta (switch)

##### Detail Modal (Visualização)
```javascript
openDetailModal(taskId)
```
- Exibe todos os dados da tarefa
- Botões: Editar, Excluir

#### Fluxo de Interação

```
Clique na tarefa
    ↓
Detail Modal (visualização)
    ↓
Usuário escolhe:
    → Editar → Task Modal (edição)
    → Excluir → Confirmação → Remove
    → Fechar → Volta para lista
```

---

### 5. `alerts.js` - Sistema de Notificações

**Responsabilidade:** Verificar e disparar alertas de tarefas

#### Funcionamento

```javascript
startAlertSystem()
```

- **Intervalo:** 30 segundos
- **Verificação:** Tarefas com `alert: true`
- **Condições:**
  - Status ≠ DONE
  - Data = hoje
  - Horário ± 1 minuto
  - Não alertada anteriormente

#### Algoritmo

```javascript
1. Obter data/hora atual
2. Para cada tarefa:
   a. Verificar condições
   b. Converter horários para minutos
   c. Calcular diferença absoluta
   d. Se ≤ 1 minuto → alert()
   e. Adicionar ID ao Set de alertadas
```

---

## ⚙️ Funcionalidades

### CRUD de Tarefas

| Ação | Como Fazer |
|------|-----------|
| **Criar** | Clicar no FAB (+) → Preencher formulário → Criar Tarefa |
| **Visualizar** | Clicar na tarefa → Detail Modal |
| **Editar** | Detail Modal → Editar → Modificar → Salvar |
| **Excluir** | Detail Modal → Excluir → Confirmar |

### Navegação

- **Desktop:** Sidebar fixa à esquerda
- **Mobile:** Bottom navigation (3 abas)
- **Abas:** Home, Today, Profile

### Filtros

- **Home:** Filtro por data (input date)
- **Today:** Automático (data atual)
- **Ordenação:** Prioridade (maior → menor)

### Persistência

- **Automática:** Salva após cada operação
- **Restauração:** Carrega ao iniciar
- **Limpeza:** Limpar localStorage do navegador

---

## 🎨 Design System

### Paleta de Cores (Notion)

```css
--notion-bg: #ffffff           /* Fundo principal */
--notion-sidebar: #f7f6f3      /* Sidebar */
--notion-text: #37352f         /* Texto principal */
--notion-text-light: #787774   /* Texto secundário */
--notion-border: #e9e9e7       /* Bordas */
--notion-hover: #f1f1ef        /* Hover states */
--notion-selected: #e3e2e0     /* Selecionado */
--notion-blue: #2383e2         /* Azul (ações) */
--notion-red: #eb5757          /* Vermelho (perigo) */
--notion-green: #4dab5f        /* Verde (sucesso) */
```

### Tipografia

- **Fonte:** Inter (Google Fonts)
- **Tamanhos:**
  - Título: 40px (bold)
  - Subtítulo: 14px
  - Corpo: 14px
  - Labels: 13px (semibold)
  - Small: 12px

### Espaçamento

- **Padding:** 8px, 12px, 16px, 20px, 24px
- **Gap:** 8px, 12px, 16px
- **Border Radius:** 3px, 4px, 6px, 8px

### Componentes

#### Task Row
```css
.task-row {
  padding: 8px 4px;
  border-radius: 3px;
  hover: background: var(--notion-hover);
}
```

#### Badges
- **Status:** TODO (amarelo), DOING (azul), DONE (verde)
- **Prioridade:** 1-3 (cinza), 4-5 (vermelho)

#### Modais
- **Overlay:** rgba(0, 0, 0, 0.4)
- **Sombra:** 0 16px 70px rgba(0, 0, 0, 0.2)
- **Animação:** Fade in

---

## 🚀 Como Executar

### Pré-requisitos

- Node.js 16+
- npm ou yarn

### Instalação

```bash
# Clone o repositório
git clone <url-do-repo>

# Entre na pasta
cd frontend-todo-list

# Instale dependências
npm install
```

### Desenvolvimento

```bash
# Inicia servidor de desenvolvimento
npm run dev

# Acesse: http://localhost:5173
```

### Build para Produção

```bash
# Gera build otimizado
npm run build

# Preview do build
npm run preview
```

### Popular Dados de Exemplo

```bash
# Opção 1: Console do navegador
# Cole o conteúdo de populate-tasks.js

# Opção 2: Adicione temporariamente ao HTML
<script src="/populate-tasks.js"></script>
```

---

## 🔌 API Interna

### State Management

```javascript
import { state, addTask, updateTask, deleteTask, getTask } from './model.js'

// Adicionar tarefa
addTask({
  title: 'Nova tarefa',
  description: 'Descrição',
  status: 'TODO',
  date: '2024-01-15',
  time: '14:00',
  category: 'Trabalho',
  priority: 3,
  alert: true
})

// Atualizar tarefa
updateTask('task-id', { status: 'DONE' })

// Deletar tarefa
deleteTask('task-id')

// Buscar tarefa
const task = getTask('task-id')
```

### Renderização

```javascript
import { render } from './view.js'

// Re-renderiza interface
render()

// Muda de aba
state.currentTab = 'home'
render()
```

### Modais

```javascript
import { openTaskModal, openDetailModal } from './controller.js'

// Abrir modal de criação
openTaskModal()

// Abrir modal de edição
openTaskModal('task-id')

// Abrir modal de detalhes
openDetailModal('task-id')
```

---

## 📊 Estrutura de Dados (LocalStorage)

```json
{
  "tasks": [
    {
      "id": "550e8400-e29b-41d4-a716-446655440000",
      "title": "Reunião com equipe",
      "description": "Discutir progresso do projeto",
      "status": "TODO",
      "date": "2024-01-15",
      "time": "09:00",
      "category": "Trabalho",
      "priority": 5,
      "alert": true,
      "createdAt": "2024-01-15T08:00:00.000Z"
    }
  ],
  "userName": "Usuário"
}
```

---

## 🐛 Troubleshooting

### Tarefas não aparecem
- Verifique localStorage: `localStorage.getItem('todolist_data')`
- Limpe e recarregue: `localStorage.clear()` + F5

### Alertas não funcionam
- Verifique permissões do navegador
- Confirme que `alert: true` na tarefa
- Horário deve estar ± 1 minuto

### Estilos quebrados
- Verifique se Font Awesome carregou
- Limpe cache do navegador
- Recompile com `npm run dev`

---

## 📝 Notas de Desenvolvimento

### Boas Práticas Implementadas

- ✅ Separação de responsabilidades (MVC)
- ✅ Código modular (ES6 modules)
- ✅ Nomenclatura semântica
- ✅ Comentários em pontos-chave
- ✅ Validação de formulários
- ✅ Tratamento de erros
- ✅ Responsividade mobile-first

### Melhorias Futuras

- [ ] Drag and drop para reordenar
- [ ] Filtros avançados (categoria, status)
- [ ] Temas (claro/escuro)
- [ ] Exportar/importar dados (JSON)
- [ ] Integração com backend
- [ ] PWA (offline-first)
- [ ] Testes automatizados

---

## 📄 Licença

Este projeto é de código aberto para fins educacionais.

---

## 👨‍💻 Autor

Desenvolvido como projeto de estudo de JavaScript vanilla e design minimalista.

---

**Última atualização:** Janeiro 2024
