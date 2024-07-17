package org.koreait.util;

public class Rq {
    String controllerName;
    String actionMethod;
    int idx;
    String searchKeyword;
    int page;

    public Rq(String cmd) {
        String[] cmdBits = cmd.split(" ");
        page = -1;
        controllerName = "";
        actionMethod = "";
        idx = 0;
        searchKeyword = "";
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
    }

    public String getControllerName() {
        return controllerName;
    }

    public String getActionMethod() {
        return actionMethod;
    }

    public int getIdx() {
        return idx;
    }

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public int getPage() {
        return page;
    }
}
