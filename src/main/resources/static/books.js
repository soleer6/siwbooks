document.getElementById('logout')
  .addEventListener('click', () => {
    localStorage.removeItem('jwt');
    window.location = 'login.html';
});

const token = localStorage.getItem('jwt');
if (!token) window.location = 'login.html';

const payload = (function parseJwt(token) {
  try {
    const base64 = token.split('.')[1].replace(/-/g, '+').replace(/_/g, '/');
    return JSON.parse(atob(base64));
  } catch {
    return null;
  }
})(token);

const container = document.getElementById('booksContainer');

(async function loadBooks() {
  const res = await fetch('/api/books?page=0&size=50', {
    headers: { 'Authorization': 'Bearer ' + token }
  });

  if (!res.ok) {
    localStorage.removeItem('jwt');
    return window.location = 'login.html';
  }

  const data = await res.json();
  const libros = data.content || [];

  libros.forEach(book => {
    const div = document.createElement('div');
    div.className = "border p-3 mb-4";

    div.innerHTML = `
      <h5>${book.title} – ${book.author}</h5>
      <div id="reviews-${book.id}" class="mb-2"></div>
      <form id="form-${book.id}" class="mb-3">
        <input type="hidden" name="bookId" value="${book.id}">
        <input type="text" name="title" placeholder="Título de la reseña" class="form-control mb-2" required>
        <textarea name="text" placeholder="Escribe tu reseña" class="form-control mb-2" required></textarea>
        <input type="number" name="rating" min="1" max="5" class="form-control mb-2" placeholder="Nota (1-5)" required>
        <button class="btn btn-sm btn-success">Enviar reseña</button>
      </form>
    `;

    container.appendChild(div);

    // cargar reseñas y añadir lógica de envío
    loadReviews(book.id);
    handleReviewForm(book.id);
  });
})();

async function loadReviews(bookId) {
  const res = await fetch(`/api/books/${bookId}/reviews`, {
    headers: { 'Authorization': 'Bearer ' + token }
  });
  const reviews = await res.json();
  const target = document.getElementById(`reviews-${bookId}`);

  if (reviews.length === 0) {
    target.innerHTML = `<em>No hay reseñas aún.</em>`;
    return;
  }

  target.innerHTML = reviews.map(r => `
    <div class="border p-2 mb-1">
      <strong>${r.title}</strong> – <span>Nota: ${r.rating}/5</span><br>
      <small>${r.text}</small>
    </div>
  `).join('');
}

function handleReviewForm(bookId) {
  const form = document.getElementById(`form-${bookId}`);
  form.addEventListener('submit', async e => {
    e.preventDefault();

    const data = {
      title: form.title.value,
      text: form.text.value,
      rating: parseInt(form.rating.value),
      book: { id: bookId }
    };

    const res = await fetch('/api/reviews', {
      method: 'POST',
      headers: {
        'Authorization': 'Bearer ' + token,
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(data)
    });

    if (res.ok) {
      loadReviews(bookId);
      form.remove(); // una reseña por usuario
    } else {
      const msg = await res.text();
      alert("Error al enviar reseña: " + msg);
    }
  });
}
