import Data.TxtFileWorker;
import Logic.Meal;
import Logic.Product;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        String data1 = "/Users/maks_rz/Documents/Lab_4/Laboratorium_4/src/main/resources/Data.txt";
        String data2 = "/Users/maks_rz/Documents/Lab_4/Laboratorium_4/src/main/resources/Data2.txt";

        TxtFileWorker worker = new TxtFileWorker(data1, data2);

        ArrayList<Product> products = worker.getTxt();
        ArrayList<Meal> meals = null;

        /*products.stream()
                .forEach(product -> System.out.println(product.getName() + ": "
                        + "Carbs: " + product.getCarbs()
                        + ", Fats: " + product.getFats()
                        + ", Proteins: " + product.getProteins()
                        + ", Category: " + product.getType()));*/

        GUI.Main g = new GUI.Main(products, meals);

    }
}
