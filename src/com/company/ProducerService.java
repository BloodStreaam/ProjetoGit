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



}
