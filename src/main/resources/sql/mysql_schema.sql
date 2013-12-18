create table account(
    id bigint auto_increment,
    username varchar(64) not null unique,
    email varchar(64) not null unique,
    password varchar(255) not null,
    salt varchar(64) not null,
    register_date timestamp not null default 0,
    primary key(id)
) engine=InnoDB;

create table role(
    id bigint auto_increment,
    rolename varchar(64) not null,
    primary key(id)
) engine=InnoDB;

create table account_role(
    account_id bigint not null,
    role_id bigint not null,
    primary key(account_id, role_id)
) engine=InnoDB;