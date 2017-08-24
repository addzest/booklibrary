create table books
(
	id int auto_increment
		primary key,
	title varchar(255) null,
	author varchar(255) null,
	publish_year int null,
	count int null,
	description text null,
	constraint id_UNIQUE
		unique (id)
)
;

create table books_onhold
(
	operation_id int auto_increment
		primary key,
	book_id int null,
	user_id int null,
	hold_type varchar(255) null,
	approved tinyint(1) null,
	constraint books_onhold_operation_id_uindex
		unique (operation_id)
)
;

create table user_role_mapping
(
	user_id int not null,
	role_id int not null
)
;

create table user_roles
(
	id int not null
		primary key,
	role_name varchar(45) null,
	constraint id_UNIQUE
		unique (id)
)
;

create table users
(
	id int auto_increment
		primary key,
	user_name varchar(255) null,
	user_password varchar(255) null,
	first_name varchar(255) null,
	last_name varchar(255) null,
	email varchar(255) null,
	constraint id_UNIQUE
		unique (id)
)
;

