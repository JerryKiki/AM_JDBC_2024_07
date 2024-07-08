package org.koreait;

import java.util.Scanner;

public class Main {

    static int system_status = 0;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ArticleContorller articleContorller = new ArticleContorller(sc);

        System.out.println("== Article Manager Run ==");
        system_status = 1;

        while (system_status == 1) {
            System.out.print("명령어> ");

            String cmd = sc.nextLine();
            String[] cmdBits = cmd.split(" ");
            String managerName = "";
            String actionMethod = "";
            String idx = "";
            if (cmdBits.length == 1) actionMethod = cmdBits[0];
            else if (cmdBits.length == 2) actionMethod = cmdBits[1];
            else if (cmdBits.length == 3) {
                actionMethod = cmdBits[1];
                idx = cmdBits[2];
            }

            if (actionMethod.equals("write")) {
                articleContorller.doWrite();
            } else if (actionMethod.equals("list")) {
                articleContorller.doList();
            } else if (actionMethod.equals("exit")) {
                System.out.println("== Article Manager Exit ==");
                system_status = 0;
            } else {
                System.out.println("올바른 명령어를 입력해주세요.");
            }
        }
    }
}

