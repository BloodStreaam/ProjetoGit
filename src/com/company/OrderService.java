package com.company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class OrderService {
    private int o_id;
    private int t_id;
    private int or_id;
    private String bill;
    private String invoice;
    private String payment_status;

    public int getO_id() {
        return o_id;
    }
    public void setO_id(int o_id) {
        this.o_id = o_id;
    }
    public int getT_id() {
        return t_id;
    }
    public void setT_id(int t_id) {
        this.t_id = t_id;
    }
    public int getOr_id() {
        return or_id;
    }
    public void setOr_id(int or_id) {
        this.or_id = or_id;
    }
    public String getBill() {
        return bill;
    }
    public void setBill(String bill) {
        this.bill = bill;
    }
    public String getInvoice() {
        return invoice;
    }
    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }
    public String getPayment_status() {
        return payment_status;
    }
    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public static List<OrderService> readAll(){
        Connection conn = SQLConnection.criarConexao();
        String sqlCommand = "SELECT * FROM ONLINE_ORDER ";
        List<OrderService> list = new ArrayList<>();

        try {
            PreparedStatement st = conn.prepareStatement(sqlCommand);
            ResultSet rs = st.executeQuery();

            while(rs.next()){
                OrderService order = new OrderService();

                order.setO_id(rs.getInt("o_id"));
                order.setOr_id(rs.getInt("or_id"));
                order.setT_id(rs.getInt("t_id"));
                order.setBill(rs.getString("billing_address"));
                order.setInvoice(rs.getString("invoice_address"));
                order.setPayment_status(rs.getString("payment_status"));
            }

        } catch (SQLException ex) {
            System.out.println("ERRO: " + ex.getMessage());
        }

        return list;
    }





}
