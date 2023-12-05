package GUI;

import Logic.Meal;
import Logic.Product;
import Logic.ProductwWeight;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AddToMeal extends JFrame{
    private JPanel panel1;
    private JTextField textField1;
    private JButton AddWeight;
    private JLabel EnterWeight;

    public AddToMeal(ArrayList<Meal> meals, String mealName, Product product){
        setContentPane(panel1);
        setTitle("Add to meal");
        setSize(300, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);


        AddWeight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    double weight = Double.parseDouble(textField1.getText());
                    product.setCarbs(product.getCarbs() * weight / 100);
                    product.setProteins(product.getProteins() * weight / 100);
                    product.setFats(product.getFats() * weight / 100);
                    ProductwWeight item = new ProductwWeight(product, weight);
                    meals.stream()
                            .filter(Meal -> Meal.getCategory().equals(mealName))
                            .findFirst()
                            .ifPresent(Meal -> Meal.getProducts().add(item));
                    dispose();
                    JOptionPane.showMessageDialog(AddToMeal.this, "Added successfully");
                } catch (NumberFormatException exception)   {
                    JOptionPane.showMessageDialog(AddToMeal.this, "Invalid data");
                }
            }
        });
    }
}
