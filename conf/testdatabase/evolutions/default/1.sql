# --- !Ups

create table user (
    id int auto_increment primary key,
    name varchar(31) not null,
    email varchar(127) not null,
    password varchar(31) not null,
    created_at timestamp not null default current_timestamp,
    updated_at timestamp not null default current_timestamp
);

insert into user (name, email, password) values ('taro', 'taro@gmail.com', 'taro123');
insert into user (name, email, password) values ('sato', 'sato@gmail.com', 'sato123');
insert into user values ('kato', 'kato@gmail.com', 'kato123');

# --- !Downs
drop table user