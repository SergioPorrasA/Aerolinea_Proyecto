/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mains;
import Vista.CrudVistaUsuarios;
import Controlador.ControladorCrudUs;
import Modelo.*;

/**
 *
 * @author itzel
 */
public class mainUsuarios {
    
    public static void main(String[] args) {
        mainUsuarios crud = new mainUsuarios(); // Crear una instancia de la clase CrudMvc
        crud.crudus(); // Llamar al método crudus() desde la instancia creada
    }

    public void crudus() {
        CrudVistaUsuarios vistaC = new CrudVistaUsuarios();
        UsuarioDAO modeloc = new UsuarioDAO();
        ControladorCrudUs controlaC = new ControladorCrudUs(vistaC, modeloc);
        
        vistaC.setVisible(true);
        vistaC.setLocationRelativeTo(null);
    }
}
