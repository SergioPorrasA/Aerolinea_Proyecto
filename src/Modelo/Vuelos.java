/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;
//import VuelosCrudModelo.*;
//import UsCrudModelo.*;
/**
 *
 * @author itzel
 */
public class Vuelos {
    private int id_vuelo;
    private String origen; 
    private String destino; 
    private String precio; 
    private String fecha; 
    private String hora; 
    private String disponibilidad; 

    public Vuelos(){
        id_vuelo = 0;
        origen = "";
        destino = "";
        precio = "";
        fecha = "";
        hora = "";
        disponibilidad = "";
    }
    public int getId_vuelo() {
        return id_vuelo;
    }
    public void setId_vuelo(int id_vuelo) {
        this.id_vuelo = id_vuelo;
    }
    public String getOrigen() {
        return origen;
    }
    public void setOrigen(String origen) {
        this.origen = origen;
    }
    public String getDestino() {
        return destino;
    }
    public void setDestino(String destino) {
        this.destino = destino;
    }
    public String getPrecio() {
        return precio;
    }
    public void setPrecio(String precio) {
        this.precio = precio;
    }
    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public String getHora() {
        return hora;
    }
    public void setHora(String hora) {
        this.hora = hora;
    }
    public String getDisponibilidad() {
        return disponibilidad;
    }
    public void setDisponibilidad(String disponibilidad) {
        this.disponibilidad = disponibilidad;
    }   
}
