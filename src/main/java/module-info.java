module com.hacktheborder {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.fxmisc.richtext;
    requires com.gluonhq.richtextarea;
    requires javafx.web;
    opens com.hacktheborder to javafx.fxml;
    exports com.hacktheborder;
    opens com.hacktheborder.controllers to javafx.fxml;
    exports com.hacktheborder.controllers;
}
