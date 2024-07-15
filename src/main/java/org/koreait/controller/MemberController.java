package org.koreait.controller;

import org.koreait.Container;
import org.koreait.Service.MemberService;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class MemberController {
    private static Map<String, Object> loginedMember;
    private MemberService memberService;
    private Connection con;

    public MemberController(Connection con) {
        this.con = con;
        memberService = new MemberService(con);
        this.loginedMember = null;
    }

    public void doAction(String actionMethod, int idx) {
        switch (actionMethod) {
            case "join" -> joinMember();
            case "login" -> loginMember();
            case "page" -> showMyPage();
            case "logout" -> logoutMember();
            default -> System.out.println("올바른 명령어를 입력해주세요.");
        }
    }

    public void joinMember() {
        if (loginedMember != null) {
            System.out.println("로그인 중에는 이용하실 수 없습니다.");
            return;
        }
        System.out.println("== 회원가입 ==");
        String loginId;
        String loginPw;
        String pwCheck;
        String nickName;
        while (true) {
            System.out.print("아이디 : ");
            loginId = checkInput(Container.getSc().nextLine());
            if (loginId == null) continue;
            if (memberService.checkMemberId(loginId)) {
                System.out.println("이미 사용중인 아이디입니다.");
                continue;
            }
            break;
        }
        while (true) {
            System.out.print("비밀번호 : ");
            loginPw = checkInput(Container.getSc().nextLine());
            if (loginPw == null) continue;
            System.out.print("비밀번호 확인 : ");
            pwCheck = checkInput(Container.getSc().nextLine());
            if (pwCheck == null) continue;
            else if (!pwCheck.equals(loginPw)) {
                System.out.println("비밀번호가 다릅니다.");
                continue;
            }
            break;
        }
        while (true) {
            System.out.print("닉네임 : ");
            nickName = checkInput(Container.getSc().nextLine());
            if (nickName != null) break;
        }
        int lastId = memberService.insertMember(loginId, loginPw, nickName);
        System.out.printf("%d번 가입자 %s님 환영합니다.\n", lastId, nickName);
    }

    public void loginMember() {
        if (loginedMember != null) {
            System.out.println("이미 로그인 중입니다.");
            return;
        }
        System.out.println("== 로그인 ==");
        Map<String, Integer> triedId = new HashMap<>();
        while (true) {
            System.out.print("아이디 : ");
            String inputId = checkInput(Container.getSc().nextLine());
            System.out.print("비밀번호 : ");
            String inputPw = checkInput(Container.getSc().nextLine());

            Map<String, Object> tryingLogin = memberService.getMemberInfo(inputId);

            if (tryingLogin.isEmpty()) {
                System.out.println("해당 ID는 존재하지 않습니다.");
                continue;
            }
            if (!tryingLogin.get("loginPw").equals(inputPw)) {
                System.out.println("비밀번호가 틀립니다.");
                if (triedId.containsKey(inputId)) {
                    triedId.put(inputId, triedId.get(inputId) + 1);
                } else {
                    triedId.put(inputId, 1);
                }
                if (triedId.get(inputId) == 3) {
                    System.out.println("비밀번호 3회 오류!");
                    System.out.println("잠시 후 다시 시도해주십시오.");
                    break;
                }
                continue;
            }

            this.loginedMember = tryingLogin;

            System.out.println("== 로그인 성공 ==");
            break;
        }
    }

    public void showMyPage() {
        if (loginedMember != null) {
            System.out.println("고유번호 : " + loginedMember.get("id"));
            System.out.println("아이디 : " + loginedMember.get("loginId"));
            System.out.println("가입일시 : " + loginedMember.get("regDate"));
            System.out.println("닉네임 : " + loginedMember.get("nickName"));
        } else System.out.println("로그인 후 사용해주세요.");
    }

    public void logoutMember() {
        if (loginedMember == null) {
            System.out.println("이미 로그아웃 상태입니다.");
        } else {
            System.out.println("== 로그아웃 ==");
            this.loginedMember = null;
        }
    }

    public String checkInput(String s) {
        s = s.trim();
        if (s.isEmpty()) {
            System.out.println("필수 입력 사항입니다.");
            return null;
        } else if (s.contains(" ")) {
            System.out.println("공백은 포함하실 수 없습니다.");
            return null;
        } else return s;
    }

    public static Map<String, Object> getLoginedMember() {
        return loginedMember;
    }
}
