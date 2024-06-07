/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Vista;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/**
 *
 * @author itzel
 */
public class CrudVistaVuelos extends javax.swing.JFrame {
    /**
     * Creates new form jFrom
     */
    public CrudVistaVuelos() {
        initComponents();
        txtid.setVisible(false);
        txtDisponibilidad.setVisible(false);
         // Personaliza los botones
        customizeButton(btnListar);
        customizeButton(btnEditar);
        customizeButton(btnEliminar);
        customizeButton(btnGEdit);
        customizeButton(btnRegistrar);

        // Personaliza los campos de texto con título
        customizeTextFieldWithTitle(txtOrigen, "Origen");
        customizeTextFieldWithTitle(txtDestino, "Destino");
        customizeTextFieldWithTitle(txtBusqueda, "Búsqueda");
        customizeTextFieldWithTitle(txtDisponibilidad, "Disponibilidad");
        customizeTextFieldWithTitle(txtPrecio, "Precio");
        customizeTextFieldWithTitle(txtHora, "Hora");
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        txtOrigen = new javax.swing.JTextField();
        txtDestino = new javax.swing.JTextField();
        txtPrecio = new javax.swing.JTextField();
        txtHora = new javax.swing.JTextField();
        txtDisponibilidad = new javax.swing.JTextField();
        btnRegistrar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnListar = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jtDatos = new javax.swing.JTable();
        txtBusqueda = new javax.swing.JTextField();
        btnGEdit = new javax.swing.JButton();
        txtid = new javax.swing.JTextField();
        jDate = new com.toedter.calendar.JDateChooser();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(jTable2);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Admi");
        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(txtOrigen, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, 168, 40));
        jPanel1.add(txtDestino, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 80, 168, 40));
        jPanel1.add(txtPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, 168, 40));
        jPanel1.add(txtHora, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 260, 168, 40));
        jPanel1.add(txtDisponibilidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 330, 168, 40));

        btnRegistrar.setText("Registrar");
        jPanel1.add(btnRegistrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 20, 200, -1));

        btnEditar.setText("Editar");
        jPanel1.add(btnEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 60, 90, -1));

        btnEliminar.setText("Eliminar");
        jPanel1.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 110, 200, -1));

        btnListar.setText("Listar");
        jPanel1.add(btnListar, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 160, 200, -1));

        jtDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "", "", "", "", ""
            }
        ));
        jScrollPane4.setViewportView(jtDatos);

        jPanel1.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 390, 660, 147));

        txtBusqueda.setBorder(javax.swing.BorderFactory.createTitledBorder("Busqueda"));
        txtBusqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBusquedaActionPerformed(evt);
            }
        });
        jPanel1.add(txtBusqueda, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 230, 200, -1));

        btnGEdit.setText("Ok");
        jPanel1.add(btnGEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 60, 70, 30));

        txtid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtidActionPerformed(evt);
            }
        });
        jPanel1.add(txtid, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 10, 40, 40));
        jPanel1.add(jDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 200, 170, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 700, 550));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtidActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtidActionPerformed

    private void txtBusquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBusquedaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBusquedaActionPerformed

    private void customizeButton(JButton button) {
        // Color de fondo
        button.setBackground(new Color(60, 63, 65));
        // Color del texto
        button.setForeground(Color.WHITE);
        // Fuente y tamaño del texto
        button.setFont(new Font("Arial", Font.BOLD, 14));
        // Desactiva el resaltado cuando está enfocado
        button.setFocusPainted(false);
        // Establece un borde vacío para dar espacio alrededor del texto
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    private void customizeTextFieldWithTitle(JTextField textField, String title) {
        // Fuente y tamaño del texto del campo de texto
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        // Crea un borde con título alrededor del campo de texto
        textField.setBorder(BorderFactory.createTitledBorder(
                // Borde sólido con un color específico
                BorderFactory.createLineBorder(new Color(60, 63, 65), 1), 
                title, 
                // Alineación del título
                TitledBorder.DEFAULT_JUSTIFICATION, 
                // Posición del título
                TitledBorder.DEFAULT_POSITION, 
                // Fuente y tamaño del título
                new Font("Arial", Font.BOLD, 12), 
                // Color del título
                new Color(60, 63, 65)));
    }

/*private void customizeTextField(JTextField textField) {
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 63, 65), 1), 
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
    }*/
 

    /*private void customizeLabel(JLabel label) {
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(new Color(60, 63, 65));
    }*/
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CrudVistaVuelos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CrudVistaVuelos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CrudVistaVuelos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CrudVistaVuelos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CrudVistaVuelos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnEditar;
    public javax.swing.JButton btnEliminar;
    public javax.swing.JButton btnGEdit;
    public javax.swing.JButton btnListar;
    public javax.swing.JButton btnRegistrar;
    public com.toedter.calendar.JDateChooser jDate;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    public javax.swing.JTable jtDatos;
    public javax.swing.JTextField txtBusqueda;
    public javax.swing.JTextField txtDestino;
    public javax.swing.JTextField txtDisponibilidad;
    public javax.swing.JTextField txtHora;
    public javax.swing.JTextField txtOrigen;
    public javax.swing.JTextField txtPrecio;
    public javax.swing.JTextField txtid;
    // End of variables declaration//GEN-END:variables
}
