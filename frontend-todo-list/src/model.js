const STORAGE_KEY = 'todolist_data'

// Model default - simulate user
export const state = {
  tasks: [],
  currentTab: 'today',
  dateFilter: null,
  userName: 'Usuário',
  alertedTasks: new Set()
}

// Load data state from localStorage
export function loadState() {
  const saved = localStorage.getItem(STORAGE_KEY)
  if (saved) {
    const data = JSON.parse(saved)
    state.tasks = data.tasks || []
    state.userName = data.userName || 'Usuário'
    state.currentTab = data.currentTab  || 'today'
  }
}

// Save to localStorage
function saveState() {
  localStorage.setItem(STORAGE_KEY, JSON.stringify({
    tasks: state.tasks,
    userName: state.userName,
    currentTab: state.currentTab
  }))
}

export function addTask(task) {
  state.tasks.push({
    id: crypto.randomUUID(), // Unique ID
    createdAt: new Date().toISOString(),
    ...task // Spread oparator, similar to Groovy
  })
  saveState()
}

export function updateTask(id, updates) {
  const index = state.tasks.findIndex(t => t.id === id)
  if (index !== -1) {
    state.tasks[index] = { ...state.tasks[index], ...updates }
    saveState()
  }
}

export function deleteTask(id) {
  state.tasks = state.tasks.filter(t => t.id !== id)
  saveState()
}

export function getTask(id) {
  return state.tasks.find(t => t.id === id)
}

export function setTab(tab) {
  state.currentTab = tab
}

export function setDateFilter(date) {
  state.dateFilter = date
}

export function clearDateFilter() {
  state.dateFilter = null
}

export function setUserName(name) {
  state.userName = name
  saveState()
}
