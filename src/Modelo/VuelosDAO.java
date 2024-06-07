/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;
//import Modelo.Vuelos; // Importación del modelo Vuelos
import conexion.ConexionVuelos; // Importación de la clase de conexión
import java.sql.*;
import java.util.ArrayList;

public class VuelosDAO {

    public VuelosDAO() {
    }
    // Método para insertar un nuevo vuelo en la base de datos
    public String insertVuelos(String origen, String destino, String precio, String fecha, String hora, String disponibilidad) {
        String rptaRegistro = null;
        try {
            Connection acceDB = ConexionVuelos.getConexion(); // Obtener conexión a la base de datos
            CallableStatement cs = acceDB.prepareCall("{call insertarVuelo(?,?,?,?,?,?)}"); // Llamada al procedimiento almacenado
            cs.setString(1, origen); // Establecer parámetros para la llamada
            cs.setString(2, destino);
            cs.setString(3, precio);
            cs.setString(4, fecha);
            cs.setString(5, hora);
            cs.setString(6, disponibilidad);
            int numFafectas = cs.executeUpdate(); // Ejecutar la llamada
            if (numFafectas > 0) {
                // Obtener el ID del vuelo recién insertado
                Statement stmt = acceDB.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");
                int vueloId = 0;
                if (rs.next()) {
                    vueloId = rs.getInt(1);
                }
                // Insertar registros en la tabla detalle_vuelo para los asientos disponibles
                int disponibilidadInt = Integer.parseInt(disponibilidad);
                for (int i = 0; i < disponibilidadInt; i++) {
                    PreparedStatement psDetalle = acceDB.prepareStatement("INSERT INTO detalle_vuelo (id_vuelo, asientos, estado) VALUES (?, ?, ?)");
                    psDetalle.setInt(1, vueloId);
                    psDetalle.setInt(2, i + 1);  // Asiento 
                    psDetalle.setInt(3, 1);  // Disponibilidad
                    psDetalle.executeUpdate();
                }
                rptaRegistro = "Registro exitoso.";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rptaRegistro;
    }

    // Método para obtener una lista de todos los vuelos almacenados en la base de datos
    public ArrayList<Vuelos> listVuelos() {
        ArrayList<Vuelos> listaVuelos = new ArrayList<>();
        Vuelos vuelo;
        try {
            Connection acceDB = ConexionVuelos.getConexion(); // Obtener conexión a la base de datos
            PreparedStatement ps = acceDB.prepareStatement("SELECT * FROM vuelo"); // Consulta SQL para obtener todos los vuelos
            ResultSet rs = ps.executeQuery(); // Ejecutar la consulta
            while (rs.next()) {
                vuelo = new Vuelos(); // Crear objeto Vuelos para almacenar los datos de cada vuelo
                vuelo.setId_vuelo(rs.getInt("id_vuelo"));
                vuelo.setOrigen(rs.getString("origen"));
                vuelo.setDestino(rs.getString("destino"));
                vuelo.setPrecio(rs.getString("precio"));
                vuelo.setFecha(rs.getString("fecha"));
                vuelo.setHora(rs.getString("hora"));
                vuelo.setDisponibilidad(rs.getString("disponibilidad"));
                listaVuelos.add(vuelo); // Agregar el vuelo a la lista
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaVuelos; // Devolver la lista de vuelos
    }

    // Método para editar la información de un vuelo existente en la base de datos
    public int editarVuelo(int id, String origen, String destino, String precio, String fecha, String hora, String disponibilidad) {
        int numFA = 0;
        try {
            Connection acceDB = ConexionVuelos.getConexion(); // Obtener conexión a la base de datos
            CallableStatement cs = acceDB.prepareCall("{call editarVuelo(?, ?, ?, ?, ?, ?, ?)}"); // Llamada al procedimiento almacenado
            cs.setInt(1, id); // Establecer parámetros para la llamada
            cs.setString(2, origen);
            cs.setString(3, destino);
            cs.setString(4, precio);
            cs.setString(5, fecha);
            cs.setString(6, hora);
            cs.setString(7, disponibilidad);
            numFA = cs.executeUpdate(); // Ejecutar la llamada y obtener el número de filas afectadas
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numFA; // Devolver el número de filas afectadas
    }

    // Método para eliminar un vuelo de la base de datos según su ID
    public int eliminarVuelo(int id) {
        int numFA = 0;
        try {
            Connection acceDB = ConexionVuelos.getConexion(); // Obtener conexión a la base de datos
            CallableStatement cs = acceDB.prepareCall("{call eliminarVuelo(?)}"); // Llamada al procedimiento almacenado
            cs.setInt(1, id); // Establecer parámetro para la llamada
            numFA = cs.executeUpdate(); // Ejecutar la llamada y obtener el número de filas afectadas
        } catch (Exception e) {
            e.printStackTrace();
        }
        return numFA; // Devolver el número de filas afectadas
    }

    // Método para buscar vuelos por su origen
    public ArrayList<Vuelos> buscarVueloPorOrigen(String origen) {
        ArrayList<Vuelos> listaVuelos = new ArrayList<>();
        Vuelos vuelo;
        try {
            Connection acceDB = ConexionVuelos.getConexion(); // Obtener conexión a la base de datos
            CallableStatement cs = acceDB.prepareCall("{call buscarVueloPorOrigen(?)}"); // Llamada al procedimiento almacenado
            cs.setString(1, origen); // Establecer parámetro para la llamada
            ResultSet rs = cs.executeQuery(); // Ejecutar la llamada
            while (rs.next()) {
                vuelo = new Vuelos(); // Crear objeto Vuelos para almacenar los datos de cada vuelo
                vuelo.setId_vuelo(rs.getInt("id_vuelo"));
                vuelo.setOrigen(rs.getString("origen"));
                vuelo.setDestino(rs.getString("destino"));
                vuelo.setPrecio(rs.getString("precio"));
                vuelo.setFecha(rs.getString("fecha"));
                vuelo.setHora(rs.getString("hora"));
                vuelo.setDisponibilidad(rs.getString("disponibilidad"));
                listaVuelos.add(vuelo); // Agregar el vuelo a la lista
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaVuelos; // Devolver la lista de vuelos
    }
}