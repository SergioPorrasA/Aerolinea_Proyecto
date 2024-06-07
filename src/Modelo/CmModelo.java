package Modelo;

import com.itextpdf.text.Document; // Representa un documento PDF en iText
import com.itextpdf.text.DocumentException; // Maneja excepciones relacionadas con documentos PDF en iText
import com.itextpdf.text.FontFactory; // Accede a fábricas de fuentes en iText para documentos PDF
import com.itextpdf.text.Paragraph; // Representa un párrafo en un documento PDF en iText
import com.itextpdf.text.pdf.PdfWriter; // Escribe contenido en un documento PDF en iText
import conexion.ConexionVuelos; // Establece la conexión con la base de datos de vuelos
import java.io.BufferedReader; // Lee texto desde un flujo de entrada de caracteres
import java.io.File; // Representa archivos y directorios en el sistema de archivos
import java.io.FileNotFoundException; // Maneja excepciones cuando un archivo no se encuentra
import java.io.FileOutputStream; // Escribe datos en un archivo en el sistema de archivos
import java.io.FileReader; // Lee caracteres desde un archivo
import java.io.IOException; // Maneja excepciones relacionadas con operaciones de entrada/salida
import java.sql.Connection; // Representa la conexión con la base de datos
import java.sql.PreparedStatement; // Prepara y ejecuta consultas SQL precompiladas
import java.sql.ResultSet; // Almacena los resultados de consultas SQL
import java.sql.SQLException; // Maneja excepciones relacionadas con operaciones de base de datos
import java.util.ArrayList; // Crea una lista dinámica de objetos
import java.util.Date; // Representa fechas y horas
import java.util.HashMap; // Representa una colección de pares clave-valor no ordenados
import java.util.List; // Representa una lista ordenada de elementos
import java.util.Map; // Representa una colección de pares clave-valor
import java.util.Properties; // Representa una colección de propiedades
import javax.mail.Message; // Representa un mensaje de correo electrónico
import javax.mail.Multipart; // Representa una parte del contenido de un mensaje de correo electrónico con múltiples partes
import javax.mail.Session; // Representa una sesión de correo electrónico
import javax.mail.Transport; // Envía mensajes de correo electrónico
import javax.mail.internet.InternetAddress; // Representa una dirección de correo electrónico
import javax.mail.internet.MimeBodyPart; // Representa una parte de un mensaje de correo electrónico con un tipo de contenido específico
import javax.mail.internet.MimeMessage; // Representa un mensaje MIME de correo electrónico
import javax.mail.internet.MimeMultipart; // Representa una parte del contenido de un mensaje de correo electrónico con múltiples partes
import javax.swing.JComboBox; // Representa un componente de lista desplegable en Swing
import javax.swing.JLabel; // Representa un componente de etiqueta en Swing
import javax.swing.JOptionPane; // Muestra cuadros de diálogo en la interfaz gráfica de usuario en Swing
import javax.swing.table.DefaultTableModel; // Representa el modelo de datos de una tabla en Swing
import com.itextpdf.text.BaseColor; // Para definir colores en iText
import com.itextpdf.text.Element; // Para la alineación de elementos en iText
import com.itextpdf.text.Image; // Para manejar imágenes en documentos PDF en iText
import com.itextpdf.text.Phrase; // Para crear frases de texto en iText
import com.itextpdf.text.pdf.PdfPCell; // Para manejar celdas en tablas de documentos PDF en iText
import com.itextpdf.text.pdf.PdfContentByte; // Para dibujar contenido en PDF en iText
import com.itextpdf.text.pdf.PdfPTable; // Para manejar tablas en documentos PDF en iText

public class CmModelo {
    // Declaración de variables estáticas para el correo
    // Variables estáticas para el correo
    private static String remitente = "viajesitos2216@gmail.com";
    private static String passwordRemitente = "dnqe jbii bmvm ydfz";
    
