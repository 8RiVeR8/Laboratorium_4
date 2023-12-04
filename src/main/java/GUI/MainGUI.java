package GUI;

import Logic.Meal;
import Logic.Product;
import Logic.ProductwWeight;
import Logic.TypeFood;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Objects;

public class MainGUI extends JFrame{
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
    private JComboBox TypeOfMeals;
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

    public MainGUI(ArrayList<Product> products, ArrayList<Meal> meals) {
        setContentPane(Background);
        setTitle("Food Table");
        setSize(1000, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        this.products = products;
        this.meals = meals;

        makeTable();
        DefaultTableModel model = (DefaultTableModel)ProductsTable.getModel();
        setMeals();
        makeTableForMeal();
        KcalAmountField.setEditable(false);
        CarbsAmountField.setEditable(false);
        ProteinsAmountField.setEditable(false);
        FatsAmountField.setEditable(false);
        AddProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (NameField.getText().isEmpty() || CarbsField.getText().isEmpty() || FatsField.getText().isEmpty() || ProteinsField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(MainGUI.this, "Missing data");
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

                        JOptionPane.showMessageDialog(MainGUI.this, "Product added");
                        NameField.setText("");
                        CarbsField.setText("");
                        FatsField.setText("");
                        ProteinsField.setText("");
                        makeTable();
                    } catch (NumberFormatException exception) {
                        JOptionPane.showMessageDialog(MainGUI.this, "Invalid data");
                    } catch (IllegalArgumentException exception) {
                        JOptionPane.showMessageDialog(MainGUI.this, "Invalid product type");
                    }
                }
            }
        });
        DeleteProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(ProductsTable.getSelectedRowCount() == 1) {
                    products.remove(ProductsTable.getSelectedRow());
                    model.removeRow(ProductsTable.getSelectedRow());
                    model.fireTableDataChanged();
                    makeTable();

                }   else if (ProductsTable.getSelectedRowCount() == 0) {
                    JOptionPane.showMessageDialog(MainGUI.this, "No row selected");
                }   else {
                    JOptionPane.showMessageDialog(MainGUI.this, "Please select a single row");
                }
            }
        });
        EditProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(ProductsTable.getSelectedRowCount() == 1) {

                    String Name = NameField.getText();
                    String Carbs = CarbsField.getText();
                    String Fats = FatsField.getText();
                    String Proteins = ProteinsField.getText();
                    String Category = Objects.requireNonNull(TypeField.getSelectedItem()).toString();

                    try {
                        Product product = (Product) products.get(ProductsTable.getSelectedRow());
                        product.setName(Name);
                        product.setCarbs(Double.parseDouble(Carbs));
                        product.setFats(Double.parseDouble(Fats));
                        product.setProteins(Double.parseDouble(Proteins));
                        product.setType(TypeFood.valueOf(Category));

                        model.setValueAt(Name, ProductsTable.getSelectedRow(), 0);
                        model.setValueAt(Carbs, ProductsTable.getSelectedRow(), 1);
                        model.setValueAt(Fats, ProductsTable.getSelectedRow(), 2);
                        model.setValueAt(Proteins, ProductsTable.getSelectedRow(), 3);
                        model.setValueAt(Category, ProductsTable.getSelectedRow(), 4);

                        JOptionPane.showMessageDialog(MainGUI.this, "Update successful");

                    } catch (NumberFormatException exception)   {
                        JOptionPane.showMessageDialog(MainGUI.this, "Invalid data");
                    }

                } else if (ProductsTable.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(MainGUI.this, "No data to edit");
                } else {
                    JOptionPane.showMessageDialog(MainGUI.this, "Please select a single row");
                }

            }
        });
        ProductsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String name = model.getValueAt(ProductsTable.getSelectedRow(), 0).toString();
                String carbs = model.getValueAt(ProductsTable.getSelectedRow(), 1).toString();
                String fats = model.getValueAt(ProductsTable.getSelectedRow(), 2).toString();
                String proteins = model.getValueAt(ProductsTable.getSelectedRow(), 3).toString();
                String type = model.getValueAt(ProductsTable.getSelectedRow(), 4).toString();

                NameField.setText(name);
                CarbsField.setText(carbs);
                FatsField.setText(fats);
                ProteinsField.setText(proteins);
                TypeField.setSelectedItem(type);
            }
        });
        TypeOfMeals.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                makeTableForMeal();
            }
        });
        DeleteMeal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                meals.remove(TypeOfMeals.getSelectedIndex());
                TypeOfMeals.removeItem(TypeOfMeals.getSelectedItem());
            }
        });
        DeleteFromMeal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(MealsTable.getSelectedRowCount() == 1)   {
                    int selectedRow = MealsTable.getSelectedRow();

                    if (selectedRow != -1) {
                        String selectedMealType = Objects.requireNonNull(TypeOfMeals.getSelectedItem()).toString();

                        meals.stream()
                                .filter(Meal -> Meal.getCategory().equals(selectedMealType))
                                .findFirst()
                                .ifPresent(Meal -> {
                                    Meal.getProducts().remove(selectedRow);
                                });

                        makeTableForMeal();
                    }
                } else {
                    JOptionPane.showMessageDialog(MainGUI.this, "No row selected");
                }
            }
        });
        AddMeal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new CreateMeal(meals, TypeOfMeals);
            }
        });
    }

    private void setMeals(){
        meals.forEach(Meal -> TypeOfMeals.addItem(String.valueOf(Meal.getCategory())));
    }

    public void makeTableForMeal(){
        ArrayList<ProductwWeight> items = meals.stream()
                .filter(Meal -> Meal.getCategory().equals(Objects.requireNonNull(TypeOfMeals.getSelectedItem()).toString()))
                .findFirst()
                .map(Meal::getProducts)
                .orElse(new ArrayList<>());

        Object[][] data = items.stream()
                .map(ProductwWeight -> new Object[]{ProductwWeight.getProducts().getName(), Round(ProductwWeight.getProducts().getCarbs()), Round(ProductwWeight.getProducts().getFats()), Round(ProductwWeight.getProducts().getProteins()), Round(ProductwWeight.getWeight())})
                .toArray(Object[][]::new);

        MealsTable.setModel(new DefaultTableModel(
                data,
                new String[]{"Name", "Carbs", "Fats", "Proteins", "Weight"}
        ));
        calculateNutrition();
    }

    private void calculateNutrition(){
        double car, pro, fat, kcal = 0.0;
        car = meals.stream()
                .filter(Meal -> Meal.getCategory().equals(Objects.requireNonNull(TypeOfMeals.getSelectedItem()).toString()))
                .findFirst()
                .map(Meal -> Meal.getProducts().stream()
                        .mapToDouble(ProductwWeight -> ProductwWeight.getProducts().getCarbs())
                        .sum())
                .orElse(0.0);
        CarbsAmountField.setText(String.valueOf(Round(car)));

        pro = meals.stream()
                .filter(Meal -> Meal.getCategory().equals(Objects.requireNonNull(TypeOfMeals.getSelectedItem()).toString()))
                .findFirst()
                .map(Meal -> Meal.getProducts().stream()
                        .mapToDouble(ProductwWeight -> ProductwWeight.getProducts().getProteins())
                        .sum())
                .orElse(0.0);
        ProteinsAmountField.setText(String.valueOf(Round(pro)));

        fat = meals.stream()
                .filter(Meal -> Meal.getCategory().equals(Objects.requireNonNull(TypeOfMeals.getSelectedItem()).toString()))
                .findFirst()
                .map(Meal -> Meal.getProducts().stream()
                        .mapToDouble(ProductwWeight -> ProductwWeight.getProducts().getFats())
                        .sum())
                .orElse(0.0);
        FatsAmountField.setText(String.valueOf(Round(fat)));
        kcal = ( car + pro ) * 4 + fat * 9;
        KcalAmountField.setText(String.valueOf(Round(kcal)));

    }


    public static double Round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    private void makeTable() {

        Object[][] data = products.stream()
                .map(Product -> new Object[]{Product.getName(), Round(Product.getCarbs()), Round(Product.getFats()), Round(Product.getProteins()), Product.getType().toString()})
                .toArray(Object[][]::new);

        ProductsTable.setModel(new DefaultTableModel(
                data,
                new String[]{"Name", "Carbs", "Fats", "Proteins", "Type"}
        ));
    }
}
