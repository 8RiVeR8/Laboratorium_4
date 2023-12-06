package org.example.Data;

import org.example.Logic.Meal;
import org.example.Logic.Product;
import org.example.Logic.ProductwWeight;
import org.example.Logic.TypeFood;

import java.io.*;
import java.util.ArrayList;


public class TxtFileWorker {

    private final String productsInTxt;
    private final String mealsTxt;

    public TxtFileWorker(String productsInTxt, String meals) {
        this.productsInTxt = productsInTxt;
        this.mealsTxt = meals;
    }

    public ArrayList<Product> getTxt()    {
        ArrayList<Product> product = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(productsInTxt))) {
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
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(productsInTxt, false))) {
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
    public void insertTxtMeals(ArrayList<Meal> meals){
        try (FileWriter fileWriter = new FileWriter(this.mealsTxt, false)) {
            for (Meal meal : meals) {
                StringBuilder lineBuilder = new StringBuilder("*" + meal.getCategory() + "\n");

                for (ProductwWeight product : meal.getProducts()) {
                    lineBuilder.append(product.getProducts().getName()).append(";")
                            .append(product.getProducts().getCarbs()).append(";")
                            .append(product.getProducts().getFats()).append(";")
                            .append(product.getProducts().getProteins()).append(";")
                            .append(product.getProducts().getType().name().toUpperCase()).append(";")
                            .append(product.getWeight()).append(";").append("\n");
                }

                String line = lineBuilder.toString();
                fileWriter.write(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Meal> getTxtMeals(){
        ArrayList<ProductwWeight> items = new ArrayList<>();
        ArrayList<Meal> meals = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(this.mealsTxt))) {
            String line;
            while((line = br.readLine()) != null)    {
                if(line.startsWith("*"))    {
                    items = new ArrayList<>();
                    line = line.substring(1).trim();
                    meals.add(new Meal(line, items));
                }   else {
                    String[] parts = line.split(";");
                    String name = parts[0];
                    double carbs = Double.parseDouble(parts[1]);
                    double fats = Double.parseDouble(parts[2]);
                    double proteins = Double.parseDouble(parts[3]);
                    TypeFood type = TypeFood.valueOf(parts[4].toUpperCase());
                    double weight = Double.parseDouble(parts[5]);
                    items.add(new ProductwWeight(new Product(name, carbs, fats, proteins, type), weight));
                }
            }

        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return meals;
    }
}
