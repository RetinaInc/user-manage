drop table if exists users_table;

create table users_table (
    id bigint auto_increment,
    username varchar(64) not null unique,
    email varchar(64) not null unique,
    roles varchar(255) not null,
    password varchar(255) not null,
    salt varchar(64) not null,
    register_date timestamp not null default 0,
    primary key(id)
) engine=InnoDB;