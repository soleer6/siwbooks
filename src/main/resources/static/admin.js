document.addEventListener('DOMContentLoaded', () => {
    const token = localStorage.getItem('jwt');
    if (!token) {
    alert("No has iniciado sesión");
    window.location = 'login.html';
    }

    const payload = (function parseJwt(token) {
    try {
        const base64 = token.split('.')[1].replace(/-/g, '+').replace(/_/g, '/');
        return JSON.parse(atob(base64));
    } catch {
        return null;
    }
    })(token);

    if (!payload || payload.role !== 'ADMIN') {
    alert("Acceso restringido solo a administradores");
    window.location = 'login.html';
    }

    const form = document.getElementById('bookForm');
    const responseDiv = document.getElementById('response');
    const tableBody = document.getElementById('bookTableBody');
    const resetBtn = document.getElementById('resetForm');

    loadBooks();

    form.addEventListener('submit', async (e) => {
    e.preventDefault();

    const bookId = document.getElementById('bookId').value;
    const data = {
        title: document.getElementById('title').value,
        author: document.getElementById('author').value,
        isbn: document.getElementById('isbn').value
    };

    const method = bookId ? 'PUT' : 'POST';
    const url = bookId ? `/api/books/${bookId}` : '/api/books';

    const res = await fetch(url, {
        method,
        headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + token
        },
        body: JSON.stringify(data)
    });

    if (res.ok) {
        responseDiv.innerHTML = `<div class="alert alert-success">✅ Libro ${bookId ? 'actualizado' : 'creado'} correctamente</div>`;
        form.reset();
        document.getElementById('bookId').value = '';
        loadBooks();
    } else {
        const err = await res.text();
        responseDiv.innerHTML = `<div class="alert alert-danger">❌ Error: ${err}</div>`;
    }
    });

    resetBtn.addEventListener('click', () => {
    form.reset();
    document.getElementById('bookId').value = '';
    });

    async function loadBooks() {
    const res = await fetch('/api/books?page=0&size=50', {
        headers: { 'Authorization': 'Bearer ' + token }
    });
    const data = await res.json();
    const books = data.content || [];
    tableBody.innerHTML = '';

    books.forEach(book => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
        <td>${book.id}</td>
        <td>${book.title}</td>
        <td>${book.author}</td>
        <td>
            <button class="btn btn-sm btn-warning me-2" onclick="editBook(${book.id}, '${book.title}', '${book.author}', '${book.isbn}')">✏️</button>
            <button class="btn btn-sm btn-danger" onclick="deleteBook(${book.id})">🗑️</button>
        </td>
        `;
        tableBody.appendChild(tr);
    });
    }

    window.editBook = function (id, title, author, isbn) {
    document.getElementById('bookId').value = id;
    document.getElementById('title').value = title;
    document.getElementById('author').value = author;
    document.getElementById('isbn').value = isbn;
    }

    window.deleteBook = async function (id) {
    if (!confirm('¿Seguro que quieres eliminar este libro?')) return;
    const res = await fetch(`/api/books/${id}`, {
        method: 'DELETE',
        headers: { 'Authorization': 'Bearer ' + token }
    });
    if (res.ok) {
        loadBooks();
    } else {
        alert('❌ Error al eliminar libro');
    }
    }

    // Cargar reseñas
    loadAllReviews();

    async function loadAllReviews() {
        const res = await fetch('/api/reviews', {
            headers: { 'Authorization': 'Bearer ' + token }
        });

        if (!res.ok) {
            const err = await res.text();
            console.error("Error al obtener reseñas:", err);
            return;
        }

        const reviews = await res.json();
        const container = document.getElementById('reviewContainer');
        container.innerHTML = '';

        if (!Array.isArray(reviews) || reviews.length === 0) {
            container.innerHTML = `<em>No hay reseñas aún.</em>`;
            return;
        }

        reviews.forEach(r => {
            const div = document.createElement('div');
            div.className = 'border p-2 mb-2';
            div.innerHTML = `
            <strong>${r.title}</strong> – Nota: ${r.rating}/5<br>
            <small>${r.text}</small><br>
            <button class="btn btn-sm btn-danger mt-2" onclick="deleteReview(${r.id})">🗑️ Eliminar</button>
            `;
            container.appendChild(div);
        });
    }


    window.deleteReview = async function (id) {
    if (!confirm("¿Eliminar esta reseña?")) return;

    const res = await fetch(`/api/reviews/${id}`, {
        method: 'DELETE',
        headers: { 'Authorization': 'Bearer ' + token }
    });

    if (res.ok) {
        loadAllReviews();
    } else {
        alert("Error al eliminar reseña");
    }
    }
});



