package org.example.GUI;

import org.example.Logic.Meal;
import org.example.Logic.ProductwWeight;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CreateMeal extends JFrame{
    private JPanel Background;
    private JButton AddMeal;
    private JTextField textField1;
    private JLabel EnterMeal;

    public CreateMeal(ArrayList<Meal> meals, JComboBox MealsBox){

        setContentPane(Background); //Background
        setTitle("Create Meal");
        setSize(300, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
        AddMeal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                meals.add(new Meal(textField1.getText(), new ArrayList<ProductwWeight>()));
                MealsBox.addItem(textField1.getText());
                JOptionPane.showMessageDialog(CreateMeal.this, "Created successfully");
            }
        });
    }

}
