package com.company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddresService {

    private int A_ID;
    private String Streetname;
    private int housenumber;
    private int c_id;
    private int zip;

    public int getA_ID() {
        return A_ID;
    }

    public void setA_ID(int a_ID) {
        A_ID = a_ID;
    }

    public String getStreetname() {
        return Streetname;
    }

    public void setStreetname(String streetname) {
        Streetname = streetname;
    }

    public int getHousenumber() {
        return housenumber;
    }

    public void setHousenumber(int housenumber) {
        this.housenumber = housenumber;
    }

    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }
    public static List<AddresService> readAll(){
        Connection conn = SQLConnection.criarConexao();

        String sqlCommand = "SELECT * FROM ADDRESS";

        List<AddresService> list = new ArrayList<>();

        try {
            PreparedStatement st = conn.prepareStatement(sqlCommand);
            ResultSet rs = st.executeQuery();

            while(rs.next()){
                AddresService address = new AddresService();

                address.setA_ID(rs.getInt("A_ID"));
                if (rs.getString("STREETNAME") != null)  address.setStreetname(rs.getString("STREETNAME"));
                address.setHousenumber(rs.getInt("HOUSENUMBER"));
                address.setC_id(rs.getInt("C_ID"));
                address.setZip(rs.getInt("ZIP"));


                list.add(address);
            }

        } catch (SQLException ex) {
            System.out.println("ERRO: " + ex.getMessage());
        }

        return list;
    }


}
