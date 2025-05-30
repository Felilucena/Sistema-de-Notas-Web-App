const API_URL = 'http://localhost:8080/notes';
let currentTab = 'home';
let editingNoteId = null;

const notesContainer = document.getElementById('notesContainer');
const searchInput = document.getElementById('searchInput');
const categoryFilter = document.getElementById('categoryFilter');
const dateFilter = document.getElementById('dateFilter');
const noteForm = document.getElementById('noteForm');
const noteCategory = document.getElementById('noteCategory');
const customCategoryInput = document.getElementById('customCategoryInput');
const customCategoryColor = document.getElementById('customCategoryColor');
const customCategoryContainer = document.getElementById('customCategoryContainer');
const customCategoryColorContainer = document.getElementById('customCategoryColorContainer');
const noteModal = new bootstrap.Modal(document.getElementById('noteModal'));

noteCategory.addEventListener('change', () => {
  customCategoryContainer.classList.toggle('d-none', noteCategory.value !== 'custom');
  customCategoryColorContainer.classList.toggle('d-none', noteCategory.value !== 'custom');
});

// Tabs

document.querySelectorAll('#noteTabs .nav-link').forEach(tab => {
  tab.addEventListener('click', (e) => {
    e.preventDefault();
    document.querySelector('#noteTabs .active').classList.remove('active');
    tab.classList.add('active');
    currentTab = tab.dataset.tab;
    fetchAndRenderNotes();
  });
});

// Inicializar filtro de categorías desde backend
async function loadCategoryFilterOptions() {
  const res = await fetch("http://localhost:8080/categories");
  const categories = await res.json();

  categoryFilter.innerHTML = '<option value="">Todas las categorías</option>';
  categories.forEach(cat => {
    const option = document.createElement('option');
    option.value = cat.name;
    option.textContent = cat.name;
    categoryFilter.appendChild(option);
  });
}

async function loadNoteCategoryOptions() {
  const res = await fetch("http://localhost:8080/categories");
  const categories = await res.json();

  noteCategory.innerHTML = '';

  // Insertar las categorías existentes
  categories.forEach(cat => {
    const option = document.createElement('option');
    option.value = cat.name;
    option.textContent = cat.name;
    noteCategory.appendChild(option);
  });

  // Añadir opción para personalizadas
  const customOption = document.createElement('option');
  customOption.value = 'custom';
  customOption.textContent = 'Añadir Categoría';
  noteCategory.appendChild(customOption);
}

// Obtener notas con filtros
async function fetchAndRenderNotes() {
  let url = `${API_URL}?archived=${currentTab === 'archive'}`;

  const category = categoryFilter.value;
  const dueDate = dateFilter.value;

  if (category) url += `&category=${encodeURIComponent(category)}`;
  if (dueDate) url += `&dueDateBefore=${dueDate}`;

  const res = await fetch(url);
  const notes = await res.json();
  renderNotes(notes);
}

function renderNotes(notes) {
  notesContainer.innerHTML = '';
  const search = searchInput.value.toLowerCase();

  notes
    .filter(note =>
      note.title.toLowerCase().includes(search) ||
      note.description.toLowerCase().includes(search)
    )
    .sort((a, b) => a.priority - b.priority)
    .forEach(note => notesContainer.appendChild(createNoteCard(note)));
}

