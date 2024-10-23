package forms;

import cls.AuthenticationState;
import cls.User;
import db.DBconnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginForm extends JFrame{
    private JPanel MainPanel;
    private JPanel LeftPanel;
    private JPanel RightPanel;
    private JLabel lblwelcome;
    private JLabel lblicon;
    private JTextField txtUserName;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnCancel;
    private JLabel lblUserName;
    private JLabel lblPassword;
    private JLabel lblTittle;
    private JLabel lblMsg;

    public LoginForm(JFrame parent, MainForm mainForm){
        super(String.valueOf(parent));
        setContentPane(MainPanel);
        setTitle("Login");
        setMinimumSize(new Dimension(600, 400));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        //setVisible(true);
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = new User();
                try{
                    String username = txtUserName.getText();
                    String password = String.valueOf(txtPassword.getPassword());
                    Connection con = DBconnection.getConnection();
                    Statement stm = con.createStatement();
                    String query = "SELECT * FROM USER WHERE username=? AND password=?";
                    PreparedStatement preparedStatement = con.prepareStatement(query);
                    preparedStatement.setString(1,username);
                    preparedStatement.setString(2,password);
                    ResultSet rs = preparedStatement.executeQuery();
                    if (rs.next()){
                        User.setUserName(rs.getString("username"));
                        User.setRoleID(rs.getInt("role_id"));
                        AuthenticationState.setAuthenticated(true);
                        MainForm mainForm = new MainForm();
                        mainForm.setVisible(true);
                        dispose();

                    }
                    else{
                        AuthenticationState.setAuthenticated(false);
                        lblMsg.setText("Login failed.");
                        lblMsg.setForeground(Color.RED);



                    }

                }catch (SQLException ex){
                    ex.printStackTrace();
                }

            }
        });

    }

    public LoginForm() {



    }

    private void setModal(boolean b) {
    }
}
