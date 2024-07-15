package org.koreait.DAO;

import org.koreait.util.DBUtil;
import org.koreait.util.SecSql;

import java.sql.Connection;
import java.util.Map;

public class MemberDao {
    private Connection con;

    public MemberDao(Connection con) {
        this.con = con;
    }

    public boolean checkMemberId(String tryingJoin) {
        SecSql sql = new SecSql();
        sql.append("SELECT COUNT(*) > 0");
        sql.append("FROM member");
        sql.append("WHERE `loginId` = ?;", tryingJoin);
//        Map<String, Object> check = DBUtil.selectRow(con, sql);
//        if (check.get("loginId") != null) return true; //있으면 트루
//        return false; //없으면 false
        return DBUtil.selectRowBooleanValue(con, sql);
    }

    public int insertMember(String loginId, String loginPw, String nickName) {
        SecSql sql = new SecSql();
        sql.append("INSERT INTO member");
        sql.append("SET regDate = NOW(),");
        sql.append("loginId = ?,", loginId);
        sql.append("loginPw = ?,", loginPw);
        sql.append("nickName = ?;", nickName);
        return DBUtil.insert(con, sql);
    }

    public Map<String, Object> getMemberInfo(String tryingLogin) {
        SecSql sql = new SecSql();
        sql.append("SELECT * FROM member");
        sql.append("WHERE `loginId` = ?;", tryingLogin);
        return DBUtil.selectRow(con, sql);
    }
}
