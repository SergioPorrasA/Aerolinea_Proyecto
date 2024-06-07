/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;
import Vista.CrudVistaUsuarios; // Vista para operaciones CRUD de usuarios
import Modelo.*; // Todas las clases del paquete Modelo
import java.awt.event.ActionEvent; // Eventos de acción
import java.awt.event.ActionListener; // Escucha eventos de acción
import java.awt.event.KeyEvent; // Eventos de teclado
import java.awt.event.KeyListener; // Escucha eventos de teclado
import java.util.ArrayList; // Listas dinámicas
import javax.swing.JOptionPane; // Cuadros de diálogo
import javax.swing.JTable; // Tablas en Swing
import javax.swing.table.DefaultTableModel; // Modelo de datos de una tabla en Swing


/**
 *
 * @author itzel
 */
public class ControladorCrudUs implements ActionListener, KeyListener {

    // Declaración de variables
    CrudVistaUsuarios vistaCrud = new CrudVistaUsuarios(); // Vista de la aplicación
    UsuarioDAO modelOCrud = new UsuarioDAO(); // Modelo de datos para CRUD de usuarios

    // Constructor
    public ControladorCrudUs(CrudVistaUsuarios vistaCrud, UsuarioDAO modeloCrud) {
        // Inicializar la vista y el modelo
        this.vistaCrud = vistaCrud;
        this.modelOCrud = modeloCrud;

        // Asignar listeners a los botones y campos de texto
        this.vistaCrud.btnRegistrar.addActionListener(this);
        this.vistaCrud.btnListar.addActionListener(this);
        this.vistaCrud.btnEliminar.addActionListener(this);
        this.vistaCrud.btnEditar.addActionListener(this);
        this.vistaCrud.btnGEdit.addActionListener(this);
        this.vistaCrud.txtBusqueda.addKeyListener(this);
        this.vistaCrud.txtNombre.addKeyListener(this);
        this.vistaCrud.txtApellido.addKeyListener(this);
        this.vistaCrud.txtContraseña.addKeyListener(this);
        this.vistaCrud.txtTelefono.addKeyListener(this);
        
        
        vistaCrud.btnGEdit.setEnabled(false);
    }

    // Método para inicializar la vista CRUD
    public void InicializarCrud() {
        
    }

    // Método para llenar la tabla de usuarios en la vista
    public void LlenarTabla(JTable tablaD) {
        // Crear un nuevo modelo de tabla
        DefaultTableModel modeloT = new DefaultTableModel();
        tablaD.setModel(modeloT);
        // Agregar columnas a la tabla
        modeloT.addColumn("ID");
        modeloT.addColumn("NOMBRE");
        modeloT.addColumn("APELLIDO");
        modeloT.addColumn("EMAIL");
        modeloT.addColumn("TELEFONO");
        modeloT.addColumn("CONTRASEÑA");
        // Obtener la lista de usuarios del modelo de datos
        ArrayList<Usuario> listaUsuarios = modelOCrud.listUsuario();
        
        // Llenar la tabla con los datos de los usuarios
        for (Usuario usuario : listaUsuarios) {
            Object[] columna = new Object[6]; 
            columna[0] = usuario.getIdUsuario(); 
            columna[1] = usuario.getNombre();
            columna[2] = usuario.getApellidos();
            columna[3] = usuario.getCorreo();
            columna[4] = usuario.getTelefono();
            columna[5] = usuario.getContraseña();
            modeloT.addRow(columna);
        }
    }

    // Método para limpiar los campos de texto en la vista
    public void Limpiar(){
        // Limpiar los campos de texto
        vistaCrud.txtNombre.setText("");
        vistaCrud.txtApellido.setText("");
        vistaCrud.txtEmail.setText("");
        vistaCrud.txtTelefono.setText("");
        vistaCrud.txtContraseña.setText("");        
    }

