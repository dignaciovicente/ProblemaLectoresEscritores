package logica;

import excepciones.LecturaException;
import excepciones.EscrituraException;

/**
 * Clase que simula el acceso concurrente de lectores y escritores a un recurso compartido.
 */
public class LogicaRecursosCompartidos {

    private int lectoresActivos = 0;
    private boolean escribiendo = false;

    public synchronized void empezarLeer(String nombre) throws LecturaException {
        try {
            while (escribiendo) {
                wait();
            }
            lectoresActivos++;
            System.out.println(nombre + " empieza a leer. Lectores activos: " + lectoresActivos);
        } catch (InterruptedException e) {
            throw new LecturaException("Lectura interrumpida para " + nombre);
        }
    }

    public synchronized void terminarLeer(String nombre) {
        lectoresActivos--;
        System.out.println(nombre + " termina de leer. Lectores restantes: " + lectoresActivos);
        if (lectoresActivos == 0) {
            notifyAll(); // Permite a escritores continuar
        }
    }

    public synchronized void empezarEscribir(String nombre) throws EscrituraException {
        try {
            while (lectoresActivos > 0 || escribiendo) {
                wait();
            }
            escribiendo = true;
            System.out.println(nombre + " empieza a escribir.");
        } catch (InterruptedException e) {
            throw new EscrituraException("Escritura interrumpida para " + nombre);
        }
    }

    public synchronized void terminarEscribir(String nombre) {
        escribiendo = false;
        System.out.println(nombre + " termina de escribir.");
        notifyAll(); // Permite a lectores o nuevos escritores continuar
    }
}
