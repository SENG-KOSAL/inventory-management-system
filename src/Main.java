import cls.AuthenticationState;
import db.DBconnection;

import forms.LoginForm;
import forms.MainForm;
//import forms.MainForm;

import java.sql.Connection;

public class Main{
    public static void main(String[] args){
        System.out.println("Hello World");
        Connection con = DBconnection.getConnection();
        if (con != null){
            System.out.println("Connection is established successfully");
        }
        else{
            System.out.println("cannot connect to database");
        }

        MainForm mainForm = new MainForm();

        LoginForm loginForm = new LoginForm(mainForm,mainForm);
        loginForm.setVisible(true);

        if(AuthenticationState.isAuthenticated()){
            mainForm.setVisible(true);
        }

    }
}