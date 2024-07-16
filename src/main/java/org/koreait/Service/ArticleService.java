package org.koreait.Service;

import org.koreait.DAO.ArticleDao;
import org.koreait.util.Container;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArticleService {

    ArticleDao articleDao;

    public ArticleService() {
        this.articleDao = Container.articleDao;
    }

    public int insertArticle(String title, String body, int nowMemberId) {
        return articleDao.insertArticle(title, body, nowMemberId);
    }

    public List<Map<String, Object>> viewArticleList(int page, int itemsInPage, String searchKeyword) {

        Map<String, Object> args = new HashMap<>();

        if (page == -1) {
            //페이지를 지정하지 않았다면 서치키워드만 넘김
            args.put("searchKeyword", searchKeyword);
        } else {
            //페이지를 지정했다면 전부 넘김
            int limitFrom = (page - 1) * itemsInPage;
            int limitTake = itemsInPage;
            args.put("limitFrom", limitFrom);
            args.put("limitTake", limitTake);
            args.put("searchKeyword", searchKeyword);
        }

        return articleDao.viewArticleList(args);
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
