CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(20),
    avatar VARCHAR(255),
    status ENUM('ACTIVE', 'INACTIVE', 'SUSPENDED', 'PENDING') DEFAULT 'ACTIVE',
    role ENUM('USER', 'ADMIN', 'MODERATOR') DEFAULT 'USER',
    deleted BOOLEAN DEFAULT FALSE,
    deleted_at TIMESTAMP NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_status ON users(status);
CREATE INDEX idx_users_deleted ON users(deleted);

-- Seed admin
INSERT INTO users (name, username, password, email, phone, avatar, status, role, deleted, created_at, updated_at)
VALUES ('Administrator', 'admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDa', 'admin@example.com', '+1234567890', 'https://via.placeholder.com/150', 'ACTIVE', 'ADMIN', false, NOW(), NOW())
ON DUPLICATE KEY UPDATE username=username;

-- Seed moderator
INSERT INTO users (name, username, password, email, phone, avatar, status, role, deleted, created_at, updated_at)
VALUES ('Moderator User', 'moderator', '$2a$10$8K1p/a0dL1LXMIgoEDFrwOfgqwAGcwZQh3UPHz3UaCzHxKJ5J5J5K', 'moderator@example.com', '+1234567891', 'https://via.placeholder.com/150', 'ACTIVE', 'MODERATOR', false, NOW(), NOW())
ON DUPLICATE KEY UPDATE username=username;

-- Seed user
INSERT INTO users (name, username, password, email, phone, avatar, status, role, deleted, created_at, updated_at)
VALUES ('Regular User', 'user', '$2a$10$8K1p/a0dL1LXMIgoEDFrwOfgqwAGcwZQh3UPHz3UaCzHxKJ5J5J5K', 'user@example.com', '+1234567892', 'https://via.placeholder.com/150', 'ACTIVE', 'USER', false, NOW(), NOW())
ON DUPLICATE KEY UPDATE username=username; 