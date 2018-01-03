DROP TABLE IF EXISTS books;
CREATE TABLE books
(
	id           BIGINT(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
	author       VARCHAR(255),
	count        INT(11),
	description  VARCHAR(255),
	publish_year INT(11),
	title        VARCHAR(255)
);
DROP TABLE IF EXISTS books_onhold;
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
DROP TABLE IF EXISTS user_role_mapping;
CREATE TABLE user_role_mapping
(
	role_id BIGINT(20) NOT NULL,
	user_id BIGINT(20) NOT NULL,
	CONSTRAINT `PRIMARY` PRIMARY KEY (user_id, role_id)
);
CREATE INDEX FKl49eoylmyin6gvqydg53gps1e
	ON user_role_mapping (role_id);
DROP TABLE IF EXISTS user_roles;
CREATE TABLE user_roles
(
	id        BIGINT(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
	role_name VARCHAR(255)
);
DROP TABLE IF EXISTS users;
CREATE TABLE users
(
	id            BIGINT(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
	email         VARCHAR(255),
	first_name    VARCHAR(255),
	last_name     VARCHAR(255),
	user_password VARCHAR(255),
	user_name     VARCHAR(255)
);

INSERT INTO users VALUES (1, 'admin@admin.by', 'admin', 'admin', 'admin', 'admin');
INSERT INTO user_roles VALUES (1, 'librarian');
INSERT INTO user_roles VALUES (2, 'reader');
INSERT INTO user_role_mapping VALUES (1, 1);
INSERT INTO books VALUES (1, 'Robert C.Martin', 2, 'Very userful book', 2008, 'Clean Code');
INSERT INTO books VALUES (2, 'Kathy Sierra', 3, 'One more useful book', 2005, 'Head First Java');
INSERT INTO books VALUES (3, 'Bert Bates', 2, 'Awesome book', 2004, 'Head First Servlets and JSP');