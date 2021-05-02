package com.company;

import javax.swing.*;
import java.awt.event.*;

public class ProducerManagement extends JDialog {
    private JPanel contentPane;
    private JButton ADD;
    private JButton editButton;
    private JButton deleteButton;
    private JButton backButton;
    private JButton seachButton;
    private JTextField textField1;
    private JComboBox comboBox1;
    private JTable table1;
    private JButton viewRequestButton;
    private JTextField name_in;
    private JTextField phone_in;
    private JTextField email_in;
    private JComboBox comboBox2;
    private JButton saveButton;
    private JButton ADDButton;
    private JButton buttonOK;
    private JButton buttonCancel;

    public ProducerManagement() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        ProducerManagement dialog = new ProducerManagement();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
