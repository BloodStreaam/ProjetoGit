package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ProducerManagement extends JDialog {
    private JPanel contentPane;
    private JButton ADD;
    private JButton editButton;
    public static boolean jAddCheck = false;
    public static boolean jEditCheck = false;
    private JButton deleteButton;
    private JButton backButton;
    private JButton searchB;
    private JTextField in_search;
    private JButton viewRequestButton;
    private JTextField name_in;
    private JTextField phone_in;
    private JTextField email_in;
    private JComboBox farmInput;
    private JButton saveButton;
    private JButton addB;
    private JTextField text2;
    private JScrollPane scroll1;
    private JLabel nomeInfo;
    private JLabel emailInfo;
    private JLabel phoneInfo;
    private JPanel infoPanel;
    private JPanel editPanel;
    private JLabel farmInfo;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JScrollPane scrollPane1;
    private JTable jtable;
    private static List<ProducerService> producers;
    public static ProducerManagement JProducer;
    public static String idProducer;
    public static String idProducerSelected;
    private static int idProducerConverted;
    private static int idProducerOnEdit;
    private static List<FarmService> farms;

    public ProducerManagement() {
        setContentPane(contentPane);
        setModal(true);

        scroll1.getViewport().add(jtable=createJTable());
        infoPanel.setVisible(false);
        editPanel.setVisible(false);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        ADD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearInputs();
                editPanel.setVisible(true);
                addB.setVisible(true);
                saveButton.setVisible(false);
                pack();

            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idProducer = jtable.getValueAt(jtable.getSelectedRow(),0).toString();
                jEditCheck = true;
                clearInputs();
                idProducerSelected = jtable.getValueAt(jtable.getSelectedRow(),0).toString();
                idProducerConverted = Integer.parseInt(idProducerSelected);
                addB.setVisible(false);
                saveButton.setVisible(true);
                editPanel.setVisible(true);
                showInfoEdit(idProducerConverted); //Apresenta os campos preenchido do employee
                pack(); //Dá rezise da janela ao aparecer a parte de editar do employee

            }
        });

        jtable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                idProducerSelected = jtable.getValueAt(jtable.getSelectedRow(),0).toString();
                idProducerConverted = Integer.parseInt(idProducerSelected);
                System.out.print(idProducer);
                showFullDetail(idProducerConverted);
                infoPanel.setVisible(true);
                pack();
            }

        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { deleteProducer(); }
        });

        addB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    addProducer();
                } catch (SQLException | ParseException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    updateEmployee();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        searchB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    searchProducer();
                    jtable.revalidate();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();

                }
            }
        });



    }
    private void deleteProducer(){
        int dialogButton = JOptionPane.YES_NO_OPTION;
        ProducerService delete = new ProducerService();

        int dialogResult = JOptionPane.showConfirmDialog (null, "Would You Like to Delete this Producer?","Warning",dialogButton);
        if(dialogResult == JOptionPane.YES_OPTION){
            delete.delete(idProducerConverted);
            scroll1.getViewport().remove(jtable);
            scroll1.getViewport().add(jtable=createJTable());

        }
    }

    private void searchProducer() throws SQLException {

        producers = ProducerService.search(in_search.getText());

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Mail");
        model.addColumn("Phone");

        for (ProducerService prod : producers) {
            model.addRow(new Object[]{prod.getPROD_ID(), prod.getName(), prod.getMail(), prod.getPhone()});
        }

        this.jtable.setModel(model);

    }

    private void showFullDetail(int id){

        ProducerService producer = new ProducerService();
        producer.read(id);
        nomeInfo.setText("Name: " + producer.getName());
        emailInfo.setText("Email: "+ producer.getMail());
        phoneInfo.setText("Phone: "+ producer.getPhone());
        for(FarmService farm: farms){
            if(farm.getIdProducer() == id){
                farmInfo.setText("Farm "+ farm.getName());
            }
        }




    }
    private void showInfoEdit(int id){
        idProducerOnEdit= id; //Guarda o ID do produto que está a ser editado para mais tarde ser usado no update
        ProducerService producer = new ProducerService();

        producer.read(id); //lê o produto que tem o id indicado
        System.out.println(producer.getName());
        name_in.setText(producer.getName());
        email_in.setText(String.valueOf(producer.getMail()));
        phone_in.setText(String.valueOf(producer.getPhone()));


        //Adiciona as quintas a combobox,
        for(FarmService farm: farms){
            farmInput.addItem(farm.getName());
            if(farm.getIdProducer() == id){
                farmInput.setSelectedItem(farm.getName());
            }
        }


    }
    private void clearInputs(){
        List<JTextField> tfList = new ArrayList<JTextField>();

        tfList.add(name_in);
        tfList.add(email_in);
        tfList.add(phone_in);

        for(JTextField tf : tfList){
            tf.setText("");
        }

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
    private void addProducer() throws SQLException, ParseException {

        ProducerService addPro = new ProducerService();

        addPro.create(name_in.getText(), email_in.getText(), Integer.parseInt(phone_in.getText()));

        scroll1.getViewport().remove(jtable);
        scroll1.getViewport().add(jtable=createJTable());

        clearInputs();
        editPanel.setVisible(false);
        pack();

    }

    private void updateEmployee() throws SQLException {
        ProducerService upPro = new ProducerService();

        //Atualiza o produto com o id definido na tabela produtos
        upPro.update(name_in.getText(), email_in.getText(), Integer.parseInt(phone_in.getText()), idProducerOnEdit);
        scroll1.getViewport().remove(jtable);
        scroll1.getViewport().add(jtable=createJTable());
        clearInputs();
        editPanel.setVisible(false);
        pack();

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
