package org.koreait;

import org.koreait.controller.ArticleContorller;
import org.koreait.controller.DBController;
import org.koreait.controller.MemberController;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Container.init();

        try{
            new App().run();
        } catch(SQLException e){
            System.out.println("run 에러 : " + e);
        }

        Container.close();

    }
}

