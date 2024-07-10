package org.koreait;

import org.koreait.util.DBUtil;
import org.koreait.util.SecSql;
import org.koreait.util.Util;

import java.sql.*;

public class Database {
    Connection con = null; //데이터베이스와 연결을 위한 객체
    PreparedStatement pstmt = null; //SQL문을 데이터베이스에 보내기 위한 객체
    String driver = "org.mariadb.jdbc.Driver";   //1. JDBC Driver Class
    // 2. 데이터베이스에 연결하기 위한 정보
    String url = "jdbc:mariadb://127.0.0.1:3306/AM_JDBC_2024_07?useUnicode=true&characterEncoding=utf8&autoReconnect=true&severTimezone=Asia/Seoul"; //연결 문자열
    String user = "root"; //데이터베이스 ID
    String pw = ""; //데이터베이스 PW
    String SQL = "SELECT * from article";
    ResultSet rs = null;

    Database() {
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

    public int insertArticle(String title, String body) throws SQLException {
        SecSql sql = new SecSql();
        sql.append("INSERT INTO article");
        sql.append("SET regDate = NOW(),");
        sql.append("updateDate = NOW(),");
        sql.append("title = ?,", title);
        sql.append("`body` = ?;", body);
        return DBUtil.insert(con, sql);
    }

    public ResultSet viewArticleList() throws SQLException {
        String listSQL = "SELECT * FROM article ORDER BY `id` DESC";
        return pstmt.executeQuery(listSQL);
    }

    public int deleteArticle(int deleteId) throws SQLException {
        String deleteSQL = "DELETE FROM article WHERE id = ";
        deleteSQL += Integer.toString(deleteId);
        deleteSQL += ";";
        pstmt = con.prepareStatement(deleteSQL);
        return pstmt.executeUpdate();
    }

    public ResultSet viewOneArticle(int Idx) throws SQLException {
        String viewSQL = "SELECT * FROM article WHERE `id` = ";
        viewSQL += Integer.toString(Idx);
        viewSQL += ";";
        pstmt = con.prepareStatement(viewSQL);
        return pstmt.executeQuery();
    }

    public int updateArticle(String title, String body, int idx) throws SQLException {
        SecSql sql = new SecSql();
        sql.append("UPDATE article");
        sql.append("SET updateDate = NOW()");
        if(!title.isEmpty()) sql.append(", title = ?", title);
        if(!body.isEmpty()) sql.append(", `body` = ?", body);
        sql.append("WHERE id = ?;", idx);
        return DBUtil.update(con, sql);
    }

    public void closeSource() {
        try {
            if (rs != null && !rs.isClosed()) {
                con.close();
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
        try {
            if (pstmt != null && !pstmt.isClosed()) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

//기존 insert code
//        String insertSQL = "INSERT INTO article(regDate, updateDate, title, body) VALUES (?, ?, ?, ?)";
//        pstmt = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
//        pstmt.setString(1, Util.getNow());
//        pstmt.setString(2, Util.getNow());
//        pstmt.setString(3, title);
//        pstmt.setString(4, body);
//        pstmt.executeUpdate();
//        rs = pstmt.getGeneratedKeys();
//        rs.next();
//기존 update
//        String updateSQL = "UPDATE article";
//        updateSQL += " SET updateDate = NOW()";
//        if(!title.isEmpty()) updateSQL += "," + " title = " + "'" + title + "'";
//        if(!body.isEmpty()) updateSQL += "," + " body = " + "'" + body + "'";
//        updateSQL += " WHERE id = " + idx + ";";
//        pstmt = con.prepareStatement(updateSQL);
//        String updateSQL = "UPDATE article SET updateDate = ?, title = ?, body = ? WHERE id = ?";
//        pstmt = con.prepareStatement(updateSQL);
//        pstmt.setString(1, Util.getNow());
//        pstmt.setString(2, title);
//        pstmt.setString(3, body);
//        pstmt.setInt(4, idx);
//        return pstmt.executeUpdate(updateSQL);
