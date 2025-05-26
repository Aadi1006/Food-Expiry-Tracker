package com.aadidev.foodexpirytracker.model;

import java.time.LocalDate;

public class FoodItem {
    private final String name;
    private final LocalDate expiryDate;

    public FoodItem(String name, LocalDate expiryDate) {
        this.name = name;
        this.expiryDate = expiryDate;
    }

    public String getName() {
        return name;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }
}