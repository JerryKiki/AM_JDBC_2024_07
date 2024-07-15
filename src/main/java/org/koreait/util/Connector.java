package org.koreait.util;

import java.sql.*;

public class Connector {
    Connection con = null; //데이터베이스와 연결을 위한 객체
    PreparedStatement pstmt = null; //SQL문을 데이터베이스에 보내기 위한 객체
    String driver = "org.mariadb.jdbc.Driver";   //1. JDBC Driver Class
    // 2. 데이터베이스에 연결하기 위한 정보
    String url = "jdbc:mariadb://127.0.0.1:3306/AM_JDBC_2024_07?useUnicode=true&characterEncoding=utf8&autoReconnect=true&severTimezone=Asia/Seoul"; //연결 문자열
    String user = "root"; //데이터베이스 ID
    String pw = ""; //데이터베이스 PW
    String SQL = "SELECT * from article";
    ResultSet rs = null;

    public Connector() {
        try {
            // 1. JDBC 드라이버 로딩 - MySQL JDBC 드라이버의 Driver Class 로딩
            Class.forName(driver);
            // 2. Connection 생성 - .getConnection(연결문자열, DB-ID, DB-PW)
            con = DriverManager.getConnection(url, user, pw);
            // 3. PreParedStatement 객체 생성, 객체 생성시 SQL 문장 저장
            pstmt = con.prepareStatement(SQL);
            System.out.println("연결 성공!");
        } catch (SQLException e) {
            System.out.println("에러 : " + e);
        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 로딩 실패" + e);
        }
    }

    public Connection getCon() {
        return con;
    }

    public void closeConnection() {
        try {
            if (rs != null && !rs.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null && !pstmt.isClosed()) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
