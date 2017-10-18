CREATE TABLE books
(
	id           BIGINT(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
	author       VARCHAR(255),
	count        INT(11),
	description  VARCHAR(255),
	publish_year INT(11),
	title        VARCHAR(255)
);
CREATE TABLE books_onhold
(
	approved  TINYINT(4),
	hold_type VARCHAR(255) NOT NULL,
	user_id   BIGINT(20)   NOT NULL,
	book_id   BIGINT(20)   NOT NULL,
	CONSTRAINT `PRIMARY` PRIMARY KEY (book_id, user_id)
);
CREATE INDEX FKthh32fbe8d2vn0i9da7b8h9en
	ON books_onhold (user_id);
CREATE TABLE user_role_mapping
(
	role_id BIGINT(20) NOT NULL,
	user_id BIGINT(20) NOT NULL,
	CONSTRAINT `PRIMARY` PRIMARY KEY (user_id, role_id)
);
CREATE INDEX FKl49eoylmyin6gvqydg53gps1e
	ON user_role_mapping (role_id);
CREATE TABLE user_roles
(
	id        BIGINT(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
	role_name VARCHAR(255)
);
CREATE TABLE users
(
	id            BIGINT(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
	email         VARCHAR(255),
	first_name    VARCHAR(255),
	last_name     VARCHAR(255),
	user_password VARCHAR(255),
	user_name     VARCHAR(255)
);