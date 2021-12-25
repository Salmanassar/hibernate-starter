CREATE TABLE users
(
user_name VARCHAR(128) PRIMARY KEY,
first_name VARCHAR(128) NOT NULL,
last_name VARCHAR (128) NOT NULL,
birth_date DATE,
role VARCHAR(32) NOT NULL
);
DROP table users;