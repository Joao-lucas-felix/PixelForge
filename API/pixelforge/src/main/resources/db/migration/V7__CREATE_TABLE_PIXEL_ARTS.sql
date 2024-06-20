CREATE TABLE pixel_arts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(1100) NOT NULL,
    is_free_use bit(1) NOT NULL,
    file_path VARCHAR(255) NOT NULL
);
