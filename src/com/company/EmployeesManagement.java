package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
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
    private JTextField textField1;
    public static EmployeesManagement JEmployees;
    public static boolean jAddCheck = false;
    public static boolean jEditCheck = false;
    private static List<EmployeeService> employees;
    private static List<PositionService> positions;
    private JTable jtable;
    public static String idEmployee;


    public EmployeesManagement() {
        setContentPane(contentPane);
        setModal(true);

        scrollPane1.getViewport().add(jtable=createJTable());

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jAddCheck = true;
            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idEmployee = jtable.getValueAt(jtable.getSelectedRow(),0).toString();
                jEditCheck = true;
            }
        });
        jtable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                idEmployee = jtable.getValueAt(jtable.getSelectedRow(),0).toString();
                System.out.print(idEmployee);
            }

        });
<<<<<<< Updated upstream
=======
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteEmployee();
            }
        });

        addButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    addEmployee();
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


>>>>>>> Stashed changes
    }
    private void showEditInfoEmployee(){
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





       // for(EmployeeService emp : employees)

            /*values.add(new String[] { String.valueOf(emp.getE_id()),  emp.getName(),  String.valueOf(emp.getBirthdate()),
                    emp.getMail(),  String.valueOf(emp.getPhone()),  String.valueOf(emp.getSalary()), emp.getAddress(), emp.getZip() });
*/

        JTable jtable = new JTable(model);
        jtable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        return jtable;
    }

    public static void main(String[] args) {
        System.out.print(createJTable());
        JEmployees =  new EmployeesManagement();
        JEmployees.pack();
        JEmployees.setVisible(true);
        System.exit(0);


    }


}
