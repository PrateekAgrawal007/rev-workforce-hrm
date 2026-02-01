CREATE DATABASE rev_workforce;
USE rev_workforce;

CREATE TABLE employees (
    employee_id INT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100),
    password VARCHAR(100),
    phone VARCHAR(15),
    address TEXT,
    role ENUM('EMPLOYEE','MANAGER','ADMIN'),
    manager_id INT,
    department VARCHAR(50),
    designation VARCHAR(50),
    joining_date DATE,
    active BOOLEAN DEFAULT TRUE
);


INSERT INTO employees VALUES
(1,'Admin User','admin@rev.com','admin123','9999999999','HQ','ADMIN',NULL,'HR','Admin','2020-01-01',true),

(2,'Manager One','manager@rev.com','manager123','8888888888','Indore','MANAGER',1,'IT','Manager','2021-01-01',true),

(3,'Employee One','emp@rev.com','emp123','7777777777','Bhopal','EMPLOYEE',2,'IT','Developer','2022-01-01',true);


CREATE TABLE leaves (
    leave_id INT AUTO_INCREMENT PRIMARY KEY,
    employee_id INT,
    leave_type VARCHAR(20),
    start_date DATE,
    end_date DATE,
    reason VARCHAR(255),
    status VARCHAR(20) DEFAULT 'PENDING',
    applied_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id)
);

CREATE TABLE leave_balance (
    employee_id INT PRIMARY KEY,
    casual_leave INT DEFAULT 10,
    sick_leave INT DEFAULT 10,
    paid_leave INT DEFAULT 15,
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id)
);

INSERT INTO leave_balance (employee_id) VALUES (1), (2), (3);

ALTER TABLE leaves
ADD COLUMN manager_comment VARCHAR(255);


CREATE TABLE notifications (
    notification_id INT AUTO_INCREMENT PRIMARY KEY,
    employee_id INT,
    message VARCHAR(255),
    is_read BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id)
);

CREATE TABLE performance_reviews (
    review_id INT AUTO_INCREMENT PRIMARY KEY,
    employee_id INT,
    achievements TEXT,
    improvements TEXT,
    self_rating INT,
    manager_feedback TEXT,
    manager_rating INT,
    status VARCHAR(20) DEFAULT 'SUBMITTED',
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id)
);

CREATE TABLE goals (
    goal_id INT AUTO_INCREMENT PRIMARY KEY,
    employee_id INT,
    goal_description VARCHAR(255),
    priority VARCHAR(20),
    status VARCHAR(20) DEFAULT 'IN_PROGRESS',
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id)
);
