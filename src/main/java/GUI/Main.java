package GUI;

import Data.TxtFileWorker;
import Logic.Meal;
import Logic.Product;
import Logic.TypeFood;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

public class Main extends JFrame{
    private JPanel Background;
    private JButton PDFexport;
    private JTable MealsTable;
    private JButton AddToMeal;
    private JButton EditProduct;
    private JButton DeleteProduct;
    private JButton DeleteFromMeal;
    private JButton AddMeal;
    private JButton DeleteMeal;
    private JLabel Name;
    private JLabel Carbs;
    private JLabel Fats;
    private JLabel Proteins;
    private JComboBox TypeField;
    private JButton AddProduct;
    private JTextField NameField;
    private JTextField CarbsField;
    private JTextField FatsField;
    private JTextField ProteinsField;
    private JComboBox TypeOfProduct;
    private JLabel ListOfProducts;
    private JLabel ListOfMeals;
    private JTable ProductsTable;
    private JTextField FatsAmountField;
    private JTextField ProteinsAmountField;
    private JTextField CarbsAmountField;
    private JTextField KcalAmountField;
    private JLabel AmountOfKcal;
    private JLabel AmountOfCarbs;
    private JLabel AmountOfProteins;
    private JLabel AmountOfFats;
    private ArrayList<Product> products;
    private ArrayList<Meal> meals;

    public Main(ArrayList<Product> products, ArrayList<Meal> meals) {
        DefaultTableModel model = (DefaultTableModel)ProductsTable.getModel();
        setContentPane(Background);
        setTitle("Food Table");
        setSize(1000, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        this.products = products;
        this.meals = meals;

        makeTable(products);
        AddProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (NameField.getText().isEmpty() || CarbsField.getText().isEmpty() || FatsField.getText().isEmpty() || ProteinsField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(Main.this, "Missing data");
                } else {
                    try {
                        // Pobierz tekstową reprezentację wybranego elementu z TypeField
                        String typeText = Objects.requireNonNull(TypeField.getSelectedItem()).toString();
                        TypeFood category = TypeFood.valueOf(typeText);
                        Product product = new Product(NameField.getText(), Double.parseDouble(CarbsField.getText()), Double.parseDouble(FatsField.getText()), Double.parseDouble(ProteinsField.getText()), category);
                        products.add(product);

                        String[] data = new String[]{NameField.getText(), CarbsField.getText(), FatsField.getText(), ProteinsField.getText(), typeText};

                        model.addRow(data);
                        model.fireTableDataChanged();

                        JOptionPane.showMessageDialog(Main.this, "Product added");
                        NameField.setText("");
                        CarbsField.setText("");
                        FatsField.setText("");
                        ProteinsField.setText("");
                    } catch (NumberFormatException exception) {
                        JOptionPane.showMessageDialog(Main.this, "Invalid data");
                    } catch (IllegalArgumentException exception) {
                        JOptionPane.showMessageDialog(Main.this, "Invalid product type");
                    }
                }
            }
        });
    }

    public static double Round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    private void makeTable(ArrayList<Product> products)    {

        Object[][] data = products.stream()
                .map(Product -> new Object[]{Product.getName(), Round(Product.getCarbs()), Round(Product.getFats()), Round(Product.getProteins()), Product.getType().toString()})
                .toArray(Object[][]::new);

        ProductsTable.setModel(new DefaultTableModel(
                data,
                new String[]{"Name", "Carbs", "Fats", "Proteins", "Category"}
        ));
    }
}
