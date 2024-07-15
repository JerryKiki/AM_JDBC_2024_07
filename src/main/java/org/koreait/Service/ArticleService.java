package org.koreait.Service;

import org.koreait.DAO.ArticleDao;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class ArticleService {

    ArticleDao articleDao;

    public ArticleService(Connection con) {
        this.articleDao = new ArticleDao(con);
    }

    public int insertArticle(String title, String body) {
        return articleDao.insertArticle(title, body);
    }

    public List<Map<String, Object>> viewArticleList() {
        return articleDao.viewArticleList();
    }

    public int deleteArticle(int deleteId) {
        return articleDao.deleteArticle(deleteId);
    }

    public Map<String, Object> viewOneArticle(int Idx) {
        return articleDao.viewOneArticle(Idx);
    }

    public int updateArticle(String title, String body, int idx) {
        return articleDao.updateArticle(title, body, idx);
    }
}
