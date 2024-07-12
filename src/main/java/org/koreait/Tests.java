package org.koreait;

import org.koreait.controller.DBController;
import org.koreait.controller.MemberController;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Tests {
    public static void main(String[] args) {
        DBController db = new DBController();
        MemberController mc = new MemberController(new Scanner(System.in), db);
        Map<String, Object> test = new HashMap<String, Object>();

        mc.loginMember();
    }
}
