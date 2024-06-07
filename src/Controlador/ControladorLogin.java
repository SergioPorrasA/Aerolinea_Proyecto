/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;


//import Vista.vistaPag;
// Para interactuar con la base de datos de usuarios
import Modelo.ModeloLogin;

// Para la interfaz gráfica de inicio de sesión y registro
import Vista.VistaLogin;

// Clase principal de la aplicación
import Mains.mainPrinc;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// Para mostrar mensajes en la interfaz gráfica
import javax.swing.JOptionPane;

// Para manejar errores y eventos
import java.util.logging.Level;
import java.util.logging.Logger;

// Para el envío de correos electrónicos
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;
import javax.mail.*;
import javax.activation.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;


/**
 *
 * @author itzel
 */
public class ControladorLogin  implements KeyListener{
     
    private ModeloLogin mod; // Modelo asociado al controlador
    private VistaLogin vista; // Vista asociada al controlador
    private static String remitente = "viajesitos2216@gmail.com"; // Dirección de correo electrónico del remitente
    private static String passwordRemitente = "dnqe jbii bmvm ydfz"; // Contraseña del remitente
    private String destinatario; // Dirección de correo electrónico del destinatario
    private String asunto = "Codigo de verificacion de ViajesITOs"; // Asunto del correo electrónico
    private String contenido; // Contenido del correo electrónico
    private int codigo; // Código de verificación generado
    private Properties mProperties; // Propiedades del correo electrónico
    Session mSession; // Sesión de correo electrónico
    MimeMessage mCorreo; // Objeto de mensaje de correo electrónico

    // Constructor que recibe el modelo y la vista
   public ControladorLogin(ModeloLogin mod, VistaLogin vista) {
    this.mod = mod;
    this.vista = vista;
    mProperties = new Properties();
       
    this.vista.txtPassword.addKeyListener(this);
    this.vista.txtUsuario.addKeyListener(this);
    this.vista.txtNombre.addKeyListener(this);
    this.vista.txtApellidos.addKeyListener(this);
    this.vista.txtCodigo.addKeyListener(this);
    this.vista.txtContraseña.addKeyListener(this);
    this.vista.txtTelefono.addKeyListener(this);
    
   
}
    
