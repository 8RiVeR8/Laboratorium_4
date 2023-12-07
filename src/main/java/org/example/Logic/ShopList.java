package org.example.Logic;

import java.util.ArrayList;

public class ShopList {
    private ArrayList<ProductwWeight> weight;
    private TypeFood typeFood;
    public ShopList(ArrayList<ProductwWeight> weight, TypeFood typeFood) {
        this.weight = weight;
        this.typeFood = typeFood;
    }

    public ArrayList<ProductwWeight> getWeight() {
        return weight;
    }

    public TypeFood getTypeFood() {
        return typeFood;
    }

    public void setWeight(ArrayList<ProductwWeight> weight) {
        this.weight = weight;
    }

    public void setTypeFood(TypeFood typeFood) {
        this.typeFood = typeFood;
    }

    public void replaceProduct(ProductwWeight existingProduct, ProductwWeight newProduct) {
        weight.remove(existingProduct);
        weight.add(newProduct);
    }
}
