# --- !Ups

create table micropost (
    id int auto_increment primary key,
    user_id int not null,
    title varchar(127) not null,
    message varchar(255) not null,
    link varchar(255),
    created_at timestamp not null default current_timestamp,
    updated_at timestamp not null default current_timestamp
);

insert into micropost (user_id, title, message, link) values (1, '挨拶です', 'こんにちは', 'https://www.kent-web.com/bbs/light/light.cgi');
insert into micropost (user_id, title, message, link) values (2, 'テスト投稿です', 'テストですよ', '');
insert into micropost (user_id, title, message, link) values (1, '開発', 'テストコードは重要です', '');

# --- !Downs
drop table micropost