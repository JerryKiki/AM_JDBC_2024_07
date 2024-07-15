package org.koreait.Service;

import org.koreait.DAO.MemberDao;
import org.koreait.util.DBUtil;
import org.koreait.util.SecSql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public class MemberService {

    private MemberDao memberDao;
    private Connection con;

    public MemberService(Connection con) {
        this.memberDao = new MemberDao(con);
        this.con = con;
    }

    public boolean checkMemberId(String tryingJoin) throws SQLException {
        return memberDao.checkMemberId(tryingJoin);
    }

    public int insertMember(String loginId, String loginPw, String nickName) throws SQLException {
        return memberDao.insertMember(loginId, loginPw, nickName);
    }

    public Map<String, Object> getMemberInfo(String tryingLogin) throws SQLException {
        return memberDao.getMemberInfo(tryingLogin);
    }
}
