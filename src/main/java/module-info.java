module com.hacktheborder {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.fxmisc.richtext;
    opens com.hacktheborder to javafx.fxml;
    exports com.hacktheborder;
}
