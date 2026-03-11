import { state, addTask, updateTask, deleteTask, getTask } from "./model.js";
import { render } from "./view.js";

let currentEditId = null;

export function openChangeStatusModal() {
  const modal = document.getElementById("changeStatusModal");
  const tasks = state.tasks.filter(
    (t) => t.date === state.dateFilter
  );

  if (tasks.length === 0) {
    alert("Nenhuma tarefa pendente encontrada para esta data!");
    return;
  }

  renderTaskSelection(tasks);
  modal.classList.add("active");
  attachChangeStatusModalListeners();
}

function renderTaskSelection(tasks) {
  const container = document.getElementById("taskSelectionList");
  let html = '<div style="margin-bottom: 20px;">';
  html += '<p style="color: #666; margin-bottom: 15px;">Selecione as tarefas que deseja alterar (ou deixe todas marcadas):</p>';
  html += '<div style="max-height: 300px; overflow-y: auto; margin-bottom: 20px;">';
  
  tasks.forEach((task) => {
    html += `
      <label class="task-checkbox-item" style="display: flex; align-items: center; padding: 12px; border: 1px solid #e0e0e0; border-radius: 8px; margin-bottom: 8px; cursor: pointer; transition: background 0.2s;">
        <input type="checkbox" class="task-select-checkbox" data-task-id="${task.id}" checked style="margin-right: 12px; width: 18px; height: 18px; cursor: pointer;">
        <div style="flex: 1;">
          <div style="font-weight: 500; margin-bottom: 4px;">${task.title}</div>
          <div style="font-size: 12px; color: #666;">
            <span class="task-badge badge-status ${task.status.toLowerCase()}" style="margin-right: 8px;">${task.status}</span>
            <i class="fa-regular fa-clock"></i> ${task.time}
            <span style="margin-left: 8px;"><i class="fa-solid fa-tag"></i> ${task.category}</span>
          </div>
        </div>
      </label>
    `;
  });
  
  html += '</div>';
  html += '<button id="selectAllTasks" class="btn btn-secondary" style="margin-right: 10px;"><i class="fa-solid fa-check-double"></i> Marcar Todas</button>';
  html += '<button id="deselectAllTasks" class="btn btn-secondary"><i class="fa-solid fa-xmark"></i> Desmarcar Todas</button>';
  html += '</div>';
  
  container.innerHTML = html;
  
  // Add hover effect
  setTimeout(() => {
    document.querySelectorAll('.task-checkbox-item').forEach(item => {
      item.addEventListener('mouseenter', () => item.style.background = '#f5f5f5');
      item.addEventListener('mouseleave', () => item.style.background = 'transparent');
    });
  }, 0);
}

function attachChangeStatusModalListeners() {
  const modal = document.getElementById("changeStatusModal");
  
  document.getElementById("closeChangeStatusModal").onclick = () => closeModal("changeStatusModal");
  modal.onclick = (e) => {
    if (e.target.id === "changeStatusModal") closeModal("changeStatusModal");
  };

  // Select/Deselect all buttons
  document.getElementById("selectAllTasks").onclick = () => {
    document.querySelectorAll(".task-select-checkbox").forEach(cb => cb.checked = true);
  };
  
  document.getElementById("deselectAllTasks").onclick = () => {
    document.querySelectorAll(".task-select-checkbox").forEach(cb => cb.checked = false);
  };

  document.querySelectorAll(".status-option").forEach((btn) => {
    btn.onclick = () => {
      const newStatus = btn.dataset.status;
      const selectedTaskIds = Array.from(document.querySelectorAll(".task-select-checkbox:checked"))
        .map(cb => cb.dataset.taskId);
      
      if (selectedTaskIds.length === 0) {
        alert("Selecione pelo menos uma tarefa!");
        return;
      }
      
      changeGroupStatus(newStatus, selectedTaskIds);
      closeModal("changeStatusModal");
      render();
    };
  });
}

function changeGroupStatus(newStatus, selectedTaskIds) {
  selectedTaskIds.forEach((taskId) => {
    updateTask(taskId, { status: newStatus });
  });

  alert(`${selectedTaskIds.length} tarefa(s) atualizada(s) para ${newStatus}!`);
}

