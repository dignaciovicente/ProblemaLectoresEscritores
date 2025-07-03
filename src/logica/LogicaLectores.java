package logica;

import excepciones.LecturaException;
import modelo.Lectores;
import interfaz.Main;

public class LogicaLectores extends Thread {

    private LogicaRecursosCompartidos recurso;
    private Lectores lector;
    private Main interfaz;  // Referencia a la interfaz Main

    public LogicaLectores(LogicaRecursosCompartidos recurso, Lectores lector, Main interfaz) {
        this.recurso = recurso;
        this.lector = lector;
        this.interfaz = interfaz;
    }

    @Override
    public void run() {
        try {
            while (true) {
                recurso.empezarLeer(lector.getNombre());
                interfaz.appendLectores(lector.getNombre() + " empieza a leer.");

                Thread.sleep((int) (Math.random() * 2000 + 1000));

                recurso.terminarLeer(lector.getNombre());
                interfaz.appendLectores(lector.getNombre() + " termina de leer.");

                Thread.sleep((int) (Math.random() * 3000 + 1000));
            }
        } catch (InterruptedException e) {
            System.out.println(lector.getNombre() + " ha sido interrumpido.");
            interfaz.appendLectores(lector.getNombre() + " ha sido interrumpido.");
        } catch (LecturaException e) {
            System.err.println("Excepción de lectura: " + e.getMessage());
            interfaz.appendLectores("Excepción de lectura: " + e.getMessage());
        }
    }
}
