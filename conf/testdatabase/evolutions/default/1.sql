# --- !Ups

create table user (
    id int auto_increment primary key,
    name varchar(31) not null,
    email varchar(127) not null,
    password varchar(31) not null,
    created_at timestamp not null default current_timestamp,
    updated_at timestamp not null default current_timestamp
);

insert into user values (default, 'taro', 'taro@gmail.com', 'taro123', default, default);
insert into user values (default, 'sato', 'sato@gmail.com', 'sato123', default, default);
insert into user values (default, 'kato', 'kato@gmail.com', 'kato123', default, default);

# --- !Downs
drop table user