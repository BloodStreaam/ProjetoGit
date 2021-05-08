package com.company;

import java.sql.*;
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
    public int loggedEmployeeID;

    public int getLoggedEmployeeID() {
        return loggedEmployeeID;
    }

    public void setLoggedEmployeeID(int loggedEmployeeID) {
        this.loggedEmployeeID = loggedEmployeeID;
    }

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


    public static List<EmployeeService> readAll(){
        Connection conn = SQLConnection.criarConexao();

        String sqlCommand = "SELECT E_ID, NAME, BIRTHDATE, MAIL, PHONE, SALARY, P_ID, ADDRESS, ZIP FROM EMPLOYEE ORDER BY E_ID";

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


    public void delete(int id){

        Connection conn = SQLConnection.criarConexao();

        String sqlCommand = "DELETE EMPLOYEE WHERE E_ID = ?";

        try {
            PreparedStatement st = conn.prepareStatement(sqlCommand);
            st.setInt(1, id);

            st.execute();

        } catch (SQLException ex) {
            System.out.println("ERRO: " + ex.getMessage());
        }
    }

    public static List<EmployeeService> search(int positionid, String employ) throws SQLException {
        Connection conn = SQLConnection.criarConexao();


        String sqlCommand = "SELECT E_ID, NAME, BIRTHDATE, MAIL, PHONE, SALARY, P_ID, ADDRESS, ZIP FROM EMPLOYEE ";
        if(positionid != 0 && employ.isEmpty()){
            sqlCommand+= "WHERE P_ID = ? ";
        }
        if(!employ.isEmpty() && positionid == 0){
            sqlCommand+= "WHERE NAME LIKE ? ";
        }
        if(positionid != 0 && !employ.isEmpty()){
            sqlCommand+="WHERE P_ID = ? AND NAME LIKE ? ";
        }

        sqlCommand+="ORDER BY E_ID";
        System.out.println(sqlCommand);
        List<EmployeeService> list = new ArrayList<>();

        try {

            PreparedStatement st = conn.prepareStatement(sqlCommand);

            if(positionid != 0 && employ.isEmpty()){
                st.setInt(1, positionid);
            }

            if(employ.isEmpty() == false && positionid == 0){
                st.setString(1, "%" + employ + "%");
            }

            if(positionid != 0 && employ.isEmpty() == false){
                st.setInt(1, positionid);
                st.setString(2, employ);
            }

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
                System.out.println("ERRO: Não existe Employee com o ID definido ");
            }
        } catch (SQLException ex) {
            System.out.println("ERRO: " + ex.getMessage());
        }

    }

    public void readEmployeeEmail(String email) throws SQLException {
        Connection conn = SQLConnection.criarConexao();

        String sqlCommand = "SELECT E_ID, NAME, BIRTHDATE, MAIL, PHONE, SALARY, P_ID, ADDRESS, ZIP FROM EMPLOYEE WHERE to_char(MAIL) = ?";

        try {
            PreparedStatement st = conn.prepareStatement(sqlCommand);
            st.setString(1, email);


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
                System.out.println("ERRO: Não existe Employee com o ID definido ");
            }
        } catch (SQLException ex) {
            System.out.println("ERRO: " + ex.getMessage());
        }

    }


    public void create(String name, Date birthdate, String mail, int phone, float salary, int p_id, String address, String zip) throws SQLException {
        Connection conn = SQLConnection.criarConexao();

        String sqlCommand = "INSERT INTO EMPLOYEE COLUMNS (NAME, BIRTHDATE, MAIL, PHONE, SALARY, P_ID, ADDRESS, ZIP) "
                + "VALUES (?, ?, ?, ?, ?, ? ,?, ?)";
        PreparedStatement pst = conn.prepareStatement(sqlCommand);

        pst.setString(1, name);
        pst.setDate(2, birthdate);
        pst.setString(3, mail);
        pst.setInt(4, phone);
        pst.setFloat(5, salary);
        pst.setInt(6, p_id);
        pst.setString(7, address);
        pst.setString(8, zip);
        pst.execute();
    }

    public void update(String name, Date birthdate, String mail, int phone, float salary, int p_id, String address, String zip, int e_id) throws SQLException {
        Connection conn = SQLConnection.criarConexao();

        String sqlCommand = "UPDATE EMPLOYEE SET NAME = ?, BIRTHDATE = ?, MAIL = ?, PHONE = ?, SALARY = ?, P_ID = ?, ADDRESS = ?, ZIP = ? WHERE E_ID = ?";
        PreparedStatement pst = conn.prepareStatement(sqlCommand);

        pst.setString(1, name);
        pst.setDate(2, birthdate);
        pst.setString(3, mail);
        pst.setInt(4, phone);
        pst.setFloat(5, salary);
        pst.setInt(6, p_id);
        pst.setString(7, address);
        pst.setString(8, zip);
        pst.setInt(9, e_id);
        pst.execute();
    }


}

