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
