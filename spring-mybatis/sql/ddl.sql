create database spring_test CHARACTER set utf8 collate utf8_general_ci;

create table school(
    id int primary key auto_increment,
	s_name varchar(100) comment '学校名称',
	s_grade int comment '学校等级'
)