    // Variables para el correo
    /*private String destinatario; // Dirección de correo del destinatario
    private String asunto = "Boleto para tu viaje"; // Asunto del correo
   private String contenido = "Estimado/a,\n\n"
        + "Adjunto encontrarás tu boleto de avión para tu próximo viaje con viajesITOs MEXICANA.\n\n"
        + "¡Esperamos que tengas un excelente viaje!\n\n"
        + "Atentamente,\n"
        + "El equipo de viajesITOs MEXICANA";*/
    
    // Propiedades y sesión para el envío de correo
    //private Properties mProperties; // Propiedades para la configuración del correo
    //Session mSession; // Sesión de correo electrónico
    //MimeMessage mCorreo; // Mensaje de correo electrónico MIME


    // Método para buscar vuelos en la base de datos y devolver un modelo de tabla
    public DefaultTableModel buscarVuelos(String origen, String destino) {
        DefaultTableModel modeloTabla = new DefaultTableModel();
        try {
            Connection acceDB = ConexionVuelos.getConexion();
            // Consulta SQL para obtener vuelos según origen y destino
            String consulta = "SELECT id_vuelo, origen, destino, precio, fecha, hora, disponibilidad FROM vuelo WHERE origen = ? AND destino = ?";
            PreparedStatement statement = acceDB.prepareStatement(consulta);
            statement.setString(1, origen);
            statement.setString(2, destino);
            ResultSet resultado = statement.executeQuery();
            // Agregar columnas al modelo de tabla
            modeloTabla.addColumn("id_vuelo");
            modeloTabla.addColumn("origen");
            modeloTabla.addColumn("destino");
            modeloTabla.addColumn("precio");
            modeloTabla.addColumn("fecha");
            modeloTabla.addColumn("hora");
            modeloTabla.addColumn("disponibilidad");
            // Llenar el modelo de tabla con los resultados de la consulta
            while (resultado.next()) {
                Object[] fila = {
                    resultado.getInt("id_vuelo"),
                    resultado.getString("origen"),
                    resultado.getString("destino"),
                    resultado.getDouble("precio"),
                    resultado.getDate("fecha"),
                    resultado.getTime("hora"),
                    resultado.getInt("disponibilidad")
                };
                modeloTabla.addRow(fila);
            }

            resultado.close();
            statement.close();
            acceDB.close();
        } catch (SQLException e) {
            // Mostrar mensaje de error en caso de fallo en la consulta
            JOptionPane.showMessageDialog(null, "Error al buscar vuelos: " + e.getMessage());
            System.err.println("Error al buscar vuelos: " + e.getMessage());
        }

        return modeloTabla;
    }

    // Método para rellenar un JComboBox con los orígenes disponibles en la base de datos
    public void rellenarComboBoxOrigenes(JComboBox<String> comboBox) {
        try {
            Connection acceDB = ConexionVuelos.getConexion();
            // Consulta SQL para obtener orígenes distintos
            String consulta = "SELECT DISTINCT origen FROM vuelo";
            PreparedStatement statement = acceDB.prepareStatement(consulta);

            ResultSet resultado = statement.executeQuery();

            comboBox.removeAllItems();

            // Llenar el JComboBox con los orígenes obtenidos
            while (resultado.next()) {
                String origen = resultado.getString("origen");
                comboBox.addItem(origen);
            }

            resultado.close();
            statement.close();
            acceDB.close();
        } catch (SQLException e) {
            // Mostrar mensaje de error en caso de fallo en la consulta
            JOptionPane.showMessageDialog(null, "Error al rellenar el combo box de orígenes: " + e.getMessage());
            System.err.println("Error al rellenar el combo box de orígenes: " + e.getMessage());
        }
    }

