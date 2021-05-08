package com.company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RestockDetailsService {

    private int rd_id;
    private int quantity;
    private float price;
    private int p_id;
    private int r_id;
    private int req_id;

    public int getP_id() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public int getR_id() {
        return r_id;
    }

    public void setR_id(int r_id) {
        this.r_id = r_id;
    }

    public int getReq_id() {
        return req_id;
    }

    public void setReq_id(int req_id) {
        this.req_id = req_id;
    }

    public int getRd_id() {
        return rd_id;
    }

    public void setRd_id(int rd_id) {
        this.rd_id = rd_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public static List<RestockDetailsService> readAll(){
        Connection conn = SQLConnection.criarConexao();

        String sqlCommand = "SELECT RD_ID, QUANTITY, PRICE, P_ID, R_ID, REQ_ID FROM RESTOCK_DETAILS";

        List<RestockDetailsService> list = new ArrayList<>();

        try {
            PreparedStatement st = conn.prepareStatement(sqlCommand);


            ResultSet rs = st.executeQuery();

            while(rs.next()){
                RestockDetailsService restockDetail = new RestockDetailsService();

                restockDetail.setRd_id(rs.getInt("RD_ID"));
                restockDetail.setQuantity(rs.getInt("QUANTITY"));
                restockDetail.setPrice(rs.getFloat("PRICE"));
                restockDetail.setP_id(rs.getInt("P_ID"));
                restockDetail.setR_id(rs.getInt("R_ID"));
                restockDetail.setReq_id(rs.getInt("REQ_ID"));

                list.add(restockDetail);
            }

        } catch (SQLException ex) {
            System.out.println("ERRO: " + ex.getMessage());
        }

        return list;
    }



    public static List<RestockDetailsService> read(int idRestockDetail){
        Connection conn = SQLConnection.criarConexao();

        String sqlCommand = "SELECT RD_ID, QUANTITY, PRICE, P_ID, R_ID, REQ_ID FROM RESTOCK_DETAILS WHERE R_ID = ?";
        List<RestockDetailsService> list = new ArrayList<>();

        try {
            PreparedStatement st = conn.prepareStatement(sqlCommand);
            st.setInt(1, idRestockDetail);

            ResultSet rs = st.executeQuery();

            while(rs.next()){
                RestockDetailsService restockDetail = new RestockDetailsService();

                restockDetail.setRd_id(rs.getInt("RD_ID"));
                restockDetail.setQuantity(rs.getInt("QUANTITY"));
                restockDetail.setPrice(rs.getFloat("PRICE"));
                restockDetail.setP_id(rs.getInt("P_ID"));
                restockDetail.setR_id(rs.getInt("R_ID"));
                restockDetail.setReq_id(rs.getInt("REQ_ID"));

                list.add(restockDetail);
            }

        } catch (SQLException ex) {
            System.out.println("ERRO: " + ex.getMessage());
        }

        return list;

    }


}
