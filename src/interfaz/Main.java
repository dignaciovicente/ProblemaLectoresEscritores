package interfaz;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import logica.LogicaLectores;
import logica.LogicaEscritores;
import logica.LogicaRecursosCompartidos;
import modelo.Lectores;
import modelo.Escritores;
import persistencia.GestorPersistencia;

public class Main extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public JTextArea txtLectores;
    public JTextArea txtEscritores;
    public JLabel lblEstado;
    public JButton btnIniciar;
    public JButton btnAgregarLector;
    public JButton btnAgregarEscritor;
    public JButton btnMostrarTodos;

    private LogicaRecursosCompartidos recursoCompartido;
    private GestorPersistencia persistencia;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Main frame = new Main();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Main() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 700);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitulo = new JLabel("Problema de Lectores y Escritores");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setBounds(230, 10, 500, 30);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(lblTitulo);

        txtLectores = new JTextArea();
        txtLectores.setEditable(false);
        txtLectores.setFont(new Font("Consolas", Font.PLAIN, 14));
        txtLectores.setBorder(new TitledBorder("Lectores"));
        JScrollPane scrollLectores = new JScrollPane(txtLectores);
        scrollLectores.setBounds(50, 70, 400, 400);
        contentPane.add(scrollLectores);

        txtEscritores = new JTextArea();
        txtEscritores.setEditable(false);
        txtEscritores.setFont(new Font("Consolas", Font.PLAIN, 14));
        txtEscritores.setBorder(new TitledBorder("Escritores"));
        JScrollPane scrollEscritores = new JScrollPane(txtEscritores);
        scrollEscritores.setBounds(530, 70, 400, 400);
        contentPane.add(scrollEscritores);

        btnIniciar = new JButton("Iniciar Simulación");
        btnIniciar.setBounds(380, 490, 220, 40);
        btnIniciar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        contentPane.add(btnIniciar);

        btnAgregarLector = new JButton("Agregar Lector");
        btnAgregarLector.setBounds(50, 520, 180, 30);
        contentPane.add(btnAgregarLector);

        btnAgregarEscritor = new JButton("Agregar Escritor");
        btnAgregarEscritor.setBounds(723, 520, 180, 30);
        contentPane.add(btnAgregarEscritor);

        btnMostrarTodos = new JButton("Mostrar Todos");
        btnMostrarTodos.setBounds(380, 540, 220, 30);
        contentPane.add(btnMostrarTodos);

        lblEstado = new JLabel("Estado: Esperando...");
        lblEstado.setBounds(50, 580, 880, 30);
        lblEstado.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        lblEstado.setForeground(Color.DARK_GRAY);
        contentPane.add(lblEstado);

        recursoCompartido = new LogicaRecursosCompartidos();

        try {
            persistencia = new GestorPersistencia();
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Error conectando a la base de datos:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        btnIniciar.addActionListener(e -> iniciarSimulacion());
        btnAgregarLector.addActionListener(e -> agregarLector());
        btnAgregarEscritor.addActionListener(e -> agregarEscritor());
        btnMostrarTodos.addActionListener(e -> mostrarTodos());
    }

    private void iniciarSimulacion() {
        lblEstado.setText("Estado: Simulación iniciada");
        btnIniciar.setEnabled(false);

        for (int i = 1; i <= 3; i++) {
            Lectores lectorModel = new Lectores(i, "Lector " + i);
            LogicaLectores lectorThread = new LogicaLectores(recursoCompartido, lectorModel, this);
            lectorThread.start();
        }

        for (int i = 1; i <= 2; i++) {
            Escritores escritorModel = new Escritores(i, "Escritor " + i);
            LogicaEscritores escritorThread = new LogicaEscritores(recursoCompartido, escritorModel, this);
            escritorThread.start();
        }
    }

    private void agregarLector() {
        try {
            String idStr = JOptionPane.showInputDialog(this, "ID del lector:");
            String nombre = JOptionPane.showInputDialog(this, "Nombre del lector:");

            if (idStr == null || nombre == null || idStr.trim().isEmpty() || nombre.trim().isEmpty()) {
                return;
            }

            int id = Integer.parseInt(idStr);
            Lectores lector = new Lectores(id, nombre);
            persistencia.agregarLector(lector);
            JOptionPane.showMessageDialog(this, "Lector agregado correctamente.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Error al agregar lector:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void agregarEscritor() {
        try {
            String idStr = JOptionPane.showInputDialog(this, "ID del escritor:");
            String nombre = JOptionPane.showInputDialog(this, "Nombre del escritor:");

            if (idStr == null || nombre == null || idStr.trim().isEmpty() || nombre.trim().isEmpty()) {
                return;
            }

            int id = Integer.parseInt(idStr);
            Escritores escritor = new Escritores(id, nombre);
            persistencia.agregarEscritor(escritor);
            JOptionPane.showMessageDialog(this, "Escritor agregado correctamente.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Error al agregar escritor:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarTodos() {
        try {
            txtLectores.setText("");
            txtEscritores.setText("");

            for (Lectores l : persistencia.obtenerLectores()) {
                txtLectores.append(l.getId() + " - " + l.getNombre() + "\n");
            }

            for (Escritores e : persistencia.obtenerEscritores()) {
                txtEscritores.append(e.getId() + " - " + e.getNombre() + "\n");
            }
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Error al obtener datos:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Métodos para actualizar las áreas de texto desde los hilos
    public synchronized void appendLectores(String texto) {
        txtLectores.append(texto + "\n");
    }

    public synchronized void appendEscritores(String texto) {
        txtEscritores.append(texto + "\n");
    }
}
