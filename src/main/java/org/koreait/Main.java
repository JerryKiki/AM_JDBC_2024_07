package org.koreait;
import java.sql.SQLException;

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

