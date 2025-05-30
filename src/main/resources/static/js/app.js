const API = '/api';
const alertDiv = id => document.getElementById(id);

// Helpers
function showAlert(container, msg, type='danger') {
  container.innerHTML = 
    `<div class="alert alert-${type}">${msg}</div>`;
  setTimeout(() => container.innerHTML = '', 3000);
}

// REGISTER
document.getElementById('register-form')?.addEventListener('submit', async e => {
  e.preventDefault();
  try {
    const res = await fetch(`${API}/auth/register`, {
      method:'POST',
      headers:{ 'Content-Type':'application/json' },
      body: JSON.stringify({
        username: document.getElementById('reg-username').value,
        email:    document.getElementById('reg-email').value,
        password: document.getElementById('reg-password').value
      })
    });
    if (res.status===201) {
      showAlert(alertDiv('alert'), '¡Registrado! Ahora inicia sesión.', 'success');
      setTimeout(()=> location.href='login.html', 1500);
    } else {
      const text = await res.text();
      showAlert(alertDiv('alert'), text||res.statusText);
    }
  } catch(err) {
    showAlert(alertDiv('alert'), err);
  }
});

// LOGIN
document.getElementById('login-form')?.addEventListener('submit', async e => {
  e.preventDefault();
  try {
    const res = await fetch(`${API}/auth/login`, {
      method:'POST',
      headers:{ 'Content-Type':'application/json' },
      body: JSON.stringify({
        username: document.getElementById('username').value,
        password: document.getElementById('password').value
      })
    });
    if (res.ok) {
      const { token } = await res.json();
      localStorage.setItem('jwt', token);
      location.href = 'books.html';
    } else {
      const text = await res.text();
      showAlert(alertDiv('alert'), text||'Credenciales incorrectas.');
    }
  } catch(err) {
    showAlert(alertDiv('alert'), err);
  }
});

// LOGOUT
document.getElementById('logout')?.addEventListener('click', _ => {
  localStorage.removeItem('jwt');
  location.href = 'login.html';
});

// ON BOOKS PAGE: fetch con JWT
// books.html → cargar listado
if (location.pathname.endsWith('books.html')) {
  (async () => {
    const token = localStorage.getItem('jwt');
    if (!token) {
      // si no hay token, voy a login
      return location.href = 'login.html';
    }
    // inyecto el Bearer token
    const res = await fetch('/api/books', {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + token
      }
    });
    if (res.ok) {
      const books = await res.json();
      const tbody = document.querySelector('#books-table tbody');
      books.forEach(b => {
        const tr = document.createElement('tr');
        tr.innerHTML = `<td>${b.id}</td><td>${b.title}</td><td>${b.author}</td>`;
        tbody.appendChild(tr);
      });
    } else {
      // token expirado o inválido: vuelvo a login
      localStorage.removeItem('jwt');
      location.href = 'login.html';
    }
  })();
}

