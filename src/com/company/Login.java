package com.company;

import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;

public class Login extends JDialog {
    private JPanel contentPane;
    private JTextField email;
    private JButton login;
    public static AdminManagement jAdminManagement;
    public static Login jLogin;


    public Login() {
        setContentPane(contentPane);
        setModal(true);


        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    checkLogin();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }

    public void checkLogin() throws SQLException {
       EmployeeService employee = new EmployeeService();

       employee.readEmployeeEmail(String.valueOf(email.getText()));

       if(employee.getPosition_id() == 1){
           employee.setLoggedEmployeeID(employee.getE_id());
           System.out.println(employee.getLoggedEmployeeID());
           jAdminManagement = new AdminManagement();
           jAdminManagement.pack();
           jAdminManagement.setVisible(true);
           setVisible(false);


       }else{
            JOptionPane.showConfirmDialog (null, "Wrong mail or you dont have permissions to use this application!", "Error!", JOptionPane.CANCEL_OPTION);
       }
    }



    public static void main(String[] args) {
        Login dialog = new Login();
        jLogin = dialog;
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);

    }
}
