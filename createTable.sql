CREATE TABLE users
(
    id         BIGSERIAL PRIMARY KEY,
    user_name  VARCHAR(128) UNIQUE,
    first_name VARCHAR(128),
    last_name  VARCHAR(128),
    birth_date DATE,
    role       VARCHAR(32),
    info       JSONB,
    company_id INT references company (id)
);

CREATE TABLE company
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255)
);
-- create sequence users_id_seq
-- owned by users.id;
create table profile
(
    id BIGSERIAL PRIMARY KEY,
    user_id  BIGINT NOT NULL UNIQUE references users (id),
    street   VARCHAR(255),
    language CHAR(2)
);
DROP table users;
DROP table profile;