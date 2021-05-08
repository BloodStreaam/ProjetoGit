package com.company;

import javax.swing.text.Position;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PositionService{
    private int P_ID;
    private String position;
    private Date start_date;
    private Date end_date;

    public int getP_ID() {return P_ID;}
    public void setP_ID(int p_ID) {P_ID = p_ID;}

    public String getPosition() {return position;}
    public void setPosition(String position) {this.position = position;}

    public Date getStart_date() {return start_date;}
    public void setStart_date(Date start_date) {this.start_date = start_date;}

    public Date getEnd_date() {return end_date;}
    public void setEnd_date(Date end_date) {this.end_date = end_date;}

    public static List<PositionService> readAll(){
        Connection conn = SQLConnection.criarConexao();

        String sqlCommand = "SELECT P_ID, POSITION, START_DATE, END_DATE FROM EMPLOYEE_POSITION";

        List<PositionService> list = new ArrayList<>();

        try {
            PreparedStatement st = conn.prepareStatement(sqlCommand);


            ResultSet rs = st.executeQuery();

            while(rs.next()){
                PositionService position = new PositionService();

                position.setP_ID(rs.getInt("P_ID"));
                if (rs.getString("POSITION") != null)  position.setPosition(rs.getString("POSITION"));
                if (rs.getDate("START_DATE") != null) position.setStart_date(rs.getDate("START_DATE"));
                if (rs.getString("END_DATE") != null) position.setEnd_date(rs.getDate("END_DATE"));

                list.add(position);
            }

        } catch (SQLException ex) {
            System.out.println("ERRO: " + ex.getMessage());
        }

        return list;
    }


    public void readPosition(int idPosition){
        Connection conn = SQLConnection.criarConexao();

        String sqlCommand = "SELECT P_ID, POSITION, START_DATE, END_DATE FROM EMPLOYEE_POSITION WHERE P_ID = ?";

        try {
            PreparedStatement st = conn.prepareStatement(sqlCommand);
            st.setInt(1, idPosition);


            ResultSet rs = st.executeQuery();

            if(rs.next()){
                this.P_ID=(rs.getInt("P_ID"));
                if (rs.getString("POSITION") != null) this.position= (rs.getString("POSITION"));

            }
            else{
                System.out.println("ERRO: Não existe Posição com o ID definido ");
            }
        } catch (SQLException ex) {
            System.out.println("ERRO: " + ex.getMessage());
        }

    }

}
