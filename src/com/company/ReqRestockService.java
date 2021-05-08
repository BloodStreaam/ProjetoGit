package com.company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReqRestockService {
    private int req_id;
    private int e_id;
    private Date req_date;

    public Date getReq_date() {
        return req_date;
    }

    public void setReq_date(Date req_date) {
        this.req_date = req_date;
    }

    public int getReq_id() {
        return req_id;
    }

    public void setReq_id(int req_id) {
        this.req_id = req_id;
    }

    public int getE_id() {
        return e_id;
    }

    public void setE_id(int e_id) {
        this.e_id = e_id;
    }

    public static List<ReqRestockService> readAll(){
        Connection conn = SQLConnection.criarConexao();

        String sqlCommand = "SELECT REQ_ID, E_ID, REQ_DATE FROM REQ_RESTOCK";

        List<ReqRestockService> list = new ArrayList<>();

        try {
            PreparedStatement st = conn.prepareStatement(sqlCommand);


            ResultSet rs = st.executeQuery();

            while(rs.next()){
               ReqRestockService reqRestock = new ReqRestockService();

                reqRestock.setReq_id(rs.getInt("REQ_ID"));
                reqRestock.setE_id(rs.getInt("E_ID"));
                reqRestock.setReq_date(rs.getDate("REQ_DATE"));

                list.add(reqRestock);
            }

        } catch (SQLException ex) {
            System.out.println("ERRO: " + ex.getMessage());
        }

        return list;
    }



    public void read(int idReqRestock){
        Connection conn = SQLConnection.criarConexao();

        String sqlCommand = "SELECT REQ_ID, E_ID, REQ_DATE FROM REQ_EMPLOYEE WHERE REQ_ID = ?";

        try {
            PreparedStatement st = conn.prepareStatement(sqlCommand);
            st.setInt(1, idReqRestock);


            ResultSet rs = st.executeQuery();

            if(rs.next()){
                this.req_id=(rs.getInt("REQ_ID"));
                this.e_id=(rs.getInt("E_ID"));
                this.req_date=(rs.getDate("REQ_DATE"));

            }
            else{
                System.out.println("ERRO: Não existe Cliente com o ID definido ");
            }
        } catch (SQLException ex) {
            System.out.println("ERRO: " + ex.getMessage());
        }

    }
}
