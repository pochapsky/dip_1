create table files
(
    id           BIGSERIAL PRIMARY KEY,
    filename     varchar(256) unique not null,
    edited_at    timestamp,
    size         bigint              not null,
    file_content bytea               not null,
    user_id      bigserial           references users (id) on delete set null
);
