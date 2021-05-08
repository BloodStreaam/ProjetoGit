package com.company;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.List;

public class EmployeesManagement extends JDialog {
    private JPanel contentPane;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton backButton;
    private JScrollPane scrollPane1;
    private JTextField nameField1;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField salaryField;
    private JTextField addressField;
    private JTextField zipField;
    private JComboBox positionBox;
    private JComboBox dayBox;
    private JComboBox monthBox;
    private JComboBox yearBox;
    private JLabel nomeInfo;
    private JLabel emailInfo;
    private JLabel phoneInfo;
    private JLabel salaryInfo;
    private JLabel addressInfo;
    private JLabel zipInfo;
    private JLabel birthdateInfo;
    private JLabel positionInfo;
    private JComboBox positionSearchBox;
    private JTextField searchInput;
    private JButton searchButton;
    private JButton saveButton;
    private JButton addButton1;
    private JPanel infoPanel;
    private JPanel editPanel;
    private JButton xButton;
    public static EmployeesManagement JEmployees;
    public static boolean jAddCheck = false;
    public static boolean jEditCheck = false;
    private static List<EmployeeService> employees;
    private static List<PositionService> positions;
    private JTable jtable;
    private static String idEmployee;
    public static String idEmployeeSelected;
    private static int idEmployeeConverted;
    private static int idEmployeeOnEdit;



