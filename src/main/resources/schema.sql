-- =============================================
-- Script de creación de la base de datos
-- Gestión de Usuarios y Edificios - Arquitectura Hexagonal (Spring Boot)
-- =============================================

CREATE DATABASE IF NOT EXISTS crud_edificios
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE crud_edificios;

CREATE TABLE IF NOT EXISTS users (
    id          VARCHAR(36)  NOT NULL PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    email       VARCHAR(150) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    role        ENUM('ADMIN', 'MEMBER', 'REVIEWER') NOT NULL,
    status      ENUM('ACTIVE', 'INACTIVE', 'PENDING', 'BLOCKED') NOT NULL DEFAULT 'PENDING',
    created_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS edificios (
    id                    VARCHAR(36)       NOT NULL PRIMARY KEY,
    nombre                VARCHAR(120)      NOT NULL,
    metros_cuadrados      DECIMAL(12,2)     NOT NULL,
    altura                DECIMAL(7,2)      NOT NULL,
    num_pisos             SMALLINT UNSIGNED NOT NULL,
    num_apartamentos      INT UNSIGNED      NOT NULL DEFAULT 0,
    num_oficinas          INT UNSIGNED      NOT NULL DEFAULT 0,
    nombre_parqueadero    VARCHAR(100)      NOT NULL,
    num_piscinas          SMALLINT UNSIGNED NOT NULL DEFAULT 0,
    pais                  VARCHAR(100)      NOT NULL,
    departamento          VARCHAR(100)      NOT NULL,
    ciudad                VARCHAR(100)      NOT NULL,
    tiene_ascensor        TINYINT(1)        NOT NULL DEFAULT 0,
    valor_administracion  DECIMAL(12,2)     NOT NULL DEFAULT 0.00,
    tiene_zona_social     TINYINT(1)        NOT NULL DEFAULT 0,
    created_at            DATETIME          NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at            DATETIME          NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uq_edificios_nombre_ciudad (nombre, ciudad)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Usuario administrador inicial (password: Admin1234!)
INSERT INTO users (id, name, email, password, role, status)
VALUES (
    '00000000-0000-0000-0000-000000000001',
    'Administrador',
    'admin@example.com',
    '$2a$12$sfwkajsls.fktQb0Ngw5cOiSiV3z4LIwbucSAy4sw2wyNlxbUBt4q',
    'ADMIN',
    'ACTIVE'
)
ON DUPLICATE KEY UPDATE name = VALUES(name);
