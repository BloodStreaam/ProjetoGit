package com.company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProducerReqService {
    private int ID_REQ;
    private String name;
    private int phone;
    private String mail;
    private int ID_E;

    public int getID_REQ() {
        return ID_REQ;
    }

    public void setID_REQ(int ID_REQ) {
        this.ID_REQ = ID_REQ;
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

    public int getID_E() {
        return ID_E;
    }

    public void setID_E(int ID_E) {
        this.ID_E = ID_E;
    }

    public static List<ProducerReqService> readAll(){
        Connection conn = SQLConnection.criarConexao();
        String sqlCommand = "SELECT * FROM REQ_PRODUCER ORDER BY ID_REQ";
        List<ProducerReqService> list = new ArrayList<>();

        try {
            PreparedStatement st = conn.prepareStatement(sqlCommand);
            ResultSet rs = st.executeQuery();

            while(rs.next()){
                ProducerReqService producer = new ProducerReqService();

                producer.setID_REQ(rs.getInt("ID_REQ"));
                if (rs.getString("NOME") != null) producer.setName(rs.getString("NOME"));
                if (rs.getString("EMAIL") != null) producer.setMail(rs.getString("EMAIL"));
                producer.setID_E(rs.getInt("ID_E"));
                producer.setPhone(rs.getInt("PHONE"));

                list.add(producer);
            }

        } catch (SQLException ex) {
            System.out.println("ERRO: " + ex.getMessage());
        }

        return list;
    }
    public void read(int id){
        Connection conn = SQLConnection.criarConexao();
        String sqlCommand="SELECT * FROM REQ_PRODUCER WHERE ID_REQ = ?";
        try{
            PreparedStatement st = conn.prepareStatement(sqlCommand);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                this.ID_REQ=(rs.getInt("ID_REQ"));
                if(rs.getString("NOME") != null) this.name= (rs.getString("NOME"));
                if (rs.getString("EMAIL") != null) this.mail=(rs.getString("EMAIL"));
                if (rs.getString("PHONE") != null) this.phone=(rs.getInt("PHONE"));
            }else{

                System.out.println("Erro: Não existe Requisito com o id Definido");
            }


        }catch(SQLException ex) {
            System.out.println("ERRO: " + ex.getMessage());
        }




    }
    public void delete(int id){

        Connection conn = SQLConnection.criarConexao();

        String sqlCommand = "DELETE REQ_PRODUCER WHERE ID_REQ = ?";

        try {
            PreparedStatement st = conn.prepareStatement(sqlCommand);
            st.setInt(1, id);

            st.execute();

        } catch (SQLException ex) {
            System.out.println("ERRO: " + ex.getMessage());
        }
    }



}
