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

insert into micropost values (default, 1, '挨拶です', 'こんにちは', 'https://www.kent-web.com/bbs/light/light.cgi', default, default);
insert into micropost values (default, 2, 'テスト投稿です', 'テストですよ', '', default, default);

# --- !Downs
drop table micropost