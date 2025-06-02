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
                expiry_date TEXT NOT NULL,
                user_id INTEGER NOT NULL
            );
        """;
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addItem(FoodItem item) {
        String sql = "INSERT INTO food_items (name, expiry_date, user_id) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, item.getName());
            pstmt.setString(2, item.getExpiryDate().toString());
            pstmt.setInt(3, item.getUserId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteItem(int id) {
        String sql = "DELETE FROM food_items WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<FoodItem> getItemsForUser(int userId) {
        List<FoodItem> items = new ArrayList<>();
        String sql = "SELECT * FROM food_items WHERE user_id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                items.add(new FoodItem(
                        rs.getInt("id"),
                        rs.getString("name"),
                        LocalDate.parse(rs.getString("expiry_date")),
                        rs.getInt("user_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
    public List<FoodItem> loadAll(int userId) {
        List<FoodItem> list = new ArrayList<>();
        String sql = "SELECT * FROM food_items WHERE user_id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                list.add(new FoodItem(
                        rs.getInt("id"),
                        rs.getString("name"),
                        LocalDate.parse(rs.getString("expiry_date")),
                        rs.getInt("user_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public void saveItem(FoodItem item) {
        String sql = "INSERT INTO food_items (name, expiry_date, user_id) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, item.getName());
            pstmt.setString(2, item.getExpiryDate().toString());
            pstmt.setInt(3, item.getUserId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}