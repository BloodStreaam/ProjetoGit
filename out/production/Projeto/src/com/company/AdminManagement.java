package com.company;

import javax.swing.*;
import java.awt.event.*;

public class AdminManagement extends JDialog {
    private JPanel contentPane;
    private JButton productsButton;
    private JButton ordersButton;
    private JButton employeesButton;
    private JButton producersButton;
    private JButton clientsButton;
    private JButton restockButton;
    private JButton userViewButton;
    private JButton logOutButton;
    private JButton globalViewButton;
    private JButton buttonOK;
    private JButton buttonCancel;
    public static ProductsManagement jProductsManagement;
    public static AdminManagement jAdminManagement;
    public static EmployeesManagement jEmployeesManagement;
    public static ClientsManagement jClientsManagement;

    public AdminManagement() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        productsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jProductsManagement = new ProductsManagement();
                jProductsManagement.pack();
                jProductsManagement.setVisible(true);
            }
        });
        employeesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jEmployeesManagement = new EmployeesManagement();
                jEmployeesManagement.pack();
                jEmployeesManagement.setVisible(true);
            }
        });
        clientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jClientsManagement = new ClientsManagement();
                jClientsManagement.pack();
                jClientsManagement.setVisible(true);
            }
        });
    }



    public static void main(String[] args) {
        jAdminManagement = new AdminManagement();
        jAdminManagement.pack();
        jAdminManagement.setVisible(true);
        System.exit(0);


    }
}
