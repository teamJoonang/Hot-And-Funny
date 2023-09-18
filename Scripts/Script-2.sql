create table qna (
                id bigint not null primary key auto_increment,
                admin_id bigint not null,
                title varchar(200) not null,
                writer varchar(100) not null,
                content text null,
                hit int default 0,
                created_at datetime not null default now(),
                category varchar(50) not null,
                foreign key (admin_id) references admin(id)
);

insert into qna  (admin_id, title, writer, content, category)
     values(1, '제목', '나', '내용', 'test_1');