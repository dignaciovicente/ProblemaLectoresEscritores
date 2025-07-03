package logica;

import excepciones.EscrituraException;
import modelo.Escritores;
import interfaz.Main;

public class LogicaEscritores extends Thread {

    private LogicaRecursosCompartidos recurso;
    private Escritores escritor;
    private Main interfaz;  // Referencia a la interfaz Main

    // Constructor recibe la instancia de Main para poder llamar a sus métodos
    public LogicaEscritores(LogicaRecursosCompartidos recurso, Escritores escritor, Main interfaz) {
        this.recurso = recurso;
        this.escritor = escritor;
        this.interfaz = interfaz;
    }

    @Override
    public void run() {
        try {
            while (true) {
                recurso.empezarEscribir(escritor.getNombre());
                interfaz.appendEscritores(escritor.getNombre() + " empieza a escribir.");

                Thread.sleep((int) (Math.random() * 2000 + 1000));

                recurso.terminarEscribir(escritor.getNombre());
                interfaz.appendEscritores(escritor.getNombre() + " termina de escribir.");

                Thread.sleep((int) (Math.random() * 3000 + 1000));
            }
        } catch (InterruptedException e) {
            System.out.println(escritor.getNombre() + " ha sido interrumpido.");
            interfaz.appendEscritores(escritor.getNombre() + " ha sido interrumpido.");
        } catch (EscrituraException e) {
            System.err.println("Excepción de escritura: " + e.getMessage());
            interfaz.appendEscritores("Excepción de escritura: " + e.getMessage());
        }
    }
}
