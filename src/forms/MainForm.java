package forms;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;

import db.DBconnection;
import forms.Brand.Brand;
import forms.POS.POS;
import forms.Product.Product;
import net.proteanit.sql.DbUtils;


public class MainForm extends JFrame {
    private JPanel MainPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JTextField txtcat;
    private JButton Add;
    private JButton Delete;
    private JButton Update;
    private JTable tblStore;
    private JLabel Brand;
    private JLabel Product;
    private JLabel POS;
    private JLabel Exit;
    private JLabel Cashier;
    private JComboBox txtstus;
    private JLabel txtstatus;
    private JPanel PanelTable;
    private JButton Search;
    private JTextField txtid;
    private JLabel Category;


    Connection con1;
    PreparedStatement pst;


    public MainForm() {
        setContentPane(MainPanel);
        setTitle("Main From");
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        connect();
        table_load();


        Add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String category = txtcat.getText();
                String status = txtstus.getSelectedItem().toString();

                try {
                    // Attempt to load the JDBC driver
                    System.out.println("Loading JDBC driver...");
                    Class.forName("com.mysql.cj.jdbc.Driver");

                    // Attempt to connect to the database
                    System.out.println("Connecting to the database...");
                    con1 = DriverManager.getConnection("jdbc:mysql://localhost/POS", "root", "Liisol1122@#");

                    // Prepare and execute the SQL statement
                    System.out.println("Preparing SQL statement...");
                    pst = con1.prepareStatement("INSERT INTO category (category, status) VALUES (?, ?)");
                    pst.setString(1, category);
                    pst.setString(2, status);

                    System.out.println("Executing SQL statement...");
                    pst.executeUpdate();

                    // Success message
                    JOptionPane.showMessageDialog(null, "Category addeddd");
//                    table_load();
                    // Reset input fields
                    txtcat.setText("");
                    txtstus.setSelectedIndex(-1);
                    txtcat.requestFocus();
                } catch (ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "JDBC Driver not found: " + ex.getMessage());
                    Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "SQL Error: " + ex.getMessage());
                    Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });


        Update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model1 = (DefaultTableModel) tblStore.getModel();
                int selectIndex = tblStore.getSelectedRow();


                String category = txtcat.getText();
                String status = txtstus.getSelectedItem().toString();
                int id = Integer.parseInt(model1.getValueAt(selectIndex, 0).toString());


                try {

                    // Attempt to load the JDBC driver
                    System.out.println("Loading JDBC driver...");
                    Class.forName("com.mysql.cj.jdbc.Driver");

                    // Attempt to connect to the database
                    System.out.println("Connecting to the database...");
                    con1 = DriverManager.getConnection("jdbc:mysql://localhost/POS", "root", "Liisol1122@#");

                    // Prepare and execute the SQL statement
                    System.out.println("Preparing SQL statement...");
                    pst = con1.prepareStatement("update category set category = ?, status = ? where id = ?");
                    pst.setString(1, category);
                    pst.setString(2, status);
                    pst.setInt(3, id);

                    System.out.println("Executing SQL statement...");
                    pst.executeUpdate();

                    // Success message
                    JOptionPane.showMessageDialog(null, "Category updateeee");
//                        table_load();
                    // Reset input fields
                    txtcat.setText("");
                    txtstus.setSelectedIndex(-1);
                    txtcat.requestFocus();

                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });


        tblStore.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DefaultTableModel model1 = (DefaultTableModel) tblStore.getModel();
                int selectIndex = tblStore.getSelectedRow();

                // Set the category field
                txtcat.setText(model1.getValueAt(selectIndex, 1).toString());
                txtstus.setSelectedItem(model1.getValueAt(selectIndex, 2).toString());

            }
        });


        Search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    String id = txtid.getText();

                    pst = con1.prepareStatement("select category ,status from category where id = ?");
                    pst.setString(1, id);
                    ResultSet rs = pst.executeQuery();


                    if (rs.next() == true) {

                        String category = rs.getString(1);
                        String status = rs.getString(2);

                        txtcat.setText(category);
                        txtstus.setSelectedIndex(-1);
                    } else {
                        txtcat.setText("");
                        txtstus.setSelectedIndex(-1);
                        JOptionPane.showMessageDialog(null, "Category not found");
                    }

                } catch (SQLException ex) {

                }
            }
        });
        Delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model1 = (DefaultTableModel) tblStore.getModel();
                int selectIndex = tblStore.getSelectedRow();


                String category = txtcat.getText();
                String status = txtstus.getSelectedItem().toString();
                int id = Integer.parseInt(model1.getValueAt(selectIndex, 0).toString());

                int dialogResult = JOptionPane.showConfirmDialog(null, "Do you want to delete the record ", "Warnning", JOptionPane.YES_NO_OPTION);

                if (dialogResult == JOptionPane.YES_OPTION) {

                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        con1 = DriverManager.getConnection("jdbc:mysql://localhost/POS", "root", "Liisol1122@#");
                        pst = con1.prepareStatement("delete from category where id = ?");
                        pst.setInt(1, id);


                        pst.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Category deleted");


//                        table_load();
                        // Reset input fields
                        txtcat.setText("");
                        txtstus.setSelectedIndex(-1);
                        txtcat.requestFocus();

                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }


            }
        });


        Brand.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Brand b =new Brand();
                b.setVisible(true);
                MainForm.this.setVisible(false);
            }
        });

        Category.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MainForm m =new MainForm();
                m.setVisible(true);
                MainForm.this.setVisible(false);
            }
        });
        Product.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Product p = new Product();
                p.setVisible(true);
                MainForm.this.setVisible(false);
            }
        });
        POS.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                POS p =new POS();
                p.setVisible(true);
                MainForm.this.setVisible(false);
            }
        });

    }


//    void table_load(){
//        try{
//            int c;
//            try{
//                Class.forName("com.mysql.cj.jdbc.Driver");
//                con1 = DriverManager.getConnection("jdbc:mysql://localhost/category", "root", "Liisol1122@#");
//                pst = con1.prepareStatement("select *from category");
//                ResultSet rs = pst.executeQuery();
//
//                ResultSetMetaData rsd = rs.getMetaData();
//                c =rsd.getColumnCount();
//
//                DefaultTableModel model = (DefaultTableModel) tblStore.getModel();
//                model.setRowCount(0);
//
//
//                while(rs.next()){
//                    Vector v2 = new Vector();
//
//                    for(int i=0;i<=c;i++){
//                        v2.add(rs.getString("id"));
//                        v2.add(rs.getString("category"));
//                        v2.add(rs.getString("status"));
//
//                    }
//                    model.addRow(v2);
//                }
//            }catch (ClassNotFoundException ex){
//                Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }catch (SQLException ex){
//            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    public void connect(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con1 = DriverManager.getConnection("jdbc:mysql://localhost/POS", "root", "Liisol1122@#");
            System.out.println("Successsss Conecting to the database...");
        }catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    void table_load(){
        try{
            pst = con1.prepareStatement("select * from category");
            ResultSet rs =pst.executeQuery();

            tblStore.setModel(DbUtils.resultSetToTableModel(rs));
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}

