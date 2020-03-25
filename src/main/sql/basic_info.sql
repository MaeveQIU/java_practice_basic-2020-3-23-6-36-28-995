CREATE DATABASE user DEFAULT CHARSET utf8 COLLATE utf8_general_ci;
USE user;
DROP TABLE basic_info;
CREATE TABLE basic_info (
	id SMALLINT AUTO_INCREMENT,
    name VARCHAR(20) NOT NULL UNIQUE,
	phone_number VARCHAR(20) NOT NULL,
	email VARCHAR(160) NOT NULL,
	password VARCHAR(160) NOT NULL,
    password_input_count TINYINT,
    account_status VARCHAR(20),
	PRIMARY KEY (id)
);
