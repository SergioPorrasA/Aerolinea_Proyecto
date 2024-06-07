/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Vista.CrudVistaVuelos; // Vista para CRUD de vuelos
import Modelo.*; // Todas las clases del paquete Modelo
import java.awt.event.ActionEvent; // Eventos de acción
import java.awt.event.ActionListener; // Escucha eventos de acción
import java.awt.event.KeyEvent; // Eventos de teclado
import java.awt.event.KeyListener; // Escucha eventos de teclado
import java.text.SimpleDateFormat; // Formateo de fechas y horas
import java.util.ArrayList; // Listas dinámicas
import javax.swing.JOptionPane; // Cuadros de diálogo
import javax.swing.JTable; // Tablas en Swing
import javax.swing.table.DefaultTableModel; // Modelo de datos de una tabla en Swing


/**
 *
 * @author itzel
 */
public class ControladorCrud_Vuelos implements ActionListener, KeyListener {   
    // Declaración de variables para la vista y el modelo de datos
    CrudVistaVuelos vistaCrud = new CrudVistaVuelos();
    VuelosDAO modelOCrud = new VuelosDAO();

    // Constructor
    public ControladorCrud_Vuelos(CrudVistaVuelos vistaCrud, VuelosDAO modeloCrud) {
        // Inicialización de las variables
        this.vistaCrud = vistaCrud;
        this.modelOCrud = modeloCrud;
        // Registro de componentes de la interfaz para escuchar eventos de acción
        this.vistaCrud.btnRegistrar.addActionListener(this);
        this.vistaCrud.btnListar.addActionListener(this);
        this.vistaCrud.btnEliminar.addActionListener(this);
        this.vistaCrud.btnEditar.addActionListener(this);
        this.vistaCrud.btnGEdit.addActionListener(this);     
        // Registro de componentes de la interfaz para escuchar eventos de teclado
        this.vistaCrud.txtBusqueda.addKeyListener(this);
        this.vistaCrud.txtOrigen.addKeyListener(this);
        this.vistaCrud.txtDestino.addKeyListener(this);
        this.vistaCrud.txtDisponibilidad.addKeyListener(this);
        this.vistaCrud.txtHora.addKeyListener(this);
        this.vistaCrud.txtPrecio.addKeyListener(this);
        
        // Deshabilitar el botón de edición al inicio
        vistaCrud.btnGEdit.setEnabled(false);
    }

    // Método para inicializar el CRUD
    public void InicializarCrud() {
        
    }

    // Método para llenar la tabla con los vuelos
    public void LlenarTabla(JTable tablaD) {
        // Crear un modelo de tabla
        DefaultTableModel modeloT = new DefaultTableModel();
        tablaD.setModel(modeloT);
        // Agregar columnas a la tabla
        modeloT.addColumn("ID"); 
        modeloT.addColumn("ORIGEN");
        modeloT.addColumn("DESTINO");
        modeloT.addColumn("PRECIO");
        modeloT.addColumn("FECHA");
        modeloT.addColumn("HORA");
        modeloT.addColumn("DISPONIBILIDAD");
        // Obtener lista de vuelos del modelo de datos
        ArrayList<Vuelos> listaVuelos = modelOCrud.listVuelos();      
        // Recorrer la lista de vuelos y agregar cada vuelo como una fila en la tabla
        for (Vuelos vuelo : listaVuelos) {
            Object[] columna = new Object[7]; 
            columna[0] = vuelo.getId_vuelo(); 
            columna[1] = vuelo.getOrigen();
            columna[2] = vuelo.getDestino();
            columna[3] = vuelo.getPrecio();
            columna[4] = vuelo.getFecha();
            columna[5] = vuelo.getHora();
            columna[6] = vuelo.getDisponibilidad();
            modeloT.addRow(columna);
        }
    }
     
    // Método para limpiar los campos de entrada de la interfaz
    public void Limpiar() {
        vistaCrud.txtOrigen.setText("");
        vistaCrud.txtDestino.setText("");
        vistaCrud.txtPrecio.setText("");
        vistaCrud.txtHora.setText("");
        vistaCrud.txtDisponibilidad.setText("");
        vistaCrud.jDate.setDate(null);
    }
    
