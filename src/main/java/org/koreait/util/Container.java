package org.koreait.util;

import org.koreait.DAO.ArticleDao;
import org.koreait.DAO.MemberDao;
import org.koreait.Service.ArticleService;
import org.koreait.Service.MemberService;
import org.koreait.controller.ArticleController;
import org.koreait.controller.MemberController;

import java.sql.Connection;
import java.util.Scanner;

public class Container {
    private static Scanner sc;
    public static Session session;
    public static ArticleController articleController;
    public static MemberController memberController;
    public static ArticleService articleService;
    public static MemberService memberService;
    public static ArticleDao articleDao;
    public static MemberDao memberDao;
    public static Connector connector;
    public static Connection con;

    public static void init() {
        sc = new Scanner(System.in);

        session = new Session();

        connector = new Connector();
        con = connector.getCon();

        articleDao = new ArticleDao();
        memberDao = new MemberDao();

        articleService = new ArticleService();
        memberService = new MemberService();

        articleController = new ArticleController();
        memberController = new MemberController();
    }

    public static void close() {
        sc.close();
        connector.closeConnection();
    }

    public static Scanner getSc() {
        return sc;
    }
}
