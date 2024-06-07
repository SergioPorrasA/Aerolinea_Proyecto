/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mains;

import Vista.vistaPag;
import Modelo.CmModelo;
import Controlador.CmControlador;

/**
 * Clase principal de la aplicación.
 * 
 * @author itzel
 */
public class mainPrinc {
    

        public static void pantprincipal(String[] detallesUsuario){

        // Crear una instancia del modelo
        CmModelo modelo = new CmModelo();
        
        vistaPag vista = new vistaPag();
        // Crear una instancia del controlador y pasar el modelo y la vista
        CmControlador controlador = new CmControlador(modelo, vista);
        
        //rellenar boleto
        
        vista.rellenarBoleto(detallesUsuario[0], detallesUsuario[1]);
        vista.etiquedaUs(detallesUsuario[0]);
            // si es admin mostrar componentes específicos
    if (detallesUsuario[0].equals("ADMIN")) {
        vista.administrador();
    }
        // Mostrar la vista
        vista.setVisible(true);
    }
    
    public static void main(String[] args) {
        pantprincipal(null);
    }

}
    

