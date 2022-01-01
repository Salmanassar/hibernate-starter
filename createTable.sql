CREATE TABLE users
(
    id         BIGINT PRIMARY KEY,
    user_name  VARCHAR(128) UNIQUE,
    first_name VARCHAR(128) NOT NULL,
    last_name  VARCHAR(128) NOT NULL,
    birth_date DATE,
    role       VARCHAR(32)  NOT NULL,
    info       JSONB        NOT NULL
);
create sequence users_id_seq
owned by users.id;

DROP table users;