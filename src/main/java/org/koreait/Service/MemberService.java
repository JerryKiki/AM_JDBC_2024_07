package org.koreait.Service;

import org.koreait.DAO.MemberDao;
import org.koreait.util.Container;

import java.sql.Connection;
import java.util.Map;

public class MemberService {

    private MemberDao memberDao;

    public MemberService() {
        this.memberDao = Container.memberDao;
    }

    public boolean checkMemberId(String tryingJoin) {
        return memberDao.checkMemberId(tryingJoin);
    }

    public int insertMember(String loginId, String loginPw, String nickName) {
        return memberDao.insertMember(loginId, loginPw, nickName);
    }

    public Map<String, Object> getMemberInfo(String tryingLogin) {
        return memberDao.getMemberInfo(tryingLogin);
    }
}
