package forms.POS;

import com.mysql.cj.protocol.Resultset;
import forms.Brand.Brand;
import forms.MainForm;
import forms.Product.Product;
import net.proteanit.sql.DbUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;


public class POS extends JFrame{
    private JPanel POSPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JTextField txtbrand;
    private JTable tblStore;
    private JTextField txtcost;
    private JTextField txtqty;
    private JTextField txtpd;
    private JButton Add;
    private JButton Delete;
    private JButton Update;
    private JButton  bntPay;
    private JTextField txttotal ;
    private JTextField txtpay;
    private JTextField txtbalance ;
    private JLabel Category;
    private JLabel Brand;
    private JLabel Product;
    private JLabel POS;
    private JLabel Exit;
    private JLabel Cashier;
    private JComboBox txtstus;
    private JLabel txtstatus;
    private JPanel PanelTable;
    private JButton Search;
    private JTextField txtBarcode;

    Connection con1;
    PreparedStatement insert;
    ResultSet rs;
    PreparedStatement pst;
    public POS(){
        setContentPane(POSPanel);
        setTitle("POS Form");
        setMinimumSize(new Dimension(1200, 700));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        String[] columnNames = {"Barcode", "Product name", "Cost", "Quantity", "Total"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        tblStore.setModel(model);

        connect();
//        table_load();




        Add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pos();
                String name = txtBarcode.getText();


                try {


                    Class.forName("com.mysql.cj.jdbc.Driver");

                    con1 = DriverManager.getConnection("jdbc:mysql://localhost/POS", "root", "Liisol1122@#");



                    insert = con1.prepareStatement("select * from product where Barcode = ?");
                    insert.setString(1, name);
                    rs = insert.executeQuery();

                    while (rs.next()) {
                        int currentqty;
                        currentqty = rs.getInt("qty");
                        int price = Integer.parseInt(txtcost.getText());
                        int qtynew = Integer.parseInt(txtqty.getText());

                        int tot = price * qtynew;

                        if(qtynew >= currentqty)
                        {
                            //the problem (POS.this)
                            JOptionPane.showMessageDialog(POS.this,"Avaliable product  " + " = " + currentqty);
                            JOptionPane.showMessageDialog(POS.this,"Qty is not enough");
                        }else{
                            DefaultTableModel model = (DefaultTableModel) tblStore.getModel();
                            model.addRow(new Object[]
                                    {
                                            txtBarcode.getText(),
                                            txtpd.getText(),
                                            txtcost.getText(),
                                            txtqty.getText(),
                                            tot,
                                    });
                            txtBarcode.setText("");
                            txtpd.setText("");
                            txtcost.setText("");
                            txtqty.setText("");
                            txtBarcode.requestFocus();
                        }


                    }



                } catch (ClassNotFoundException ex) {

                    Logger.getLogger(POS.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {

                    Logger.getLogger(POS.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });





        tblStore.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                DefaultTableModel model1 = (DefaultTableModel) tblStore.getModel();
//                int selectIndex = tblStore.getSelectedRow();
                String[] columnNames = {"Barcode", "Product Name", "Cost", "Quantity", "Total"};
                DefaultTableModel model = new DefaultTableModel(columnNames, 0);
                tblStore.setModel(model); // Set the model to the table

                connect();

            }
        });


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
                        pst = con1.prepareStatement("delete from pos where id = ?");
                        pst.setInt(1,id);



                        pst.executeUpdate();
                        JOptionPane.showMessageDialog(null, "pos deleteddd");


//                        table_load();
                        // Reset input fields
                        txtBarcode.setText("");
                        txtpd.setText("");
                        txtcost.setText("");
                        txtqty.setText("");
                        txtBarcode.requestFocus();

                    }catch (ClassNotFoundException ex){
                        Logger.getLogger(POS.class.getName()).log(Level.SEVERE, null, ex);
                    }catch (SQLException ex){
                        Logger.getLogger(POS.class.getName()).log(Level.SEVERE, null, ex);
                    }
//                    Logger.getLogger(forms.Product.Product.class.getName()).log(Level.SEVERE, null, ex);
                }






            }
        });
        Brand.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Brand b = new Brand();
                b.setVisible(true);
                POS.this.setVisible(false);
            }
        });
        Category.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MainForm m =new MainForm();
                m.setVisible(true);
                POS.this.setVisible(false);
            }
        });
        Product.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Product p =new Product();
                p.setVisible(true);
                POS.this.setVisible(false);
            }
        });
        POS.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                POS p =new POS();
                p.setVisible(true);
                POS.this.setVisible(false);
            }
        });



        txtBarcode.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt) {
                if(evt.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    String name = txtBarcode.getText();

                    try{
                        System.out.println("Loading JDBC driver...");
                        Class.forName("com.mysql.cj.jdbc.Driver");

                        // Attempt to connect to the database
                        System.out.println("Connecting to the database...");
                        con1 = DriverManager.getConnection("jdbc:mysql://localhost/POS", "root", "Liisol1122@#");
                        insert = con1.prepareStatement("select * from product where Barcode=?");
                        insert.setString(1,name);
                        rs  = insert.executeQuery();


                        if(rs.next() == false){
                            JOptionPane.showMessageDialog(POS.this, "Barcode not foundddd");

                        }else{
                            String productname = rs.getString("Product");
                            String  price      = rs.getString("Retail_price");

                            txtpd.setText(productname.trim());
                            txtcost.setText(price.trim());
                        }





                    }catch( ClassNotFoundException ex){
                        Logger.getLogger(POS.class.getName()).log(Level.SEVERE, null, ex);
                    }catch( SQLException ex){
                        Logger.getLogger(POS.class.getName()).log(Level.SEVERE, null, ex);
                    }



                }
            }
        });


        bntPay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int pay = Integer.parseInt(txtpay.getText());
                int subtotal = Integer.parseInt(txttotal.getText());
                int bal = pay - subtotal ;
                txtbalance.setText(String.valueOf(bal));
                 salse();
            }
        });
    }


    private  void salse(){
        //datetime
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String date = dtf.format(now);


        String subtotal = txttotal.getText();
        String pay = txtpay.getText();
        String balance = txtbalance.getText();
        int  lastisetid =0 ;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con1 = DriverManager.getConnection("jdbc:mysql://localhost/POS", "root", "Liisol1122@#");
            String query = "insert into pos(date, subtotal ,pay ,balance)values(?,?,?,?)";
            insert = con1.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            insert.setString(1, date);
            insert.setString(2, subtotal);
            insert.setString(3, pay);
            insert.setString(4, balance);
            insert.executeUpdate();
            ResultSet genteratedKeyResult = insert.getGeneratedKeys();

            if(genteratedKeyResult.next()){
                lastisetid = genteratedKeyResult.getInt(1);
            }


            JTable JTable1 = tblStore;
            int rows = JTable1.getRowCount();


            String query1 = "INSERT INTO product_sale(sale_id, product_id, sell_price, qty, total) VALUES(?, ?, ?, ?, ?)";
            insert = con1.prepareStatement(query1);


            String product_id="";
            String price="";
            String qty="";
            int total= 0 ;


            for(int i = 0 ; i<JTable1.getRowCount();i++){
                product_id =(String) JTable1.getValueAt(i , 0);
                price =(String) JTable1.getValueAt(i , 2);
                qty = (String) JTable1.getValueAt(i , 3);
                total =(int) JTable1.getValueAt(i , 4);



                insert.setInt(1,lastisetid);
                insert.setString(2,product_id);
                insert.setString(3,price);
                insert.setString(4,qty);
                insert.setInt(5,total);
                insert.executeUpdate();

            }



//            String query3 ="update product set qty = qty-? where barcode=? ";
//            insert = con1.prepareStatement(query3);
//            for(int i = 0 ; i<JTable.getRowCount();i++){
//                product_id =(String) JTable.getValueAt(i , 0);
//                qty = (String) JTable.getValueAt(i , 3);
//                insert.setString(1,qty);
//                insert.setString(2,product_id);
//                insert.execute();
//
//            }

            // Update product quantities after the sale
            String query3 = "update product SET qty = qty - ? WHERE Barcode = ?";
            insert = con1.prepareStatement(query3);
            for (int i = 0; i < JTable1.getRowCount(); i++) {


                 product_id = (String) tblStore.getValueAt(i, 0);
                 qty = (String) tblStore.getValueAt(i, 3);

                insert.setString(1, qty);
                insert.setString(2, product_id);
                insert.execute();
            }











            JOptionPane.showMessageDialog(this,"recorded saved");

        }catch (ClassNotFoundException ex) {
            Logger.getLogger(POS.class.getName()).log(Level.SEVERE, null, ex);
        }catch (Exception ex){
            Logger.getLogger(POS.class.getName()).log(Level.SEVERE, null, ex);
        }



    }

    private void pos(){
        String name = txtBarcode.getText();


        try {


            Class.forName("com.mysql.cj.jdbc.Driver");

            con1 = DriverManager.getConnection("jdbc:mysql://localhost/POS", "root", "Liisol1122@#");



            insert = con1.prepareStatement("select * from product where Barcode = ?");
            insert.setString(1, name);
            rs = insert.executeQuery();



            while (rs.next()) {
                int currentqty;
                currentqty = rs.getInt("qty");
                int price = Integer.parseInt(txtcost.getText());
                int qtynew = Integer.parseInt(txtqty.getText());

                int tot = price * qtynew;



                if(qtynew > currentqty)
                {
                    //the problem (POS.this)
                    JOptionPane.showMessageDialog(this,"Avaliable product  " + " = " + currentqty);
                    JOptionPane.showMessageDialog(this,"Qty is not enough");
                }
                else
                {
                  DefaultTableModel model = (DefaultTableModel) tblStore.getModel();
                    model.addRow(new Object[]{
                                    txtBarcode.getText(),
                                    txtpd.getText(),
                                    txtcost.getText(),
                                    txtqty.getText(),
                                    tot,
                    });
                            int sum = 0;
                            for(int i =0 ; i<tblStore.getRowCount(); i++){
                                sum = sum + Integer.parseInt(tblStore.getValueAt(i, 4).toString());

                            }
                            txttotal.setText(Integer.toString(sum));



                             txtBarcode.setText("");
                             txtpd.setText("");
                             txtcost.setText("");
                             txtqty.setText("");
                             txtBarcode.requestFocus();
                }


            }



        } catch (ClassNotFoundException ex) {

            Logger.getLogger(POS.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {

            Logger.getLogger(POS.class.getName()).log(Level.SEVERE, null, ex);
        }
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

//    void table_load(){
//        try{
//            pst = con1.prepareStatement("select * from product");
//            ResultSet rs =pst.executeQuery();
//
//            tblStore.setModel(DbUtils.resultSetToTableModel(rs));
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
//    }
}
