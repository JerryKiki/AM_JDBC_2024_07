package org.koreait.controller;

import org.koreait.util.Container;
import org.koreait.Service.MemberService;
import org.koreait.util.Session;

import java.util.HashMap;
import java.util.Map;

public class MemberController {
    private MemberService memberService;
    private Session session;

    public MemberController() {
        memberService = Container.memberService;
        session = Container.session;
    }

    public void doAction(String actionMethod, int idx) {
        switch (actionMethod) {
            case "join" -> joinMember();
            case "login" -> loginMember();
            case "profile" -> showMyPage();
            case "logout" -> logoutMember();
            default -> System.out.println("올바른 명령어를 입력해주세요.");
        }
    }

    public void joinMember() {
        if (session.loginMember != null) {
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
        if (session.loginMember != null) {
            System.out.println("이미 로그인 중입니다.");
            return;
        }
        System.out.println("== 로그인 ==");
        Map<String, Integer> triedId = new HashMap<>();
        while (true) {
            System.out.print("아이디 : ");
            String inputId = checkInput(Container.getSc().nextLine());
            if (inputId.isEmpty()) {
                System.out.println("id를 다시 입력해주세요.");
                continue;
            }
            System.out.print("비밀번호 : ");
            String inputPw = checkInput(Container.getSc().nextLine());
            if (inputPw.isEmpty()) {
                System.out.println("다시 입력해주세요.");
                continue;
            }

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
                if (triedId.get(inputId) >= 3) {
                    System.out.println("해당 아이디에 대해 비밀번호 3회 오류!");
                    System.out.println("잠시 후 다시 시도해주십시오.");
                    break;
                }
                continue;
            }

            session.login(tryingLogin, Integer.parseInt(tryingLogin.get("id").toString()));

            System.out.println("== 로그인 성공 ==");
            System.out.printf("== %s님 환영합니다 ==\n", session.loginMember.get("nickName"));
            break;
        }
    }

    public void showMyPage() {
        if (session.loginMember != null) {
            System.out.println("== member profile ==");
            System.out.println("고유번호 : " + session.loginMember.get("id"));
            System.out.println("아이디 : " + session.loginMember.get("loginId"));
            System.out.println("가입일시 : " + session.loginMember.get("regDate"));
            System.out.println("닉네임 : " + session.loginMember.get("nickName"));
        } else System.out.println("로그인 후 사용해주세요.");
    }

    public void logoutMember() {
        if (session.loginMember == null) {
            System.out.println("이미 로그아웃 상태입니다.");
        } else {
            System.out.printf("== %s님 로그아웃 ==\n", session.loginMember.get("nickName"));
            session.logout();
        }
    }

    public String checkInput(String s) {
        s = s.trim();
        if (s.isEmpty()) {
            System.out.println("필수 입력 사항입니다.");
            return "";
        } else if (s.contains(" ")) {
            System.out.println("공백은 포함하실 수 없습니다.");
            return "";
        } else return s;
    }
}
