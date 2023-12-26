package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.lang.*;

public class DatabaseConnection {
    static Connection connection=null;
    public static Connection getConnection(){
        if(connection!=null){
            return connection;
        }
        String user="root";
        String pwd="Dhruva@123";
        String db="searchengineapp";
        return getConnection(user,pwd,db);
    }
    private static Connection getConnection(String user,String pwd,String db){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + db + "?user=root&password=" + pwd);
        }
        catch(SQLException | ClassNotFoundException sqlexception){
            sqlexception.printStackTrace();
        }
        return connection;
    }
}