    // Método para manejar eventos de acción
    public void actionPerformed(ActionEvent e) {
        // Acciones realizadas al hacer clic en el botón "Registrar"
        if (e.getSource() == vistaCrud.btnRegistrar) {
            // Obtener datos de los campos de la interfaz
            String origen = vistaCrud.txtOrigen.getText();
            String destino = vistaCrud.txtDestino.getText();
            String precio = vistaCrud.txtPrecio.getText();
            SimpleDateFormat formatofecha = new SimpleDateFormat("yyyy-MM-dd");
            String fecha = formatofecha.format(vistaCrud.jDate.getDate());
            String hora = vistaCrud.txtHora.getText();
            // Asignar disponibilidad fija
            String disponibilidad = "15";
            // Validar que la fecha no esté vacía
            if (fecha == null || fecha.isEmpty()) {
                JOptionPane.showMessageDialog(null, "La fecha no puede estar vacía");
                return;
            }
            // Insertar vuelo en el modelo de datos
            String rptaRegistro = modelOCrud.insertVuelos(origen, destino, precio, fecha, hora, disponibilidad);
            // Mostrar mensaje según resultado de la operación
            if (rptaRegistro != null) {
                JOptionPane.showMessageDialog(null, rptaRegistro);
            } else {
                JOptionPane.showMessageDialog(null, "Registro con errores.");
            }
            // Actualizar la tabla y limpiar los campos de la interfaz
            LlenarTabla(vistaCrud.jtDatos);
            //modelOCrud.rellenarComboBoxOrigenes(vistaCrud.cboOrigen);
            Limpiar();
        }
        
        // Acciones realizadas al hacer clic en el botón "Listar"
        if (e.getSource() == vistaCrud.btnListar) {
            // Actualizar la tabla con los vuelos
            LlenarTabla(vistaCrud.jtDatos);
        }

        // Acciones realizadas al hacer clic en el botón "Editar"
        if(e.getSource()==vistaCrud.btnEditar){
            // Obtener la fila seleccionada para edición
            int filaEditar = vistaCrud.jtDatos.getSelectedRow();
            int numFS = vistaCrud.jtDatos.getSelectedRowCount();
            
            // Verificar si se seleccionó una fila para editar
            if(filaEditar >= 0 && numFS == 1) {
                // Obtener los datos de la fila seleccionada y cargarlos en los campos de la interfaz
                vistaCrud.txtid.setText(String.valueOf(vistaCrud.jtDatos.getValueAt(filaEditar, 0)));
                vistaCrud.txtOrigen.setText(String.valueOf(vistaCrud.jtDatos.getValueAt(filaEditar, 1)));
                vistaCrud.txtDestino.setText(String.valueOf(vistaCrud.jtDatos.getValueAt(filaEditar, 2)));
                vistaCrud.txtPrecio.setText(String.valueOf(vistaCrud.jtDatos.getValueAt(filaEditar, 3)));
                String fechaStr = String.valueOf(vistaCrud.jtDatos.getValueAt(filaEditar, 4));
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    java.util.Date fecha = dateFormat.parse(fechaStr);
                    vistaCrud.jDate.setDate(fecha);
                } catch (java.text.ParseException ex) {
                    ex.printStackTrace();
                }
                vistaCrud.txtHora.setText(String.valueOf(vistaCrud.jtDatos.getValueAt(filaEditar, 5)));
                vistaCrud.txtDisponibilidad.setText(String.valueOf(vistaCrud.jtDatos.getValueAt(filaEditar, 6)));
                
                // Deshabilitar botones y campos de la interfaz según sea necesario
                vistaCrud.btnRegistrar.setEnabled(false);
                vistaCrud.btnEliminar.setEnabled(false);
                vistaCrud.btnEditar.setEnabled(false);
                vistaCrud.txtBusqueda.setEnabled(false);
                vistaCrud.btnGEdit.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(null, "Debes seleccionar una fila");
            }                    
        }
        
        // Acciones realizadas al hacer clic en el botón "Guardar Edición"
        if (e.getSource() == vistaCrud.btnGEdit) {   
            // Obtener datos de los campos de la interfaz
            String origen = vistaCrud.txtOrigen.getText();
            String destino = vistaCrud.txtDestino.getText();
            String precio = vistaCrud.txtPrecio.getText();
            SimpleDateFormat formatofecha = new SimpleDateFormat("yyyy-MM-dd");
            String fecha = formatofecha.format(vistaCrud.jDate.getDate());
            String hora = vistaCrud.txtHora.getText();
            String disponibilidad = vistaCrud.txtDisponibilidad.getText();
            int id = Integer.parseInt(vistaCrud.txtid.getText());           
            // Editar vuelo en el modelo de datos
            int rptEdit = modelOCrud.editarVuelo(id, origen, destino, precio, fecha, hora, disponibilidad);          
            // Mostrar mensaje según resultado de la operación
            if (rptEdit > 0) {
                JOptionPane.showMessageDialog(null, "Edición exitosa");
                LlenarTabla(vistaCrud.jtDatos);
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo realizar la edición");
            }         
            // Limpiar campos de la interfaz y habilitar botones y campos necesarios
            Limpiar();
            vistaCrud.btnRegistrar.setEnabled(true);
            vistaCrud.btnEliminar.setEnabled(true);
            vistaCrud.btnEditar.setEnabled(true);
            vistaCrud.txtBusqueda.setEnabled(true);
            vistaCrud.btnGEdit.setEnabled(false);   
        }
        
