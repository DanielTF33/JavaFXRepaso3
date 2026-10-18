package org.example.estudio5;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;

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
    @FXML
    private BorderPane bpTODO;

    int contador = 0;
    String titulo = "";


    @FXML
    public void initialize() {

        // conecto la lista con la tabla
        tbServer.setItems(listaServidores);

        // le digo que contenido tiene que tener cada columna
        tcNombre.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());

        // los int o los double necesitan tener el asObject
        tcPuerto.setCellValueFactory(cellData -> cellData.getValue().puertoProperty().asObject());
        tcCpu.setCellValueFactory(cellData -> cellData.getValue().usoCpuProperty().asObject());
        tcEstado.setCellValueFactory(cellData -> cellData.getValue().estadoProperty());

        // el boton estara desactivado si no hay ningun servidor seleccionado
        btnEliminar.disableProperty().bind(

                tbServer.getSelectionModel().selectedItemProperty().isNull()

        );


        // este boolBinding lo que hace es comprobar que todos los tf estan bien
        BooleanBinding datosBien = Bindings.createBooleanBinding(() -> {

            // si el nombre o el estado estan vacios devuelve true
            Boolean textoMal = (tfNombre.getText().trim().isEmpty() || tfEstado.getText().trim().isEmpty());

            // creo variable
            Boolean numerosBien = true;

            try {

                // paso el contenido del tf a numero, si no es un digito
                Integer.parseInt(tfPuerto.getText());
                Double.parseDouble(tfCpu.getText());

            } catch (NumberFormatException nfe) {

                // da esta excepcion y retorno false
                numerosBien = false;

            }

            // retorno textos mal ! pq devuelve true cuando esta vacio
            return !textoMal && numerosBien;

            // recalcula la condicion si cualquiera de estos cambia
        }, tfNombre.textProperty(), tfPuerto.textProperty(), tfCpu.textProperty(), tfEstado.textProperty()

        );

        // como ya he hecho el boolBinding, desactivo el boton cuando los datos NO estan bien
        btnAnadir.disableProperty().bind(datosBien.not());

        // escucha los cambios del valor del slider
        sldTemperatura.valueProperty().addListener((observable, oldV, newV ) -> {

            // cambio el color una tonteria pero para que sepa que puedo tocar estilos desde aqui
            if (sldTemperatura.getValue() == 0) {
                lblTemperatura.setStyle("-fx-text-fill: black");
            } else if (sldTemperatura.getValue() < 30 ) {
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


        // evento que ocurre si clickas el progressbar
        pbTemperatura.setOnMouseClicked(event -> {
            titulo = lblTitulo.getText();
            lblTitulo.setText("HAS PULSADO EL PROGRESSBAR JAJAJAJ");
        });

        // y si haces escape en all el borderPane pongo el otro texto al titulo
        bpTODO.setOnKeyPressed(event -> {

            if (event.getCode() == KeyCode.ESCAPE) {
                lblTitulo.setText("SERVEROPS - Despues de Clickar");
            }

        });

        // enlaza el progress bar con lo del slider
        // y como el slider va de 0 a 100 divides el resultado entre 100 pq el progressBar va de 0 a 1
        pbTemperatura.progressProperty().bind(sldTemperatura.valueProperty().divide(100));

    }

    // metodo que le he puesto al btnELiminar
    @FXML
    public void onEliminar(ActionEvent actionEvent) {

        // busca el item seleccionado en la lista y lo elimina
        listaServidores.remove(tbServer.getSelectionModel().getSelectedItem());
        contador--;

        lblContadorServidores.setText(String.valueOf("Numero de Servers: "+contador));

    }

    // metodo que le he puesto al btnAnadir
    @FXML
    public void onAnadir(ActionEvent actionEvent) {

        // convertimos de string a ints y doubles
        double usoCpu = Double.parseDouble(tfCpu.getText());
        int puerto = Integer.parseInt(tfPuerto.getText());

        // anadimos el nuevo server con los datos del textfield
        listaServidores.add(new Servidor(tfNombre.getText(), puerto, usoCpu, tfEstado.getText()));

        contador++;

        lblContadorServidores.setText(String.valueOf("Numero de Servers: "+contador));

    }
}
