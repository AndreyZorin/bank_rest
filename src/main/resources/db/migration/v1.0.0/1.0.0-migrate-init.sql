CREATE TABLE IF NOT EXISTS users
(
    id         bigserial PRIMARY KEY,
    username varchar(20) UNIQUE NOT NULL,
    password varchar(128) UNIQUE NOT NULL,
    first_name varchar(128) NOT NULL,
    last_name  varchar(128) NOT NULL,
    created_at timestamptz  NOT NULL,
    updated_at timestamptz  NOT NULL
);

COMMENT ON TABLE users IS 'Пользователи';
COMMENT ON COLUMN users.id IS 'Идентификатор пользователя';
COMMENT ON COLUMN users.username IS 'Уникальный ник пользователя';
COMMENT ON COLUMN users.password IS 'Пароль пользователя';
COMMENT ON COLUMN users.first_name IS 'Имя пользователя';
COMMENT ON COLUMN users.last_name IS 'Фамилия пользователя';
COMMENT ON COLUMN users.created_at IS 'Время создания записи';
COMMENT ON COLUMN users.updated_at IS 'Время обновления записи';

CREATE TABLE IF NOT EXISTS cards
(
    id              bigserial PRIMARY KEY,
    card_number     varchar(20) UNIQUE NOT NULL,
    user_id         bigint             NOT NULL REFERENCES users,
    validity_period date               NOT NULL,
    status          varchar(20)        NOT NULL,
    balance         numeric(15, 2)     NOT NULL,
    block_request   boolean            default false,
    created_at      timestamptz        NOT NULL,
    updated_at      timestamptz        NOT NULL
);

COMMENT ON TABLE cards IS 'Банковские карты';
COMMENT ON COLUMN cards.id IS 'Идентификатор карты';
COMMENT ON COLUMN cards.card_number IS 'Номер карты';
COMMENT ON COLUMN cards.user_id IS 'Идентификатор владельца карты';
COMMENT ON COLUMN cards.validity_period IS 'Срок действия карты';
COMMENT ON COLUMN cards.status IS 'Статус карты';
COMMENT ON COLUMN cards.balance IS 'Баланс карты';
COMMENT ON COLUMN cards.block_request IS 'Запрос на блокировку карты';
COMMENT ON COLUMN cards.created_at IS 'Время создания записи';
COMMENT ON COLUMN cards.updated_at IS 'Время обновления записи';

CREATE TABLE IF NOT EXISTS roles
(
    id         bigserial PRIMARY KEY,
    name       varchar(128) NOT NULL
);

COMMENT ON TABLE roles IS 'Роли пользователя';
COMMENT ON COLUMN roles.id IS 'Идентификатор роли пользователя';
COMMENT ON COLUMN roles.name IS 'Имя роли пользователя';

CREATE TABLE IF NOT EXISTS users_roles
(
    user_id bigserial NOT NULL,
    role_id bigserial NOT NULL
);

COMMENT ON TABLE users_roles IS 'Таблица связи ролей и пользователей';
COMMENT ON COLUMN users_roles.user_id IS 'Идентификатор пользователя';
COMMENT ON COLUMN users_roles.role_id IS 'Идентификатор роли пользователя';

INSERT INTO roles (name)
VALUES ('ADMIN'), ('USER');

INSERT INTO users (username, password, first_name, last_name, created_at, updated_at)
VALUES ('superman', '$2a$10$faAgFGXt9/dicw9uc3sMruH8srAYam1bA1Spjw1NqQiFesOoNA24.', 'Jhon', 'Smith', now(), now());

INSERT INTO users_roles (user_id, role_id)
VALUES (1, 1);
