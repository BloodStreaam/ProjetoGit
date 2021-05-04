package com.company;

import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.*;
import java.awt.event.*;

public class Login extends JDialog {
    private JPanel contentPane;
    private JTextField email;
    private JPasswordField password;
    private JButton login;
    private JButton register;
    private JLabel forgotpass;
    private JRadioButton clientRadioButton;
    private JRadioButton employeeRadioButton;
    private JLabel LPass;
    public static Login jLogin;
    public static AdminManagement jAdminManagement;

    public Login() {
        setContentPane(contentPane);
        setModal(true);
        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });


        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkLogin();
            }
        });
        forgotpass.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showMessageDialog(null, "Email or Password doesn't exist! Try again using the correct Email or Password!");

            }
        });
        clientRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                employeeRadioButton.setSelected(false);
                password.setVisible(true);
                LPass.setVisible(true);
                forgotpass.setVisible(true);
            }
        });
        employeeRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientRadioButton.setSelected(false);
                password.setVisible(false);
                LPass.setVisible(false);
                forgotpass.setVisible(false);
            }
        });
    }

    public void checkLogin(){
        if("admin".equals(email.getText())){
            showMessageDialog(null, "Login Sucessefully!");

            jAdminManagement = new AdminManagement();
            jAdminManagement.pack();
            jAdminManagement.setVisible(true);
        }else{
            showMessageDialog(null, "Email or Password doesn't exist! Try again using the correct Email or Password!");
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
