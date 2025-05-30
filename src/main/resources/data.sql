-- src/main/resources/data.sql

-- 0) Truncamos las tablas y reiniciamos sus secuencias
/* TRUNCATE TABLE books, users RESTART IDENTITY;

-- 1) Usuarios: admin + 5 de prueba
--     – ADMIN tiene password "admin"
--     – usuario1..5 tienen password "password"
INSERT INTO users (username, email, password) VALUES
  ('admin',    'admin@example.com',   '$2a$10$7EqJtq98hPqEX7fNZaFWoOYc4pBhKqTVK8o3ru6X2N1uB3W/01G4a'),
  ('usuario1', 'usuario1@ejemplo.com','$2a$10$DowJ/7H5FZ9j/7RkqL/U5u8MNbXubO5Ern8DbxyplOOEjLuWgtOrS'),
  ('usuario2', 'usuario2@ejemplo.com','$2a$10$DowJ/7H5FZ9j/7RkqL/U5u8MNbXubO5Ern8DbxyplOOEjLuWgtOrS'),
  ('usuario3', 'usuario3@ejemplo.com','$2a$10$DowJ/7H5FZ9j/7RkqL/U5u8MNbXubO5Ern8DbxyplOOEjLuWgtOrS'),
  ('usuario4', 'usuario4@ejemplo.com','$2a$10$DowJ/7H5FZ9j/7RkqL/U5u8MNbXubO5Ern8DbxyplOOEjLuWgtOrS'),
  ('usuario5', 'usuario5@ejemplo.com','$2a$10$DowJ/7H5FZ9j/7RkqL/U5u8MNbXubO5Ern8DbxyplOOEjLuWgtOrS');

-- 2) Libros: 25 entradas genéricas
INSERT INTO books (title, author, isbn) VALUES
  ('Libro 1',  'Autor 1',  'ISBN-0000000001'),
  ('Libro 2',  'Autor 2',  'ISBN-0000000002'),
  ('Libro 3',  'Autor 3',  'ISBN-0000000003'),
  ('Libro 4',  'Autor 4',  'ISBN-0000000004'),
  ('Libro 5',  'Autor 5',  'ISBN-0000000005'),
  ('Libro 6',  'Autor 6',  'ISBN-0000000006'),
  ('Libro 7',  'Autor 7',  'ISBN-0000000007'),
  ('Libro 8',  'Autor 8',  'ISBN-0000000008'),
  ('Libro 9',  'Autor 9',  'ISBN-0000000009'),
  ('Libro 10', 'Autor 10', 'ISBN-0000000010'),
  ('Libro 11', 'Autor 11', 'ISBN-0000000011'),
  ('Libro 12', 'Autor 12', 'ISBN-0000000012'),
  ('Libro 13', 'Autor 13', 'ISBN-0000000013'),
  ('Libro 14', 'Autor 14', 'ISBN-0000000014'),
  ('Libro 15', 'Autor 15', 'ISBN-0000000015'),
  ('Libro 16', 'Autor 16', 'ISBN-0000000016'),
  ('Libro 17', 'Autor 17', 'ISBN-0000000017'),
  ('Libro 18', 'Autor 18', 'ISBN-0000000018'),
  ('Libro 19', 'Autor 19', 'ISBN-0000000019'),
  ('Libro 20', 'Autor 20', 'ISBN-0000000020'),
  ('Libro 21', 'Autor 21', 'ISBN-0000000021'),
  ('Libro 22', 'Autor 22', 'ISBN-0000000022'),
  ('Libro 23', 'Autor 23', 'ISBN-0000000023'),
  ('Libro 24', 'Autor 24', 'ISBN-0000000024'),
  ('Libro 25', 'Autor 25', 'ISBN-0000000025');

 */