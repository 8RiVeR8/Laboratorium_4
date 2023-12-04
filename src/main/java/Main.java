import Data.TxtFileWorker;
import GUI.MainGUI;
import Logic.Meal;
import Logic.Product;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        String data1 = "/Users/maks_rz/Documents/Lab_4/Laboratorium_4/src/main/resources/Data.txt";
        String data2 = "/Users/maks_rz/Documents/Lab_4/Laboratorium_4/src/main/resources/Meals.txt";

        TxtFileWorker worker = new TxtFileWorker(data1, data2);

        ArrayList<Product> products = worker.getTxt();
        ArrayList<Meal> meals = worker.getTxtMeals();

        /*products.stream()
                .forEach(product -> System.out.println(product.getName() + ": "
                        + "Carbs: " + product.getCarbs()
                        + ", Fats: " + product.getFats()
                        + ", Proteins: " + product.getProteins()
                        + ", Category: " + product.getType()));*/

        /*meals.stream()
                .forEach(Meal -> {
                    System.out.println("Meal Type: " + Meal.getCategory());
                    System.out.println("Meal Items:");
                    Meal.getProducts().forEach(ProductwWeight -> {
                        System.out.println("  Product: " + ProductwWeight.getProducts().getName()
                                + ", Weight: " + ProductwWeight.getWeight());
                    });
                    System.out.println("------------------------");
                });*/

        MainGUI g = new MainGUI(products, meals);

    }
}
