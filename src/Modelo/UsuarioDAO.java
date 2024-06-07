/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;
//import UsCrudModelo.*;
import conexion.ConexionVuelos;
import java.sql.*;
import java.util.ArrayList;
/**
 *
 * @author itzel
 */
public class UsuarioDAO {
    public UsuarioDAO(){
    }
    // Método para insertar un nuevo usuario en la base de datos
    public String insertUsuario(String nombre, String apellido,String contacto, String email,  String contraseña) {
        String rptaRegisto = null;
        try {
            // Obtener conexión 
            Connection acceDB = ConexionVuelos.getConexion();
            // Preparar llamada
            CallableStatement cs = acceDB.prepareCall("{call insertarUsuario(?,?,?,?,?)}");
            // inserción
            cs.setString(1, nombre);
            cs.setString(2, apellido);
            cs.setString(3, contacto);
            cs.setString(4, email);
            cs.setString(5, contraseña);
            // filas afectadas
            int numFafectas = cs.executeUpdate();
            // Verificar
            if (numFafectas > 0) {
                rptaRegisto = "Registro exitoso.";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rptaRegisto;
    }

    // Método para obtener una lista de usuarios desde la base de datos
    public ArrayList<Usuario> listUsuario(){
        ArrayList<Usuario> listaUsuario = new ArrayList<>();
        Usuario usuario;
        try {
            // Obtener conexión 
            Connection acceDB = ConexionVuelos.getConexion();
            // Consulta SQL para obtener todos los usuarios
            PreparedStatement ps = acceDB.prepareStatement("SELECT * FROM usuario");
            ResultSet rs = ps.executeQuery();
            // construir objetos Usuario
            while (rs.next()) {
                usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario")); 
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellidos(rs.getString("apellido"));
                usuario.setCorreo(rs.getString("email"));
                usuario.setContraseña(rs.getString("contraseña"));
                usuario.setTelefono(rs.getString("contacto"));
                // Agregar usuario a la lista
                listaUsuario.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaUsuario;
    }

    // Método para editar un usuario en la base de datos
    public int editarrUsuario(int id, String nombre, String apellido, String contacto, String contraseña, String email) {
        int numFA = 0;
        try {
            // Obtener conexión
            Connection acceDB = ConexionVuelos.getConexion();
            // llamada al procedimiento almacenado
            CallableStatement cs = acceDB.prepareCall("{call editarUsuario(?, ?, ?, ?, ?, ?)}");
            //parámetros para la edición
            cs.setInt(1, id);
            cs.setString(2, nombre);
            cs.setString(3, apellido);
            cs.setString(4, contacto);
            cs.setString(6, contraseña);
            cs.setString(5, email);
            // filas afectadas
            numFA = cs.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numFA;
    }

    // Método para eliminar un usuario de la base de datos
    public int eliminarUsuario(int id){
        int numFA=0;
        try {
            // Obtener conexión
            Connection acceDB = ConexionVuelos.getConexion();
            //llamada al procedimiento almacenado
            CallableStatement cs=acceDB.prepareCall("{call eliminarUsuario(?)}");
            // Establecer parámetro para la eliminación
            cs.setInt(1, id);
            //filas afectadas
            numFA=cs.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return numFA;
    }

    // Método para buscar usuarios por apellido en la base de datos
    public ArrayList<Usuario> buscarUsuarioxApellido(String apellidos) {
        ArrayList<Usuario> listaUsuario = new ArrayList<>();
        Usuario usuario;
        try {
            // Obtener conexión 
            Connection acceDB = ConexionVuelos.getConexion();
            // llamada al procedimiento almacenado
            CallableStatement cs = acceDB.prepareCall("{call buscarUsuarioPorApellido(?)}");
            // parámetro para la búsqueda por apellido
            cs.setString(1, apellidos);
            // Ejecutar
            ResultSet rs = cs.executeQuery();
            // objetos Usuario
            while (rs.next()) {
                usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellidos(rs.getString("apellido"));
                usuario.setCorreo(rs.getString("email"));
                usuario.setTelefono(rs.getString("contacto"));
                usuario.setContraseña(rs.getString("contraseña"));
                // Agregar usuario a la lista
                listaUsuario.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaUsuario;
    }
}