        // Acciones realizadas al hacer clic en el botón "Eliminar"
        if (e.getSource() == vistaCrud.btnEliminar) {
            int filaInicio = vistaCrud.jtDatos.getSelectedRow();
            int numFilas = vistaCrud.jtDatos.getSelectedRowCount();
            ArrayList<Integer> listaIdsUsuarios = new ArrayList<>();
            int idVuelo = 0;
            if (filaInicio >= 0) {
                for (int i = 0; i < numFilas; i++) {
                    idVuelo = Integer.parseInt(String.valueOf(vistaCrud.jtDatos.getValueAt(i + filaInicio, 0)));
                    listaIdsUsuarios.add(idVuelo);
                }
                // Iterar sobre los IDs de vuelo y eliminar cada uno
                for (int id : listaIdsUsuarios) {
                    modelOCrud.eliminarVuelo(id);
                }
            }
            else{
                JOptionPane.showMessageDialog(null,"Debes seleccionar una fila");
            }
            // Volver a llenar la tabla después de eliminar los vuelos
            LlenarTabla(vistaCrud.jtDatos);
        }
    }

    // Método para manejar eventos de tecla presionada
    @Override
    public void keyTyped(KeyEvent e) {
        // Limitar la entrada de texto en campos específicos a números
        if (e.getSource() == vistaCrud.txtPrecio || e.getSource() == vistaCrud.txtDisponibilidad) {
            char c = e.getKeyChar();
            if (c < '0' || c > '9') {
                e.consume();
            }
        } 
        // Limitar la entrada de texto en el campo de hora a números
        if (e.getSource() == vistaCrud.txtHora) {
            char c = e.getKeyChar();
            if (c < '0' || c > '9') {
                e.consume();
            }
        }
        // Limitar la entrada de texto en los campos de origen y destino a letras y espacios
        if (e.getSource() == vistaCrud.txtOrigen || e.getSource() == vistaCrud.txtDestino) {
            char c = e.getKeyChar();
            if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z') && (c != (char) KeyEvent.VK_SPACE)) {
                e.consume();
            }
        }
    }

    // Método para manejar eventos de tecla liberada
    @Override
    public void keyReleased(KeyEvent e) {
        // Realizar búsqueda de vuelos por origen cada vez que se libera una tecla en el campo de búsqueda
        if (e.getSource() == vistaCrud.txtBusqueda) {
            String origen = vistaCrud.txtBusqueda.getText().trim(); // Obtener el texto de búsqueda y quitar espacios en blanco al principio y al final
            DefaultTableModel modeloT = new DefaultTableModel();
            vistaCrud.jtDatos.setModel(modeloT);

            modeloT.addColumn("ID"); 
            modeloT.addColumn("ORIGEN");
            modeloT.addColumn("DESTINO");
            modeloT.addColumn("PRECIO");
            modeloT.addColumn("FECHA");
            modeloT.addColumn("HORA");
            modeloT.addColumn("DISPONIBILIDAD");

            // Obtener los vuelos que coinciden con el origen de búsqueda
            ArrayList<Vuelos> listaVuelosFiltrados = modelOCrud.buscarVueloPorOrigen(origen);
            
            // Agregar vuelos filtrados a la tabla
            for (Vuelos vuelo : listaVuelosFiltrados) {
                Object[] columna = new Object[7]; 

                columna[0] = vuelo.getId_vuelo(); 
                columna[1] = vuelo.getOrigen();
                columna[2] = vuelo.getDestino();
                columna[3] = vuelo.getPrecio();
                columna[4] = vuelo.getFecha();
                columna[5] = vuelo.getHora();
                columna[6] = vuelo.getDisponibilidad();
                modeloT.addRow(columna);
            }
        }
    }
       public void keyPressed(KeyEvent e) {
        // Este método podría contener acciones adicionales según sea necesario
    }
}