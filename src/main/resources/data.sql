-- Insert sample admin user
-- Password: admin123 (BCrypt encoded)
INSERT INTO users (name, username, password, email, phone, avatar, status, role, deleted, created_at, updated_at)
VALUES (
           'Administrator',
           'admin',
           '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDa',
           'admin@example.com',
           '+1234567890',
           'https://via.placeholder.com/150',
           'ACTIVE',
           'ADMIN',
           false,
           NOW(),
           NOW()
       );

-- Insert sample moderator user
-- Password: moderator123 (BCrypt encoded)
INSERT INTO users (name, username, password, email, phone, avatar, status, role, deleted, created_at, updated_at)
VALUES (
           'Moderator User',
           'moderator',
           '$2a$10$8K1p/a0dL1LXMIgoEDFrwOfgqwAGcwZQh3UPHz3UaCzHxKJ5J5J5K',
           'moderator@example.com',
           '+1234567891',
           'https://via.placeholder.com/150',
           'ACTIVE',
           'MODERATOR',
           false,
           NOW(),
           NOW()
       );

-- Insert sample regular user
-- Password: user123 (BCrypt encoded)
INSERT INTO users (name, username, password, email, phone, avatar, status, role, deleted, created_at, updated_at)
VALUES (
           'Regular User',
           'user',
           '$2a$10$8K1p/a0dL1LXMIgoEDFrwOfgqwAGcwZQh3UPHz3UaCzHxKJ5J5J5K',
           'user@example.com',
           '+1234567892',
           'https://via.placeholder.com/150',
           'ACTIVE',
           'USER',
           false,
           NOW(),
           NOW()
       );