    public EmployeesManagement() {
        setContentPane(contentPane);
        setModal(true);

        scrollPane1.getViewport().add(jtable=createJTable());
        fillSearchCombobox();
        editPanel.setVisible(false);
        infoPanel.setVisible(false);

        for(EmployeeService emp : employees)
            System.out.println("ID " + emp.getName() + " chama-se " + emp.getMail() + " e mora em " + emp.getAddress());



        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearInputs();
                fillAddAreaComboboxes();
                editPanel.setVisible(true);
                addButton1.setVisible(true);
                saveButton.setVisible(false);
                pack();

            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearInputs();
                idEmployeeSelected = jtable.getValueAt(jtable.getSelectedRow(),0).toString();
                idEmployeeConverted = Integer.parseInt(idEmployeeSelected);
                addButton1.setVisible(false);
                saveButton.setVisible(true);
                editPanel.setVisible(true);
                showInfoEdit(idEmployeeConverted); //Apresenta os campos preenchido do employee
                pack(); //Dá rezise da janela ao aparecer a parte de editar do employee


            }
        });


        jtable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                idEmployeeSelected = jtable.getValueAt(jtable.getSelectedRow(),0).toString();
                idEmployeeConverted = Integer.parseInt(idEmployeeSelected);
                System.out.println(idEmployeeConverted);
                showFullDetail(idEmployeeConverted);
                infoPanel.setVisible(true);
                pack();



            }

        });


        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteEmployee();
                jtable.revalidate();
            }
        });

        addButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    addEmployee();
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
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    searchEmployee();
                    jtable.revalidate();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }

    private void showFullDetail(int idEmployee){
        EmployeeService employee = new EmployeeService();
        PositionService position = new PositionService();


        employee.read(idEmployee);
        position.readPosition(employee.getPosition_id());

        nomeInfo.setText("Name: " + employee.getName());
        emailInfo.setText("Email: " + employee.getMail());
        phoneInfo.setText("Phone: " + employee.getPhone());
        salaryInfo.setText("Salary: " + employee.getSalary());
        addressInfo.setText("Address: " + employee.getAddress());
        zipInfo.setText("Zip: " + employee.getZip());
        positionInfo.setText("Position: " + position.getPosition());
        birthdateInfo.setText("Birthdate: " + employee.getBirthdate());
    }

    private void showInfoEdit(int idEmployee){
        idEmployeeOnEdit = idEmployee; //Guarda o ID do produto que está a ser editado para mais tarde ser usado no update
        EmployeeService employee = new EmployeeService(); //Guarda o valor do produto selecionado

        employee.read(idEmployee); //lê o produto que tem o id indicado
        positions = PositionService.readAll(); //lê todos os tipos que estão na tabela Type
        System.out.println(employee.getName());
        nameField1.setText(employee.getName());
        emailField.setText(String.valueOf(employee.getMail()));
        phoneField.setText(String.valueOf(employee.getPhone()));
        salaryField.setText(String.valueOf(employee.getSalary()));
        addressField.setText(employee.getAddress());
        zipField.setText(String.valueOf(employee.getZip()));

        //Adiciona os tipos a combo box
        for (PositionService position: positions) {
            positionBox.addItem(position.getPosition());
            //Seleciona o tipo baseado no id que o produto tem
            if(position.getP_ID() == employee.getPosition_id()){
                positionBox.setSelectedItem(position.getPosition());
            }
        }
        String date= String.valueOf(employee.getBirthdate());
        String [] dateParts = date.split("-");
        String day = dateParts[2];
        String month = dateParts[1];
        String year = dateParts[0];

        for(int i = 1; i<=31; i++){
            System.out.println("I: " + i + " Day: " + day);
            dayBox.addItem(i);
            if(i == Integer.parseInt(day)){
                dayBox.setSelectedItem(i);
            }
        }

        for(int i = 1; i<=12; i++){
            monthBox.addItem(i);
            if(i == Integer.parseInt(month)){
                monthBox.setSelectedItem(i);
            }
        }

        for(int i = 1940; i<=2021; i++){
            yearBox.addItem(i);
            if(i == Integer.parseInt(year)){
                yearBox.setSelectedItem(i);
            }
        }



    }

    private void fillAddAreaComboboxes(){
        positions = PositionService.readAll(); //lê todos os tipos que estão na tabela Type
         //lê todas as quintas que estão na tabela Farms

        for (PositionService position : positions) {
            positionBox.addItem(position.getPosition());
        }

        for(int i = 1; i<=31; i++){
            dayBox.addItem(i);
        }

        for(int i = 1; i<=12; i++){
            monthBox.addItem(i);
        }

        for(int i = 1940; i<=2021; i++){
            yearBox.addItem(i);
        }


    }

    private void fillSearchCombobox(){
        positions = PositionService.readAll(); //lê todos os tipos que estão na tabela Type

        positionSearchBox.addItem("No Filter");
        for (PositionService position : positions) {
            positionSearchBox.addItem(position.getPosition());
        }
    }

    private void clearInputs(){
        List<JTextField> tfList = new ArrayList<JTextField>();
        List<JComboBox> cbList = new ArrayList<JComboBox>();


        tfList.add(nameField1);
        tfList.add(emailField);
        tfList.add(phoneField);
        tfList.add(salaryField);
        tfList.add(addressField);
        tfList.add(zipField);

        cbList.add(positionBox);
        cbList.add(dayBox);
        cbList.add(monthBox);
        cbList.add(yearBox);

        for(JTextField tf : tfList){
            tf.setText("");
        }

        for(JComboBox cb : cbList){
            cb.removeAllItems();
        }

        // add to list
    }

    private void deleteEmployee(){
        int dialogButton = JOptionPane.YES_NO_OPTION;
        EmployeeService deleteEmp = new EmployeeService();

        int dialogResult = JOptionPane.showConfirmDialog (null, "Would You Like to Delete this Employee?","Warning",dialogButton);
        if(dialogResult == JOptionPane.YES_OPTION){
            deleteEmp.delete(idEmployeeConverted);
            this.jtable.setModel(updateTableAfterAddDeleteUpdate());
        }
    }

    private void addEmployee() throws SQLException, ParseException {
        int position_id = 0;
        String dateSelected;

        EmployeeService addEmp = new EmployeeService(); //Para correr a função create() que se encontra o file ProductService
        positions = PositionService.readAll(); //Lista de Todos os tipos

        for (PositionService position : positions) {
            if(position.getPosition().equals(positionBox.getSelectedItem())){
                position_id = position.getP_ID();
                break;
            }
        }

        dateSelected = yearBox.getSelectedItem() + "-" + monthBox.getSelectedItem() + "-" +  dayBox.getSelectedItem();
        Date date1 = Date.valueOf(dateSelected);

        addEmp.create(nameField1.getText(), date1, emailField.getText(), Integer.parseInt(phoneField.getText()), Float.parseFloat(salaryField.getText()),position_id, addressField.getText(), zipField.getText());

        this.jtable.setModel(updateTableAfterAddDeleteUpdate());

        clearInputs();
        editPanel.setVisible(false);
        pack();

    }

    private void updateEmployee() throws SQLException {
        int position_id = 0; //Guarda o id do tipo de produto
        String dateSelected;

        EmployeeService updateEmployee = new EmployeeService(); //Para correr a função update() que se encontra o file ProductService
        positions = PositionService.readAll(); //Lista de Todos os tipos


        //Corre um ciclo para vericar se o tipo selecionada na combo box está na lista de tipos e atribui o id ao type_id
        for (PositionService position : positions) {
            if(position.getPosition().equals(positionBox.getSelectedItem())){
                position_id = position.getP_ID();
                break;
            }
        }

        dateSelected = yearBox.getSelectedItem() + "-" + monthBox.getSelectedItem() + "-" +  dayBox.getSelectedItem();
        Date date1 = Date.valueOf(dateSelected);

        //Atualiza o produto com o id definido na tabela produtos
        updateEmployee.update(nameField1.getText(), date1, emailField.getText(), Integer.parseInt(phoneField.getText()), Float.parseFloat(salaryField.getText()),position_id, addressField.getText(), zipField.getText(), idEmployeeOnEdit);
        this.jtable.setModel(updateTableAfterAddDeleteUpdate());
        clearInputs();
        editPanel.setVisible(false);
        pack();

    }

    private void searchEmployee() throws SQLException {
        int position_id = 0;
        String employeePosition = "";

        positions = PositionService.readAll(); //lê todos os tipos que estão na tabela Type

        for (PositionService position : positions) {
            if(position.getPosition().equals(positionSearchBox.getSelectedItem())){
                position_id = position.getP_ID();
            }
        }

        employees = EmployeeService.search(position_id,searchInput.getText());

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Position");
        model.addColumn("Mail");


        for(EmployeeService emp : employees) {
            for(PositionService pos: positions){
                if(emp.getPosition_id() == pos.getP_ID()){
                    employeePosition = pos.getPosition();
                }
            }
            model.addRow(new Object[] {emp.getE_id(), emp.getName(), employeePosition , emp.getMail()});
        }

        this.jtable.setModel(model);

    }

    private DefaultTableModel updateTableAfterAddDeleteUpdate(){
        employees = EmployeeService.readAll();
        positions = PositionService.readAll();
        String employeePosition = "";
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Position");
        model.addColumn("Mail");



        for(EmployeeService emp : employees) {
            for(PositionService pos: positions){
                if(emp.getPosition_id() == pos.getP_ID()){
                    employeePosition = pos.getPosition();
                }
            }
            model.addRow(new Object[] {emp.getE_id(), emp.getName(), employeePosition , emp.getMail()});
        }

        return model;

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    private static JTable createJTable(){
        employees = EmployeeService.readAll();
        positions = PositionService.readAll();
        String employeePosition = "";
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Position");
        model.addColumn("Mail");



        for(EmployeeService emp : employees) {
            for(PositionService pos: positions){
                if(emp.getPosition_id() == pos.getP_ID()){
                    employeePosition = pos.getPosition();
                }
            }
            model.addRow(new Object[] {emp.getE_id(), emp.getName(), employeePosition , emp.getMail()});
        }

        JTable jtable = new JTable(model);
        jtable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        return jtable;
    }





    public static void main(String[] args) {

        JEmployees =  new EmployeesManagement();
        JEmployees.pack();
        JEmployees.setVisible(true);
        System.exit(0);


    }


}
