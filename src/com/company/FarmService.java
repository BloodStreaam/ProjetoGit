package com.company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FarmService {
    private int idFarm;
    private int idProducer;
    private String name;
    private String address;
    private String f_size;

    public int getIdFarm() {return idFarm;}
    public void setIdFarm(int idFarm) {this.idFarm = idFarm;}

    public int getIdProducer() {return idProducer;}
    public void setIdProducer(int idProducer) {this.idProducer = idProducer;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getAddress() {return address;}
    public void setAddress(String address) {this.address = address;}

    public String getF_size() {return f_size;}
    public void setF_size(String f_size) {this.f_size = f_size;}

    public static List<FarmService> readAllFarms(){
        Connection conn = SQLConnection.criarConexao();

        String sqlCommand = "SELECT FARM_ID, PROD_ID, NAME, ADDRESS, F_SIZE FROM FARM";

        List<FarmService> listType = new ArrayList<>();

        try {
            PreparedStatement st = conn.prepareStatement(sqlCommand);


            ResultSet rs = st.executeQuery();

            while(rs.next()){
                FarmService farms = new FarmService();

                farms.setIdFarm(rs.getInt("FARM_ID"));
                farms.setIdProducer(rs.getInt("PROD_ID"));
                if (rs.getString("NAME") != null) farms.setName(rs.getString("NAME"));
                if (rs.getString("ADDRESS") != null) farms.setAddress(rs.getString("ADDRESS"));
                if (rs.getString("F_SIZE") != null) farms.setF_size(rs.getString("F_SIZE"));
                listType.add(farms);
            }

        } catch (SQLException ex) {
            System.out.println("ERRO: " + ex.getMessage());
        }

        return listType;
    }

    public void readFarm(int idFarm){
        Connection conn = SQLConnection.criarConexao();

        String sqlCommand = "SELECT FARM_ID, PROD_ID, NAME, ADDRESS, F_SIZE FROM FARM WHERE FARM_ID = ?";

        try {
            PreparedStatement st = conn.prepareStatement(sqlCommand);
            st.setInt(1, idFarm);


            ResultSet rs = st.executeQuery();

            if(rs.next()){
                this.idFarm=(rs.getInt("FARM_ID"));
                this.idProducer=(rs.getInt("PROD_ID"));
                if (rs.getString("NAME") != null) this.name= (rs.getString("NAME"));
                if (rs.getString("ADDRESS") != null) this.address= (rs.getString("ADDRESS"));
                if (rs.getString("F_SIZE") != null) this.f_size= (rs.getString("F_SIZE"));
            }
            else{
                System.out.println("ERRO: Não existe Tipo com o ID definido ");
            }
        } catch (SQLException ex) {
            System.out.println("ERRO: " + ex.getMessage());
        }

    }
}
