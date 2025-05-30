/* console.log(" login.js cargado"); */

const form = document.getElementById('login-form');
const err  = document.getElementById('error');

function parseJwt(token) {
  try {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
      return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));
    return JSON.parse(jsonPayload);
  } catch (e) {
    console.error("Error al decodificar token", e);
    return null;
  }
}

form.addEventListener('submit', async e => {
  e.preventDefault();
  err.textContent = '';

  const username = form.username.value.trim();
  const password = form.password.value;

  try {
    const resp = await fetch('/api/auth/login', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ username, password })
    });

    if (!resp.ok) {
      if (resp.status === 401) throw new Error('Usuario o contraseña incorrectos');
      else throw new Error(`Error ${resp.status}`);
    }

    const { token } = await resp.json();
    if (!token) throw new Error('No se recibió token');

    localStorage.setItem('jwt', token);

    const payload = parseJwt(token);
    if (!payload || !payload.role) throw new Error("Token inválido");

    console.log("🎯 ROL:", payload.role);

    if (payload.role === "ADMIN") {
      window.location = "admin.html";
    } else {
      window.location = "books.html";
    }

  } catch (e) {
    err.textContent = e.message;
    console.error(e);
  }
});
