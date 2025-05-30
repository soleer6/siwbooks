1--	Definición de la entidad (Book)

Creamos una clase Java con campos id, title, author e isbn.

Gracias a JPA, esa clase “se mapea” automáticamente a una tabla en PostgreSQL, donde cada libro es una fila.

2--	Capa de acceso a datos (BookRepository)

Hereda de una interfaz de Spring (JpaRepository).

Nos da métodos listos para usar: save(), findAll(), findById(), delete(), etc., sin escribir SQL.

3--	Lógica de negocio (BookService + BookServiceImpl)

Definimos una interfaz con métodos como findAll(), findById(id), create(book), update(id, book) y delete(id).

La implementación se encarga de:

- Verificar si un libro existe antes de actualizar o borrar.
- Lanzar una excepción si no lo encuentra (para devolver un 404 al cliente).

4--	Controladores REST (BookController)

Exponen rutas HTTP:

- GET /api/books → lista todos los libros.
- GET /api/books/{id} → devuelve un solo libro.
- POST /api/books → crea un nuevo libro.
- PUT /api/books/{id} → actualiza un libro existente.
- DELETE /api/books/{id} → borra un libro.

Cada método recibe o devuelve objetos Java que Spring automáticamente traduce a JSON.

5--	Seguridad básica

Protegemos todas las rutas con Basic Auth (usuario+contraseña).

Configuramos credenciales fijas en application.properties o en SecurityConfig, para que no cambien al reiniciar.

6--	Manejo de errores y validación

Si el cliente pide un libro que no existe, devolvemos un 404 Not Found con un mensaje claro.

Si el cliente envía datos mal formados (p. ej. ISBN con formato incorrecto), devolvemos un 400 Bad Request con las razones de validación.

7--	Pruebas y puesta en marcha

Probamos manualmente con curl y Postman: listas, creación, lectura, actualización y borrado de libros.

Verificamos que la base de datos (PostgreSQL) y la herramienta de cliente (DBeaver) estén sincronizadas.

8--	Control de versiones

Inicializamos un repositorio git y subimos el proyecto a GitHub, para mantener el historial de cambios y facilitarnos el trabajo en equipo.

9--	Paginación y filtros en GET /api/books

- Parámetros opcionales:
  • page (número de página, defecto 0)  
  • size (tamaño de página, defecto 10)  
  • title (filtra libros cuyo título contiene esta cadena, case-insensitive)  
  • author (filtra libros cuyo autor contiene esta cadena, case-insensitive)  

10--	Registro e inicio de sesión de usuarios

- POST /api/auth/register: crea una nueva cuenta con email y contraseña (encrypted).  
- POST /api/auth/login: valida credenciales y devuelve un token JWT.

