package com.aadidev.foodexpirytracker.repository;

import com.aadidev.foodexpirytracker.model.FoodItem;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FoodItemRepository {
    private static final String DB_URL = "jdbc:sqlite:food_items.db";

    public FoodItemRepository() {
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        String sql = """
            CREATE TABLE IF NOT EXISTS food_items (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                expiry_date TEXT NOT NULL
            );
        """;
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveItem(FoodItem item) {
        String sql = "INSERT INTO food_items (name, expiry_date) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, item.getName());
            pstmt.setString(2, item.getExpiryDate().toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<FoodItem> loadAll() {
        List<FoodItem> list = new ArrayList<>();
        String sql = "SELECT name, expiry_date FROM food_items";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String name = rs.getString("name");
                LocalDate date = LocalDate.parse(rs.getString("expiry_date"));
                list.add(new FoodItem(name, date));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}