export function openTaskModal(taskId = null) {
  currentEditId = taskId;
  const task = taskId ? getTask(taskId) : null;

  const modal = document.getElementById("taskModal");
  const form = document.getElementById("taskForm");

  // Update modal title and button
  document.getElementById("taskModalTitle").textContent = task
    ? "Editar Tarefa"
    : "Nova Tarefa";
  document.getElementById("taskSubmitBtn").textContent = task
    ? "Salvar Alterações"
    : "Criar Tarefa";

  // Fill form
  if (task) {
    document.getElementById("taskTitle").value = task.title;
    document.getElementById("taskDescription").value = task.description;
    document.getElementById("taskDate").value = task.date;
    document.getElementById("taskTime").value = task.time;
    document.getElementById("taskCategory").value = task.category;

    // Status
    document.querySelectorAll("#statusGroup .toggle-btn").forEach((btn) => {
      btn.classList.toggle("active", btn.dataset.status === task.status);
    });

    // Priority
    document.querySelectorAll("#priorityGroup .toggle-btn").forEach((btn) => {
      btn.classList.toggle(
        "active",
        parseInt(btn.dataset.priority) === task.priority,
      );
    });

    // Alert
    document.getElementById("taskAlert").classList.toggle("active", task.alert);
  } else {
    form.reset();
    document
      .querySelectorAll("#statusGroup .toggle-btn")[0]
      .classList.add("active");
    document
      .querySelectorAll("#priorityGroup .toggle-btn")[2]
      .classList.add("active");
    document.getElementById("taskAlert").classList.remove("active");
  }

  modal.classList.add("active");
  attachTaskModalListeners();
}

export function openDetailModal(taskId) {
  const task = getTask(taskId);
  if (!task) return;

  const modal = document.getElementById("detailModal");

  document.getElementById("detailTitle").innerHTML =
    `<strong>${task.title}</strong>`;
  document.getElementById("detailDescription").textContent =
    task.description || "Sem descrição";
  document.getElementById("detailStatus").innerHTML =
    `<span class="task-badge badge-status ${task.status.toLowerCase()}">${task.status}</span>`;
  document.getElementById("detailDateTime").innerHTML =
    `<i class="fa-regular fa-calendar"></i> ${task.date} às ${task.time}`;
  document.getElementById("detailCategory").innerHTML =
    `<i class="fa-solid fa-tag"></i> ${task.category}`;
  document.getElementById("detailPriority").innerHTML =
    `<i class="fa-solid fa-flag"></i> ${task.priority}/5`;
  document.getElementById("detailAlert").innerHTML = task.alert
    ? '<i class="fa-solid fa-bell"></i> Ativado'
    : '<i class="fa-solid fa-bell-slash"></i> Desativado';

  modal.classList.add("active");
  attachDetailModalListeners(taskId);
}

function attachTaskModalListeners() {
  const modal = document.getElementById("taskModal");
  const form = document.getElementById("taskForm");

  // Close
  document.getElementById("closeTaskModal").onclick = () =>
    closeModal("taskModal");
  modal.onclick = (e) => {
    if (e.target.id === "taskModal") closeModal("taskModal");
  };

  // Toggle buttons
  document
    .querySelectorAll("#statusGroup .toggle-btn, #priorityGroup .toggle-btn")
    .forEach((btn) => {
      btn.onclick = () => {
        const group = btn.parentElement;
        group
          .querySelectorAll(".toggle-btn")
          .forEach((b) => b.classList.remove("active"));
        btn.classList.add("active");
      };
    });

  // Alert switch
  document.getElementById("taskAlert").onclick = function () {
    this.classList.toggle("active");
  };

  // Form submit
  form.onsubmit = (e) => {
    e.preventDefault();

    const taskData = {
      title: document.getElementById("taskTitle").value,
      description: document.getElementById("taskDescription").value,
      status: document.querySelector("#statusGroup .toggle-btn.active").dataset.status,
      date: document.getElementById("taskDate").value,
      time: document.getElementById("taskTime").value,
      category: document.getElementById("taskCategory").value,
      priority: parseInt(
        document.querySelector("#priorityGroup .toggle-btn.active").dataset.priority,
      ),
      alert: document.getElementById("taskAlert").classList.contains("active"),
    };

    if (currentEditId) {
      updateTask(currentEditId, taskData);
    } else {
      addTask(taskData);
    }

    closeModal("taskModal");
    render();
  };
}

function attachDetailModalListeners(taskId) {
  const modal = document.getElementById("detailModal");

  document.getElementById("closeDetailModal").onclick = () =>
    closeModal("detailModal");
  modal.onclick = (e) => {
    if (e.target.id === "detailModal") closeModal("detailModal");
  };

  document.getElementById("editTaskBtn").onclick = () => {
    closeModal("detailModal");
    openTaskModal(taskId);
  };

  document.getElementById("deleteTaskBtn").onclick = () => {
    if (confirm("Deseja realmente excluir esta tarefa?")) {
      deleteTask(taskId);
      closeModal("detailModal");
      render();
    }
  };
}

function closeModal(modalId) {
  document.getElementById(modalId).classList.remove("active");
}
