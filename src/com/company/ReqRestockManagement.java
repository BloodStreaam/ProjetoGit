package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

 class AddedProduct{
    private String productName;
    private int quantity;
    private float price;

    public void setData(String name, int quantity, float price)
    {
        this.productName = name;
        this.quantity = quantity;
        this.price = price;
    }

     public String getProductName() {
         return productName;
     }

     public int getQuantity() {
         return quantity;
     }

     public float getPrice() {
         return price;
     }
 }

public class ReqRestockManagement extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton backButton;
    private JTextField employeeField;
    private JTextField dateField;
    private JComboBox farmBox;
    private JComboBox productBox;
    private JButton addButton1;
    private JButton saveButton;
    private JLabel idInfo;
    private JLabel employeeInfo;
    private JScrollPane scrollpanel;
    private JScrollPane scrollpanel1;
    private JLabel dateInfo;
    private JLabel tpriceInfo;
    private JPanel infoPanel;
    private JPanel editPanel;
    private JScrollPane scrollpanel2;
    private JList list1;
    private JPanel panelInfo;
    private static JTable jtable;
    private static JTable jtableProducts;
    private static JTable jtableProductsAdded;
    private static List<RestockDetailsService> restockDetails;
    private static List<RestockService> restocks;
    private static List<ReqRestockService> reqs_restock;
    private static List<FarmService> farms;
    private static List<ProductService> products;
    private static String idReqRestockSelected;
    private static int idReqRestockConverted;
    private static int idReqRestockOnEdit;
    private static AddedProduct[] productsAddedToRequest;
    private static int productCount = 0;


    public ReqRestockManagement() {

        setContentPane(contentPane);
        setModal(true);

        editPanel.setVisible(false);
        infoPanel.setVisible(false);
        scrollpanel1.getViewport().add( jtable=createJTableReqRestock());
        scrollpanel1.setPreferredSize(new Dimension(500,150));
        scrollpanel1.revalidate();

        jtable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                idReqRestockSelected = jtable.getValueAt(jtable.getSelectedRow(),0).toString();
                idReqRestockConverted = Integer.parseInt(idReqRestockSelected);
                System.out.println(idReqRestockConverted);
                showFullDetail(idReqRestockConverted);
                infoPanel.setVisible(true);

                pack();

            }

        });
        if(jtableProductsAdded != null) {
            jtableProductsAdded.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    String name;
                    name = jtableProductsAdded.getValueAt(jtableProductsAdded.getSelectedRow(), 0).toString();
                    System.out.println(name+ "Merda para ti");
                    for (int i = 0; i <= productCount; i++) {
                        if (productsAddedToRequest[i].getProductName().equals(name)) {
                            int dialogButton = JOptionPane.YES_NO_OPTION;

                            int dialogResult = JOptionPane.showConfirmDialog (null, "Would You Like to Delete this Product?","Warning",dialogButton);
                            if(dialogResult == JOptionPane.YES_OPTION){
                                productsAddedToRequest[i] = null;
                            }

                        }
                    }

                    pack();

                }

            });
        }
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                productCount = showInfoEdit(idReqRestockConverted);
                saveButton.setVisible(true);
                addButton1.setVisible(false);
                editPanel.setVisible(true);
                pack();
            }
        });
        farmBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    changeProducts();
                }

            }
        });
        productBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {

                    //addQuantityProductToRequest();
                }

            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
    }

    private void addQuantityProductToRequest(){
        products = ProductService.readAll();
        ProductService prod = new ProductService();
        AddedProduct addProduct = new AddedProduct();


        for(ProductService product: products){
            if(product.getName().equals(productBox.getSelectedItem())){
                prod.read(product.getProduct_id());
            }
        }

        String quantity = JOptionPane.showInputDialog("Quantity");
        int quantityInt = Integer.parseInt(quantity);

        if(quantityInt >= 0){

            productsAddedToRequest[productCount].setData(prod.getName(), quantityInt, prod.getPrice_un());
            productCount++;
        }

    }


    private void showFullDetail(int idReqRestock){
        ReqRestockService reqRestock = new ReqRestockService();
        EmployeeService employee = new EmployeeService();
        ProductService product = new ProductService();
        float totalPrice = 0;



        reqRestock.read(idReqRestock);
        employee.read(reqRestock.getE_id());

        restockDetails = RestockDetailsService.readReq(reqRestock.getReq_id());


        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Name");
        model.addColumn("Quantity");
        model.addColumn("Price");



        for(RestockDetailsService detail : restockDetails){
            product.read(detail.getP_id());
            totalPrice+=detail.getPrice();
            model.addRow(new Object[]{product.getName(), detail.getQuantity() , detail.getPrice()});

        }

        JTable jtable = new JTable(model);


        scrollpanel.getViewport().add(jtableProducts=jtable);
        scrollpanel.setPreferredSize(new Dimension(250,100));
        scrollpanel.revalidate();


        idInfo.setText("ID: " + reqRestock.getReq_id());
        employeeInfo.setText("Employee: " + employee.getName());
        dateInfo.setText(String.valueOf("Date: " + reqRestock.getReq_date()));
        tpriceInfo.setText(String.valueOf("Total Price: " + totalPrice + "€"));


    }

    private int showInfoEdit(int id){
        idReqRestockOnEdit= id; //Guarda o ID do produto que está a ser editado para mais tarde ser usado no update
        EmployeeService employee = new EmployeeService();
        ReqRestockService reqRestock = new ReqRestockService();
        ProductService prod = new ProductService();
        productsAddedToRequest = new AddedProduct[20];
        int count = 0;


        farms = FarmService.readAllFarms();
        products = ProductService.readAll();

        reqRestock.read(id); //lê o produto que tem o id indicado
        employee.read(reqRestock.getE_id());
        employeeField.setText(employee.getName());
        dateField.setText(String.valueOf(reqRestock.getReq_date()));

        productBox.removeAllItems();
        for(FarmService farm: farms){
            farmBox.addItem(farm.getName());
            if(farm.getName().equals(farmBox.getSelectedItem())){
                for(ProductService product: products){
                    if(farm.getIdFarm() == product.getFarm_id()){
                        productBox.addItem(product.getName());
                    }
                }
            }
        }

        restockDetails = RestockDetailsService.readReq(reqRestock.getReq_id());

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Name");
        model.addColumn("Quantity");
        model.addColumn("Price");

        for(RestockDetailsService detail : restockDetails){
            prod.read(detail.getP_id());
            productsAddedToRequest[count] = new AddedProduct();
            productsAddedToRequest[count].setData(prod.getName(), detail.getQuantity(), prod.getPrice_un());
            count++;
        }

        System.out.println(productsAddedToRequest[count-1].getProductName());
        for(int i = 0; i<=count-1; i++){
            model.addRow(new Object[]{productsAddedToRequest[i].getProductName(), productsAddedToRequest[i].getQuantity() , productsAddedToRequest[i].getPrice()});
        }

        JTable jtable = new JTable(model);



        scrollpanel2.getViewport().add(jtableProductsAdded=jtable);
        scrollpanel2.setPreferredSize(new Dimension(250,100));
        scrollpanel2.revalidate();

        return count-1;

    }

    private void changeProducts(){
        farms = FarmService.readAllFarms();
        products = ProductService.readAll();



        System.out.println(farmBox.getSelectedItem());
        productBox.removeAllItems();
        for(FarmService farm: farms){
            if(farm.getName().equals(farmBox.getSelectedItem())){
                for(ProductService product: products){
                    if(farm.getIdFarm() == product.getFarm_id()){
                        productBox.addItem(product.getName());
                    }
                }
            }
        }

    }


    private static JTable createJTableReqRestock() {
        reqs_restock = ReqRestockService.readAll();
        restockDetails = RestockDetailsService.readAll();
        int precoTotal = 0;
        int nProdutos = 0;
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nº Products");
        model.addColumn("Price");
        model.addColumn("Date");

        for (ReqRestockService reqRestock : reqs_restock) {
            precoTotal = 0;
            nProdutos = 0;
            for(RestockDetailsService restockDetail: restockDetails){
                System.out.println(restockDetail.getR_id());
                if(restockDetail.getReq_id() == reqRestock.getReq_id()){
                    precoTotal +=  restockDetail.getPrice();
                    nProdutos++;
                }

            }
            model.addRow(new Object[]{reqRestock.getReq_id(), nProdutos , precoTotal, reqRestock.getReq_date()});

        }
        JTable jtable = new JTable(model);
        jtable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        return jtable;
    }


    public static void main(String[] args) {
        ReqRestockManagement dialog = new ReqRestockManagement();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
