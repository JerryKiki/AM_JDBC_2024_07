package org.koreait.member;

import org.koreait.Database;

import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;

public class MemberController {
    private Scanner sc;
    private Database db;

    public MemberController(Scanner sc, Database db) {
        this.sc = sc;
        this.db = db;
    }

    public void joinMember() {
        System.out.println("== 회원가입 ==");
        while (true) {
            System.out.print("아이디 : ");
            String loginId = checkInputEmpty(sc.nextLine());
            try {
                if (db.checkMemberId(loginId)) {
                    System.out.println("이미 사용중인 아이디입니다.");
                    continue;
                }
            } catch (SQLException e) {
                System.out.println("id체크 에러 : " + e);
            }
            if (loginId == null) continue;
            System.out.print("비밀번호 : ");
            String loginPw = checkInputEmpty(sc.nextLine());
            if (loginPw == null) continue;
            System.out.print("비밀번호 확인 : ");
            String PwCheck = checkInputEmpty(sc.nextLine());
            if (PwCheck == null) continue;
            else if (!PwCheck.equals(loginPw)) {
                System.out.print("비밀번호가 다릅니다.");
                continue;
            }
            System.out.print("닉네임 : ");
            String nickName = checkInputEmpty(sc.nextLine());
            if (nickName != null) {
                try {
                    int lastId = db.insertMember(loginId, loginPw, nickName);
                    System.out.printf("%d번 가입자 %s님 환영합니다.\n", lastId, nickName);
                } catch (SQLException e) {
                    System.out.println("joinMember 에러 : " + e);
                }
                break;
            }
        }
    }

    public String checkInputEmpty(String s) {
        if (s.isEmpty()) {
            System.out.println("필수 입력 사항입니다.");
            return null;
        }
        else return s;
    }
}
