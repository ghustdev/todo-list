import { state, addTask, updateTask, deleteTask, getTask } from "./model.js";
import { render } from "./view.js";

let currentEditId = null;

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
