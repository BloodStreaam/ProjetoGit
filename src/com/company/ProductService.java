package com.company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductService {

    private int product_id;
    private int rd_id;
    private int farm_id;
    private int type_id;
    private int or_id;
    private float price_un;
    private int pr_quantity;
    private String name;

    //Table Product
    public int getProduct_id() {return product_id;}
    public void setProduct_id(int product_id) {this.product_id = product_id;}

    public int getRd_id() {return rd_id;}
    public void setRd_id(int rd_id) {this.rd_id = rd_id;}

    public int getFarm_id() {return farm_id;}
    public void setFarm_id(int farm_id) {this.farm_id = farm_id;}

    public int getType_id() {return type_id;}
    public void setType_id(int type_id) {this.type_id = type_id;}

    public int getOr_id() {return or_id;}
    public void setOr_id(int or_id) {this.or_id = or_id;}

    public float getPrice_un() {return price_un;}
    public void setPrice_un(float price_un) {this.price_un = price_un;}

    public int getPr_quantity() {return pr_quantity;}
    public void setPr_quantity(int pr_quantity) {this.pr_quantity = pr_quantity;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public static List<ProductService> readAll(){
        Connection conn = SQLConnection.criarConexao();

        String sqlCommand = "SELECT PRODUCT_ID, RD_ID, FARM_ID, TYPE_ID, OR_ID, PRICE_UNI, PR_QUANTITY, PRODUCT_NAME, REQ_ID FROM PRODUCTS";

        List<ProductService> list = new ArrayList<>();

        try {
            PreparedStatement st = conn.prepareStatement(sqlCommand);


            ResultSet rs = st.executeQuery();

            while(rs.next()){
                ProductService product = new ProductService();

                product.setProduct_id(rs.getInt("PRODUCT_ID"));
                product.setRd_id(rs.getInt("RD_ID"));
                product.setFarm_id(rs.getInt("FARM_ID"));
                product.setType_id(rs.getInt("TYPE_ID"));
                product.setOr_id(rs.getInt("OR_ID"));
                product.setPrice_un(rs.getFloat("PRICE_UNI"));
                product.setPr_quantity(rs.getInt("PR_QUANTITY"));
                if (rs.getString("PRODUCT_NAME") != null) product.setName(rs.getString("PRODUCT_NAME"));
                list.add(product);
            }

        } catch (SQLException ex) {
            System.out.println("ERRO: " + ex.getMessage());
        }

        return list;
    }



    public void read(int idProduct){
        Connection conn = SQLConnection.criarConexao();

        String sqlCommand = "SELECT PRODUCT_ID, RD_ID, FARM_ID, TYPE_ID, OR_ID, PRICE_UNI, PR_QUANTITY, PRODUCT_NAME, REQ_ID FROM PRODUCTS WHERE PRODUCT_ID = ?";

        try {
            PreparedStatement st = conn.prepareStatement(sqlCommand);
            st.setInt(1, idProduct);


            ResultSet rs = st.executeQuery();

            if(rs.next()){
                this.product_id=(rs.getInt("PRODUCT_ID"));
                this.rd_id=(rs.getInt("RD_ID"));
                this.farm_id=(rs.getInt("FARM_ID"));
                this.type_id=(rs.getInt("TYPE_ID"));
                this.or_id=(rs.getInt("OR_ID"));
                this.price_un=(rs.getFloat("PRICE_UNI"));
                this.pr_quantity=(rs.getInt("PR_QUANTITY"));
                if (rs.getString("PRODUCT_NAME") != null) this.name= (rs.getString("PRODUCT_NAME"));

            }
            else{
                System.out.println("ERRO: Não existe Cliente com o ID definido ");
            }
        } catch (SQLException ex) {
            System.out.println("ERRO: " + ex.getMessage());
        }

    }

    public void update(int farmid, int typeid, float price, int quantity, String name, int prodId) throws SQLException {
        Connection conn = SQLConnection.criarConexao();

        String sqlCommand = "UPDATE PRODUCTS SET FARM_ID = ?, TYPE_ID = ?, PRICE_UNI = ?, PR_QUANTITY = ?, PRODUCT_NAME = ? WHERE PRODUCT_ID = ?";
        PreparedStatement pst = conn.prepareStatement(sqlCommand);

        pst.setInt(1, farmid);
        pst.setInt(2, typeid);
        pst.setFloat(3, price);
        pst.setInt(4, quantity);
        pst.setString(5, name);
        pst.setInt(6, prodId);
        pst.execute();
    }
}
