# --- !Ups

create table micropost (
    id int auto_increment primary key,
    name varchar(255) not null,
    message varchar(255) not null,
    link varchar(255),
    delete_key varchar(15)
);

insert into micropost values (default, 'taro', 'こんにちは', 'https://www.kent-web.com/bbs/light/light.cgi', '1111');
insert into micropost values (default, 'sato', 'テストですよ', '', '1111');

# --- !Downs
drop table micropost