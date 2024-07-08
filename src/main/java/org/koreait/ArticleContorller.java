package org.koreait;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class ArticleContorller {
    private Map<Integer, Article> articles;
    private Scanner sc;
    private Database db;

    ArticleContorller(Scanner sc, Database db) {
        this.articles = new HashMap<>();
        this.sc = sc;
        this.db = db;
    }

    public Map<Integer, Article> getArticles() {
        return articles;
    }

    public void setArticles(Map<Integer, Article> articles) {
        this.articles = articles;
    }

    public void doWrite() throws SQLException {
        System.out.println("게시글을 작성합니다.");
        System.out.print("title : ");
        String title = sc.nextLine();
        System.out.print("body : ");
        String body = sc.nextLine();
        int lastId = db.insertArticle(title, body);
        System.out.printf("%d번 게시글이 작성되었습니다.\n", lastId);

    }

    public void doList() throws SQLException {
        System.out.println(" 번호  /      시간      /      제목      /     내용     ");
        ResultSet rs = db.viewArticleList();
        while (rs.next()) {
            int displayId = rs.getInt("id");
            String displayRegDate = dateTimeForDisplay(rs.getString("regDate"));
            String displayTitle = subForDisplay(rs.getString("title"));
            String displayBody = subForDisplay(rs.getString("body"));

            System.out.printf("   %d   /   %s   /     %s     /     %s     \n",
                    displayId, displayRegDate, displayBody, displayTitle);
        }
    }

    public void doDelete(int idx) {
        try {
            db.deleteArticle(idx);
            System.out.printf("%d번 article이 삭제되었습니다.\n", idx);
        } catch (SQLException e) {
            System.out.println("올바른 id number가 아닙니다.");
        }
    }

    public String subForDisplay(String willSub) {
        if (willSub.length() > 3) willSub = willSub.substring(0, 3) + "...";
        else if (willSub.length() == 3) willSub = willSub + "   ";
        else if (willSub.length() == 2) willSub = willSub + "    ";
        else if (willSub.length() == 1) willSub = willSub + "     ";

        return willSub;
    }

    public String dateTimeForDisplay(String dateTime) {
        String[] DateAndTime = dateTime.split(" ");
        String[] now = Util.getNow().split(" ");
        if (DateAndTime[0].equals(now[0])) dateTime = DateAndTime[1];
        else dateTime = DateAndTime[0];

        return dateTime;
    }
}
