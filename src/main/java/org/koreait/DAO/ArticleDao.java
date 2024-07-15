package org.koreait.DAO;

import org.koreait.util.DBUtil;
import org.koreait.util.SecSql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ArticleDao {

    Connection con;

    public ArticleDao(Connection con) {
        this.con = con;
    }

    public int insertArticle(String title, String body) {
        SecSql sql = new SecSql();
        sql.append("INSERT INTO article");
        sql.append("SET regDate = NOW(),");
        sql.append("updateDate = NOW(),");
        sql.append("title = ?,", title);
        sql.append("`body` = ?;", body);
        return DBUtil.insert(con, sql);
    }

    public List<Map<String, Object>> viewArticleList() {
        SecSql sql = new SecSql();
        sql.append("SELECT * FROM article");
        sql.append("ORDER BY `id` DESC");
        return DBUtil.selectRows(con, sql);
    }

    public int deleteArticle(int deleteId) {
        SecSql sql = new SecSql();
        sql.append("DELETE FROM article WHERE `id` = ?;", deleteId);
        return DBUtil.delete(con, sql);
    }

    public Map<String, Object> viewOneArticle(int Idx) {
        SecSql sql = new SecSql();
        sql.append("SELECT * FROM article WHERE `id` = ?;", Idx);
        return DBUtil.selectRow(con, sql);
    }

    public int updateArticle(String title, String body, int idx) {
        SecSql sql = new SecSql();
        sql.append("UPDATE article");
        sql.append("SET updateDate = NOW()");
        if(!title.isEmpty()) sql.append(", title = ?", title);
        if(!body.isEmpty()) sql.append(", `body` = ?", body);
        sql.append("WHERE id = ?;", idx);
        return DBUtil.update(con, sql);
    }
}
