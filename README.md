# ProblemaLectoresEscritores

## Autor
Ignacio Vicente del Rosal Hernández

## Descripción
Proyecto Java que simula el problema clásico de los lectores y escritores, donde múltiples hilos acceden de forma concurrente a una base de datos MySQL.

El programa incluye:

- Implementación de la lógica de sincronización para lectores y escritores.
- Interfaz gráfica para visualizar el comportamiento de los hilos.
- Acceso y almacenamiento de datos en una base MySQL para persistencia de información.
- Manejo de excepciones personalizadas para mejorar la robustez.

## Objetivo
El objetivo principal es comprender y resolver problemas de concurrencia, evitando condiciones de carrera y garantizando acceso ordenado a recursos compartidos.

## Tecnologías
- Lenguaje: Java SE 8 o superior.
- Base de datos: MySQL.
- IDE recomendado: Eclipse o cualquier IDE compatible con Java.
- Librerías usadas: JDBC para conexión con base de datos.

## Estructura del proyecto
- `modelo`: Clases que representan entidades como Lectores y Escritores.
- `logica`: Contiene la lógica para la sincronización y control de acceso.
- `interfaz`: Código responsable de la interfaz gráfica del usuario (GUI).
- `persistencia`: Clases para la conexión y operaciones con la base de datos MySQL.
- `excepciones`: Definición de excepciones personalizadas para lectura y escritura.

## Cómo ejecutar
1. Configurar una base de datos MySQL local con las tablas necesarias para lectores y escritores.
2. Modificar las credenciales de conexión en la clase `GestorPersistencia.java`.
3. Ejecutar el proyecto desde el IDE o consola, asegurándose de que la base de datos esté activa.
4. Utilizar la interfaz gráfica para iniciar la simulación y observar el comportamiento.

## Uso en entornos reales
Este proyecto puede ser la base para sistemas donde múltiples usuarios acceden y modifican información simultáneamente, como bases de datos, sistemas de archivos o aplicaciones web concurrentes.

## Contacto
Para dudas o sugerencias, contactar con Ignacio Vicente del Rosal Hernández a través de dignaciovicente@gmail.com
