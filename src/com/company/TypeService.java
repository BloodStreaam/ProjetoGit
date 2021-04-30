package com.company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TypeService {
    private int id_type;
    private String type;

    public int getId_type() {return id_type;}
    public void setId_type(int id_type) {this.id_type = id_type;}

    public String getType() {return type;}
    public void setType(String type) {this.type = type;}

    public static List<TypeService> readAllTypes(){
        Connection conn = SQLConnection.criarConexao();

        String sqlCommand = "SELECT TYPE_ID, TYPE FROM TYPE";

        List<TypeService> listType = new ArrayList<>();

        try {
            PreparedStatement st = conn.prepareStatement(sqlCommand);


            ResultSet rs = st.executeQuery();

            while(rs.next()){
                TypeService types = new TypeService();

                types.setId_type(rs.getInt("TYPE_ID"));
                if (rs.getString("TYPE") != null) types.setType(rs.getString("TYPE"));
                listType.add(types);
            }

        } catch (SQLException ex) {
            System.out.println("ERRO: " + ex.getMessage());
        }

        return listType;
    }

    public void readType(int idType){
        Connection conn = SQLConnection.criarConexao();

        String sqlCommand = "SELECT TYPE_ID, TYPE FROM TYPE WHERE TYPE_ID = ?";

        try {
            PreparedStatement st = conn.prepareStatement(sqlCommand);
            st.setInt(1, idType);


            ResultSet rs = st.executeQuery();

            if(rs.next()){
                this.id_type=(rs.getInt("TYPE_ID"));
                if (rs.getString("TYPE") != null) this.type= (rs.getString("TYPE"));

            }
            else{
                System.out.println("ERRO: Não existe Tipo com o ID definido ");
            }
        } catch (SQLException ex) {
            System.out.println("ERRO: " + ex.getMessage());
        }

    }

}
