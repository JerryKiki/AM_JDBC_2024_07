package org.koreait;

import org.koreait.article.ArticleContorller;
import org.koreait.member.MemberController;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    static int system_status = 0;

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        Scanner sc = new Scanner(System.in);
        Database db = new Database();
        ArticleContorller articleContorller = new ArticleContorller(sc, db);
        MemberController memberController = new MemberController(sc, db);

        System.out.println("== Article Manager Run ==");
        system_status = 1;

        while (system_status == 1) {
            System.out.print("명령어> ");

            String cmd = sc.nextLine();
            String[] cmdBits = cmd.split(" ");
            String managerName = "";
            String actionMethod = "";
            int idx = 0;
            if (cmdBits.length == 1) actionMethod = cmdBits[0];
            else if (cmdBits.length == 2) actionMethod = cmdBits[1];
            else if (cmdBits.length == 3) {
                actionMethod = cmdBits[1];
                try {
                    idx = Integer.parseInt(cmdBits[2]);
                } catch (NumberFormatException e) {
                    System.out.println("삭제/수정 id는 정수여야 합니다.");
                }
            }

            switch (actionMethod) {
                case "exit" -> {
                    System.out.println("== Article Manager Exit ==");
                    db.closeSource();
                    system_status = 0;
                }
                case "write" -> articleContorller.doWrite();
                case "list" -> articleContorller.doList();
                case "delete" -> articleContorller.doDelete(idx);
                case "update" -> articleContorller.doUpdate(idx);
                case "detail" -> articleContorller.viewDetail(idx);
                case "join" -> memberController.joinMember();
                case "login" -> memberController.loginMember();
                case "page" -> memberController.showMyPage();
                case "logout" -> memberController.logoutMember();
                default -> System.out.println("올바른 명령어를 입력해주세요.");
            }
        }
    }
}

