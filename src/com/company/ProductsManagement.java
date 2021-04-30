package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductsManagement extends JDialog {
    private JPanel contentPane;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton backButton;
    private JTable table1;
    private JTextField nameInput;
    private JTextField quantityInput;
    private JTextField priceInput;
    private JComboBox TypeInput;
    private JComboBox farmInput;
    private JButton save;
    private JScrollPane jscrollpane1;
    private JLabel namefield;
    private JLabel quantityField;
    private JLabel priceField;
    private JLabel farmField;
    private JLabel typefield;
    private JPanel editPanel;
    private JPanel infoPanel;
    private JButton xButton;
    private JTextField textField4;
    private JTable jtable;
    private static List<ProductService> products;
    private static List<TypeService> types;
    private static List<FarmService> farms;
    public static String idProductSelected;
    private static int idProductConverted;
    private static int idProductOnEdit;



    public ProductsManagement() {
        setContentPane(contentPane);
        setModal(true);

        jscrollpane1.getViewport().add(jtable=createJTable());
        editPanel.setVisible(false);
        infoPanel.setVisible(false);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminManagement.jProductsManagement.setVisible(false);
            }
        });

        jtable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                idProductSelected = jtable.getValueAt(jtable.getSelectedRow(),0).toString();
                idProductConverted = Integer.parseInt(idProductSelected);
                System.out.println(idProductConverted);
                infoPanel.setVisible(true);
                pack();
                showFullDetail(idProductConverted);


            }

        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showInfoEdit(idProductConverted);
                editPanel.setVisible(true);
                pack();
            }
        });
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    updateProduct();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        xButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editPanel.setVisible(false);
                pack();
            }
        });
    }

    private void updateProduct() throws SQLException {
        int type_id = 0; //Guarda o id do tipo de produto
        int farm_id = 0; //guarda o id da quinta

        ProductService updateProd = new ProductService(); //Para correr a função update() que se encontra o file ProductService
        types = TypeService.readAllTypes(); //Lista de Todos os tipos
        farms = FarmService.readAllFarms(); //Lista de todas as quintas

        //Corre um ciclo para vericar se o tipo selecionada na combo box está na lista de tipos e atribui o id ao type_id
        for (TypeService type : types) {

            if(type.getType().equals(TypeInput.getSelectedItem())){

                type_id = type.getId_type();
                break;
            }
        }
        for (FarmService farm : farms) {
            if(farm.getName().equals(farmInput.getSelectedItem())){
                farm_id = farm.getIdFarm();
                break;
            }
        }


        //Atualiza o produto com o id definido na tabela produtos
        updateProd.update(farm_id, type_id, Float.parseFloat(priceInput.getText()), Integer.parseInt(quantityInput.getText()), String.valueOf(nameInput.getText()), idProductOnEdit);
        clearInputs();
        editPanel.setVisible(false);
        pack();

    }

    private void clearInputs(){
        List<JTextField> tfList = new   ArrayList<JTextField>();
        List<JComboBox> cbList = new   ArrayList<JComboBox>();
// somewhere in your code will have
        JPanel panel = new JPanel();
        tfList.add(nameInput);
        tfList.add(priceInput);
        tfList.add(quantityInput);

        cbList.add(TypeInput);
        cbList.add(farmInput);

        for(JTextField tf : tfList){
            tf.setText("");
        }

        for(JComboBox cb : cbList){
            cb.removeAllItems();
        }

        // add to list
    }

    // Apreesenta toda a ifnroamção do produto selecionado para edição
    private void showInfoEdit(int idProduct){
        idProductOnEdit = idProduct; //Guarda o ID do produto que está a ser editado para mais tarde ser usado no update
        ProductService product = new ProductService(); //Guarda o valor do produto selecionado
        types = TypeService.readAllTypes(); //lê todos os tipos que estão na tabela Type
        farms = FarmService.readAllFarms(); //lê todas as quintas que estão na tabela Farms
        product.read(idProduct); //lê o produto que tem o id indicado
        System.out.println(product.getName());
        nameInput.setText(product.getName());
        quantityInput.setText(String.valueOf(product.getPr_quantity()));
        priceInput.setText(String.valueOf(product.getPrice_un()));

       //Adiciona os tipos a combo box
        for (TypeService type : types) {
            TypeInput.addItem(type.getType());
            //Seleciona o tipo baseado no id que o produto tem
            if(type.getId_type() == product.getType_id()){
                TypeInput.setSelectedItem(type.getType());
            }
        }

        //Adiciona as quintas a combobox,
        for(FarmService farm: farms){
            farmInput.addItem(farm.getName());
            //Seleciona a quinta do produto baseado no id  da quinta que o mesmo tempo
            if(farm.getIdFarm() == product.getFarm_id()){
                farmInput.setSelectedItem(farm.getName());
            }
        }
    }

    //Apresenta a informação completa do produto
    private void showFullDetail(int idProduct){
        ProductService product = new ProductService();
        TypeService type = new TypeService();
        FarmService farm = new FarmService();

        product.read(idProduct);
        type.readType(product.getType_id());
        farm.readFarm(product.getFarm_id());

        System.out.println("Type_ID" + product.getType_id());
        System.out.println(product.getName());

        namefield.setText("Name: " + product.getName());
        priceField.setText("Price: " + String.valueOf(product.getPrice_un()));
        quantityField.setText("Stock: " + String.valueOf(product.getPr_quantity()));
        farmField.setText("Farm: " + farm.getName());
        typefield.setText("Type: " + String.valueOf(type.getType()));
    }

    //Cria a tabela de produtos com as informações mais importantes
    private static JTable createJTable() {
        products = ProductService.readAll();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Price");
        model.addColumn("Quantity");


        for (ProductService prod : products) {
            model.addRow(new Object[]{prod.getProduct_id(), prod.getName(), prod.getPrice_un(), prod.getPr_quantity()});
        }


        // for(EmployeeService emp : employees)

            /*values.add(new String[] { String.valueOf(emp.getE_id()),  emp.getName(),  String.valueOf(emp.getBirthdate()),
                    emp.getMail(),  String.valueOf(emp.getPhone()),  String.valueOf(emp.getSalary()), emp.getAddress(), emp.getZip() });
*/

        JTable jtable = new JTable(model);
        jtable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        return jtable;
    }

    public static void main(String[] args) {
        ProductsManagement dialog = new ProductsManagement();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);

        AdminManagement.jAdminManagement.setVisible(false);
    }

}