    // Método para registrar un nuevo usuario
    public void registrarUsuario() {
        // Obtener datos del formulario de registro
        String nombre = vista.obtenerNombre();
        String apellidos = vista.obtenerApellidos();
        String telefono = vista.obtenerTelefono();
        
        String correo = vista.obtenerCorreo();
        String contraseña = vista.obtenerContraseña();
        
        // Verificar campos vacíos
        if (nombre.isEmpty() || apellidos.isEmpty() || correo.isEmpty() || contraseña.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe rellenar todos los campos", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
        } else {    
            // Limpiar campos del formulario
            vista.limpiarNombre();
            vista.limpiarApellidos();
            vista.limpiarCorreo();
            vista.limpiarContraseña();
            vista.limpiarTelefono();
            
            // Insertar nuevo usuario en la base de datos
            mod.insertarUsuario(nombre, apellidos, telefono, correo, contraseña);
            
            // Ocultar diálogo de registro
            vista.ocultarDialogoRegistro();
        }
        
        // Validar que la contraseña contiene solo números
        if (!contraseña.matches("\\d+")) {
            JOptionPane.showMessageDialog(null, "La contraseña debe contener solo números", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validar longitud de la contraseña
        if (!vista.validarLongitudContraseña(contraseña)) {
            JOptionPane.showMessageDialog(null, "La contraseña debe tener al menos 4 caracteres", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

    // Método para iniciar sesión
    public void iniciarSesion() {
        // Obtener nombre de usuario y contraseña
        String nombreUsuario = vista.obtenerNombreUsuario();
        String contraseña = vista.obtenerContraseña2();
        
        // Realizar inicio de sesión mediante el modelo de datos
        String[] detallesUsuario = mod.basedatos(nombreUsuario, contraseña);

        // Verificar si se encontró un usuario con las credenciales proporcionadas
        if (detallesUsuario != null) {
            // Iniciar la aplicación principal y pasar los detalles del usuario
            mainPrinc nuevaAplicacion = new mainPrinc();
            nuevaAplicacion.pantprincipal(detallesUsuario);
            
            // Ocultar la vista de inicio de sesión
            vista.setVisible(false);
        }
    }


    // Método para crear un correo de verificación 
    public void crearCorreo() {
        // Generar un código de verificación aleatorio
        Random random = new Random();
        codigo = 1000 + random.nextInt(9000);
        System.out.println("El codigo es: " + codigo);

        // Obtener la dirección de correo electrónico del destinatario
        destinatario = vista.obtenerCorreo();

        // Configurar el contenido del correo
        String contenidoC = String.valueOf(codigo);
        contenido = "----------------BIENVENIDO--------------"
                    + "<br>Usted ha solicitado un codigo de registro en nuestra pagina viajesITOs MEXICANA."
                    + "<br>Si no ha solicitado este codigo en nuestra aplicacion ignore este correo."
                    + "<br>"
                    + "<br>Su codigo de verificacion es " + contenidoC
                    + "<br><img src=\"cid:image_id\" width=\"200\" height=\"100\">"; 

        // Configuración de propiedades para el correo
        mProperties.put("mail.smtp.host", "smtp.gmail.com");
        mProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        mProperties.setProperty("mail.smtp.starttls.enable", "true");
        mProperties.setProperty("mail.smtp.port", "587");
        mProperties.setProperty("mail.smtp.user", remitente);
        mProperties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        mProperties.setProperty("mail.smtp.auth", "true");

        mSession = Session.getDefaultInstance(mProperties);

        try {
            // Crear un nuevo mensaje de correo electrónico
            mCorreo = new MimeMessage(mSession);
            mCorreo.setFrom(new InternetAddress(remitente));
            mCorreo.setRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
            mCorreo.setSubject("Codigo de verificacion de ViajesITOs");

            // Crear el cuerpo del mensaje con imagen incrustada
            MimeMultipart multipart = new MimeMultipart("related");

            // Primera parte: el texto del correo
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(contenido, "text/html");
            multipart.addBodyPart(messageBodyPart);

            // Segunda parte: la imagen incrustada
            messageBodyPart = new MimeBodyPart();
            DataSource fds = new FileDataSource("C:\\Users\\Sergio Porras A\\Documents\\NetBeansProjects\\Aerolinea_Proyecto.1.5.1\\src\\aerolinea\\imagenes\\AEM-completo-negro-blanco.jpg"); 
            messageBodyPart.setDataHandler(new DataHandler(fds));
            messageBodyPart.setHeader("Content-ID", "<image_id>");
            multipart.addBodyPart(messageBodyPart);

            // Adjuntar multipart al mensaje
            mCorreo.setContent(multipart);
        } catch (AddressException ex) {
            Logger.getLogger(ControladorLogin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(ControladorLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        

    // Método para enviar el correo de verificación
    public void enviarCorreo() {
        try {
            // Conectar y enviar el correo mediante el protocolo SMTP
            Transport mTransport = mSession.getTransport("smtp");
            mTransport.connect(remitente, passwordRemitente);
            mTransport.sendMessage(mCorreo, mCorreo.getRecipients(Message.RecipientType.TO));
            mTransport.close();

            JOptionPane.showMessageDialog(null, "Correo enviado");
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(ControladorLogin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(ControladorLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Método para comparar el código introducido con el código de verificación
public void compararCodigo() {
    // Obtener el código introducido por el usuario
    String codigoIngresado = vista.obtenerCodigo();
      
    // Verificar si el campo de código está vacío
    if (codigoIngresado.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Por favor ingrese un código", "Campo vacío", JOptionPane.WARNING_MESSAGE);
        return; // Salir del método si el campo está vacío
    }

    // Convertir el código introducido a un entero
    int codigoIntroducido;
    try {
        codigoIntroducido = Integer.parseInt(codigoIngresado);
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(null, "Por favor ingrese un código válido", "Error", JOptionPane.WARNING_MESSAGE);
        return; // Salir del método si el código no es válido
    }

    // Comparar el código introducido con el código generado
    if (codigoIntroducido == codigo) {
        // Habilitar el registro de usuario
        vista.habilitarRegistro();
    } else {
        JOptionPane.showMessageDialog(null, "El código no corresponde", "Error", JOptionPane.ERROR_MESSAGE);
    }
}



    @Override
   public void keyTyped(KeyEvent e) {
    // Validar entrada de texto solo para el campo de usuario
    if (e.getSource() == vista.txtUsuario || e.getSource() == vista.txtApellidos || e.getSource() == vista.txtNombre) {
        char c = e.getKeyChar();
        if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z') && (c != (char) KeyEvent.VK_SPACE)) {
            e.consume();
        }
    }

    // Validar entrada de texto solo para el campo de contraseña
    if (e.getSource() == vista.txtPassword ){
        if (vista.txtPassword.getText().length() >= 4) {
            e.consume(); // Si ya se ingresaron 4 caracteres, no permitir más entrada
        } 
            char c = e.getKeyChar();
            if (c < '0' || c > '9') {
                e.consume(); // Si el carácter no es un dígito, no permitir la entrada
            }
                }
    
     if (e.getSource() == vista.txtCodigo ){
        if (vista.txtCodigo.getText().length() >= 4) {
            e.consume(); // Si ya se ingresaron 4 caracteres, no permitir más entrada
        } 
            char c = e.getKeyChar();
            if (c < '0' || c > '9') {
                e.consume(); // Si el carácter no es un dígito, no permitir la entrada
            }
        
        }
        
      if (e.getSource() == vista.txtContraseña ){
        if (vista.txtContraseña.getText().length() >= 4) {
            e.consume(); // Si ya se ingresaron 4 caracteres, no permitir más entrada
        } 
            char c = e.getKeyChar();
            if (c < '0' || c > '9') {
                e.consume(); // Si el carácter no es un dígito, no permitir la entrada
            }
        }
        
        
    
    

    // Validar entrada de texto solo para el campo de teléfono
    if (e.getSource() == vista.txtTelefono) {
        if (vista.txtTelefono.getText().length() >= 10) {
            e.consume();
            return;
        }
        char c = e.getKeyChar();
        if (c < '0' || c > '9') {
            e.consume();
        }
    }
   }


    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}
