package org.koreait;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
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

    public void doWrite() {
        try {
            System.out.println("게시글을 작성합니다.");
            System.out.print("title : ");
            String title = sc.nextLine();
            System.out.print("body : ");
            String body = sc.nextLine();
            int lastId = db.insertArticle(title, body);
            System.out.printf("%d번 게시글이 작성되었습니다.\n", lastId);
        } catch (SQLException e) {
            System.out.println("doWrite SQL 에러 : " + e);
        }
    }

    public void doList() throws SQLException {
        try {
            System.out.println("  번호  /      일시      /      제목      /     내용     ");
            ResultSet rs = db.viewArticleList();
            while (rs.next()) {
                String displayId = rs.getString("id");
                String displayRegDate = dateTimeForDisplay(rs.getString("regDate"));
                String displayTitle = subForDisplay(rs.getString("title"));
                String displayBody = subForDisplay(rs.getString("body"));

                if (displayId.length() == 1) displayId = "0" + displayId;

                System.out.printf("   %s   /   %s   /     %s     /     %s     \n",
                        displayId, displayRegDate, displayBody, displayTitle);
            }
        } catch (SQLException e) {
            System.out.println("doList SQL 에러 : " + e);
        }
    }

    public void doDelete(int idx) {
        if (idx == 0) System.out.println("올바른 id를 입력해주세요.");
        else {
            try {
                int row = db.deleteArticle(idx);
                if (row == 0) System.out.printf("%d번 article은 없습니다.\n", idx);
                else System.out.printf("%d번 article이 삭제되었습니다.\n", idx);
            } catch (SQLException e) {
                System.out.println("doDelete SQL 에러 : " + e);
            }
        }
    }

    public void viewDetail(int idx) {
        if (idx == 0) System.out.println("올바른 id를 입력해주세요.");
        else {
            try {
                ResultSet rs = db.viewOneArticle(idx);
                if (rs.next()) {
                    System.out.println("번호 : " + rs.getString("id"));
                    System.out.println("작성 일시 : " + rs.getString("regDate").substring(0, 19));
                    System.out.println("최종 수정 날짜 : " + rs.getString("updateDate"));
                    System.out.println("제목 : " + rs.getString("title"));
                    System.out.println("내용 : " + rs.getString("body"));
                } else System.out.printf("%d번 article은 없습니다.\n", idx);
            } catch (SQLException e) {
                System.out.println("viewOneArticle SQL에러 : " + e);
            }
        }
    }

    public void doUpdate(int idx) {
        if (idx == 0) System.out.println("올바른 id를 입력해주세요.");
        else {
            try {
                ResultSet rs = db.viewOneArticle(idx);
                if (rs.next()) {
                    String oldTitle = rs.getString("title");
                    String oldBody = rs.getString("body");
                    System.out.println("기존 title : " + oldTitle);
                    System.out.println("기존 body : " + oldBody);
                    System.out.print("새로운 title : ");
                    String newTitle = sc.nextLine();
                    System.out.print("새로운 body : ");
                    String newBody = sc.nextLine();
                    try {
                        int row = db.updateArticle(newTitle, newBody, idx);
                        if (row > 0) System.out.printf("%d번 article이 수정되었습니다.\n", idx);
                    } catch (SQLException e) {
                        System.out.println("doUpdate SQL 에러 : " + e);
                    }
                } else System.out.printf("%d번 article은 없습니다.\n", idx);
            } catch (SQLException e) {
                System.out.println("viewOneArticle SQL 에러 : " + e);
            }
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
        if (DateAndTime[0].equals(now[0])) dateTime = " " + DateAndTime[1].substring(0, 8) + " ";
        else dateTime = DateAndTime[0];

        return dateTime;
    }
}
