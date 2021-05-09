package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class RestockManagement extends JDialog {
    private JPanel contentPane;
    private JButton addButton1;
    private JButton showRequestButton;
    private JButton editButton;
    private JButton backButton;
    private JTextField dateField;
    private JTextField employeeField;
    private JComboBox farmBox;
    private JComboBox prodBox;
    private JButton addButton;
    private JButton saveButton;
    private JLabel idInfo;
    private JLabel farmInfo;
    private JLabel employeeInfo;
    private JLabel dateInfo;
    private JLabel paymentInfo;
    private JLabel transportationInfo;
    private JScrollPane productsTableInfo;
    private static JTable jtableRestock;
    private static JTable jtable2;
    private static JTable jtableReqRestock;
    private JScrollPane scrollpanel;
    private JPanel editPanel;
    private JPanel infoPanel;
    private JLabel tpriceInfo;
    private JTable jtableAddedProducts;
    private JScrollPane jscrollpane2;
    private JButton showRestocksButton;
    private static List<ProductService> products;
    private static List<FarmService> farms;
    private static List<RestockDetailsService> restockDetails;
    private static List<RestockService> restocks;
    private static List<ReqRestockService> reqs_restock;
    private static String idRestockSelected;
    private static int idRestockConverted;
    private static String addedProductName;
    private static Object[] addedProducts  = new Object[20];;


    public RestockManagement() {
        setContentPane(contentPane);
        setModal(true);


        scrollpanel.getViewport().add(jtableRestock=createJTableRestock());
        editPanel.setVisible(false);
        infoPanel.setVisible(false);

        jtableRestock.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                idRestockSelected = jtableRestock.getValueAt(jtableRestock.getSelectedRow(),0).toString();
                idRestockConverted = Integer.parseInt(idRestockSelected);
                System.out.println(idRestockConverted);
                infoPanel.setVisible(true);
                showFullDetail(idRestockConverted);
                pack();

            }

        });

        jtableAddedProducts.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                addedProductName = jtableAddedProducts.getValueAt(jtableAddedProducts.getSelectedRow(),0).toString();

                pack();

            }

        });




        showRequestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jtableRestock.setVisible(false);
                idRestockConverted = 0;
                showRequestButton.setVisible(false);
                showRestocksButton.setVisible(true);
                jtableReqRestock.setVisible(true);


            }
        });

        addButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearInputs();
                fillAddArea();
                editPanel.setVisible(true);
                pack();
            }
        });
        farmBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                updateComboBoxProducts();
                pack();

            }
        });
        prodBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    //Apresenta a informação completa do produto
    private void showFullDetail(int idRestock){
        RestockService restock = new RestockService();
        EmployeeService employee = new EmployeeService();
        FarmService farm = new FarmService();
        ProductService product = new ProductService();
        float totalPrice = 0;



        restock.read(idRestock);
        employee.read(restock.getE_id());
        farm.readFarm(restock.getFarm_id());
        restockDetails = RestockDetailsService.read(restock.getR_id());


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


        productsTableInfo.getViewport().add(jtable2=jtable);


        idInfo.setText("ID: " + restock.getR_id());
        farmInfo.setText("Farm: " + farm.getName());
        employeeInfo.setText("Employee: " + employee.getName());
        dateInfo.setText(String.valueOf("Date: " + restock.getR_date()));
        tpriceInfo.setText(String.valueOf("Total Price: " + totalPrice + "€"));


    }

    private void fillAddArea(){
       farms = FarmService.readAllFarms();
       products = ProductService.readAll();
       EmployeeService employee = new EmployeeService();

       employee.read(1); //Mudar o ID para GetLoggedemployeeId
       employeeField.setText(employee.getName());
       dateField.setText(String.valueOf(java.time.LocalDate.now()));

       farmBox.addItem("");
       prodBox.addItem("");
       prodBox.setSelectedItem("");
       farmBox.setSelectedItem("");
        for (FarmService farm : farms) {
            farmBox.addItem(farm.getName());

            if(farm.getName().equals(farmBox.getSelectedItem())){
                farmBox.setSelectedItem(farm.getName());
                for (ProductService product: products){
                    if(farm.getIdFarm() == product.getFarm_id()){
                        prodBox.addItem(product.getName());
                    }
                }
            }
        }

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nº Products");
        model.addColumn("Price");
        model.addColumn("Date");


        JTable jtable = new JTable(model);
        jtable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        jscrollpane2.getViewport().add(jtableAddedProducts = jtable);
        jscrollpane2.setPreferredSize(new Dimension(250,100));
        jscrollpane2.revalidate();

    }

    private void clearInputs(){
        List<JTextField> tfList = new ArrayList<JTextField>();
        List<JComboBox> cbList = new ArrayList<JComboBox>();


        tfList.add(employeeField);
        tfList.add(dateField);


        cbList.add(farmBox);
        cbList.add(prodBox);


        for(JTextField tf : tfList){
            tf.setText("");
        }

        for(JComboBox cb : cbList){
            cb.removeAllItems();
        }


    }

    private void updateComboBoxProducts(){

        farms = FarmService.readAllFarms();
        products = ProductService.readAll();

        prodBox.removeAllItems();
        prodBox.addItem("");
        prodBox.setSelectedItem("");

        for(FarmService farm: farms){
            if(farm.getName().equals(farmBox.getSelectedItem())){
                for(ProductService product: products){
                    if(product.getFarm_id() == farm.getIdFarm()){
                        prodBox.addItem(product.getName());
                    }
                }
            }
        }

    }

    private void selectProductToAdd(){
        int dialogButton = JOptionPane.YES_NO_OPTION;

        int dialogResult = JOptionPane.showConfirmDialog (null, "How many items do you want to do restock?","Warning",dialogButton);
        if(dialogResult == JOptionPane.YES_OPTION){
            if(dialogResult >=0){

            }
        }



    }

    private void updateAddedProductTable(int quantity){
        products = ProductService.readAll();


        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Name");
        model.addColumn("Quantity");
        model.addColumn("Price");


        this.jtableAddedProducts.setModel(model);
        this.jscrollpane2.revalidate();


    }




    //Cria a tabela de produtos com as informações mais importantes
    private static JTable createJTableRestock() {
        restocks = RestockService.readAll();
        restockDetails = RestockDetailsService.readAll();
        int precoTotal = 0;
        int nProdutos = 0;
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nº Products");
        model.addColumn("Price");
        model.addColumn("Date");



        for (RestockService restock : restocks) {
            precoTotal = 0;
            nProdutos = 0;
            for(RestockDetailsService restockDetail: restockDetails){
                System.out.println(restockDetail.getR_id());
                if(restockDetail.getR_id() == restock.getR_id()){
                    precoTotal +=  restockDetail.getPrice();
                    nProdutos++;
                }

            }
            model.addRow(new Object[]{restock.getR_id(), nProdutos , precoTotal, restock.getR_date()});

        }

        JTable jtable = new JTable(model);
        jtable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);



        return jtable;
    }




    public static void main(String[] args) {
        RestockManagement dialog = new RestockManagement();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
