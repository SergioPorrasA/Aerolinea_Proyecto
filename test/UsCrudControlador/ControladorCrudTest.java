/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package UsCrudControlador;

import Controlador.ControladorCrudUs;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author itzel
 */
public class ControladorCrudTest {
    
    public ControladorCrudTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of InicializarCrud method, of class ControladorCrud.
     */
    @Test
    public void testInicializarCrud() {
        System.out.println("InicializarCrud");
        ControladorCrudUs instance = null;
        instance.InicializarCrud();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of LlenarTabla method, of class ControladorCrud.
     */
    @Test
    public void testLlenarTabla() {
        System.out.println("LlenarTabla");
        JTable tablaD = null;
        ControladorCrudUs instance = null;
        instance.LlenarTabla(tablaD);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of Limpiar method, of class ControladorCrud.
     */
    @Test
    public void testLimpiar() {
        System.out.println("Limpiar");
        ControladorCrudUs instance = null;
        instance.Limpiar();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of actionPerformed method, of class ControladorCrud.
     */
    @Test
    public void testActionPerformed() {
        System.out.println("actionPerformed");
        ActionEvent e = null;
        ControladorCrudUs instance = null;
        instance.actionPerformed(e);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
