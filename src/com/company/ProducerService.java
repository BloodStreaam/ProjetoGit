package com.company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProducerService {

    //variaveis
    private int PROD_ID;
    private String name;
    private int phone;
    private String mail;


    public int getPROD_ID() {
        return PROD_ID;
    }
    public void setPROD_ID(int PROD_ID) {
        this.PROD_ID = PROD_ID;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getPhone() {
        return phone;
    }
    public void setPhone(int phone) {
        this.phone = phone;
    }
    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }

    public static List<ProducerService> readAll(){
        Connection conn = SQLConnection.criarConexao();
        String sqlCommand = "SELECT * FROM PRODUCER";
        List<ProducerService> list = new ArrayList<>();

        try {
            PreparedStatement st = conn.prepareStatement(sqlCommand);
            ResultSet rs = st.executeQuery();

            while(rs.next()){
                ProducerService producer = new ProducerService();

                producer.setPROD_ID(rs.getInt("PROD_ID"));
                if (rs.getString("NAME") != null) producer.setName(rs.getString("NAME"));
                if (rs.getString("MAIL") != null) producer.setMail(rs.getString("MAIL"));
                producer.setPhone(rs.getInt("PHONE"));
                list.add(producer);
            }

        } catch (SQLException ex) {
            System.out.println("ERRO: " + ex.getMessage());
        }

        return list;
    }


    public void read(int idEmployee){
        Connection conn = SQLConnection.criarConexao();
        String sqlCommand="SELECT * FROM PRODUCER WHERE PROD_ID= ?";
        try{
            PreparedStatement st = conn.prepareStatement(sqlCommand);
            st.setInt(1, idEmployee);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                this.PROD_ID=(rs.getInt("PROD_ID"));
                if(rs.getString("NAME") != null) this.name= (rs.getString("NAME"));
                if (rs.getString("MAIL") != null) this.mail=(rs.getString("MAIL"));
                if (rs.getString("PHONE") != null) this.phone=(rs.getInt("PHONE"));
            }else{

                System.out.println("Erro: Não existe Producer com o id Definido");
            }


        }catch(SQLException ex) {
            System.out.println("ERRO: " + ex.getMessage());
        }




    }
    public void create(String name, String mail, int phone) throws SQLException {

        Connection conn = SQLConnection.criarConexao();

        String sqlCommand = "INSERT INTO PRODUCER COLUMNS (NAME, MAIL, PHONE) "
                + "VALUES (?, ?, ?)";
        PreparedStatement pst = conn.prepareStatement(sqlCommand);

        pst.setString(1, name);
        pst.setString(2, mail);
        pst.setInt(3, phone);
        pst.execute();
    }

    public void update(String name, String mail, int phone, int prod_id ) throws SQLException {
        Connection conn = SQLConnection.criarConexao();

        String sqlCommand = "UPDATE PRODUCER SET NAME = ?, MAIL = ?, PHONE = ? WHERE PROD_ID = ?";
        PreparedStatement pst = conn.prepareStatement(sqlCommand);


        pst.setString(1, name);
        pst.setString(2, mail);
        pst.setInt(3, phone);
        pst.setInt(4, prod_id);
        pst.execute();
    }
    public void delete(int id){

        Connection conn = SQLConnection.criarConexao();

        String sqlCommand = "DELETE PRODUCER WHERE PROD_ID = ?";

        try {
            PreparedStatement st = conn.prepareStatement(sqlCommand);
            st.setInt(1, id);
            st.execute();

        } catch (SQLException ex) {
            System.out.println("ERRO: " + ex.getMessage());
        }
    }




}
