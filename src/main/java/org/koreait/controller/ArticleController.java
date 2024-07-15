package org.koreait.controller;

import org.koreait.Container;
import org.koreait.Service.ArticleService;
import org.koreait.util.Util;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class ArticleController {
    private Connection con;
    private ArticleService articleService;

    public ArticleController(Connection con) {
        this.con = con;
        this.articleService = new ArticleService(con);
    }

    public void doAction(String actionMethod, int idx) {
        if (MemberController.getLoginedMember() == null){
            switch (actionMethod) {
                case "list" -> doList();
                case "write", "delete", "update", "detail" -> System.out.println("로그인 후에 이용해주세요.");
                default -> System.out.println("올바른 명령어를 입력해주세요.");
            }
            return;
        }
        switch (actionMethod) {
            case "write" -> doWrite();
            case "list" -> doList();
            case "delete" -> doDelete(idx);
            case "update" -> doUpdate(idx);
            case "detail" -> viewDetail(idx);
            default -> System.out.println("올바른 명령어를 입력해주세요.");
        }
    }

    public void doWrite() {
        Map<String, Object> nowMember = MemberController.getLoginedMember();
        int nowMemberId = Integer.parseInt(nowMember.get("id").toString());
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
        int lastId = articleService.insertArticle(title, body, nowMemberId);
        System.out.printf("%d번 게시글이 작성되었습니다.\n", lastId);
    }

    public void doList() {
        System.out.println("  번호  /      일시      /   작성자   /      제목      /     내용     ");
        List<Map<String, Object>> rs = articleService.viewArticleList();
        for (Map<String, Object> map : rs) {
            String displayId = map.get("id").toString();
            String displayRegDate = dateTimeForDisplay(map.get("regDate").toString());
            String displayNickName = subForDisplay(map.get("nickName").toString());
            String displayTitle = subForDisplay(map.get("title").toString());
            String displayBody = subForDisplay(map.get("body").toString());

            if (displayId.length() == 1) displayId = "0" + displayId;

            System.out.printf("   %s   /   %s   /   %s   /     %s     /     %s     \n",
                    displayId, displayRegDate, displayNickName, displayBody, displayTitle);
        }
    }

    public void doDelete(int idx) {
        Map<String, Object> nowMember = MemberController.getLoginedMember();
        String nowMemberId = nowMember.get("id").toString();

        if (idx == 0) System.out.println("올바른 id를 입력해주세요.");
        else {
            Map<String, Object> checking = articleService.viewOneArticle(idx);
            if (checking.isEmpty()) {
                System.out.printf("%d번 article은 없습니다.\n", idx);
                return;
            }
            if (!checking.get("author").toString().equals(nowMemberId)) {
                System.out.println("자신이 작성한 글만 지울 수 있습니다.");
                return;
            }
            articleService.deleteArticle(idx);
            System.out.printf("%d번 article이 삭제되었습니다.\n", idx);
        }
    }

    public void viewDetail(int idx) {
        if (idx == 0) System.out.println("올바른 id를 입력해주세요.");
        else {
            Map<String, Object> rs = articleService.viewOneArticle(idx);
            if (rs.get("id") == null) System.out.printf("%d번 article은 없습니다.\n", idx);
            else {
                System.out.println("번호 : " + rs.get("id"));
                System.out.println("작성자 : " + rs.get("nickName"));
                System.out.println("작성 일시 : " + rs.get("regDate"));
                System.out.println("최종 수정 날짜 : " + rs.get("updateDate"));
                System.out.println("제목 : " + rs.get("title"));
                System.out.println("내용 : " + rs.get("body"));
            }
        }
    }

    public void doUpdate(int idx) {
        Map<String, Object> nowMember = MemberController.getLoginedMember();
        String nowMemberId = nowMember.get("id").toString();

        if (idx == 0) System.out.println("올바른 id를 입력해주세요.");
        else {
            Map<String, Object> checking = articleService.viewOneArticle(idx);
            if (checking.get("id") != null) {
                if (!checking.get("author").toString().equals(nowMemberId)) {
                    System.out.println("자신이 작성한 글만 수정할 수 있습니다.");
                    return;
                }
                String oldTitle = (String) checking.get("title");
                String oldBody = (String) checking.get("body");
                System.out.println("기존 title : " + oldTitle);
                System.out.println("기존 body : " + oldBody);
                System.out.print("새로운 title : ");
                String newTitle = Container.getSc().nextLine();
                System.out.print("새로운 body : ");
                String newBody = Container.getSc().nextLine();

                int row = articleService.updateArticle(newTitle, newBody, idx);
                if (row > 0) System.out.printf("%d번 article이 수정되었습니다.\n", idx);

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
