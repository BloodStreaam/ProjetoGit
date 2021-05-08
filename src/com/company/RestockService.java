package com.company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RestockService {
    private int r_id;
    private int e_id;
    private int farm_id;
    private int rd_id;
    private Date r_date;

    public int getR_id() {

        return r_id;
    }

    public void setR_id(int r_id) {
        this.r_id = r_id;
    }

    public int getE_id() {
        return e_id;
    }

    public void setE_id(int e_id) {
        this.e_id = e_id;
    }

    public int getFarm_id() {
        return farm_id;
    }

    public void setFarm_id(int farm_id) {
        this.farm_id = farm_id;
    }

    public int getRd_id() {
        return rd_id;
    }

    public void setRd_int(int rd_id) {
        this.rd_id = rd_id;
    }

    public Date getR_date() {
        return r_date;
    }

    public void setR_date(Date r_date) {
        this.r_date = r_date;
    }

    public static List<RestockService> readAll(){
        Connection conn = SQLConnection.criarConexao();

        String sqlCommand = "SELECT R_ID, E_ID, FARM_ID, R_DATE FROM RESTOCK";

        List<RestockService> list = new ArrayList<>();

        try {
            PreparedStatement st = conn.prepareStatement(sqlCommand);


            ResultSet rs = st.executeQuery();

            while(rs.next()){
                RestockService restock = new RestockService();

                restock.setR_id(rs.getInt("R_ID"));
                restock.setE_id(rs.getInt("E_ID"));
                restock.setFarm_id(rs.getInt("FARM_ID"));
                restock.setR_date(rs.getDate("R_DATE"));

                list.add(restock);
            }

        } catch (SQLException ex) {
            System.out.println("ERRO: " + ex.getMessage());
        }

        return list;
    }



    public void read(int idRestock){
        Connection conn = SQLConnection.criarConexao();

        String sqlCommand = "SELECT R_ID, E_ID, FARM_ID, R_DATE FROM RESTOCK WHERE R_ID = ?";

        try {
            PreparedStatement st = conn.prepareStatement(sqlCommand);
            st.setInt(1, idRestock);


            ResultSet rs = st.executeQuery();

            if(rs.next()){
                this.r_id=(rs.getInt("R_ID"));
                this.e_id=(rs.getInt("E_ID"));
                this.farm_id=(rs.getInt("FARM_ID"));
                this.r_date=(rs.getDate("R_DATE"));

            }
            else{
                System.out.println("ERRO: Não existe Restock com o ID definido ");
            }
        } catch (SQLException ex) {
            System.out.println("ERRO: " + ex.getMessage());
        }

    }
}
