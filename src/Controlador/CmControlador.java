package Controlador;

import Modelo.CmModelo;
import Modelo.ModeloLogin;
import Vista.vistaPag;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.itextpdf.text.DocumentException;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

public class CmControlador {
    private CmModelo modelo;
    private vistaPag vista;
    private ModeloLogin modLog;

    public CmControlador(CmModelo modelo, vistaPag vista) {
        this.modelo = modelo;
        this.vista = vista;
        this.modLog = new ModeloLogin();
        rellenarComboBoxOrigenes();
        rellenarComboBoxDestinos();

        vista.btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarVuelos();
            }
        });

        vista.btnFinalizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    generarPDF();
                    actualizarEtiquetas();
                } catch (IOException ex) {
                    Logger.getLogger(CmControlador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        vista.btnMostrarGraficos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarGrafico();
            }
        });
        
        this.vista.agregarListenerBotonMostrarGraficoPorMes(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarGraficoPorMes();
            }
        });
        
       vista.agregarListenerTabla(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting() && vista.tablaVuelos.getSelectedRow() != -1) {
                    vista.btnSiguiente.setEnabled(true);
                    //actualizarEtiquetas(); 
                }
            }
        });
       vista.btnSiguiente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarEtiquetas(); // Mueve la llamada aquí
            }
        });

        vista.jLabelqn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                var rutaArchivo = "C:\\Users\\itzel\\Documents\\NetBeansProjects\\Aerolinea.1.3\\Aerolinea.1.3\\src\\QuienesSomos.txt";
                modelo.mostrarContenidoEnEtiqueta(rutaArchivo, vista.jlabelTxt); 
            }
        });
        vista.jPanelReserva.addMouseListener(new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent e) {
        rellenarComboBoxDestinos();
        rellenarComboBoxOrigenes();
    }
});
    }

    public void buscarVuelos() {
        String origen = vista.cbOrigen.getSelectedItem().toString();
        String destino = vista.cbDestino.getSelectedItem().toString();

        DefaultTableModel modeloTabla = modelo.buscarVuelos(origen, destino);

        vista.tablaVuelos.setModel(modeloTabla);
    }

    public void rellenarComboBoxOrigenes() {
        modelo.rellenarComboBoxOrigenes(vista.cbOrigen);
    }

    public void rellenarComboBoxDestinos() {
        modelo.rellenarComboBoxDestinos(vista.cbDestino);
    }

    public void actualizarDisponibilidadYEstado(int idVuelo, int asientoSeleccionado) {
        try {
            modelo.actualizarDisponibilidadYEstado(idVuelo, asientoSeleccionado);
            JOptionPane.showMessageDialog(vista, "Disponibilidad y estado actualizados.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(vista, "Error al actualizar disponibilidad y estado: " + e.getMessage());
        }
    }

    public void generarRegistroDetalleVenta(int idUsuario, int idVuelo) {
        try {
            modelo.crearRegistroDetalleVenta(idUsuario, idVuelo);
            JOptionPane.showMessageDialog(vista, "Registro de venta creado.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(vista, "Error al crear registro de venta: " + e.getMessage());
        }
    }

   
    private void actualizarEtiquetas() {
        int selectedRow = vista.tablaVuelos.getSelectedRow();
        if (selectedRow != -1) {
            DefaultTableModel model = (DefaultTableModel) vista.tablaVuelos.getModel();
            vista.lblVuelo.setText(model.getValueAt(selectedRow, 0).toString());
            vista.lblOrigen.setText(model.getValueAt(selectedRow, 1).toString());
            vista.lblDestino.setText(model.getValueAt(selectedRow, 2).toString());
            vista.lblFecha.setText(model.getValueAt(selectedRow, 4).toString());
            vista.lblHora.setText(model.getValueAt(selectedRow, 5).toString());      
            
            int idVuelo = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());
            List<Integer> asientosDisponibles = modelo.obtenerAsientosDisponibles(idVuelo);
            vista.rellenarAsientos(asientosDisponibles);
            
            String vuelo = vista.lblVuelo.getText();
            String origen = vista.lblOrigen.getText();
            String destino = vista.lblDestino.getText();
        String fecha = vista.lblFecha.getText();
            String hora = vista.lblHora.getText();
        
            vista.lblOrigen2.setText(origen);
            vista.lblDestino2.setText(destino);
            vista.lblFecha2.setText(fecha);
            vista.lblHora2.setText(hora);
                    
            JOptionPane.showMessageDialog(null, "Datos del vuelo actualizados");
        } else {
            JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún vuelo.");
        }
    }

   public void generarPDF() throws IOException {
    try {
        String nombre = vista.lblNombre.getText();
        String apellidos = vista.lblApellidos.getText();
        String origen = vista.lblOrigen.getText();
        String destino = vista.lblDestino.getText();
        String fecha = vista.lblFecha.getText();
        String vuelo = vista.lblVuelo.getText();
        String hora = vista.lblHora.getText();
        int asiento = (int) vista.cboAsientos.getSelectedItem();

        // Actualizar disponibilidad y estado del asiento
        // Actualizar disponibilidad y estado del asiento
        int idVuelo = Integer.parseInt(vista.lblVuelo.getText());
        int idUs=modLog.obtenerIdUsuario(nombre);
        System.out.println("Nombre del usuario: " + nombre); // Depuración
        System.out.println("ID del usuario: " + idUs); // Depuración
        actualizarDisponibilidadYEstado(idVuelo, asiento);
        
        generarRegistroDetalleVenta(idUs, idVuelo);

        // Crear el PDF
        String rutaArchivoPDF = modelo.crearPDF(nombre, apellidos, origen, destino, fecha, vuelo, hora, asiento);

        // Abrir el PDF
        if (rutaArchivoPDF != null) {
            File pdfFile = new File(rutaArchivoPDF);
            if (pdfFile.exists()) {
                Desktop.getDesktop().open(pdfFile);
            } else {
                JOptionPane.showMessageDialog(vista, "No se encontró el PDF.");
            }
        } else {
            JOptionPane.showMessageDialog(vista, "Error al crear el PDF.");
        }
        
            
        // Enviar el PDF por correo electrónico
        String destinatario = modelo.obtenerCorreoPorNombre(nombre);
        String asunto = "Boleto para tu viaje!!!";
        String contenido = "Estimado/a,\n\n"
        + "Adjunto encontrarás tu boleto de avión para tu próximo viaje con viajesITOs MEXICANA.\n\n"
        + "¡Esperamos que tengas un excelente viaje!\n\n"
        + "Atentamente,\n"
        + "El equipo de viajesITOs MEXICANA";
        if (rutaArchivoPDF != null && destinatario != null) {
            modelo.enviarCorreoConAdjunto(destinatario, asunto, contenido, rutaArchivoPDF);
        } else {
            JOptionPane.showMessageDialog(vista, "Error al enviar el correo o crear el PDF");
        }
    } catch (FileNotFoundException ex) {
        Logger.getLogger(CmControlador.class.getName()).log(Level.SEVERE, null, ex);
    }
}


    private void mostrarGrafico() {
        Map<Integer, Integer> ventasPorVuelo = modelo.obtenerVentasPorVuelo();
        DefaultCategoryDataset dataset = crearDataset(ventasPorVuelo);
        JFreeChart chart = ChartFactory.createBarChart("Ventas por Vuelo", "Vuelo", "Ventas", dataset);
        vista.mostrarGrafico(chart);
    }
    
    private void mostrarGraficoPorMes() {
        int mesSeleccionado = vista.obtenerMesSeleccionado();
        Map<Integer, Integer> ventasPorVuelo = modelo.obtenerVentasPorVueloYMes(mesSeleccionado);
        DefaultCategoryDataset dataset = crearDataset(ventasPorVuelo);
        JFreeChart chart = ChartFactory.createBarChart("Ventas por Vuelo - Mes " + mesSeleccionado, "Vuelo", "Ventas", dataset);
        vista.mostrarGrafico(chart);
    }
    
    private DefaultCategoryDataset crearDataset(Map<Integer, Integer> ventasPorVuelo) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<Integer, Integer> entry : ventasPorVuelo.entrySet()) {
            dataset.addValue(entry.getValue(), "Ventas", "Vuelo " + entry.getKey());
        }
        return dataset;
    }
    

}
