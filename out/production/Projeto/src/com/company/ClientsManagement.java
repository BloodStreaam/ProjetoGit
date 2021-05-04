package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;

public class ClientsManagement extends JDialog {
    private JPanel contentPane;
    private JButton ADD;
    private JButton editButton;
    public static boolean jAddCheck = false;
    public static boolean jEditCheck = false;
    private JButton deleteButton;
    private JButton backButton;
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
    private JTextField text2;
    private JTable jtable;
    private JScrollPane scroll2;
    private JButton buttonOK;
    private JButton buttonCancel;
    private static List<ClientService> clients;
    public static String idClient;
    public static ClientsManagement JClient;


    public ClientsManagement() {
        setContentPane(contentPane);
        setModal(true);

        scroll2.getViewport().add(jtable=createJTable());





        ADD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jAddCheck = true;


            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idClient = jtable.getValueAt(jtable.getSelectedRow(),0).toString();
                jEditCheck = true;



            }
        });

        jtable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                idClient = jtable.getValueAt(jtable.getSelectedRow(),0).toString();
                System.out.print(idClient);

            }

        });




    }

    private static JTable createJTable(){
        clients = ClientService.readAll();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Bithdate");
        model.addColumn("Mail");

        for(ClientService cli : clients) {
            model.addRow(new Object[] { cli.getC_id(), cli.getName(), cli.getBirthdate() , cli.getMail()});
        }

        JTable jtable = new JTable(model);
        jtable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        return jtable;
    }

    public static void main(String[] args) {

        JClient =  new ClientsManagement();
        JClient.pack();
        JClient.setVisible(true);
        System.exit(0);
    }
}
