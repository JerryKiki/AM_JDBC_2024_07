package org.koreait.controller;

import org.koreait.Container;
import org.koreait.Service.ArticleService;
import org.koreait.util.Util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ArticleController {
    private Connection con;
    private ArticleService articleService;

    public ArticleController(Connection con) {
        this.con = con;
        this.articleService = new ArticleService(con);
    }

    public void doAction() {

    }

    public void doWrite() {
        System.out.println("게시글을 작성합니다.");
        System.out.print("title : ");
        String title = Container.getSc().nextLine();
        if (title.isEmpty()) {
            System.out.println("제목은 필수 입력 사항입니다.");
            return;
        }
        System.out.print("body : ");
        String body = Container.getSc().nextLine();
        if (body.isEmpty()) {
            System.out.println("내용은 필수 입력 사항입니다.");
            return;
        }
        int lastId = articleService.insertArticle(title, body);
        System.out.printf("%d번 게시글이 작성되었습니다.\n", lastId);
    }

    public void doList() throws SQLException {
        System.out.println("  번호  /      일시      /      제목      /     내용     ");
        List<Map<String, Object>> rs = articleService.viewArticleList();
        for (Map<String, Object> map : rs) {
            String displayId = map.get("id").toString();
            String displayRegDate = dateTimeForDisplay(map.get("regDate").toString());
            String displayTitle = subForDisplay(map.get("title").toString());
            String displayBody = subForDisplay(map.get("body").toString());

            if (displayId.length() == 1) displayId = "0" + displayId;

            System.out.printf("   %s   /   %s   /     %s     /     %s     \n",
                    displayId, displayRegDate, displayBody, displayTitle);
        }
    }

    public void doDelete(int idx) {
        if (idx == 0) System.out.println("올바른 id를 입력해주세요.");
        else {
            int row = articleService.deleteArticle(idx);
            if (row == 0) System.out.printf("%d번 article은 없습니다.\n", idx);
            else System.out.printf("%d번 article이 삭제되었습니다.\n", idx);
        }
    }

    public void viewDetail(int idx) {
        if (idx == 0) System.out.println("올바른 id를 입력해주세요.");
        else {
            Map<String, Object> rs = articleService.viewOneArticle(idx);
            if (rs.get("id") == null) System.out.printf("%d번 article은 없습니다.\n", idx);
            else {
                System.out.println("번호 : " + rs.get("id"));
                System.out.println("작성 일시 : " + rs.get("regDate"));
                System.out.println("최종 수정 날짜 : " + rs.get("updateDate"));
                System.out.println("제목 : " + rs.get("title"));
                System.out.println("내용 : " + rs.get("body"));
            }
        }
    }

    public void doUpdate(int idx) {
        if (idx == 0) System.out.println("올바른 id를 입력해주세요.");
        else {
            Map<String, Object> rs = articleService.viewOneArticle(idx);
            if (rs.get("id") != null) {
                String oldTitle = (String) rs.get("title");
                String oldBody = (String) rs.get("body");
                System.out.println("기존 title : " + oldTitle);
                System.out.println("기존 body : " + oldBody);
                System.out.print("새로운 title : ");
                String newTitle = Container.getSc().nextLine();
                System.out.print("새로운 body : ");
                String newBody = Container.getSc().nextLine();
                try {
                    int row = articleService.updateArticle(newTitle, newBody, idx);
                    if (row > 0) System.out.printf("%d번 article이 수정되었습니다.\n", idx);
                } catch (SQLException e) {
                    System.out.println("doUpdate SQL 에러 : " + e);
                }
            } else System.out.printf("%d번 article은 없습니다.\n", idx);
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
