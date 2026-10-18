module org.example.estudio5 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.estudio5 to javafx.fxml;
    exports org.example.estudio5;
}