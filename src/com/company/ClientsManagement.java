package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ClientsManagement extends JDialog {
    private JPanel contentPane;
    private JButton ADD;
    private JButton editButton;
    public static boolean jAddCheck = false;
    public static boolean jEditCheck = false;
    private JButton deleteButton;
    private JButton backButton;
    private JTextField in_search;
    private JButton searchB;
    private JTextField in_name;
    private JTextField in_mail;
    private JTextField in_phone;
    private JTextField in_street;
    private JTextField in_zip;
    private JComboBox day_box;
    private JComboBox mounth_box;
    private JComboBox year_box;
    private JButton saveB;
    private JButton addB;
    private JTable jtable;
    private JScrollPane scroll2;
    private JPanel infoPanel;
    private JPanel editPanel;
    private JLabel nameInfo;
    private JLabel phoneInfo;
    private JLabel mailInfo;
    private JLabel birthInfo;
    private JLabel adressInfo;
    private JLabel zipInfo;
    private JTextField in_pass;
    private JTextField in_house;
    private JButton buttonOK;
    private JButton buttonCancel;
    private static List<ClientService> clients;
    private static List<AddresService> address;
    public static String idClient;
    public static ClientsManagement JClient;
    public static String idClientSelected;
    private static int idClientConverted;
    private static int idClientOnEdit;


    public ClientsManagement() {
        setContentPane(contentPane);
        setModal(true);

        scroll2.getViewport().add(jtable=createJTable());
        infoPanel.setVisible(false);
        editPanel.setVisible(false);

        ADD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearInputs();
                fillAddAreaComboboxes();
                editPanel.setVisible(true);
                addB.setVisible(true);
                saveB.setVisible(false);
                pack();
            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearInputs();
                idClientSelected = jtable.getValueAt(jtable.getSelectedRow(),0).toString();
                idClientConverted = Integer.parseInt(idClientSelected);
                addB.setVisible(false);
                saveB.setVisible(true);
                editPanel.setVisible(true);
                showInfoEdit(idClientConverted); //Apresenta os campos preenchido do employee
                pack(); //Dá rezise da janela ao aparecer a parte de editar do employee


            }
        });

        jtable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                idClientSelected = jtable.getValueAt(jtable.getSelectedRow(),0).toString();
                idClientConverted = Integer.parseInt(idClientSelected);
                System.out.print(idClientConverted);
                showFullDetail(idClientConverted);
                infoPanel.setVisible(true);
                pack();
            }

        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteClient();
                jtable.revalidate();
            }
        });
        addB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    addClient();
                    jtable.revalidate();
                } catch (SQLException | ParseException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        searchB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    searchClients();
                    jtable.revalidate();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();

                }
            }
        });



    }
    private void showFullDetail(int id){
        ClientService client = new ClientService();
        AddresService addres;
        addres=  readAdress(id);

        client.read(id);
        nameInfo.setText("Name: " + client.getName());
        mailInfo.setText("Email: "+ client.getMail());
        phoneInfo.setText("Phone: "+ client.getPhone());
        birthInfo.setText("Birthdate" + client.getBirthdate());
        adressInfo.setText("Address: "+ addres.getStreetname()+ " " + addres.getHousenumber());
        zipInfo.setText("zip: "+ addres.getZip());


    }
    private void clearInputs(){
        List<JTextField> tfList = new ArrayList<JTextField>();

        tfList.add(in_name);
        tfList.add(in_phone);
        tfList.add(in_mail);
        tfList.add(in_house);
        tfList.add(in_pass);
        tfList.add(in_street);
        tfList.add(in_zip);

        for(JTextField tf : tfList){
            tf.setText("");
        }

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

    private void showInfoEdit(int id){
        idClientOnEdit= id; //Guarda o ID do produto que está a ser editado para mais tarde ser usado no update
        ClientService client = new ClientService();

        client.read(id); //lê o produto que tem o id indicado
        in_name.setText(client.getName());
        in_mail.setText(String.valueOf(client.getMail()));
        in_pass.setText(String.valueOf(client.getPassword()));
        //in_street.setText(String.valueOf());
        //in_zip.setText(String.valueOf());
        in_phone.setText(String.valueOf(client.getPhone()));



        String date= String.valueOf(client.getBirthdate());
        String [] dateParts = date.split("-");
        String day = dateParts[2];
        String month = dateParts[1];
        String year = dateParts[0];

        for(int i = 1; i<=31; i++){
            System.out.println("I: " + i + " Day: " + day);
            day_box.addItem(i);
            if(i == Integer.parseInt(day)){
                day_box.setSelectedItem(i);
            }
        }

        for(int i = 1; i<=12; i++){
            mounth_box.addItem(i);
            if(i == Integer.parseInt(month)){
                mounth_box.setSelectedItem(i);
            }
        }

        for(int i = 1940; i<=2021; i++){
            year_box.addItem(i);
            if(i == Integer.parseInt(year)){
                year_box.setSelectedItem(i);
            }
        }

    }
    private void fillAddAreaComboboxes(){

        for(int i = 1; i<=31; i++){
            day_box.addItem(i);
        }

        for(int i = 1; i<=12; i++){
            mounth_box.addItem(i);
        }

        for(int i = 1940; i<=2021; i++){
            year_box.addItem(i);
        }


    }
    private AddresService readAdress(int c_id){
       AddresService cli_addres= new AddresService();

       address=cli_addres.readAll();

        for(AddresService pos: address){
            if(c_id == pos.getC_id()){
                System.out.println(pos.getC_id());
                return pos;
            }
        }
        return null;
    }
    private void searchClients() throws SQLException {


        clients = ClientService.search(in_search.getText());

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Bithdate");
        model.addColumn("Mail");

        for(ClientService client: clients){
            model.addRow(new Object[]{client.getName(), client.getName(), client.getBirthdate(), client.getMail()});
        }

        this.jtable.setModel(model);

    }

    private void deleteClient(){
        int dialogButton = JOptionPane.YES_NO_OPTION;
        ClientService deleteClient = new ClientService();

        int dialogResult = JOptionPane.showConfirmDialog (null, "Would You Like to Delete this Client?","Warning",dialogButton);
        if(dialogResult == JOptionPane.YES_OPTION){
            deleteClient.delete(idClientConverted);
            this.jtable.setModel(updateTableAfterAddDeleteUpdate());
        }
    }
    private void addClient() throws SQLException, ParseException {
        String dateSelected;

        ClientService addClient = new ClientService(); //Para correr a função create() que se encontra o file ProductService


        dateSelected = year_box.getSelectedItem() + "-" + mounth_box.getSelectedItem() + "-" +  day_box.getSelectedItem();
        Date date1 = Date.valueOf(dateSelected);

        addClient.create(in_name.getText(), date1, in_mail.getText(), Integer.parseInt(in_phone.getText()), in_street.getText(), Integer.parseInt(in_zip.getText()), Integer.parseInt(in_house.getText()), in_pass.getText(), idClientOnEdit);

        this.jtable.setModel(updateTableAfterAddDeleteUpdate());

        clearInputs();
        editPanel.setVisible(false);
        pack();

    }
    private DefaultTableModel updateTableAfterAddDeleteUpdate(){
        clients = ClientService.readAll();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Bithdate");
        model.addColumn("Mail");

        for(ClientService client: clients){
            model.addRow(new Object[]{client.getName(), client.getName(), client.getBirthdate(), client.getMail()});
        }

        return model;

    }
    public static void main(String[] args) {

        JClient =  new ClientsManagement();
        JClient.pack();
        JClient.setVisible(true);
        System.exit(0);
    }
}
