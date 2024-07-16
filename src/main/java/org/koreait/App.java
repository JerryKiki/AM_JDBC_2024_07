package org.koreait;

import org.koreait.util.Container;
import java.sql.SQLException;
import java.util.Scanner;

public class App {

    static int system_status = 0;

    public void run() throws SQLException {

        Container.init();
        Scanner sc = Container.getSc();

        System.out.println("== Article Manager Run ==");
        system_status = 1;

        while (system_status == 1) {
            System.out.print("명령어> ");

            String cmd = sc.nextLine();
            String[] cmdBits = cmd.split(" ");
            int page = -1;
            String controllerName = "";
            String actionMethod = "";
            int idx = 0;
            String searchKeyword = "";
            if (cmdBits.length == 1) {
                actionMethod = cmdBits[0];
            }
            else if (cmdBits.length == 2) {
                controllerName = cmdBits[0];
                actionMethod = cmdBits[1];
            }
            else if (cmdBits.length >= 3) {
                controllerName = cmdBits[0];
                actionMethod = cmdBits[1];
                if (actionMethod.equals("list")) {
                    if (cmdBits.length == 4) {
                        try {
                            page = Integer.parseInt(cmdBits[2]);
                        } catch (NumberFormatException e) {
                            System.out.println("article page는 정수여야 합니다.");
                        }
                        searchKeyword = cmdBits[3];
                    }
                    else searchKeyword = cmdBits[2];
                } else {
                    try {
                        idx = Integer.parseInt(cmdBits[2]);
                    } catch (NumberFormatException e) {
                        System.out.println("삭제/수정 id는 정수여야 합니다.");
                    }
                }
            }

            if (actionMethod.equals("exit")) {
                System.out.println("== Article Manager Exit ==");
                Container.close();
                system_status = 0;
                break;
            }

            switch (controllerName) {
                case "article" -> Container.articleController.doAction(actionMethod, idx, page, searchKeyword);
                case "member" -> Container.memberController.doAction(actionMethod, idx);
                default -> System.out.println("올바른 명령어를 입력해주세요.");
            }
        }
    }
}
