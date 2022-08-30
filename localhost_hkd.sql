create table student(
id char(7) primary key,
name varchar2(50) not null,
dept varchar2(50) not null
);

insert into student values ('1091011', '김철수', '컴퓨터시스템');
insert into student values('0792012','최고봉','멀티미디어');
insert into student(id, name, dept) values('7777777','아무개','아무개학과');

alter table student add(
    address varchar2(20)
    );
    
create table books(
    no number primary key,
    title varchar2(50) not null
);

insert into books values(1, 'java개론');
insert into books values(2, 'SQL기본');
insert into books values(3, 'html5');

create table bookrent(
    no number primary key,
    id char(7) not null references student(id),
    bookno number not null references books(no),
    rdate date default sysdate
);

insert into bookrent(no,id,bookno) values(1,'1091011',1);
insert into bookrent(no,id,bookno) values(2,'0792012',2);
insert into bookrent(no,id,bookno) values(4,'7777777',2);
insert into bookrent(no,id,bookno) values(3,'0494013',3);
insert into bookrent(no,id,bookno) values(5,'3333333',3);
insert into bookrent(no,id,bookno) values(6,'5555555',1);

commit;