    // Método que maneja las acciones de los botones
    public void actionPerformed(ActionEvent e) {
        // Acción de registrar un nuevo usuario
        if (e.getSource() == vistaCrud.btnRegistrar) {
            // Obtener los datos de los campos de texto
            String nombre = vistaCrud.txtNombre.getText();
            String apellido = vistaCrud.txtApellido.getText();
            String telefono = vistaCrud.txtTelefono.getText();
            String correo = vistaCrud.txtEmail.getText();
            String contraseña = vistaCrud.txtContraseña.getText();
            
            // Insertar el usuario en la base de datos
            String rptaRegistro = modelOCrud.insertUsuario(nombre, apellido, telefono, correo, contraseña);

            // Mostrar mensaje de confirmación
            if (rptaRegistro != null) {
                JOptionPane.showMessageDialog(null, rptaRegistro);
            } else {
                JOptionPane.showMessageDialog(null, "Registro con errores.");
            }
            // Actualizar la tabla de usuarios y limpiar los campos
            LlenarTabla(vistaCrud.jtDatos);
            Limpiar();  
        }
        
        // Acción de listar todos los usuarios
        if (e.getSource() == vistaCrud.btnListar) {
            LlenarTabla(vistaCrud.jtDatos);
        }
        
        // Acción de editar un usuario
        if (e.getSource() == vistaCrud.btnEditar) {
            // Obtener la fila seleccionada en la tabla
            int filaEditar = vistaCrud.jtDatos.getSelectedRow();
            int numFS = vistaCrud.jtDatos.getSelectedRowCount();
            
            // Verificar si se seleccionó una fila válida
            if (filaEditar >= 0 && numFS == 1) {
                // Obtener los datos de la fila seleccionada y mostrarlos en los campos de edición
                vistaCrud.txtid.setText(String.valueOf(vistaCrud.jtDatos.getValueAt(filaEditar, 0)));
                vistaCrud.txtNombre.setText(String.valueOf(vistaCrud.jtDatos.getValueAt(filaEditar, 1)));
                vistaCrud.txtApellido.setText(String.valueOf(vistaCrud.jtDatos.getValueAt(filaEditar, 2)));
                vistaCrud.txtEmail.setText(String.valueOf(vistaCrud.jtDatos.getValueAt(filaEditar, 3)));
                vistaCrud.txtTelefono.setText(String.valueOf(vistaCrud.jtDatos.getValueAt(filaEditar, 4)));
                vistaCrud.txtContraseña.setText(String.valueOf(vistaCrud.jtDatos.getValueAt(filaEditar, 5)));
                // Deshabilitar botones y campos para evitar ediciones múltiples
                vistaCrud.btnRegistrar.setEnabled(false);
                vistaCrud.btnEliminar.setEnabled(false);
                vistaCrud.btnEditar.setEnabled(false);
                vistaCrud.txtBusqueda.setEnabled(false);
                vistaCrud.btnGEdit.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(null,"Debes seleccionar una fila");
            }
        }
        
        // Acción de guardar la edición de un usuario
        if (e.getSource() == vistaCrud.btnGEdit) {
            // Obtener los datos editados
            String nombre = vistaCrud.txtNombre.getText();
            String apellido = vistaCrud.txtApellido.getText();
            String correo = vistaCrud.txtEmail.getText();
            String contraseña = vistaCrud.txtContraseña.getText();
            String telefono = vistaCrud.txtTelefono.getText();
            int id = Integer.parseInt(vistaCrud.txtid.getText());            
            // Realizar la edición en la base de datos
            int rptEdit = modelOCrud.editarrUsuario(id, nombre, apellido, telefono, correo, contraseña);
            // Mostrar mensaje de confirmación
            if (rptEdit > 0) {
                JOptionPane.showMessageDialog(null, "Edición exitosa");
                LlenarTabla(vistaCrud.jtDatos);
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo realizar la edición");
            }          
            // Limpiar campos y habilitar botones y campos para nuevas ediciones
            Limpiar();
            vistaCrud.btnRegistrar.setEnabled(true);
            vistaCrud.btnEliminar.setEnabled(true);
            vistaCrud.btnEditar.setEnabled(true);
            vistaCrud.txtBusqueda.setEnabled(true);
            vistaCrud.btnGEdit.setEnabled(false);    
        }
        
        // Acción de eliminar usuario(s)
        if (e.getSource() == vistaCrud.btnEliminar) {
            // Obtener la fila seleccionada en la tabla
            int filaInicio = vistaCrud.jtDatos.getSelectedRow();
            int numFilas = vistaCrud.jtDatos.getSelectedRowCount();
            ArrayList<Integer> listaIdsUsuarios = new ArrayList<>();
            int idUsuario = 0;
            // Verificar si se seleccionó al menos una fila
            if (filaInicio >= 0) {
                // Obtener los IDs de los usuarios seleccionados
                for (int i = 0; i < numFilas; i++) {
                    idUsuario = Integer.parseInt(String.valueOf(vistaCrud.jtDatos.getValueAt(i + filaInicio, 0)));
                    listaIdsUsuarios.add(idUsuario);
                }
                // Iterar sobre los IDs de usuario y eliminar cada uno
                for (int id : listaIdsUsuarios) {
                    modelOCrud.eliminarUsuario(id);
                }
            } else {
                JOptionPane.showMessageDialog(null,"Debes seleccionar una fila");
            }
            // Volver a llenar la tabla después de eliminar los usuarios
            LlenarTabla(vistaCrud.jtDatos);
        }
    }

