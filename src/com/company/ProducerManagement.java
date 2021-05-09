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
    private JScrollPane scroll1;
    private JLabel nomeInfo;
    private JLabel emailInfo;
    private JLabel phoneInfo;
    private JPanel infoPanel;
    private JPanel editPanel;
    private JScrollPane jscrollRequests;
    private JButton viewProducersButton;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JScrollPane scrollPane1;
    private JTable jtable;
    private JTable jtableRequests;
    private static List<ProducerService> producers;
    private static List<ProducerReqService> producerRequests;
    private static List<FarmService> farms;
    public static ProducerManagement JProducer;
    public static String idProducer;
    public static String idProducerSelected;
    private static int idProducerConverted;
    private static int idProducerOnEdit;
    public static String idProducerReqSelected;
    private static int idProducerReqConverted;

    public ProducerManagement() {
        setContentPane(contentPane);
        setModal(true);

        jscrollRequests.getViewport().add(jtableRequests=createJTableRequests());
        scroll1.getViewport().add(jtable=createJTable());

        viewProducersButton.setVisible(false);
        jscrollRequests.setVisible(false);
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
                showFullDetail(idProducerConverted);
                infoPanel.setVisible(true);
                pack();
            }

        });

        jtableRequests.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                idProducerReqSelected = jtableRequests.getValueAt(jtableRequests.getSelectedRow(),0).toString();
                idProducerReqConverted = Integer.parseInt(idProducerReqSelected);
                try {
                    addDeleteProducerReq();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                pack();
            }

        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { deleteProducer();
            jtable.revalidate();}
        });

        addB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    addProducer();
                    jtable.revalidate();
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
                    jtable.revalidate();
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


        viewRequestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jscrollRequests.setVisible(true);
                jtableRequests.setVisible(true);
                scroll1.setVisible(false);
                viewRequestButton.setVisible(false);
                viewProducersButton.setVisible(true);
                ADD.setVisible(false);
                editButton.setVisible(false);
                deleteButton.setVisible(false);
                pack();

            }
        });
        viewProducersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jscrollRequests.setVisible(false);
                jtableRequests.setVisible(false);
                scroll1.setVisible(true);
                jtable.setVisible(true);
                viewProducersButton.setVisible(false);
                viewRequestButton.setVisible(true);
                ADD.setVisible(true);
                editButton.setVisible(true);
                deleteButton.setVisible(true);
                pack();
            }
        });
    }



    private void deleteProducer(){
        int dialogButton = JOptionPane.YES_NO_OPTION;
        ProducerService delete = new ProducerService();

        int dialogResult = JOptionPane.showConfirmDialog (null, "Would You Like to Delete this Producer?","Warning",dialogButton);
        if(dialogResult == JOptionPane.YES_OPTION){
            delete.delete(idProducerConverted);
            this.jtable.setModel(updateTableAfterAddDeleteUpdate());


        }
    }
    private void addDeleteProducerReq() throws SQLException {
        int dialogButton = JOptionPane.YES_NO_OPTION;
        ProducerReqService ReqAddDelete = new ProducerReqService();
        ProducerService producer = new ProducerService();

        int dialogResult = JOptionPane.showConfirmDialog (null, "Would You Like Add this Producer?","Confirmation Window",dialogButton);
        if(dialogResult == JOptionPane.YES_OPTION){
            ReqAddDelete.read(idProducerReqConverted);
            producer.create(ReqAddDelete.getName(),ReqAddDelete.getMail(), ReqAddDelete.getPhone());
            ReqAddDelete.delete(idProducerReqConverted);
            this.jtableRequests.setModel(updateTableAfterAddDeleteUpdateRequests());
            this.jtable.setModel(updateTableAfterAddDeleteUpdate());
        if(dialogResult == JOptionPane.NO_OPTION){
            ReqAddDelete.delete(idProducerReqConverted);
            this.jtableRequests.setModel(updateTableAfterAddDeleteUpdateRequests());
        }
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




    }
    private void showInfoEdit(int id){
        idProducerOnEdit= id; //Guarda o ID do produto que está a ser editado para mais tarde ser usado no update
        ProducerService producer = new ProducerService();


        producer.read(id); //lê o produto que tem o id indicado
        System.out.println(producer.getName());
        name_in.setText(producer.getName());
        email_in.setText(String.valueOf(producer.getMail()));
        phone_in.setText(String.valueOf(producer.getPhone()));




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

    private static JTable createJTableRequests(){
        producerRequests = ProducerReqService.readAll();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Mail");
        model.addColumn("Phone");
        model.addColumn("Employee ID");

        for(ProducerReqService pro : producerRequests) {
            model.addRow(new Object[] { pro.getID_REQ(), pro.getName(), pro.getMail() , pro.getPhone(), pro.getID_E()});
        }

        JTable jtable = new JTable(model);
        jtable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        return jtable;
    }

    private void addProducer() throws SQLException, ParseException {

        ProducerService addPro = new ProducerService();

        addPro.create(name_in.getText(), email_in.getText(), Integer.parseInt(phone_in.getText()));

        this.jtable.setModel(updateTableAfterAddDeleteUpdate());

        clearInputs();
        editPanel.setVisible(false);
        pack();

    }

    private DefaultTableModel updateTableAfterAddDeleteUpdate(){
        producers = ProducerService.readAll();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Mail");
        model.addColumn("Phone");


        for (ProducerService producer : producers) {
            model.addRow(new Object[]{producer.getPROD_ID(), producer.getName(), producer.getMail(), producer.getPhone()});
        }


        return model;
    }

    private DefaultTableModel updateTableAfterAddDeleteUpdateRequests(){
        producerRequests = ProducerReqService.readAll();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Mail");
        model.addColumn("Phone");
        model.addColumn("Employee ID");


        for (ProducerReqService prodRequest : producerRequests) {
            model.addRow(new Object[]{prodRequest.getID_REQ(), prodRequest.getName(), prodRequest.getMail(), prodRequest.getPhone(), prodRequest.getID_E()});
        }


        return model;
    }

    private void updateEmployee() throws SQLException {
        ProducerService upPro = new ProducerService();

        //Atualiza o produto com o id definido na tabela produtos
        upPro.update(name_in.getText(), email_in.getText(), Integer.parseInt(phone_in.getText()), idProducerOnEdit);

        this.jtable.setModel(updateTableAfterAddDeleteUpdate());
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
