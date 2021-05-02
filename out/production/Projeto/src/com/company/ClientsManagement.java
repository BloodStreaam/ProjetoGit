package com.company;

import javax.swing.*;
import java.awt.event.*;

public class ClientsManagement extends JDialog {
    private JPanel contentPane;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton backButton;
    private JTable table1;
    private JComboBox comboBox1;
    private JTextField textField1;
    private JButton sButton;
    private JTextField in_name;
    private JTextField in_mail;
    private JTextField in_phone;
    private JTextField in_address;
    private JTextField in_zip;
    private JComboBox day_box;
    private JComboBox mounth_box;
    private JComboBox year_box;
    private JButton SAVEButton;
    private JButton ADDButton;
    private JButton buttonOK;
    private JButton buttonCancel;

    public ClientsManagement() {
        setContentPane(contentPane);
        setModal(true);



    }

    public static void main(String[] args) {
        ClientsManagement dialog = new ClientsManagement();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
