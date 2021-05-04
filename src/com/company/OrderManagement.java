package com.company;

import javax.swing.*;
import java.awt.event.*;

public class OrderManagement extends JDialog {
    private JPanel contentPane;
    private JButton cancelOrderButton;
    private JButton searchButton;
    private JTextField textField1;
    private JComboBox comboBox1;
    private JTable table2;
    private JButton backButton;
    private JButton cancelOrderButton1;
    private JButton confirmeOrderButton;
    private JLabel IDLabel;
    private JLabel orderDateLabel;
    private JLabel paymenteLabel;
    private JLabel totalPriceLabel;
    private JLabel clienteLabel;
    private JLabel addressLabel;
    private JLabel transportationLabel;
    private JTextField texts2;
    private JScrollPane scroll2;
    private JButton buttonOK;
    private JButton buttonCancel;

    public OrderManagement() {
        setContentPane(contentPane);
        setModal(true);



    }


    public static void main(String[] args) {
        OrderManagement dialog = new OrderManagement();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
