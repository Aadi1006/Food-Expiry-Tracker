module com.aadidev.foodexpirytracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    exports com.aadidev.foodexpirytracker;
    exports com.aadidev.foodexpirytracker.controller;

    opens com.aadidev.foodexpirytracker.controller to javafx.fxml;
}