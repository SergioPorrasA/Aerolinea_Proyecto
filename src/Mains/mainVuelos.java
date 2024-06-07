/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mains;
import Vista.CrudVistaVuelos;
import Controlador.ControladorCrud_Vuelos;
import Modelo.*;

/**
 *
 * @author itzel
 */
public class mainVuelos {
    
    public static void main(String[] args) {
        mainVuelos crud = new mainVuelos(); // Crear una instancia de la clase CrudMvc
        crud.crudus(); // Llamar al método crudus() desde la instancia creada
    }

    public void crudus() {
        CrudVistaVuelos vistaC = new CrudVistaVuelos();
        VuelosDAO modeloc = new VuelosDAO();
        ControladorCrud_Vuelos controlaC = new ControladorCrud_Vuelos(vistaC, modeloc);
        
        vistaC.setVisible(true);
        vistaC.setLocationRelativeTo(null);
    }
}
