ALTER TABLE pixel_arts
ADD COLUMN user_id CHAR(36),
ADD FOREIGN KEY (user_id) REFERENCES users(id);