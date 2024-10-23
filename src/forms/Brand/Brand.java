package forms.Brand;

import forms.MainForm;
import forms.POS.POS;
import forms.Product.Product;
import net.proteanit.sql.DbUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Brand extends JFrame{
    private JPanel BrandPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel  PanelTable;
    private JTextField txtbrand;
    private JButton Add;
    private JButton Delete;
    private JButton Update;
    private JTable tblStore;
    private JLabel Category;
    private JLabel Brand;
    private JLabel Product;
    private JLabel POS;
    private JLabel Exit;
    private JLabel Cashier;
    private JButton deleteButton;
    private JButton payInvoiceButton;
    private JButton addButton;
    private JTextField txtBarcode;
    private JTextField txtpd;
    private JTable table1;
    private JLabel txtstatus;
    private JTextField txtcost;
    private JTextField txtqty;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JComboBox txtstus;
    private JButton Search;
    private JTextField txtid;

    Connection con1;
    PreparedStatement pst;


    public Brand(){
        setContentPane(BrandPanel);
        setTitle("brand Form");
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        connect();
        table_load();










        Add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String brand = txtbrand.getText();
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
                    pst = con1.prepareStatement("INSERT INTO brand (brand, status) VALUES (?, ?)");
                    pst.setString(1, brand);
                    pst.setString(2, status);

                    System.out.println("Executing SQL statement...");
                    pst.executeUpdate();

                    // Success message
                    JOptionPane.showMessageDialog(null, "brand addeddd");
                    table_load();
                    // Reset input fields
                    txtbrand.setText("");
                    txtstus.setSelectedIndex(-1);
                    txtbrand.requestFocus();
                } catch (ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "JDBC Driver not found: " + ex.getMessage());
                    Logger.getLogger(Brand.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "SQL Error: " + ex.getMessage());
                    Logger.getLogger(Brand.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });


        Update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model1 = (DefaultTableModel) tblStore.getModel();
                int selectIndex = tblStore.getSelectedRow();



                String brand = txtbrand.getText();
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
                        pst = con1.prepareStatement("update brand set brand = ?, status = ? where id = ?");
                        pst.setString(1, brand);
                        pst.setString(2, status);
                        pst.setInt(3, id);

                        System.out.println("Executing SQL statement...");
                        pst.executeUpdate();

                        // Success message
                        JOptionPane.showMessageDialog(null, "brand updateeee");
                        table_load();
                        // Reset input fields
                        txtbrand.setText("");
                        txtstus.setSelectedIndex(-1);
                        txtbrand.requestFocus();

                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(Brand.class.getName()).log(Level.SEVERE, null, ex);
                    }catch (SQLException ex) {
                        Logger.getLogger(Brand.class.getName()).log(Level.SEVERE, null, ex);
                    }

            }
        });


        tblStore.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DefaultTableModel model1 = (DefaultTableModel) tblStore.getModel();
                int selectIndex = tblStore.getSelectedRow();

                // Set the category field
                txtbrand.setText(model1.getValueAt(selectIndex, 1).toString());
                txtstus.setSelectedItem(model1.getValueAt(selectIndex, 2).toString());

            }
        });


        Search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    String id = txtid.getText();

                    pst = con1.prepareStatement("select brand ,status from brand where id = ?");
                    pst.setString(1,id);
                    ResultSet rs = pst.executeQuery();


                    if(rs.next()==true){

                       String brand = rs.getString(1);
                        String status = rs.getString(2);

                        txtbrand.setText(brand);
                        txtstus.setSelectedIndex(-1);
                    }else{
                        txtbrand.setText("");
                        txtstus.setSelectedIndex(-1);
                        JOptionPane.showMessageDialog(null, "brand  not found");
                    }

                }catch (SQLException ex){

                }
            }
        });
        Delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model1 = (DefaultTableModel) tblStore.getModel();
                int selectIndex = tblStore.getSelectedRow();



                String brand = txtbrand.getText();
                String status = txtstus.getSelectedItem().toString();
                int id = Integer.parseInt(model1.getValueAt(selectIndex, 0).toString());

                int dialogResult = JOptionPane.showConfirmDialog(null,"Do you want to delete the record ","Warnning",JOptionPane.YES_NO_OPTION);

                if(dialogResult==JOptionPane.YES_OPTION){

                    try{
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        con1 = DriverManager.getConnection("jdbc:mysql://localhost/POS", "root", "Liisol1122@#");
                        pst = con1.prepareStatement("delete from brand where id = ?");
                        pst.setInt(1,id);



                        pst.executeUpdate();
                        JOptionPane.showMessageDialog(null, "brand deleted");


                        table_load();
                        // Reset input fields
                        txtbrand.setText("");
                        txtstus.setSelectedIndex(-1);
                        txtbrand.requestFocus();

                    }catch (ClassNotFoundException ex){
                        Logger.getLogger(Brand.class.getName()).log(Level.SEVERE, null, ex);
                    }catch (SQLException ex){
                        Logger.getLogger(Brand.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }






            }
        });
        Brand.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Brand b = new Brand();
                b.setVisible(true);
                Brand.this.setVisible(false);
            }
        });
        Category.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MainForm m =new MainForm();
                m.setVisible(true);
                Brand.this.setVisible(false);
            }
        });
        Product.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Product p =new Product();
                p.setVisible(true);
                Brand.this.setVisible(false);
            }
        });
        POS.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                POS k =new POS();
                k.setVisible(true);
                Brand.this.setVisible(false);
            }
        });


    }





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
            pst = con1.prepareStatement("select * from brand");
            ResultSet rs =pst.executeQuery();

            tblStore.setModel(DbUtils.resultSetToTableModel(rs));
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
