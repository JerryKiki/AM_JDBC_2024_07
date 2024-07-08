#테스트
CREATE DATABASE JDBC;
USE JDBC;
CREATE TABLE users (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       `Name` VARCHAR(25) NOT NULL
);
INSERT INTO users
(NAME) VALUES ('awesomeo184');

#삭제
DROP DATABASE IF EXISTS `JDBC`;

#사용할 데이터베이스 만듦
CREATE DATABASE `AM_JDBC_2024_07`;
USE `AM_JDBC_2024_07`;
CREATE TABLE `article`(
                          id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
                          regDate DATETIME NOT NULL,
                          updateDate DATE NOT NULL,
                          title CHAR(100) NOT NULL,
                          `body` TEXT NOT NULL
);

#확인
SELECT * FROM article;

#테스트 데이터 삽입
INSERT INTO article
SET regDate = '2024-05-05 12:12:12',
    updateDate = '2024-05-05 12:12:12',
    title = 'TEST',
    `body` = 'TEST'