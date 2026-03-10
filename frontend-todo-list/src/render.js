import { state } from './state.js'
import { openTaskModal, openDetailModal } from './modal.js'
import { setUserName } from './state.js'

export function render() {
  updateNavigation()
  
  if (state.currentTab === 'home') {
    showView('homeView')
    renderHome()
  } else if (state.currentTab === 'today') {
    showView('todayView')
    renderToday()
  } else if (state.currentTab === 'profile') {
    showView('profileView')
    renderProfile()
  }
  
  attachEventListeners()
}

function showView(viewId) {
  document.querySelectorAll('.view').forEach(v => v.style.display = 'none')
  document.getElementById(viewId).style.display = 'block'
}

function updateNavigation() {
  document.querySelectorAll('.nav-item, .sidebar-item').forEach(item => {
    if (item.dataset.tab === state.currentTab) {
      item.classList.add('active')
    } else {
      item.classList.remove('active')
    }
  })
}

function renderHome() {
  let tasks = state.tasks
  
  if (state.dateFilter) {
    tasks = tasks.filter(t => t.date === state.dateFilter)
    document.getElementById('clearFilter').style.display = 'inline-flex'
  } else {
    document.getElementById('clearFilter').style.display = 'none'
  }
  
  document.getElementById('dateFilterInput').value = state.dateFilter || ''
  renderTaskList(tasks, 'homeTaskList')
}

function renderToday() {
  const today = new Date().toISOString().split('T')[0]
  const tasks = state.tasks.filter(t => t.date === today)
  
  const dateStr = new Date().toLocaleDateString('pt-BR', { 
    weekday: 'long', 
    day: 'numeric', 
    month: 'long' 
  })
  
  document.getElementById('todayDate').textContent = dateStr
  renderTaskList(tasks, 'todayTaskList', true)
}

function renderProfile() {
  const total = state.tasks.length
  const today = new Date().toISOString().split('T')[0]
  const completedToday = state.tasks.filter(t => t.date === today && t.status === 'DONE').length
  const pending = state.tasks.filter(t => t.status !== 'DONE').length
  
  document.getElementById('avatarLetter').textContent = state.userName.charAt(0).toUpperCase()
  document.getElementById('userName').value = state.userName
  document.getElementById('statTotal').textContent = total
  document.getElementById('statCompletedToday').textContent = completedToday
  document.getElementById('statPending').textContent = pending
  
  const categories = {}
  state.tasks.forEach(t => {
    categories[t.category] = (categories[t.category] || 0) + 1
  })
  
  document.getElementById('statCategories').textContent = Object.keys(categories).length
  
  const categoryList = document.getElementById('categoryList')
  const categoryItems = document.getElementById('categoryItems')
  
  if (Object.keys(categories).length > 0) {
    categoryList.style.display = 'block'
    categoryItems.innerHTML = Object.entries(categories).map(([cat, count]) => `
      <div class="category-item">
        <span><i class="fa-solid fa-tag"></i> ${cat}</span>
        <span>${count}</span>
      </div>
    `).join('')
  } else {
    categoryList.style.display = 'none'
  }
}

function renderTaskList(tasks, containerId, isToday = false) {
  const container = document.getElementById(containerId)
  
  if (tasks.length === 0) {
    container.innerHTML = `
      <div class="empty-state">
        <div class="empty-icon"><i class="fa-regular fa-circle-check"></i></div>
        <p class="empty-text">${isToday ? 'Nenhuma tarefa para hoje' : 'Nenhuma tarefa encontrada'}</p>
      </div>
    `
    return
  }
  
  const pending = tasks.filter(t => t.status !== 'DONE').sort((a, b) => b.priority - a.priority)
  const completed = tasks.filter(t => t.status === 'DONE')
  
  let html = '<div class="task-list">'
  
  if (pending.length > 0) {
    html += '<div class="task-group"><div class="task-group-title">Pendentes</div>'
    pending.forEach(task => {
      html += createTaskRow(task)
    })
    html += '</div>'
  }
  
  if (completed.length > 0) {
    html += '<div class="task-group"><div class="task-group-title">Concluídas</div>'
    completed.forEach(task => {
      html += createTaskRow(task)
    })
    html += '</div>'
  }
  
  html += '</div>'
  container.innerHTML = html
}

function createTaskRow(task) {
  const date = new Date(task.date + 'T00:00:00')
  const dateStr = date.toLocaleDateString('pt-BR', { day: '2-digit', month: '2-digit' })
  
  return `
    <div class="task-row ${task.status === 'DONE' ? 'done' : ''}" data-id="${task.id}">
      <div class="task-checkbox">
        ${task.status === 'DONE' ? '<i class="fa-solid fa-check"></i>' : ''}
      </div>
      <div class="task-content">
        <span class="task-title-text">${task.title}</span>
        <span class="task-badge badge-status ${task.status.toLowerCase()}">${task.status}</span>
        <span class="task-badge badge-priority ${task.priority >= 4 ? 'high' : ''}">
          <i class="fa-solid fa-flag"></i> ${task.priority}
        </span>
        <span class="task-meta">
          <i class="fa-regular fa-calendar"></i> ${dateStr}
          <i class="fa-regular fa-clock"></i> ${task.time}
          <i class="fa-solid fa-tag"></i> ${task.category}
        </span>
      </div>
    </div>
  `
}

function attachEventListeners() {
  // Navigation
  document.querySelectorAll('.nav-item, .sidebar-item').forEach(item => {
    item.onclick = () => {
      state.currentTab = item.dataset.tab
      render()
    }
  })
  
  // Fab
  document.getElementById('fab').onclick = () => openTaskModal()
  
  // Task rows
  document.querySelectorAll('.task-row').forEach(row => {
    row.onclick = () => openDetailModal(row.dataset.id)
  })
  
  // Date filter
  const dateInput = document.getElementById('dateFilterInput')
  dateInput.onchange = () => {
    state.dateFilter = dateInput.value
    render()
  }
  
  document.getElementById('clearFilter').onclick = () => {
    state.dateFilter = null
    render()
  }
  
  // Profile name
  document.getElementById('userName').onchange = (e) => {
    setUserName(e.target.value)
    render()
  }
}