function createNoteCard(note) {
  const card = document.createElement('div');
  card.className = 'note-card';
  card.style.borderLeftColor = note.categoryColor || '#007bff';
  if (note.archived) card.classList.add('archived');
  if (note.completed) card.classList.add('complete');

  card.innerHTML = `
    <h5>${note.title}</h5>
    <p>${note.description}</p>
    <div class="note-category">📅 ${note.dueDate} | 🏷️ ${note.category}</div>
    <div class="note-actions">
      <button class="btn btn-sm btn-outline-success" onclick="toggleComplete(${note.id})"><i class="fa fa-check"></i></button>
      <button class="btn btn-sm btn-outline-primary" onclick="editNote(${note.id})"><i class="fa fa-pen"></i></button>
      <button class="btn btn-sm btn-outline-warning" onclick="toggleArchive(${note.id})"><i class="fa fa-box-archive"></i></button>
      <button class="btn btn-sm btn-outline-danger" onclick="deleteNote(${note.id})"><i class="fa fa-trash"></i></button>
    </div>
    <div class="note-priority">
      <button class="btn btn-sm btn-light" onclick="changePriority(${note.id}, 'up')"><i class="fa fa-arrow-up"></i></button>
      <button class="btn btn-sm btn-light" onclick="changePriority(${note.id}, 'down')"><i class="fa fa-arrow-down"></i></button>
    </div>
  `;

  return card;
}

// Crear/editar nota
noteForm.addEventListener('submit', async (e) => {
  e.preventDefault();

  const newNote = {
    title: document.getElementById('noteTitle').value,
    description: document.getElementById('noteDescription').value,
    dueDate: document.getElementById('noteDueDate').value
  };

  if (noteCategory.value === 'custom') {
    newNote.category = customCategoryInput.value;
    newNote.categoryColor = customCategoryColor.value;
  } else {
    newNote.category = noteCategory.value;
    newNote.categoryColor = null;
  }

  if (editingNoteId) {
    await fetch(`${API_URL}/${editingNoteId}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(newNote)
    });
  } else {
    await fetch(API_URL, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(newNote)
    });
  }

  noteModal.hide();
  noteForm.reset();
  editingNoteId = null;
  fetchAndRenderNotes();
});

// Acciones
async function toggleArchive(id) {
  await fetch(`${API_URL}/${id}/archive`, { method: 'PATCH' });
  fetchAndRenderNotes();
}

async function toggleComplete(id) {
  await fetch(`${API_URL}/${id}/complete`, { method: 'PATCH' });
  fetchAndRenderNotes();
}

async function deleteNote(id) {
  await fetch(`${API_URL}/${id}`, { method: 'DELETE' });
  fetchAndRenderNotes();
}

async function changePriority(id, direction) {
  await fetch(`${API_URL}/${id}/priority?direction=${direction}`, { method: 'PATCH' });
  fetchAndRenderNotes();
}

async function editNote(id) {
  const res = await fetch(`${API_URL}/${id}`);
  const note = await res.json();

  document.getElementById('noteTitle').value = note.title;
  document.getElementById('noteDescription').value = note.description;
  document.getElementById('noteDueDate').value = note.dueDate;

  if (["Estudios", "Trabajo", "Personal"].includes(note.category)) {
    noteCategory.value = note.category;
    customCategoryContainer.classList.add('d-none');
    customCategoryColorContainer.classList.add('d-none');
  } else {
    noteCategory.value = 'custom';
    customCategoryInput.value = note.category;
    customCategoryColor.value = note.categoryColor || '#17a2b8';
    customCategoryContainer.classList.remove('d-none');
    customCategoryColorContainer.classList.remove('d-none');
  }

  editingNoteId = id;

  document.querySelector('#noteModal .modal-title').textContent = 'Editar Nota';

  noteModal.show();
}


searchInput.addEventListener('input', fetchAndRenderNotes);
categoryFilter.addEventListener('change', fetchAndRenderNotes);
dateFilter.addEventListener('change', fetchAndRenderNotes);

document.querySelector('[data-bs-target="#noteModal"]').addEventListener('click', () => {
  noteForm.reset(); // Limpia los campos
  editingNoteId = null; // Reinicia el estado de edición
  customCategoryContainer.classList.add('d-none'); // Oculta campo personalizado
  customCategoryColorContainer.classList.add('d-none');
  document.querySelector('#noteModal .modal-title').textContent = 'Crear Nota'; // Cambia el título
});


// Inicial
loadCategoryFilterOptions();
fetchAndRenderNotes();
loadNoteCategoryOptions();