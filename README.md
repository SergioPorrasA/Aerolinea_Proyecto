# Proyecto de aplicacion para una Aerolinea

## DESCRIPCION
![1](https://github.com/SergioPorrasA/Aerolinea_Proyecto/assets/168385961/7b7e7db0-2ccd-43ff-ad5d-2341e71df7c7)
Para este proyecto se implementó el Modelo-Vista-Controlador (MVC) para organizar y gestionar la lógica de la aplicación, la interfaz de usuario y la entrada del usuario de manera estructurada.


## CARACTERISTICAS:
- Interfaz Gráfica: Utiliza componentes de interfaz gráfica como JComboBox y JLabel para mostrar y seleccionar información.

- Carga de Datos Externos:  Lee datos desde una base de datos SQL.

- Actualización Dinámica: Los campos de selección se actualizan dinámicamente según las selecciones anteriores.

- Información Seleccionada: Proporciona diferentes metodos que se utilizan para obtener informacion de vuelos o usuarios

- Envio de correos electronicos: Esta herramienta se utiliza en la aplicacion para enviar correos electronicos con archivos .pdf adjuntos desde una cuenta creada solo con fines practicos (despues de haber subido este proyecto, la contraseña de aplicacion para usar este correo sera eliminada, pero a continuacion te dejo un enlace para poder crear una cuenta y hacer uso de ella para la implementacion de esta aplicacion: https://youtu.be/ZggjlwLzrxg?si=_gSkm33CbqOke3MG).

## REQUISITOS PARA LA IMPLEMENTACIÓN EN OTROS EQUIPOS Y PROYECTOS:
1.  Java Development Kit (JDK): Es necesario tener instalado JDK para compilar y ejecutar el código.
2. Base de datos: en este caso utilizamos el gestor mySQL workbench, en el apartado "Base de datos" te dejare los scripts listos para ejecutar y asi crear la base de datos con los requerimientos necesarios para la implementacion del proyecto.



## API

- **Clase conexion:**
La clase ConexionVuelos es responsable de gestionar la conexión a la base de datos MySQL.
Proporciona un método estático getConexion que gestiona toda la lógica necesaria para establecer una conexión a la base de datos, incluyendo la carga del controlador JDBC y la gestión de posibles excepciones.
Importa la clase Connection de java.sql, que representa una conexión a la base de datos.
Importa la clase DriverManager de java.sql, que se encarga de gestionar un conjunto de controladores de base de datos JDBC.

**El metodo principal:**

| Nombre | Descripción |
|------|-------------|
| `public static Connection getConexion()` | Este método es responsable de establecer y devolver una conexión a la base de datos. |



### VISTA:

El paquete cuenta con las siguientes clases.

| Nombre | Descripción |
|------|-------------|
| CrudVistaUsuarios | Es una interfaz gráfica de usuario (GUI) construida con Swing en Java. Esta clase permite realizar operaciones CRUD (Crear, Leer, Actualizar y Eliminar) sobre usuarios en una base de datos. La interfaz incluye campos de texto para ingresar datos del usuario, botones para ejecutar acciones y una tabla para mostrar los datos.|
| CrudVistaVuelos | Es una interfaz de usuario para la gestión de operaciones CRUD (Crear, Leer, Actualizar, Eliminar) relacionadas con vuelos. Una interfaz gráfica que permite al usuario realizar las siguientes acciones: |
| VistaLogin | Tiene como propósito principal presentar una interfaz gráfica al usuario para que pueda iniciar sesión en el sistema. Esta interfaz contiene componentes como campos de texto para ingresar el usuario y la contraseña, botones para iniciar sesión y registrar nuevos usuarios, entre otros. La clase VistaLogin tiene un constructor que inicializa la interfaz de usuario y crea instancias del modelo y del controlador necesarios para el funcionamiento del sistema de inicio de sesión. Además, contiene el método main() que se utiliza para iniciar la aplicación y mostrar la ventana de inicio de sesión.|
| vistaPag | Representa la vista de la aplicación. Es responsable de la presentación de la interfaz de usuario y de interactuar con el usuario. Contiene componentes visuales como botones, etiquetas, paneles y tablas, que el usuario puede ver y manipular. |



### MODELO:

El paquete cuenta con las siguientes clases.

| Nombre | Descripción |
|------|-------------|
| UsuarioDAO | Es parte del modelo en el patrón de arquitectura MVC, a través de operaciones de lectura y escritura en la base de datos, se encarga de realizar operaciones relacionadas con los usuarios en la base de datos. Esto incluye la inserción, actualización, eliminación y búsqueda de usuarios, así como la obtención de listas de usuarios. La clase utiliza la conexión a la base de datos proporcionada por la clase ConexionVuelos para interactuar con la base de datos y ejecutar consultas SQL o llamar a procedimientos almacenados según sea necesario. |
| Usuario | Forma parte del modelo en el patrón MVC y proporciona la estructura para representar los datos de usuario en la aplicación. Es utilizada por el controlador para manejar las operaciones relacionadas con los usuarios, como el registro, inicio de sesión, edición de perfil, etc. |
| VuelosDAO | Es parte del modelo en el patrón MVC (Modelo-Vista-Controlador) y se encarga de realizar operaciones relacionadas con los vuelos en la base de datos. Estos métodos utilizan un objeto de la clase Vuelos para representar la información del vuelo que se va a insertar en la base de datos.|
| Vuelos | Sirve como la representación de un vuelo en el modelo de datos de la aplicación. Sus métodos y atributos se utilizan para almacenar y manipular la información relacionada con los vuelos. |
| ModeloLogin | Se encarga de interactuar con la base de datos para realizar operaciones relacionadas con el registro y autenticación de usuarios. |
| CmModelo | Contiene varios métodos para interactuar con una base de datos de vuelos, manipular archivos, y enviar correos electrónicos. |



### CONTROLADOR:

| Nombre | Descripción |
|------|-------------|
| ControladorCrudUs | Realiza `import Vista.CrudVistaUsuarios;` para crear la Vista para realizar operaciones CRUD (contiene los metodos de Crear, Leer, Actualizar, Eliminar) de usuarios, todo esto a travez de la clase CrudVistaUsuarios (de VISTA).|
| ControladorCrud_Vuelos | Realiza `import Vista.CrudVistaVuelos;` para crear la Vista para realizar operaciones CRUD (contiene los metodos de Crear, Leer, Actualizar, Eliminar) de vuelos, todo esto a travez de la clase CrudVistaVuelos (de VISTA). |
| ControladorLogin | Este controlador gestiona la lógica de interacción entre el modelo (clase ModeloLogin), que maneja los datos y la lógica de la aplicación, y la vista (clase VistaLogin), que representa la interfaz gráfica con la que interactúa el usuario.|
| CmControlador |  |



### MAINS:

| Nombre | Descripción |
|------|-------------|
| mainPrinc | La clase principal de la aplicación, que se encarga de iniciar la interfaz de usuario y configurar el controlador y el modelo.  |
| mainUsuarios | La clase principal mainUsuarios de una aplicación que gestiona la administración de usuarios. |
| mainVuelos | Este código representa la clase principal mainVuelos de la aplicación que gestiona la administración de vuelos. |



### IMAGENES:

Como puedes observar, tambien tenemos un paquete llamado aerolinea.imagenes en el cual guardamos todos las imágenes utilizadas en el proyecto para facilitar el uso de ellas.

## DESCARGA (INSTALACION) Y USO:
1. Descarga:
Realiza la descarga del proyecto por medio del .zip o clonando el repositorio.

2. Importar el proyecto en el IDE preferido.



## BASE DE DATOS:

En este apartado te explicare como crear la base de datos desde mySQL workbench, se recomienda usar este gestor ya que las consultas que ocupamos hacer en nuestro proyecto estan destinadas para este. 
Si necesitas ayuda para instalar MySQL Workbench te puedes apoyar del siguiente video:
https://youtu.be/KvC7iM6O4ik?si=Eo2c4hqchoFh6KfH
**NOTA**: Si cambiaste la contraseña de usuario en Workbench es importante que la cambies en la clase conexion.

Despues de instalar todos necesitaras crear la base de datos.
En el siguiente enlace encontraras dos archivos de texto que contienen los script necesarios para crear la base de datos:
https://drive.google.com/drive/folders/1tTGsMamSBQ57sh1BNjMV_dzZ8O55BD64?usp=sharing

Debes primero ejecutar el script del documento llamado "aerolinea", despues de haber ejecutado sin errores, ejecuta el script del documento "aerolinea_metodos".

**DESPUES DE HABER REALIZADO LA INSTALACION DEL PROYECTO Y LA CREACION DE LA BASE DE DATOS PODRAS USAR LA APLICACION SIN NINGUN PROBLEMA**



## FUNCIONAMIENTO:

- Hemos creado un manual de usuario para que veas el funcionamiento del programa. Para verlo da click en el siguiente enlace:
https://drive.google.com/file/d/1xyYbvI6tWGo2opsPfLZp1bdUNT6ZO5MI/view?usp=sharing

![2](https://github.com/SergioPorrasA/Aerolinea_Proyecto/assets/168385961/6b795c46-64ec-4c63-b3c3-d4bdfc23f361)

## AUTORES:
- Porras Avendaño Sergio Ezequiel - Ing. en Sistemas Computacionales ***ITO*** [https://github.com/SergioPorrasA]
- Ortiz Barroso Itzel - Ing. en Sistemas Computacionales ***ITO*** [https://github.com/ItzelOrtix]
- Gracias por tomarte el tiempo de leer, cualquier duda, sugerencia u aclaración puedes contactarte con nosotros :)
