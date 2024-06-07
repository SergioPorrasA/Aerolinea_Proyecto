
import java.sql.Connection;
import java.sql.DriverManager;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author itzel
 */
public class ConexionVuelos {
    public ConexionVuelos(){
    }
    
    public Connection getConexion(){
    Connection con = null;

    try {
    Class. forName("com.mysql.cj.jdbc.Driver") .newInstance();
    con = DriverManager.getConnection ("jdbc:mysql://localhost: 3306/aerolinea", "root", "12345");
    } 
      catch (Exception e) {
            
            e.printStackTrace(); 
        }
        return con;
    }
}
