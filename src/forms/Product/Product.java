package forms.Product;

import forms.Brand.Brand;
import forms.MainForm;
import forms.POS.POS;
import net.proteanit.sql.DbUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Product extends JFrame{
    private JPanel ProductPanel;
    private JPanel leftPanel;
    private JTextField txtpd;
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
    private JTextField txtPrice;
    private JTextField txtQty;
    private JPanel crud;
    private JComboBox txtcat;
    private JComboBox txtBrand;
    private JComboBox combostus;
    private JPanel tblProduct;
    private JTextPane txtDsp;
    private JLabel category;
    private JLabel Description;
    private JLabel categoty;
    private JLabel brand;
    private JLabel Retailprice;
    private JLabel qty;
    private JLabel Status;
    private JTextField txtcost;
    private JTextField txtBarcode;
    private JComboBox txtstus;
    private JButton Search;
    private JTextField txtid;

    Connection con1;
    PreparedStatement pst;


    public Product(){
        setContentPane(ProductPanel);
        setTitle("Product Form");
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        connect();
        table_load();
        Brand();
        category();










        Add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String product = txtpd.getText();
                String desc = txtDsp.getText();
                CategoryItem  Catitem = (CategoryItem)txtcat.getSelectedItem();
                BrandItem Braitem = (BrandItem)txtBrand.getSelectedItem();
                String costPrice = txtcost.getText();
                String retailPrice = txtPrice.getText();
                String qty = txtQty.getText();
                String barcode = txtBarcode.getText();
                String status = combostus.getSelectedItem().toString();

                try {
                    // Attempt to load the JDBC driver
                    System.out.println("Loading JDBC driver...");
                    Class.forName("com.mysql.cj.jdbc.Driver");

                    // Attempt to connect to the database
                    System.out.println("Connecting to the database...");
                    con1 = DriverManager.getConnection("jdbc:mysql://localhost/POS", "root", "Liisol1122@#");

                    // Prepare and execute the SQL statement
                    System.out.println("Preparing SQL statement...");
                    pst = con1.prepareStatement("INSERT INTO product (product ,description, category_id , brand_id,cost_price, Retail_price ,Qty , Barcode , Status) VALUES (?, ? ,? , ? ,?, ? ,?, ? ,?)");
                    pst.setString(1, product);
                    pst.setString(2, desc);
                    pst.setString(3, Catitem.name);
                    pst.setString(4, Braitem.name);
                    pst.setString(5, costPrice);
                    pst.setString(6, retailPrice);
                    pst.setString(7, qty);
                    pst.setString(8, barcode);
                    pst.setString(9, status);





                    System.out.println("Executing SQL statement...");
                    pst.executeUpdate();

                    // Success message
                    JOptionPane.showMessageDialog(null, "Product addedddddd");
                    table_load();


                    // Reset input fields
                    txtpd.setText("");
                    txtDsp.setText("");
                    txtcost.setText("");
                    txtPrice.setText("");
                    txtQty.setText("");
                    txtBarcode.setText("");
                    txtcat.setSelectedIndex(-1);
                    txtBrand.setSelectedIndex(-1);


                    combostus.setSelectedIndex(-1);
                    txtpd.requestFocus();







                } catch (ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "JDBC Driver not found: " + ex.getMessage());
                    Logger.getLogger(forms.Product.Product.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "SQL Error: " + ex.getMessage());
                    Logger.getLogger(forms.Product.Product.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        Update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model1 = (DefaultTableModel) tblStore.getModel();
                int selectIndex = tblStore.getSelectedRow();

                if (selectIndex == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a product to update.");
                    return;
                }

                String product = txtpd.getText();
                String desc = txtDsp.getText();

//                Product.BrandItem Braitem = (Product.BrandItem) txtBrand.getSelectedItem();
//                Product.CategoryItem Catitem = (Product.CategoryItem) txtcat.getSelectedItem();

                BrandItem Braitem = (BrandItem) txtBrand.getSelectedItem();
                CategoryItem Catitem = (CategoryItem) txtcat.getSelectedItem();


                String costPrice = txtcost.getText();
                String retailPrice = txtPrice.getText();
                String qty = txtQty.getText();
                String barcode = txtBarcode.getText();
                String status = combostus.getSelectedItem().toString();
                int id = Integer.parseInt(model1.getValueAt(selectIndex, 0).toString()); // Get ID of selected product

                try {
                    // Load JDBC driver
                    Class.forName("com.mysql.cj.jdbc.Driver");

                    // Connect to the database
                    con1 = DriverManager.getConnection("jdbc:mysql://localhost/POS", "root", "Liisol1122@#");

                    // Prepare SQL statement for update
                    pst = con1.prepareStatement("UPDATE product SET product = ?, description = ?, category_id = ?, brand_id = ?, cost_price = ?, Retail_price = ?, Qty = ?, Barcode = ?, Status = ? WHERE id = ?");
                    pst.setString(1, product);
                    pst.setString(2, desc);
                    pst.setString(3, Catitem.name);
                    pst.setString(4, Braitem.name);
                    pst.setString(5, costPrice);
                    pst.setString(6, retailPrice);
                    pst.setString(7, qty);
                    pst.setString(8, barcode);
                    pst.setString(9, status);
                    pst.setInt(10, id); // Set the ID for the WHERE clause

                    System.out.println("Executing SQL statement...");
                    // Execute SQL update
                    pst.executeUpdate();

                    // Success message
                    JOptionPane.showMessageDialog(null, "Product updated successfully");

                    // Reload the table data
                    table_load();

                    // Reset input fields
                    txtpd.setText("");
                    txtDsp.setText("");
                    txtcat.setSelectedIndex(-1);
                    txtBrand.setSelectedIndex(-1);
                    txtcost.setText("");
                    txtPrice.setText("");
                    txtQty.setText("");
                    txtBarcode.setText("");
                    combostus.setSelectedIndex(-1);
                    txtpd.requestFocus();

                } catch (ClassNotFoundException ex) {

                    Logger.getLogger(forms.Product.Product.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {

                    Logger.getLogger(forms.Product.Product.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

//        Update.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                DefaultTableModel model1 = (DefaultTableModel) tblStore.getModel();
//                int selectIndex = tblStore.getSelectedRow();
//
//
//
//                String brand = txtpd.getText();
//                String status = txtstus.getSelectedItem().toString();
//                int id = Integer.parseInt(model1.getValueAt(selectIndex, 0).toString());
//
//                int dialogResult = JOptionPane.showConfirmDialog(null,"Do you want to delete the record ","Warnning",JOptionPane.YES_NO_OPTION);
//
//                if(dialogResult==JOptionPane.YES_OPTION){
//
//                    try{
//                        Class.forName("com.mysql.cj.jdbc.Driver");
//                        con1 = DriverManager.getConnection("jdbc:mysql://localhost/brand", "root", "Liisol1122@#");
//                        pst = con1.prepareStatement("delete from brand where id = ?");
//                        pst.setInt(1,id);
//
//
//
//                        pst.executeUpdate();
//                        JOptionPane.showMessageDialog(null, "brand deleted");
//
//
//                        table_load();
//                        // Reset input fields
//                        txtpd.setText("");
//                        txtstus.setSelectedIndex(-1);
//                        txtpd.requestFocus();
//
//                    }catch (ClassNotFoundException ex){
//                        Logger.getLogger(forms.Product.Product.class.getName()).log(Level.SEVERE, null, ex);
//                    }catch (SQLException ex){
//                        Logger.getLogger(forms.Product.Product.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//
//                }
//            }
//        });
//


//        Update.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                DefaultTableModel model1 = (DefaultTableModel) tblStore.getModel();
//                int selectIndex = tblStore.getSelectedRow();
//
//
//
//                String product = txtpd.getText();
//                String desc = txtDsp.getText();
//                CategoryItem Catitem = (CategoryItem) txtcat.getSelectedItem();
//                BrandItem Braitem = (BrandItem) txtBrand.getSelectedItem();
//                String costPrice = txtcost.getText();
//                String retailPrice = txtPrice.getText();
//                String qty = txtQty.getText();
//                String barcode = txtBarcode.getText();
//                String Status = combostus.getSelectedItem().toString();
//                int id = Integer.parseInt(model1.getValueAt(selectIndex, 0).toString());
//
//
//
//                    try {
//
//                        // Attempt to load the JDBC driver
//                        System.out.println("Loading JDBC driver...");
//                        Class.forName("com.mysql.cj.jdbc.Driver");
//
//                        // Attempt to connect to the database
//                        System.out.println("Connecting to the database...");
//                        con1 = DriverManager.getConnection("jdbc:mysql://localhost/product", "root", "Liisol1122@#");
//
//                        // Prepare and execute the SQL statement
//                        System.out.println("Preparing SQL statement...");
//
//
//
//                        pst = con1.prepareStatement("update product set product = ?,description= ? , category_id=? , brand_id=? ,cost_price=?,Retail_price=?, Qty=? , Barcode=?,Status = ? ");
//                        pst.setString(1, product);
//                        pst.setString(2, desc);
//
//                        //the problem
////                        pst.setInt(3, Catitem.id);
////                        pst.setInt(4, Braitem.id);
//                        //
//
//                        pst.setString(5, costPrice);
//                        pst.setString(6, retailPrice);
//                        pst.setString(7, qty);
//                        pst.setString(8, barcode);
//                        pst.setString(9, Status);
//
//
//                        System.out.println("Executing SQL statement...");
//                        pst.executeUpdate();
//
//                        // Success message
//                        JOptionPane.showMessageDialog(null, "productt updateeee");
//                        table_load();
//                        // Reset input fields
//                        txtpd.setText("");
//                        txtDsp.setText("");
//                        txtcat.setSelectedIndex(-1);
//                        txtBrand.setSelectedIndex(-1);
//                        txtcost.setText("");
//                        txtPrice.setText("");
//                        txtQty.setText("");
//                        txtBarcode.setText("");
//
//
//                        combostus.setSelectedIndex(-1);
//                        txtpd.requestFocus();
//
//                    }catch (IllegalArgumentException ex) {
//                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
//                    }
//                    catch (ClassNotFoundException ex) {
//                        Logger.getLogger(forms.Product.Product.class.getName()).log(Level.SEVERE, null, ex);
//                    }catch (SQLException ex) {
//                        Logger.getLogger(forms.Product.Product.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//
//            }
//        });



        tblStore.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DefaultTableModel model1 = (DefaultTableModel) tblStore.getModel();
                int selectIndex = tblStore.getSelectedRow();

                // Set the category field
                txtpd.setText(model1.getValueAt(selectIndex, 1).toString());
                txtDsp.setText(model1.getValueAt(selectIndex, 2).toString());


                txtcat.setSelectedItem(model1.getValueAt(selectIndex, 3).toString());
                txtBrand.setSelectedItem(model1.getValueAt(selectIndex, 4).toString());


                txtcost.setText(model1.getValueAt(selectIndex, 5).toString());
                txtPrice.setText(model1.getValueAt(selectIndex, 6).toString());
                txtQty.setText(model1.getValueAt(selectIndex, 7).toString());
                txtBarcode.setText(model1.getValueAt(selectIndex, 8).toString());
                combostus.setSelectedItem(model1.getValueAt(selectIndex, 9).toString());

            }
        });


//        Search.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                try {
//                    String id = txtid.getText();
//
//                    pst = con1.prepareStatement("select brand ,status from brand where id = ?");
//                    pst.setString(1,id);
//                    ResultSet rs = pst.executeQuery();
//
//
//                    if(rs.next()==true){
//
//                       String brand = rs.getString(1);
//                        String status = rs.getString(2);
//
//                        txtcat.setText(brand);
//                        txtstus.setSelectedIndex(-1);
//                    }else{
//                        txtcat.setText("");
//                        txtstus.setSelectedIndex(-1);
//                        JOptionPane.showMessageDialog(null, "brand  not found");
//                    }
//
//                }catch (SQLException ex){
//
//                }
//            }
//        });
        Delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model1 = (DefaultTableModel) tblStore.getModel();
                int selectIndex = tblStore.getSelectedRow();




                int id = Integer.parseInt(model1.getValueAt(selectIndex, 0).toString());

                int dialogResult = JOptionPane.showConfirmDialog(null,"Do you want to delete the record ","Warnning",JOptionPane.YES_NO_OPTION);

                if(dialogResult==JOptionPane.YES_OPTION){

                    try{
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        con1 = DriverManager.getConnection("jdbc:mysql://localhost/POS", "root", "Liisol1122@#");
                        pst = con1.prepareStatement("delete from product where id = ?");
                        pst.setInt(1,id);



                        pst.executeUpdate();
                        JOptionPane.showMessageDialog(null, "product deleted successfully");


                        table_load();
                        // Reset input fields
//                        txtpd.setText("");
//                        txtstus.setSelectedIndex(-1);
//                        txtpd.requestFocus();
                        txtpd.setText("");
                        txtDsp.setText("");
                        txtcat.setSelectedIndex(-1);
                        txtBrand.setSelectedIndex(-1);
                        txtcost.setText("");
                        txtPrice.setText("");
                        txtQty.setText("");
                        txtBarcode.setText("");
                        combostus.setSelectedIndex(-1);
                        txtpd.requestFocus();

                    }catch (ClassNotFoundException ex){
                        Logger.getLogger(forms.Product.Product.class.getName()).log(Level.SEVERE, null, ex);
                    }catch (SQLException ex){
                        Logger.getLogger(forms.Product.Product.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }






            }
        });
        Brand.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Brand b = new Brand();
                b.setVisible(true);
                Product.this.setVisible(false);
            }
        });
        Category.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MainForm m =new MainForm();
                m.setVisible(true);
                Product.this.setVisible(false);
            }
        });
        Product.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Product p =new Product();
                p.setVisible(true);
                Product.this.setVisible(false);
            }
        });
        POS.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                POS p =new POS();
                p.setVisible(true);
                Product.this.setVisible(false);
            }
        });



    }








    void category(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con1 = DriverManager.getConnection("jdbc:mysql://localhost/POS","root", "Liisol1122@#");
            pst = con1.prepareStatement("select * from category");
            ResultSet rs = pst.executeQuery();
            txtcat.removeAllItems();
            while(rs.next()){
                txtcat.addItem(new CategoryItem(rs.getInt(1),rs.getString(2)));
            }


        }catch(ClassNotFoundException ex){
            Logger.getLogger(forms.Product.Product.class.getName()).log(Level.SEVERE, null, ex);
        }catch (SQLException ex){
            Logger.getLogger(forms.Product.Product.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
    void Brand(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con1 = DriverManager.getConnection("jdbc:mysql://localhost/POS","root", "Liisol1122@#");
            pst = con1.prepareStatement("select * from brand");
            ResultSet rs = pst.executeQuery();
            txtBrand.removeAllItems();
            while(rs.next()){


                txtBrand.addItem(new BrandItem(rs.getInt(1),rs.getString(2)));

            }


        }catch(ClassNotFoundException ex){
            Logger.getLogger(forms.Product.Product.class.getName()).log(Level.SEVERE, null, ex);
        }catch (SQLException ex){
            Logger.getLogger(forms.Product.Product.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    public class CategoryItem {
         int id;
         String name;

        public CategoryItem(int id, String name) {
            this.id = id;
            this.name = name;
        }
        @Override
        public String toString() {
            return name; // This will be displayed in the JComboBox
        }
    }
    public class BrandItem {
        int id;
        String name;

        public BrandItem(int id, String name) {
            this.id = id;
            this.name = name;
        }
        @Override
        public String toString() {
            return name; // This will be displayed in the JComboBox
        }
    }
//    void table_load(){
//        try {
//            // Ensure connection is not null
//            if (con1 == null || con1.isClosed()) {
//                JOptionPane.showMessageDialog(null, "Database connection is not established.");
//                return;
//            }
//
//            // Prepare the SQL statement
//            String query = "SELECT * FROM product";
//            pst = con1.prepareStatement(query);
//
//            // Execute the query
//            ResultSet rs = pst.executeQuery();
//
//            // Convert the ResultSet to TableModel and set it to the JTable
//            tblStore.setModel(DbUtils.resultSetToTableModel(rs));
//
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, "Error loading table data: " + e.getMessage());
//            e.printStackTrace();
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
            pst = con1.prepareStatement("select * from product");
            ResultSet rs =pst.executeQuery();

            tblStore.setModel(DbUtils.resultSetToTableModel(rs));
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
