package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
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
    private JTable table1;
    private JButton makeRequestButton;
    private JButton addButton;
    private JButton saveButton;
    private JLabel idInfo;
    private JLabel farmInfo;
    private JLabel employeeInfo;
    private JLabel dateInfo;
    private JLabel paymentInfo;
    private JLabel transportationInfo;
    private JScrollPane productsTableInfo;
    private JPanel tableProductsInput;
    private static JTable jtableRestock;
    private static JTable jtable2;
    private static JTable jtableReqRestock;
    private JScrollPane scrollpanel;
    private JPanel editPanel;
    private JPanel infoPanel;
    private JLabel tpriceInfo;
    private JButton showRestocksButton;
    private static List<RestockDetailsService> restockDetails;
    private static List<RestockService> restocks;
    private static List<ReqRestockService> reqs_restock;
    private static String idRestockSelected;
    private static int idRestockConverted;
    private static String idReqRestockSelected;
    private static int idReqRestockConverted;

    public RestockManagement() {
        setContentPane(contentPane);
        setModal(true);

        scrollpanel.getViewport().add(jtableReqRestock=createJTableReqRestock());
        scrollpanel.getViewport().add(jtableRestock=createJTableRestock());
        jtableReqRestock.setVisible(false);
        showRestocksButton.setVisible(false);
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


        jtableReqRestock.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    idReqRestockSelected = jtableReqRestock.getValueAt(jtableReqRestock.getSelectedRow(), 0).toString();
                    idReqRestockConverted = Integer.parseInt(idReqRestockSelected);
                    System.out.println(idReqRestockConverted);
                    infoPanel.setVisible(true);
                    showFullDetail(idReqRestockConverted);
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

        showRestocksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jtableReqRestock.setVisible(false);
                idRestockConverted = 0;
                showRequestButton.setVisible(true);
                showRestocksButton.setVisible(false);
                jtableRestock.setVisible(true);
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


        // for(EmployeeService emp : employees)

            /*values.add(new String[] { String.valueOf(emp.getE_id()),  emp.getName(),  String.valueOf(emp.getBirthdate()),
                    emp.getMail(),  String.valueOf(emp.getPhone()),  String.valueOf(emp.getSalary()), emp.getAddress(), emp.getZip() });
*/
        JTable jtable = new JTable(model);
        jtable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);



        return jtable;
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


        // for(EmployeeService emp : employees)

            /*values.add(new String[] { String.valueOf(emp.getE_id()),  emp.getName(),  String.valueOf(emp.getBirthdate()),
                    emp.getMail(),  String.valueOf(emp.getPhone()),  String.valueOf(emp.getSalary()), emp.getAddress(), emp.getZip() });
*/
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
