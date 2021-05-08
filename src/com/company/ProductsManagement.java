package com.company;

import javax.swing.*;
import java.awt.event.*;

public class ProductsManagement extends JDialog {
    private JPanel contentPane;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton backButton;
    private JTable table1;
    private JButton buttonOK;
    private JButton buttonCancel;


    public ProductsManagement() {
        setContentPane(contentPane);
        setModal(true);
<<<<<<< Updated upstream
        getRootPane().setDefaultButton(buttonOK);
=======
        jscrollpane1.getViewport().add(jtable=createJTable());
        fillSearchCombobox();
        editPanel.setVisible(false);
        infoPanel.setVisible(false);

>>>>>>> Stashed changes

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminManagement.jProductsManagement.setVisible(false);
            }
        });
    }


    public static void main(String[] args) {
        ProductsManagement dialog = new ProductsManagement();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);

        AdminManagement.jAdminManagement.setVisible(false);
    }
}
