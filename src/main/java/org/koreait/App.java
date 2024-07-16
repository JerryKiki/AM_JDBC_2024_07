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
            String controllerName = "";
            String actionMethod = "";
            int idx = 0;
            if (cmdBits.length == 1) {
                actionMethod = cmdBits[0];
            }
            else if (cmdBits.length == 2) {
                controllerName = cmdBits[0];
                actionMethod = cmdBits[1];
            }
            else if (cmdBits.length == 3) {
                controllerName = cmdBits[0];
                actionMethod = cmdBits[1];
                try {
                    idx = Integer.parseInt(cmdBits[2]);
                } catch (NumberFormatException e) {
                    System.out.println("삭제/수정 id는 정수여야 합니다.");
                }
            }

            if (actionMethod.equals("exit")) {
                System.out.println("== Article Manager Exit ==");
                Container.close();
                system_status = 0;
                break;
            }

            switch (controllerName) {
                case "article" -> Container.articleController.doAction(actionMethod, idx);
                case "member" -> Container.memberController.doAction(actionMethod, idx);
                default -> System.out.println("올바른 명령어를 입력해주세요.");
            }
        }
    }
}
