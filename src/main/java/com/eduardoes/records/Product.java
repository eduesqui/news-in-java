package com.eduardoes.records;

public record Product(String name, Double price) {

    public Product applyDiscount(Product original, double percentage) {
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException("Discount percentage must be between 0 and 100");
        }
        double newPrice = original.price - (original.price * percentage / 100);
        return new Product( original.name, newPrice);
    }
}
