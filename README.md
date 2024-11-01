# GestionNotas
Proyecto tercer semestre POO
DB
CREATE DATABASE gestion_notas;
USE gestion_notas;

CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario VARCHAR(50) NOT NULL,
    contrasena VARCHAR(50) NOT NULL,
    rol ENUM('ADMIN', 'DOCENTE', 'ESTUDIANTE') NOT NULL
);

CREATE TABLE estudiantes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(10) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100),
    correo VARCHAR(100),
    fecha_nacimiento DATE
);

CREATE TABLE cursos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(10) NOT NULL,
    nombre VARCHAR(100) NOT NULL
);

CREATE TABLE estudiantes_cursos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    estudiante_id INT,
    curso_id INT,
    FOREIGN KEY (estudiante_id) REFERENCES estudiantes(id),
    FOREIGN KEY (curso_id) REFERENCES cursos(id)
);

-- Insertar el usuario admin
INSERT INTO usuarios (usuario, contrasena, rol) VALUES ('admin', 'admin123', 'ADMIN');

Driver Java
https://dev.mysql.com/downloads/connector/j/
