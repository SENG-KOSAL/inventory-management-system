package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {
    private static final String url = "jdbc:mysql://localhost:3306/test1";
    private static final String user = "root";
    private static final String password = "Liisol1122@#";
    public static Connection getConnection(){
        Connection con = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url,user,password);
        } catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
        return con;
    }
}
