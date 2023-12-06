package org.example.Logic;

public class ProductwWeight {
    private double weight;
    private final Product products;
    public ProductwWeight(Product products, double weight) {
        this.products = products;
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Product getProducts() {
        return products;
    }

}
