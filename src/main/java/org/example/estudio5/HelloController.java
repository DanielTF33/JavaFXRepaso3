package org.example.estudio5;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class HelloController {

    ObservableList<Servidor> listaServidores = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Servidor, String> tcNombre;
    @FXML
    private TableView<Servidor> tbServer;
    @FXML
    private TextField tfCpu;
    @FXML
    private Slider sldTemperatura;
    @FXML
    private Label lblTitulo;
    @FXML
    private TableColumn<Servidor, Integer> tcPuerto;
    @FXML
    private TableColumn<Servidor, Double> tcCpu;
    @FXML
    private Label lblContadorServidores;
    @FXML
    private TableColumn<Servidor, String> tcEstado;
    @FXML
    private TextField tfEstado;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfPuerto;
    @FXML
    private Label lblTemperatura;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnAnadir;
    @FXML
    private ProgressBar pbTemperatura;

    int contador = 0;

    @FXML
    public void initialize() {

        tbServer.setItems(listaServidores);

        tcNombre.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        tcPuerto.setCellValueFactory(cellData -> cellData.getValue().puertoProperty().asObject());
        tcCpu.setCellValueFactory(cellData -> cellData.getValue().usoCpuProperty().asObject());
        tcEstado.setCellValueFactory(cellData -> cellData.getValue().estadoProperty());

        btnEliminar.disableProperty().bind(

                tbServer.getSelectionModel().selectedItemProperty().isNull()

        );


        BooleanBinding datosBien = Bindings.createBooleanBinding(() -> {

            Boolean textoMal = (tfNombre.getText().trim().isEmpty() || tfEstado.getText().trim().isEmpty());

            Boolean numerosBien = true;

            try {

                Integer.parseInt(tfPuerto.getText());
                Double.parseDouble(tfCpu.getText());

            } catch (NumberFormatException nfe) {

                numerosBien = false;

            }

            return !textoMal && numerosBien;

        }, tfNombre.textProperty(), tfPuerto.textProperty(), tfCpu.textProperty(), tfEstado.textProperty()

        );

        btnAnadir.disableProperty().bind(datosBien.not());


        sldTemperatura.valueProperty().addListener((observable, oldV, newV ) -> {

            if (sldTemperatura.getValue() < 30 ) {
                lblTemperatura.setStyle("-fx-text-fill: green");
            } else if (sldTemperatura.getValue() < 50) {
                lblTemperatura.setStyle("-fx-text-fill: lightgreen");
            } else if (sldTemperatura.getValue() < 70) {
                lblTemperatura.setStyle("-fx-text-fill: yellow");
            } else if (sldTemperatura.getValue() < 80) {
                lblTemperatura.setStyle("-fx-text-fill: orange");
            } else {
                lblTemperatura.setStyle("-fx-text-fill: red");
            }

        });



        btnAnadir.setOnMouseClicked(event -> {



        });

        pbTemperatura.progressProperty().bind(sldTemperatura.valueProperty().divide(100));

    }

    @FXML
    public void onEliminar(ActionEvent actionEvent) {

        listaServidores.remove(tbServer.getSelectionModel().getSelectedItem());
        contador--;

        lblContadorServidores.setText(String.valueOf("Numero de Servers: "+contador));

    }

    @FXML
    public void onAnadir(ActionEvent actionEvent) {

        double usoCpu = Double.parseDouble(tfCpu.getText());
        int puerto = Integer.parseInt(tfPuerto.getText());

        listaServidores.add(new Servidor(tfNombre.getText(), puerto, usoCpu, tfEstado.getText()));

        contador++;

        lblContadorServidores.setText(String.valueOf("Numero de Servers: "+contador));

    }
}
