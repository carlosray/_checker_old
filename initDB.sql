CREATE DATABASE checker;
USE checker;
CREATE TABLE notification_types
(
    notification_type_id INT AUTO_INCREMENT PRIMARY KEY,
    notification_type_name varchar(20) NOT NULL
);
CREATE TABLE roles
(
    role_id INT AUTO_INCREMENT PRIMARY KEY,
    role_name varchar(20) NOT NULL
);

CREATE TABLE users
(
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    user_name varchar(20) NOT NULL UNIQUE,
    password  varchar(32) NOT NULL,
    user_role_id INT,
    created_at date NOT NULL,
    FOREIGN KEY (user_role_id)  REFERENCES roles (role_id)
);
CREATE TABLE notifications
(
    notification_id INT AUTO_INCREMENT PRIMARY KEY,
    notification_type_id INT NOT NULL,
    user_id INT NOT NULL,
    destination_address varchar(50) NOT NULL,
    created_at date NOT NULL,
    FOREIGN KEY (notification_type_id) REFERENCES notification_types (notification_type_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);

INSERT INTO notification_types (notification_type_name) VALUES ('email');
INSERT INTO notification_types (notification_type_name) VALUES ('telegram');
INSERT INTO roles (role_name) VALUES ('admin');
INSERT INTO roles (role_name) VALUES ('member');
COMMIT;