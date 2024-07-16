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
    `body` = 'TEST';

#멤버 테이블 만들기...
CREATE TABLE member(
                       id INT(10) unsigned not null primary key auto_increment,
                       regDate DATETIME not null,
                       loginId char(100) not null unique,
                       loginPw char(100) not null,
                       nickName char(100) not null
);

DROP TABLE member;

SELECT * FROM member;

SELECT a.id, a.regDate, a.title, a.body, m.nickName
FROM article a
         INNER JOIN `member` m
                    ON a.author = m.id;

SELECT * FROM article;

##################################################

#강사님 레포지터리의 SQL문

DROP DATABASE IF EXISTS `AM_JDBC_2024_07`;
CREATE DATABASE `AM_JDBC_2024_07`;
USE `AM_JDBC_2024_07`;

SHOW TABLES;

CREATE TABLE article(
                        id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
                        regDate DATETIME NOT NULL,
                        updateDate DATETIME NOT NULL,
                        title CHAR(100) NOT NULL,
                        `body` TEXT NOT NULL
);

CREATE TABLE `member`(
                         id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
                         regDate DATETIME NOT NULL,
                         updateDate DATETIME NOT NULL,
                         loginId CHAR(30) NOT NULL,
                         loginPw CHAR(200) NOT NULL,
                         `name` CHAR(100) NOT NULL
);

ALTER TABLE article ADD COLUMN memberId INT(10) UNSIGNED NOT NULL AFTER updateDate;

## 아티클 테스트 데이터 생성
INSERT INTO article
SET regDate = NOW(),
    updateDate = NOW(),
    memberId = 1,
    title = '제목1',
    `body` = '내용1';

INSERT INTO article
SET regDate = NOW(),
    updateDate = NOW(),
    memberId = 1,
    title = '제목2',
    `body` = '내용2';

INSERT INTO article
SET regDate = NOW(),
    updateDate = NOW(),
    memberId = 2,
    title = '제목3',
    `body` = '내용3';

## 회원 테스트 데이터 생성
INSERT INTO `member`
SET regDate = NOW(),
    updateDate = NOW(),
    loginId = 'test1',
    loginPw = 'test1',
    `name` = '김철수';

INSERT INTO `member`
SET regDate = NOW(),
    updateDate = NOW(),
    loginId = 'test2',
    loginPw = 'test2',
    `name` = '홍길동';

## 게시글 테스트 데이터 대량 생성
INSERT INTO article
SET regDate = NOW(),
    updateDate = NOW(),
    title = CONCAT('제목', SUBSTRING(RAND() * 1000 FROM 1 FOR 2)),
    `body` = CONCAT('내용', SUBSTRING(RAND() * 1000 FROM 1 FOR 2)),
    author = 1;

SELECT *
FROM article;

SELECT *
FROM `member`;

###############################################################################
SELECT COUNT(*) > 0
FROM `member`
WHERE loginId = 'test2';

SELECT COUNT(*) > 0
FROM `member`
WHERE loginId = 'test3';

SELECT 1 + 1;
SELECT 1 > 1;

SELECT NOW();

SELECT '제목1';

SELECT CONCAT('제목',' 1');

SELECT SUBSTRING(RAND() * 1000 FROM 1 FOR 2);

SELECT *
FROM article;

