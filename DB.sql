CREATE DATABASE JDBC;
USE JDBC;
CREATE TABLE users (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       `Name` VARCHAR(25) NOT NULL
);
INSERT INTO users
(NAME) VALUES ('awesomeo184');

DROP DATABASE IF EXISTS `articles`;
DROP DATABASE IF EXISTS `JDBC`;

CREATE DATABASE `AM_JDBC_2024_07`;
USE `AM_JDBC_2024_07`;
CREATE TABLE `article`(
                          id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
                          regDate DATETIME NOT NULL,
                          updateDate DATE NOT NULL,
                          title CHAR(100) NOT NULL,
                          `body` TEXT NOT NULL
);

SELECT * FROM article;