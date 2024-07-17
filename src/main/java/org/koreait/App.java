package org.koreait;

import org.koreait.util.Container;
import org.koreait.util.Rq;

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

            Rq rq = new Rq(cmd);

            int page = rq.getPage();
            String controllerName = rq.getControllerName();
            String actionMethod = rq.getActionMethod();
            int idx = rq.getIdx();
            String searchKeyword = rq.getSearchKeyword();

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
