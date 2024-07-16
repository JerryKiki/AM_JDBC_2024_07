package org.koreait;
import org.koreait.util.Container;

import java.sql.SQLException;
import org.koreait.exception.SQLErrorException;

public class Main {
    public static void main(String[] args) {
        try{
            new App().run();
        } catch(SQLErrorException e){
            System.err.println("e.getOrigin : " + e.getOrigin());
            e.getOrigin().printStackTrace();
        } catch(SQLException e){
            System.err.println("SQLException : " + e.getCause());
        }
    }
}

