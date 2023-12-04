package Data;

import Logic.Product;
import Logic.TypeFood;

import java.io.*;
import java.util.ArrayList;


public class TxtFileWorker {

    private final String data1;
    private final String data2;

    public TxtFileWorker(String data1, String data2) {
        this.data1 = data1;
        this.data2 = data2;
    }

    public ArrayList<Product> getTxt()    {
        ArrayList<Product> product = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(data1))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");

                String name = parts[0];
                double carbs = Double.parseDouble(parts[1]);
                double fats = Double.parseDouble(parts[2]);
                double proteins = Double.parseDouble(parts[3]);
                String type = parts[4];

                Product products = new Product(name, carbs, fats, proteins, TypeFood.valueOf(type));
                product.add(products);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return product;
    }

    public void insertTxt(ArrayList<Product> productList) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(data1, false))) {
            for (Product product : productList) {
                String line = product.getName() + ";" +
                        product.getCarbs() + ";" +
                        product.getFats() + ";" +
                        product.getProteins() + ";" +
                        product.getType() + ";" ;

                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
