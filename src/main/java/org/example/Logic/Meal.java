package org.example.Logic;

import java.util.ArrayList;

public class Meal {

    private final String category;

    private ArrayList<ProductwWeight> products;

    public Meal(String category, ArrayList<ProductwWeight> products) {
        this.products = products;
        this.category = category;
    }

    public String getCategory(){
        return category;
    }

    public ArrayList<ProductwWeight> getProducts() {
        return products;
    }

}
