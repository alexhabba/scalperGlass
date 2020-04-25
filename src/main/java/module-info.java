module pac {
    requires javafx.controls;
    requires javafx.fxml;

    opens pac to javafx.fxml;
    exports pac;
}