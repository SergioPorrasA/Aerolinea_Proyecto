/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;
//import java.sql.Connection;
//import java.sql.DriverManager;
import conexion.ConexionVuelos; // Conexión a la base de datos
import java.sql.PreparedStatement; // Sentencias SQL precompiladas
import java.sql.SQLException; // Manejo de excepciones SQL
import java.sql.ResultSet; // Resultados de consultas SQL
import javax.swing.JOptionPane; // Cuadros de diálogo
import java.sql.Connection; // Conexión a la base de datos
//import java.sql.Statement;
/**
 *
 * @author itzel
 */
public class ModeloLogin {

    /**
     * 
     * @param 
     */
    public void insertarUsuario(String nombre, String apellidos, String tel_contacto, String email, String contraseña) {
        try {
            // Establecer la conexión con la base de datos
            Connection acceDB = ConexionVuelos.getConexion();

            // Preparar la sentencia SQL para insertar un nuevo usuario
            PreparedStatement ps = acceDB.prepareStatement("INSERT INTO usuario (nombre, apellido, contacto, email, contraseña) VALUES (?, ?, ?, ?, ?)");

            // Asignar valores a los parámetros de la sentencia SQL
            ps.setString(1, nombre);
            ps.setString(2, apellidos);
            ps.setString(3, tel_contacto);
            ps.setString(4, email);
            ps.setString(5, contraseña);

            // Ejecutar la sentencia SQL
            ps.executeUpdate();

            // Mostrar mensaje de éxito
            JOptionPane.showMessageDialog(null, "Usuario registrado exitosamente");
        } catch (SQLException ex) {
            // Mostrar mensaje de error en caso de fallo en la conexión o ejecución
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos: " + ex.getMessage());
        }
    }

    // Un arreglo de String con los detalles del usuario (nombre y apellidos) o null si la autenticación falla.
     
    public String[] basedatos(String nombreUsuario, String contraseña) {
        String[] detallesUsuario = new String[2]; // 0: nombre, 1: apellidos

        try {
            // Establecer la conexión con la base de datos
            Connection acceDB = ConexionVuelos.getConexion();

            // Preparar la consulta SQL para buscar al usuario
            String consulta = "SELECT id_usuario, nombre, apellido FROM Usuario WHERE nombre = ? AND contraseña = ?";
            PreparedStatement statement = acceDB.prepareStatement(consulta);
            statement.setString(1, nombreUsuario);
            statement.setString(2, contraseña);

            // Ejecutar la consulta y obtener el resultado
            ResultSet resultado = statement.executeQuery();

            if (resultado.next()) {
                // Si se encuentra el usuario, obtener los detalles
                detallesUsuario[0] = resultado.getString("nombre");
                detallesUsuario[1] = resultado.getString("apellido");
                JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso");
            } else {
                // Si no se encuentra el usuario, mostrar mensaje de error
                JOptionPane.showMessageDialog(null, "Nombre de usuario o contraseña incorrectos");
                detallesUsuario = null;
            }
            // Cerrar el ResultSet y el Statement
            resultado.close();
            statement.close();
        } catch (SQLException ex) {
            // Mostrar mensaje de error en caso de fallo en la conexión o ejecución
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos: " + ex.getMessage());
            detallesUsuario = null;
        }

        return detallesUsuario;
    }

   //El ID del usuario o -1 si no se encuentra.    
    public int obtenerIdUsuario(String nombreUsuario) {
        int idUsuario = -1;
        try {
            // Establecer la conexión con la base de datos
            Connection acceDB = ConexionVuelos.getConexion();
            // Preparar la consulta SQL para obtener el ID del usuario
            String consulta = "SELECT id_usuario FROM Usuario WHERE nombre = ?";
            PreparedStatement statement = acceDB.prepareStatement(consulta);
            statement.setString(1, nombreUsuario);
            // Ejecutar la consulta y obtener el resultado
            ResultSet resultado = statement.executeQuery();
            if (resultado.next()) {
                // Si se encuentra el usuario, obtener su ID
                idUsuario = resultado.getInt("id_usuario");
            }
            // Cerrar el ResultSet y el Statement
            resultado.close();
            statement.close();
            acceDB.close();
        } catch (SQLException ex) {
            // Mostrar mensaje de error en caso de fallo en la conexión o ejecución
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos: " + ex.getMessage());
        }
        return idUsuario;
    }
}
