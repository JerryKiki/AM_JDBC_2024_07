package org.koreait.DAO;

import org.koreait.util.Container;
import org.koreait.util.DBUtil;
import org.koreait.util.SecSql;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class ArticleDao {

    Connection con;

    public ArticleDao() {
        this.con = Container.con;
    }

    public int insertArticle(String title, String body, int nowMemberId) {
        SecSql sql = new SecSql();
        sql.append("INSERT INTO article");
        sql.append("SET regDate = NOW(),");
        sql.append("updateDate = NOW(),");
        sql.append("title = ?,", title);
        sql.append("`body` = ?,", body);
        sql.append("author = ?;", nowMemberId);
        return DBUtil.insert(con, sql);
    }

    public List<Map<String, Object>> viewArticleList(Map<String, Object> args) {
        SecSql sql = new SecSql();
        String searchKeyword = args.get("searchKeyword").toString();
        int limitFrom = -1;
        int limitTake = -1;

        if (args.containsKey("limitFrom")) {
            limitFrom = Integer.parseInt(args.get("limitFrom").toString());
        }
        if (args.containsKey("limitTake")) {
            limitTake = Integer.parseInt(args.get("limitTake").toString());
        }

        sql.append("SELECT a.id, a.regDate, a.title, a.`body`, m.nickName FROM article a");
        sql.append("INNER JOIN `member` m");
        sql.append("ON a.author = m.id");
        if (!searchKeyword.isEmpty()) {
            searchKeyword = "%" + searchKeyword + "%";
            sql.append("WHERE a.title LIKE ?", searchKeyword);
        }
        sql.append("ORDER BY a.id DESC");
        if (limitFrom != -1) {
            sql.append("LIMIT ?, ?", limitFrom, limitTake);
        }

        return DBUtil.selectRows(con, sql);
    }

    public int deleteArticle(int deleteId) {
        SecSql sql = new SecSql();
        sql.append("DELETE FROM article WHERE `id` = ?;", deleteId);
        return DBUtil.delete(con, sql);
    }

    public Map<String, Object> viewOneArticle(int Idx) {
        SecSql sql = new SecSql();
        sql.append("SELECT a.id, a.regDate, a.updateDate, a.author, a.title, a.`body`, m.nickName FROM article a");
        sql.append("INNER JOIN `member` m");
        sql.append("ON a.author = m.id");
        sql.append("WHERE a.id = ?;", Idx);
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
