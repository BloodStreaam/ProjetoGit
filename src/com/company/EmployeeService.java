package com.company;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class EmployeeService {
    private int e_id;
    private String address;
    private String zip;
    private String name;
    private Date birthdate;
    private String mail;
    private int phone;
    private float salary;
    private int position_id;
    private int id_position;
    private String position;


    //----ID da table Employee_Position----///
    public int getId_position() {return id_position;}
    public void setId_position(int id_position) {this.id_position = id_position;}

    //----Position da table Employee_position-----//
    public String getPosition() {return position;}
    public void setPosition(String position) {this.position = position;}

    public int getE_id() {return e_id;}
    public void setIdEmployee(int e_id) {
        this.e_id = e_id;
    }

    public String getName() {    return name; }
    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthdate() {return birthdate;}
    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getMail() {return mail;}
    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getPhone() {return phone;}
    public void setPhone(int phone) {
        this.phone = phone;
    }

    public float getSalary() {return salary;}
    public void setSalary(float salary) {
        this.salary = salary;
    }

    public int getPosition_id() {return position_id;}
    public void setPosition_id(int position_id) {
        this.position_id = position_id;
    }

    public String getAddress() {return address;}
    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {return zip;}
    public void setZip(String zip) {
        this.zip = zip;
    }


/* public void read(int e_id){
        Connection conn = SQLConnection.criarConexao();

        String sqlCommand = "SELECT (E_ID, A_ID, NAME, BIRTHDATE, MAIL, PHONE, SALARY, POSITION, P_ID) FROM EMPLOYEE";

        try {
            PreparedStatement st = conn.prepareStatement(sqlCommand);
            st.setInt(1, e_id);


            ResultSet rs = st.executeQuery();

            if(rs.next()){
                this.e_id = idCliente;
                if (rs.getString("NOME") != null) this.nome = rs.getString("NOME");
                else this.nome = "";
                //
                if (rs.getString("MORADA") != null) this.morada = rs.getString("MORADA");
                else this.morada = "";
                if (rs.getString("CPOSTAL") != null) this.cpostal = rs.getString("CPOSTAL");
                else this.cpostal = "";
            }
            else{
                System.out.println("ERRO: Não existe Cliente com o ID definido ");
            }
        } catch (SQLException ex) {
            System.out.println("ERRO: " + ex.getMessage());
        }
    }*/

    public static List<EmployeeService> readAll(){
        Connection conn = SQLConnection.criarConexao();

        String sqlCommand = "SELECT E_ID, NAME, BIRTHDATE, MAIL, PHONE, SALARY, P_ID, ADDRESS, ZIP FROM EMPLOYEE";

        List<EmployeeService> list = new ArrayList<>();

        try {
            PreparedStatement st = conn.prepareStatement(sqlCommand);


            ResultSet rs = st.executeQuery();

            while(rs.next()){
               EmployeeService employee = new EmployeeService();

                employee.setIdEmployee(rs.getInt("E_ID"));
                if (rs.getString("NAME") != null) employee.setName(rs.getString("NAME"));
                if (rs.getDate("BIRTHDATE") != null) employee.setBirthdate(rs.getDate("BIRTHDATE"));
                if (rs.getString("MAIL") != null) employee.setMail(rs.getString("MAIL"));
                //
                employee.setPhone(rs.getInt("PHONE"));
                //
                employee.setSalary(rs.getFloat("SALARY"));
                employee.setPosition_id(rs.getInt("P_ID"));
                if (rs.getString("ADDRESS") != null) employee.setAddress(rs.getString("ADDRESS"));
                if (rs.getString("ZIP") != null) employee.setZip(rs.getString("ZIP"));
                list.add(employee);
            }

        } catch (SQLException ex) {
            System.out.println("ERRO: " + ex.getMessage());
        }

        return list;
    }

    public static List<EmployeeService> readAllPositions(){
        Connection conn = SQLConnection.criarConexao();

        String sqlCommand = "SELECT P_ID, POSITION, START_DATE, END_DATE FROM EMPLOYEE_POSITION";

        List<EmployeeService> list = new ArrayList<>();

        try {
            PreparedStatement st = conn.prepareStatement(sqlCommand);
            ResultSet rs = st.executeQuery();

            while(rs.next()){
                EmployeeService positions = new EmployeeService();

                positions.setId_position(rs.getInt("P_ID"));
                if (rs.getString("POSITION") != null) positions.setPosition(rs.getString("POSITION"));
                list.add(positions);
            }

        } catch (SQLException ex) {
            System.out.println("ERRO: " + ex.getMessage());
        }

        return list;
    }


    public void delete(int id){
        // PreparedStatement
        Connection conn = SQLConnection.criarConexao();

        String sqlCommand = "DELETE employee WHERE ID = 'id'";

        try {
            PreparedStatement st = conn.prepareStatement(sqlCommand);
            st.setInt(1, id);

            st.execute();

        } catch (SQLException ex) {
            System.out.println("ERRO: " + ex.getMessage());
        }
    }

    public void read(int idEmployee){
        Connection conn = SQLConnection.criarConexao();

        String sqlCommand = "SELECT E_ID, NAME, BIRTHDATE, MAIL, PHONE, SALARY, P_ID, ADDRESS, ZIP FROM EMPLOYEE WHERE E_ID = ?";

        try {
            PreparedStatement st = conn.prepareStatement(sqlCommand);
            st.setInt(1, idEmployee);


            ResultSet rs = st.executeQuery();

            if(rs.next()){
                this.e_id=(rs.getInt("E_ID"));
                if (rs.getString("NAME") != null) this.name= (rs.getString("NAME"));
                if (rs.getDate("BIRTHDATE") != null) this.birthdate= (rs.getDate("BIRTHDATE"));
                if (rs.getString("MAIL") != null) this.mail=(rs.getString("MAIL"));
                //
                this.phone=(rs.getInt("PHONE"));
                //
               this.salary=(rs.getFloat("SALARY"));
                this.position_id=(rs.getInt("P_ID"));
                if (rs.getString("ADDRESS") != null) this.address=(rs.getString("ADDRESS"));
                if (rs.getString("ZIP") != null) this.zip= (rs.getString("ZIP"));

            }
            else{
                System.out.println("ERRO: Não existe Cliente com o ID definido ");
            }
        } catch (SQLException ex) {
            System.out.println("ERRO: " + ex.getMessage());
        }

    }



}

