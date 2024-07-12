package org.koreait;

import org.koreait.member.MemberController;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Tests {
    public static void main(String[] args) {
        Database db = new Database();
        MemberController mc = new MemberController(new Scanner(System.in), db);
        Map<String, Object> test = new HashMap<String, Object>();

        mc.loginMember();
    }
}
