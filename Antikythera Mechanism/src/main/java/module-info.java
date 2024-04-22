module com.antikythera_mechanism.simulation {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.antikythera_mechanism.simulation to javafx.fxml;
    exports com.antikythera_mechanism.simulation;
}