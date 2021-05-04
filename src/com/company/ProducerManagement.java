package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;

public class ProducerManagement extends JDialog {
    private JPanel contentPane;
    private JButton ADD;
    private JButton editButton;
    public static boolean jAddCheck = false;
    public static boolean jEditCheck = false;
    private JButton deleteButton;
    private JButton backButton;
    private JButton seachButton;
    private JTextField textField1;
    private JComboBox comboBox1;
    private JButton viewRequestButton;
    private JTextField name_in;
    private JTextField phone_in;
    private JTextField email_in;
    private JComboBox comboBox2;
    private JButton saveButton;
    private JButton ADDButton;
    private JTextField text2;
    private JScrollPane scroll1;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JScrollPane scrollPane1;
    private JTable jtable;
    private static List<ProducerService> producers;
    public static ProducerManagement JProducer;
    public static String idProducer;

    public ProducerManagement() {
        setContentPane(contentPane);
        setModal(true);

        scroll1.getViewport().add(jtable=createJTable());





        ADD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jAddCheck = true;


            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idProducer = jtable.getValueAt(jtable.getSelectedRow(),0).toString();
                jEditCheck = true;

            }
        });

        jtable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                idProducer = jtable.getValueAt(jtable.getSelectedRow(),0).toString();
                System.out.print(idProducer);

            }

        });




    }

    private static JTable createJTable(){
        producers = ProducerService.readAll();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Mail");
        model.addColumn("Phone");

        for(ProducerService pro : producers) {
            model.addRow(new Object[] { pro.getPROD_ID(), pro.getName(), pro.getMail() , pro.getPhone()});
        }

        JTable jtable = new JTable(model);
        jtable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        return jtable;
    }




    public static void main(String[] args) {
        //System.out.print(createJTable());
        JProducer =  new ProducerManagement();
        JProducer.pack();
        JProducer.setVisible(true);
        System.exit(0);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