    // Método para rellenar un JComboBox con los destinos disponibles en la base de datos
    public void rellenarComboBoxDestinos(JComboBox<String> comboBox) {
        try {
            Connection acceDB = ConexionVuelos.getConexion();
            // Consulta SQL para obtener destinos distintos
            String consulta = "SELECT DISTINCT destino FROM vuelo";
            PreparedStatement statement = acceDB.prepareStatement(consulta);

            ResultSet resultado = statement.executeQuery();

            comboBox.removeAllItems();

            // Llenar el JComboBox con los destinos obtenidos
            while (resultado.next()) {
                String destino = resultado.getString("destino");
                comboBox.addItem(destino);
            }

            resultado.close();
            statement.close();
            acceDB.close();
        } catch (SQLException e) {
            // Mostrar mensaje de error en caso de fallo en la consulta
            JOptionPane.showMessageDialog(null, "Error al rellenar el combo box de destinos: " + e.getMessage());
            System.err.println("Error al rellenar el combo box de destinos: " + e.getMessage());
        }
    }

    // Método para mostrar el contenido de un archivo en una etiqueta
    public void mostrarContenidoEnEtiqueta(String rutaArchivo, JLabel etiqueta) {
        StringBuilder contenido = new StringBuilder();
        BufferedReader lector = null;
        try {
            // Abrir el archivo para lectura
            lector = new BufferedReader(new FileReader(rutaArchivo));
            String linea;
            // Leer el archivo línea por línea y almacenar el contenido
            while ((linea = lector.readLine()) != null) {
                contenido.append(linea).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Cerrar el lector al finalizar la lectura del archivo
            if (lector != null) {
                try {
                    lector.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // Mostrar el contenido en la etiqueta
        etiqueta.setText(contenido.toString());
    }

    // Método para obtener los asientos disponibles para un vuelo
    public List<Integer> obtenerAsientosDisponibles(int idVuelo) {
        List<Integer> asientosDisponibles = new ArrayList<>();
        try {
            Connection acceDB = ConexionVuelos.getConexion();
            // Consulta SQL para obtener los asientos disponibles
            String consulta = "SELECT asientos FROM detalle_vuelo WHERE id_vuelo = ? AND estado = 1";
            PreparedStatement statement = acceDB.prepareStatement(consulta);
            statement.setInt(1, idVuelo);
            ResultSet resultado = statement.executeQuery();
            // Agregar los asientos disponibles a la lista
            while (resultado.next()) {
                asientosDisponibles.add(resultado.getInt("asientos"));
            }
            resultado.close();
            statement.close();
            acceDB.close();
        } catch (SQLException e) {
            // Mostrar mensaje de error en caso de fallo en la consulta
            JOptionPane.showMessageDialog(null, "Error al obtener asientos disponibles: " + e.getMessage());
            System.err.println("Error al obtener asientos disponibles: " + e.getMessage());
        }
        return asientosDisponibles;
    }

    
    
    // Método para actualizar la disponibilidad y el estado de un vuelo y asiento seleccionados
    public void actualizarDisponibilidadYEstado(int idVuelo, int asientoSeleccionado) throws SQLException {
        try {
            Connection acceDB = ConexionVuelos.getConexion();
            // Actualizar disponibilidad del vuelo
            String actualizarDisponibilidad = "UPDATE vuelo SET disponibilidad = disponibilidad - 1 WHERE id_vuelo = ?";
            PreparedStatement statementDisponibilidad = acceDB.prepareStatement(actualizarDisponibilidad);
            statementDisponibilidad.setInt(1, idVuelo);
            statementDisponibilidad.executeUpdate();
            statementDisponibilidad.close();
            // Actualizar estado del asiento
            String actualizarEstado = "UPDATE detalle_vuelo SET estado = 0 WHERE id_vuelo = ? AND asientos = ?";
            PreparedStatement statementEstado = acceDB.prepareStatement(actualizarEstado);
            statementEstado.setInt(1, idVuelo);
            statementEstado.setInt(2, asientoSeleccionado);
            statementEstado.executeUpdate();
            statementEstado.close();
            acceDB.close();
        } catch (SQLException e) {
            // Mostrar mensaje de error en caso de fallo en la actualización
            JOptionPane.showMessageDialog(null, "Error al actualizar la disponibilidad y el estado: " + e.getMessage());
            System.err.println("Error al actualizar la disponibilidad y el estado: " + e.getMessage());
        }
    }

    // Método para crear un registro de venta en la base de datos
    public void crearRegistroDetalleVenta(int idUsuario, int idVuelo) throws SQLException {
        try {
            Connection acceDB = ConexionVuelos.getConexion();
            // Consulta SQL para insertar un nuevo registro de venta
            String insertarRegistro = "INSERT INTO detalle_venta (id_usuario, id_vuelo, fecha_v) VALUES (?, ?, CURRENT_DATE)";
            PreparedStatement statement = acceDB.prepareStatement(insertarRegistro);
            statement.setInt(1, idUsuario);
            statement.setInt(2, idVuelo);
            statement.executeUpdate();
            statement.close();
            acceDB.close();
        } catch (SQLException e) {
            // Mostrar mensaje de error en caso de fallo en la inserción
            JOptionPane.showMessageDialog(null, "Error al crear el registro de venta: " + e.getMessage());
            System.err.println("Error al crear el registro de venta: " + e.getMessage());
        }
    }

    // Método para obtener las ventas por vuelo
    public Map<Integer, Integer> obtenerVentasPorVuelo() {
        Map<Integer, Integer> ventasPorVuelo = new HashMap<>();
        try {
            Connection acceDB = ConexionVuelos.getConexion();
            // Consulta SQL para obtener el número de ventas por vuelo
            String consulta = "SELECT id_vuelo, COUNT(*) AS ventas FROM detalle_venta GROUP BY id_vuelo";
            PreparedStatement statement = acceDB.prepareStatement(consulta);
            ResultSet resultado = statement.executeQuery();
            // Agregar las ventas por vuelo al mapa
            while (resultado.next()) {
                int idVuelo = resultado.getInt("id_vuelo");
                int ventas = resultado.getInt("ventas");
                ventasPorVuelo.put(idVuelo, ventas);
            }
            resultado.close();
            statement.close();
            acceDB.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ventasPorVuelo;
    }

    // Método para obtener las ventas por vuelo y mes
    public Map<Integer, Integer> obtenerVentasPorVueloYMes(int mes) {
        Map<Integer, Integer> ventasPorVuelo = new HashMap<>();
        try {
            Connection acceDB = ConexionVuelos.getConexion();
            // Consulta SQL para obtener el número de ventas por vuelo y mes
            String consulta = "SELECT id_vuelo, COUNT(*) AS ventas FROM detalle_venta WHERE MONTH(fecha_v) = ? GROUP BY id_vuelo";
            PreparedStatement statement = acceDB.prepareStatement(consulta);
            statement.setInt(1, mes);
            ResultSet resultado = statement.executeQuery();
            // Agregar las ventas por vuelo al mapa
            while (resultado.next()) {
                int idVuelo = resultado.getInt("id_vuelo");
                int ventas = resultado.getInt("ventas");
                ventasPorVuelo.put(idVuelo, ventas);
            }
            resultado.close();
            statement.close();
            acceDB.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ventasPorVuelo;
    }    
    
    // Método para crear un archivo PDF con información de un boleto de vuelo
    public String crearPDF(String nombre, String apellidos, String origen, String destino, String fecha, String vuelo, String hora, int asiento) throws FileNotFoundException {
        String rutaArchivo = null;
        Document documento = new Document();
        try {
            Date current = new Date();
            rutaArchivo = nombre + current.getMinutes() + "_boleto.pdf";
            PdfWriter writer = PdfWriter.getInstance(documento, new FileOutputStream(rutaArchivo));
            documento.open();

            // Agregar imagen centrada al inicio
            try {
                Image imagen = Image.getInstance("C:\\Users\\Sergio Porras A\\Documents\\NetBeansProjects\\Aerolinea_Proyecto.1.5.1\\src\\aerolinea\\imagenes\\Logo4.jpg");
                imagen.setAlignment(Element.ALIGN_CENTER);
                documento.add(imagen);
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            
            
            com.itextpdf.text.Font fuenteTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
            com.itextpdf.text.Font fuenteNormal = FontFactory.getFont(FontFactory.HELVETICA, 12);
            com.itextpdf.text.Font fuenteNegrita = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);

            // Agregar título
            Paragraph titulo = new Paragraph("Boleto de Vuelo", fuenteTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(titulo);

            // Agregar línea separadora verde
            PdfContentByte canvas = writer.getDirectContent();
            canvas.setColorStroke(new BaseColor(0, 100, 0)); // Color verde oscuro
            canvas.setLineWidth(2);
            canvas.moveTo(36, 770); // Coordenadas de inicio (x, y)
            canvas.lineTo(559, 770); // Coordenadas de fin (x, y)
            canvas.stroke();

            documento.add(new Paragraph("\n"));

            // Crear una tabla para alinear el contenido
            PdfPTable tabla = new PdfPTable(2); // 2 columnas
            tabla.setWidthPercentage(80); // Ancho de la tabla como porcentaje del ancho de la página
            tabla.setSpacingBefore(15f); // Espacio antes de la tabla
            tabla.setSpacingAfter(15f); // Espacio después de la tabla

            // Especificar anchos relativos de las columnas (en porcentaje del ancho total de la tabla)
            float[] anchosColumnas = {1f, 2f}; // La primera columna será la mitad del tamaño de la segunda
            tabla.setWidths(anchosColumnas);

            // Crear y configurar celdas
            PdfPCell celdaNombre = new PdfPCell(new Phrase("Nombre:", fuenteNegrita));
            PdfPCell celdaNombreValor = new PdfPCell(new Phrase(nombre, fuenteNormal));
            PdfPCell celdaApellidos = new PdfPCell(new Phrase("Apellidos:", fuenteNegrita));
            PdfPCell celdaApellidosValor = new PdfPCell(new Phrase(apellidos, fuenteNormal));
            PdfPCell celdaOrigen = new PdfPCell(new Phrase("Lugar/Origen:", fuenteNegrita));
            PdfPCell celdaOrigenValor = new PdfPCell(new Phrase(origen, fuenteNormal));
            PdfPCell celdaDestino = new PdfPCell(new Phrase("Lugar/Destino:", fuenteNegrita));
            PdfPCell celdaDestinoValor = new PdfPCell(new Phrase(destino, fuenteNormal));
            PdfPCell celdaFecha = new PdfPCell(new Phrase("Fecha/Vuelo:", fuenteNegrita));
            PdfPCell celdaFechaValor = new PdfPCell(new Phrase(fecha, fuenteNormal));
            PdfPCell celdaVuelo = new PdfPCell(new Phrase("Numero/Vuelo:", fuenteNegrita));
            PdfPCell celdaVueloValor = new PdfPCell(new Phrase(vuelo, fuenteNormal));
            PdfPCell celdaHora = new PdfPCell(new Phrase("Hora/Salida:", fuenteNegrita));
            PdfPCell celdaHoraValor = new PdfPCell(new Phrase(hora, fuenteNormal));
            PdfPCell celdaAsiento = new PdfPCell(new Phrase("Asiento:", fuenteNegrita));
            PdfPCell celdaAsientoValor = new PdfPCell(new Phrase(String.valueOf(asiento), fuenteNormal));

           
            float padding = 10f; 
            celdaNombre.setPadding(padding);
            celdaNombreValor.setPadding(padding);
            celdaApellidos.setPadding(padding);
            celdaApellidosValor.setPadding(padding);
            celdaOrigen.setPadding(padding);
            celdaOrigenValor.setPadding(padding);
            celdaDestino.setPadding(padding);
            celdaDestinoValor.setPadding(padding);
            celdaFecha.setPadding(padding);
            celdaFechaValor.setPadding(padding);
            celdaVuelo.setPadding(padding);
            celdaVueloValor.setPadding(padding);
            celdaHora.setPadding(padding);
            celdaHoraValor.setPadding(padding);
            celdaAsiento.setPadding(padding);
            celdaAsientoValor.setPadding(padding);

            // Agregar colores de fondo a las celdas
            BaseColor colorEncabezado = new BaseColor(204, 229, 255);
            celdaNombre.setBackgroundColor(colorEncabezado);
            celdaApellidos.setBackgroundColor(colorEncabezado);
            celdaOrigen.setBackgroundColor(colorEncabezado);
            celdaDestino.setBackgroundColor(colorEncabezado);
            celdaFecha.setBackgroundColor(colorEncabezado);
            celdaVuelo.setBackgroundColor(colorEncabezado);
            celdaHora.setBackgroundColor(colorEncabezado);
            celdaAsiento.setBackgroundColor(colorEncabezado);

            // Centrar el texto en las celdas
            celdaNombre.setHorizontalAlignment(Element.ALIGN_CENTER);
            celdaNombreValor.setHorizontalAlignment(Element.ALIGN_CENTER);
            celdaApellidos.setHorizontalAlignment(Element.ALIGN_CENTER);
            celdaApellidosValor.setHorizontalAlignment(Element.ALIGN_CENTER);
            celdaOrigen.setHorizontalAlignment(Element.ALIGN_CENTER);
            celdaOrigenValor.setHorizontalAlignment(Element.ALIGN_CENTER);
            celdaDestino.setHorizontalAlignment(Element.ALIGN_CENTER);
            celdaDestinoValor.setHorizontalAlignment(Element.ALIGN_CENTER);
            celdaFecha.setHorizontalAlignment(Element.ALIGN_CENTER);
            celdaFechaValor.setHorizontalAlignment(Element.ALIGN_CENTER);
            celdaVuelo.setHorizontalAlignment(Element.ALIGN_CENTER);
            celdaVueloValor.setHorizontalAlignment(Element.ALIGN_CENTER);
            celdaHora.setHorizontalAlignment(Element.ALIGN_CENTER);
            celdaHoraValor.setHorizontalAlignment(Element.ALIGN_CENTER);
            celdaAsiento.setHorizontalAlignment(Element.ALIGN_CENTER);
            celdaAsientoValor.setHorizontalAlignment(Element.ALIGN_CENTER);

            // Agregar bordes a las celdas
            celdaNombre.setBorderColor(BaseColor.BLACK);
            celdaNombreValor.setBorderColor(BaseColor.BLACK);
            celdaApellidos.setBorderColor(BaseColor.BLACK);
            celdaApellidosValor.setBorderColor(BaseColor.BLACK);
            celdaOrigen.setBorderColor(BaseColor.BLACK);
            celdaOrigenValor.setBorderColor(BaseColor.BLACK);
            celdaDestino.setBorderColor(BaseColor.BLACK);
            celdaDestinoValor.setBorderColor(BaseColor.BLACK);
            celdaFecha.setBorderColor(BaseColor.BLACK);
            celdaFechaValor.setBorderColor(BaseColor.BLACK);
            celdaVuelo.setBorderColor(BaseColor.BLACK);
            celdaVueloValor.setBorderColor(BaseColor.BLACK);
            celdaHora.setBorderColor(BaseColor.BLACK);
            celdaHoraValor.setBorderColor(BaseColor.BLACK);
            celdaAsiento.setBorderColor(BaseColor.BLACK);
            celdaAsientoValor.setBorderColor(BaseColor.BLACK);

            // Agregar las celdas a la tabla
            tabla.addCell(celdaNombre);
            tabla.addCell(celdaNombreValor);
            tabla.addCell(celdaApellidos);
            tabla.addCell(celdaApellidosValor);
            tabla.addCell(celdaOrigen);
            tabla.addCell(celdaOrigenValor);
            tabla.addCell(celdaDestino);
            tabla.addCell(celdaDestinoValor);
            tabla.addCell(celdaFecha);
            tabla.addCell(celdaFechaValor);
            tabla.addCell(celdaVuelo);
            tabla.addCell(celdaVueloValor);
            tabla.addCell(celdaHora);
            tabla.addCell(celdaHoraValor);
            tabla.addCell(celdaAsiento);
            tabla.addCell(celdaAsientoValor);

            // Agregar la tabla al documento
            documento.add(tabla);
            try {
                Image imagen2 = Image.getInstance("C:\\Users\\Sergio Porras A\\Documents\\NetBeansProjects\\Aerolinea_Proyecto.1.5.1\\src\\aerolinea\\imagenes\\agrad4.jpg");
                imagen2.setAlignment(Element.ALIGN_CENTER);
                documento.add(imagen2);
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            // Segunda línea separadora verde
            canvas.moveTo(36, 100); // Coordenadas de inicio (x, y)
            canvas.lineTo(559, 100); // Coordenadas de fin (x, y)
            canvas.stroke();

            documento.close();
            JOptionPane.showMessageDialog(null, "PDF creado exitosamente");
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al crear el PDF: " + e.getMessage());
        }
        return rutaArchivo;
    }

    // Método para enviar un correo electrónico con un archivo adjunto
    public void enviarCorreoConAdjunto(String destinatario, String asunto, String contenido, String rutaArchivoAdjunto) {
        Properties mProperties = new Properties();
        mProperties.put("mail.smtp.host", "smtp.gmail.com");
        mProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        mProperties.setProperty("mail.smtp.starttls.enable", "true");
        mProperties.setProperty("mail.smtp.port", "587");
        mProperties.setProperty("mail.smtp.user", remitente);
        mProperties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        mProperties.setProperty("mail.smtp.auth", "true");

        Session mSession = Session.getDefaultInstance(mProperties);

        try {
            MimeMessage mCorreo = new MimeMessage(mSession);
            mCorreo.setFrom(new InternetAddress(remitente));
            mCorreo.setRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
            mCorreo.setSubject(asunto);

            // Adjuntar el archivo al correo
            MimeBodyPart textoAdjunto = new MimeBodyPart();
            textoAdjunto.setText(contenido);

            MimeBodyPart archivoAdjunto = new MimeBodyPart();
            archivoAdjunto.attachFile(new File(rutaArchivoAdjunto));

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textoAdjunto);
            multipart.addBodyPart(archivoAdjunto);

            mCorreo.setContent(multipart);

            Transport mTransport = mSession.getTransport("smtp");
            mTransport.connect(remitente, passwordRemitente);
            mTransport.sendMessage(mCorreo, mCorreo.getAllRecipients());
            mTransport.close();

            JOptionPane.showMessageDialog(null, "Correo enviado con el PDF adjunto");
        } catch (Exception ex) {
            // Mostrar mensaje de error en caso de fallo en el envío del correo
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al enviar el correo: " + ex.getMessage());
        }
    }

    // Método para obtener el correo electrónico de un usuario por su nombre
    public String obtenerCorreoPorNombre(String nombreUsuario) {
        String correo = null;
        try {
            Connection acceDB = ConexionVuelos.getConexion();
            // Consulta SQL para obtener el correo electrónico de un usuario por su nombre
            String consulta = "SELECT email FROM usuario WHERE nombre = ?";
            PreparedStatement statement = acceDB.prepareStatement(consulta);
            statement.setString(1, nombreUsuario);

            ResultSet resultado = statement.executeQuery();

            if (resultado.next()) {
                correo = resultado.getString("email");
            }

            resultado.close();
            statement.close();
            acceDB.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return correo;
    }
}
