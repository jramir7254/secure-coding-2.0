module com.hacktheborder {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.web;
    requires java.sql;
    requires javafx.base;
    opens com.hacktheborder to javafx.fxml;
    exports com.hacktheborder;
    opens com.hacktheborder.controller to javafx.fxml;
    exports com.hacktheborder.controller;
}
