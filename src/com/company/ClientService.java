package com.company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientService {
    //variaveis
    private int c_id;
    private int o_id;
    private String name;
    private Date Birthdate;
    private String mail;

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public int getC_id() {return c_id;}
    public void setC_id(int c_id) {this.c_id = c_id;}

    public Date getBirthdate() {return Birthdate;}
    public void setBirthdate(Date birthdate) {Birthdate = birthdate;}

    public int getO_id() {return o_id;}
    public void setO_id(int o_id) {this.o_id = o_id;}

    public String getMail() {return mail;}
    public void setMail(String mail) {this.mail = mail;}

    public static List<ClientService> readAll(){
        Connection conn = SQLConnection.criarConexao();

        String sqlCommand = "SELECT C_ID, O_ID, A_ID, NAME, BIRTHDATE, MAIL FROM CLIENT";

        List<ClientService> list = new ArrayList<>();

        try {
            PreparedStatement st = conn.prepareStatement(sqlCommand);


            ResultSet rs = st.executeQuery();

            while(rs.next()){
                ClientService client = new ClientService();

                client.setC_id(rs.getInt("C_ID"));
                client.setO_id(rs.getInt("O_ID"));
                if (rs.getString("NAME") != null) client.setName(rs.getString("NAME"));
                if (rs.getDate("BIRTHDATE") != null) client.setBirthdate(rs.getDate("BIRTHDATE"));
                if (rs.getString("MAIL") != null) client.setMail(rs.getString("MAIL"));
                //

                list.add(client);
            }

        } catch (SQLException ex) {
            System.out.println("ERRO: " + ex.getMessage());
        }

        return list;
    }

}
