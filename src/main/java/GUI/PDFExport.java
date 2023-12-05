package GUI;

import Data.PDFGenerator;
import Logic.Meal;
import Logic.ProductwWeight;
import Logic.ShopList;
import Logic.TypeFood;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PDFExport extends JFrame {
    private JPanel panel1;
    private JTable table1;
    private JButton addButton;
    private JComboBox comboBox1;
    private JLabel SelectMeals;
    private JButton ExportButton;

    ArrayList<Meal> choose = new ArrayList<>();
    ArrayList<ProductwWeight> item = new ArrayList<>();

    public PDFExport(ArrayList<Meal> meal) {
        setContentPane(panel1);
        setTitle("Create shopping list");
        setSize(300, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        makeTable();
        setLocationRelativeTo(null);
        meal.forEach(Meal -> comboBox1.addItem(Meal.getCategory()));
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                meal.stream()
                        .filter(Meal -> Meal.getCategory().equals((comboBox1.getSelectedItem()).toString()))
                        .findFirst()
                        .ifPresent(Meal -> {
                            choose.add(Meal);
                            makeTable();
                        });
            }
        });
        ExportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                choose.forEach(Meal -> item.addAll(Meal.getProducts()));
                ArrayList<ShopList> shopList = groupProducts(item);
                PDFGenerator.exportToPDF(shopList);
                JOptionPane.showMessageDialog(PDFExport.this, "Exported successfully");
                dispose();
            }
        });
    }
        public static ArrayList<ShopList> groupProducts (ArrayList <ProductwWeight> productwWeightList) {
            Map<String, ProductwWeight> productMap = new HashMap<>();
            Map<TypeFood, ShopList> shopListMap = new HashMap<>();

            for (ProductwWeight productwWeight : productwWeightList) {
                TypeFood typeFood = productwWeight.getProducts().getType();
                String productName = productwWeight.getProducts().getName();

                if (!shopListMap.containsKey(typeFood)) {
                    ArrayList<ProductwWeight> productList = new ArrayList<>();
                    productList.add(productwWeight);
                    ShopList shopList = new ShopList(productList, typeFood);
                    shopListMap.put(typeFood, shopList);
                    productMap.put(productName, productwWeight);
                } else {
                    ShopList shopList = shopListMap.get(typeFood);

                    if (productMap.containsKey(productName)) {
                        productMap.get(productName).setWeight(productMap.get(productName).getWeight() + productwWeight.getWeight());
                    } else {
                        shopList.getWeight().add(productwWeight);
                        productMap.put(productName, productwWeight);
                    }
                }
            }

            return new ArrayList<>(shopListMap.values());
        }

        private void makeTable () {
            Object[][] data = choose.stream()
                    .map(Meal -> new Object[]{Meal.getCategory()})
                    .toArray(Object[][]::new);

            DefaultTableModel model = new DefaultTableModel(data, new String[]{"Meal"});

            table1.setModel(model);
        }
}