    // Método de escucha para eventos de teclado
    @Override
    public void keyTyped(KeyEvent e) {
        // Validar entrada de texto en campos específicos (solo números en teléfono y contraseña)
        if (e.getSource() == vistaCrud.txtTelefono) {
            if (vistaCrud.txtTelefono.getText().length() >= 10) {
                e.consume();
                return;
            }
            char c = e.getKeyChar();
            if (c < '0' || c > '9') {
                e.consume();
            }
        } 
        
            if (e.getSource() == vistaCrud.txtContraseña) {
            if (vistaCrud.txtContraseña.getText().length() >= 4) {
                e.consume();
                return;
            }
            char c = e.getKeyChar();
            if (c < '0' || c > '9') {
                e.consume();
            }
        }
        // Validar entrada de texto en campos de nombre y apellido (solo letras y espacios)
        if(e.getSource() == vistaCrud.txtNombre || e.getSource() == vistaCrud.txtApellido) {
            char c = e.getKeyChar();
            if((c < 'a' || c > 'z') && (c < 'A' || c > 'Z') && (c != (char)KeyEvent.VK_SPACE)) {
                e.consume();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // 
    }

    // Método para buscar usuarios por apellido cuando se escribe en el campo de búsqueda
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == vistaCrud.txtBusqueda) {
            String apellidos = vistaCrud.txtBusqueda.getText();
            DefaultTableModel modeloT = new DefaultTableModel();
            vistaCrud.jtDatos.setModel(modeloT);
            modeloT.addColumn("ID");
            modeloT.addColumn("NOMBRE");
            modeloT.addColumn("APELLIDO");
            modeloT.addColumn("EMAIL");
            modeloT.addColumn("TELEFONO");
            modeloT.addColumn("CONTRASEÑA");
            ArrayList<Usuario> listaUsuarios = modelOCrud.buscarUsuarioxApellido(apellidos);
            for (Usuario usuario : listaUsuarios) {
                Object[] columna = new Object[6];
                columna[0] = usuario.getIdUsuario();
                columna[1] = usuario.getNombre();
                columna[2] = usuario.getApellidos();
                columna[3] = usuario.getCorreo();
                columna[4] = usuario.getTelefono();
                columna[5] = usuario.getContraseña();
                modeloT.addRow(columna);
            }
        }
    }

}