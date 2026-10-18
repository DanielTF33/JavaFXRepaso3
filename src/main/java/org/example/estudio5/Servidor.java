package org.example.estudio5;

import javafx.beans.property.*;

public class Servidor {

    private StringProperty nombre = new SimpleStringProperty(this, "nombre", "");
    private IntegerProperty puerto = new SimpleIntegerProperty(this, "puerto", 0);
    private DoubleProperty usoCpu = new SimpleDoubleProperty(this, "usoCpu", 0.0);
    private StringProperty estado = new SimpleStringProperty(this, "estado", "");


    public Servidor(String nombre, Integer puerto, Double usoCpu, String estado) {
        setNombre(nombre);
        setPuerto(puerto);
        setUsoCpu(usoCpu);
        setEstado(estado);
    }

    public String getNombre() {
        return nombre.get();
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public int getPuerto() {
        return puerto.get();
    }

    public IntegerProperty puertoProperty() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto.set(puerto);
    }

    public double getUsoCpu() {
        return usoCpu.get();
    }

    public DoubleProperty usoCpuProperty() {
        return usoCpu;
    }

    public void setUsoCpu(double usoCpu) {
        this.usoCpu.set(usoCpu);
    }

    public String getEstado() {
        return estado.get();
    }

    public StringProperty estadoProperty() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado.set(estado);
    }
}
