package com.aadidev.foodexpirytracker.model;

import java.time.LocalDate;

public class FoodItem {
    private int id;
    private String name;
    private LocalDate expiryDate;
    private int userId;

    public FoodItem(int id, String name, LocalDate expiryDate, int userId) {
        this.id = id;
        this.name = name;
        this.expiryDate = expiryDate;
        this.userId = userId;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public LocalDate getExpiryDate() { return expiryDate; }
    public int getUserId() { return userId; }
}