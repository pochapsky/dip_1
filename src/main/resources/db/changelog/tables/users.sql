create table users
(
    id       BIGSERIAL PRIMARY KEY,
    login    varchar(256) unique not null,
    password varchar(256) not null
);

