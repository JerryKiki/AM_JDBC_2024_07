package org.koreait.util;

import java.util.Map;

public class Session {
    public Map<String, Object> loginMember;
    public int loginMemberId;

    public Session() {
        loginMember = null;
        loginMemberId = -1;
    }

    public void login(Map<String, Object> loginMember, int loginMemberId) {
        this.loginMember = loginMember;
        this.loginMemberId = loginMemberId;
    }

    public void logout() {
        loginMember = null;
        loginMemberId = -1;
    }

}
