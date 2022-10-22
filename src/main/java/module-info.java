module com.estudo.esqueleto {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.estudo.esqueleto to javafx.fxml;

    exports com.estudo.esqueleto;
    exports com.estudo.esqueleto.uts;
}