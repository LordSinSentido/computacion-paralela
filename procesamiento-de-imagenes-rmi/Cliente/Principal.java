package Cliente;

import java.awt.image.BufferedImage;
import java.io.File;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import java.awt.BorderLayout;
import static java.awt.BorderLayout.SOUTH;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import Servidor.Interfaz;

public class Principal extends JFrame {
    Canvas canvaOriginal = new Canvas();
    Canvas canvaProcesada = new Canvas();
    BufferedImage imagenOriginal;
    BufferedImage imagenProcesada;


    float duracion;
    long inicio, fin;
    String ruta, nombre, metodo;

    public Principal(Interfaz interfaz) {

        /* Interfaz gráfica */

            /* Layouts */
            BorderLayout areaDeElementos = new BorderLayout(10, 10);
            GridLayout layoutImagenes = new GridLayout(1, 2);
            GridLayout layoutBotones = new GridLayout(2, 2);
            GridLayout layoutResultados = new GridLayout(2, 3, 10, 0);
            FlowLayout layoutDerecha = new FlowLayout(FlowLayout.LEFT, 5, 5);
            
            setLayout(areaDeElementos);
            
            
            /* Contenedores */
            JPanel panelImagenes = new JPanel(layoutImagenes);
            JPanel panelImagenEntrada = new JPanel(layoutDerecha);
            JPanel panelImagenSalida = new JPanel(layoutDerecha);
            JPanel panelBotones = new JPanel(layoutBotones);
            JPanel panelMetodos = new JPanel(layoutDerecha);
            JPanel panelAcciones = new JPanel(layoutDerecha);
            JPanel panelResultados = new JPanel(layoutResultados);
    
    
            /* Panel de imágenes */
                
                // Titulo de la imágen original
                JLabel textoImagenOriginal = new JLabel("Imágen original");
                textoImagenOriginal.setVisible(true);
                
                // Canva de la imágen original
                canvaOriginal.setSize(500, 500);
                canvaOriginal.setBackground(Color.black);
                canvaOriginal.setVisible(true);
                
                panelImagenEntrada.add(textoImagenOriginal);
                panelImagenEntrada.add(canvaOriginal);
                
                // Titulo de la imágen procesada
                JLabel textoImagenProcesada = new JLabel("Imágen procesada");
                textoImagenProcesada.setVisible(true);
                
                // Canva de la imágen procesada
                canvaProcesada.setSize(500, 500);
                canvaProcesada.setBackground(Color.black);
                canvaProcesada.setVisible(true);
                
                panelImagenSalida.add(textoImagenProcesada);
                panelImagenSalida.add(canvaProcesada);
    
                
                panelImagenes.add(panelImagenEntrada);            
                panelImagenes.add(panelImagenSalida);
    
            /* Panel de botones */
            
                // Titulo de los metodos
                JLabel tituloMetodos = new JLabel("Metodo de proceso");
                tituloMetodos.setVisible(true);
                
                // Titulo de las acciones
                JLabel tituloAcciones = new JLabel("Opciones");
                tituloMetodos.setVisible(true);
                
                panelBotones.add(tituloMetodos);
                panelBotones.add(tituloAcciones);
            
                /*Panel de metodos */
                    
                    // Label de tiempo de secuencial
                    JLabel tiempoSecuencial = new JLabel("¡Preparado!");
                    tiempoSecuencial.setVisible(true);
                    
                    // Label de tiempo de  forkjoin
                    JLabel tiempoForJoin = new JLabel("¡Preparado!");
                    tiempoForJoin.setVisible(true);
                    
                    // Label de tiempo de executor
                    JLabel tiempoExecutor = new JLabel("¡Preparado!");
                    tiempoExecutor.setVisible(true);
                
                    // Boton para la función secuencial
                    JButton botonSecuencial = new JButton("Secuencial");
                    botonSecuencial.addActionListener((ActionEvent ae) -> {
                        inicio = System.nanoTime();

                        try {
                            ImageIcon resultado = interfaz.secuencial(0);
                            imagenProcesada = convertir(resultado);
                        } catch (Exception e) {
                            System.err.println("-!- Ocurrió un error al procesar la imágen (Secuencial): " + e.getMessage());
                        }

                        fin = System.nanoTime();
                        
                        duracion = (float) ((fin - inicio) / Math.pow(10, 6));
                        tiempoSecuencial.setText(String.valueOf(duracion) + " ms.");
                        
                        int alto = 500;
                        int ancho = 500;
                        
                        if(imagenProcesada.getWidth() > imagenProcesada.getHeight()) {
                            ancho = 500 * imagenProcesada.getWidth() / imagenProcesada.getHeight();
                        } else {
                            alto = 500 * imagenProcesada.getHeight() / imagenProcesada.getWidth();
                        }
                        metodo = "secuencial";
                        canvaProcesada.getGraphics().drawImage(imagenProcesada, 0, 0, ancho, alto, this);
                    });
                    botonSecuencial.setVisible(true);
                    
                    // Boton para la función fork/join
                    JButton botonForkJoin = new JButton("Fork/Join");
                    botonForkJoin.addActionListener((ActionEvent ae) -> {
                        inicio = System.nanoTime();

                        try {
                            ImageIcon resultado = interfaz.forkJoin(0);
                            imagenProcesada = convertir(resultado);
                        } catch (Exception e) {
                            System.err.println("-!- Ocurrió un error al procesar la imágen (Fork/Join): " + e.getMessage());
                        }

                        fin = System.nanoTime();
                        
                        duracion = (float) ((fin - inicio) / Math.pow(10, 6));
                        tiempoForJoin.setText(String.valueOf(duracion) + " ms.");
                        
                        int alto = 500;
                        int ancho = 500;
                        
                        if(imagenProcesada.getWidth() > imagenProcesada.getHeight()) {
                            ancho = 500 * imagenProcesada.getWidth() / imagenProcesada.getHeight();
                        } else {
                            alto = 500 * imagenProcesada.getHeight() / imagenProcesada.getWidth();
                        }
                        metodo = "fork join";
                        canvaProcesada.getGraphics().drawImage(imagenProcesada, 0, 0, ancho, alto, this);
                    });
                    botonForkJoin.setVisible(true);
                    
                    // Boton para la función fork/join
                    JButton botonExecutor = new JButton("ExecutorService");
                    botonExecutor.addActionListener((ActionEvent ae) -> {
                        inicio = System.nanoTime();
                        try {
                            ImageIcon resultado = interfaz.executorService(0);
                            imagenProcesada = convertir(resultado);
                        } catch (Exception e) {
                            System.err.println("-!- Ocurrió un error al procesar la imágen (Executor Service): " + e.getMessage());
                        }
                        fin = System.nanoTime();
                        
                        duracion = (float) ((fin - inicio) / Math.pow(10, 6));
                        tiempoExecutor.setText(String.valueOf(duracion) + " ms.");
                        
                        int alto = 500;
                        int ancho = 500;
                        
                        if(imagenProcesada.getWidth() > imagenProcesada.getHeight()) {
                            ancho = 500 * imagenProcesada.getWidth() / imagenProcesada.getHeight();
                        } else {
                            alto = 500 * imagenProcesada.getHeight() / imagenProcesada.getWidth();
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {
                        }
                        
                        metodo = "executor service";
                        canvaProcesada.getGraphics().drawImage(imagenProcesada, 0, 0, ancho, alto, this);
                    });
                    botonExecutor.setVisible(true);
                    
                    panelResultados.add(botonSecuencial);
                    panelResultados.add(botonForkJoin);
                    panelResultados.add(botonExecutor);
                    
                    panelResultados.add(tiempoSecuencial);
                    panelResultados.add(tiempoForJoin);
                    panelResultados.add(tiempoExecutor);
                    
                    panelMetodos.add(panelResultados);
                    
                    panelBotones.add(panelMetodos);
                    
                /* Panel de acciones */
                    
                    // Boton para limpiar el canva
                    JButton botonLimpiar = new JButton("Limpiar resultado");
                    botonLimpiar.addActionListener((ActionEvent ae) -> {
                        try {
                            if(interfaz.limpiar()) {
                                limpiarCanvas();
                            }
                        } catch (Exception e) {
                            System.err.println("-!- Ocurrió un error al limpiar la cola de imágenes: " + e.getMessage());
                        }
                    });
                    botonLimpiar.setVisible(true);
                    
                    // Boton para guardar imagen
                    JButton botonGuardar = new JButton("Guardar resultado");
                    botonGuardar.addActionListener((ActionEvent ae) -> {
                        try {
                            String[] partes = nombre.split("\\.");
                            File outputfile = new File(ruta + "/" + partes[0] + " procesada mediante " + metodo + "." + partes[1]);
                            ImageIO.write(imagenProcesada, partes[1], outputfile);                    
                        } catch (Exception e) {
                            System.err.println("-!- Ocurrió un error al guardar la imágen: " + e.getMessage());
                        }
                    });
                    botonGuardar.setVisible(true);
                    
                    // Boton para abrir imagen
                    JButton botonAbrir = new JButton("Abrir imágen");
                    botonAbrir.addActionListener((ActionEvent ae) -> {
                        imagenOriginal = cargarImagen();

                        if(imagenOriginal != null) {
                            try {
                                boolean cargado = interfaz.cargarImagen(new ImageIcon(imagenOriginal));
                                if (cargado) {
                                    int alto = 500;
                                    int ancho = 500;
                                    
                                    if(imagenOriginal.getWidth() > imagenOriginal.getHeight()) {
                                        ancho = 500 * imagenOriginal.getWidth() / imagenOriginal.getHeight();
                                    } else {
                                        alto = 500 * imagenOriginal.getHeight() / imagenOriginal.getWidth();
                                    }
                                    canvaOriginal.getGraphics().drawImage(imagenOriginal, 0, 0, ancho, alto, this);
                                }
                            } catch (Exception e) {
                                System.err.println("Ocurrió un error al cargar la imágen al servidor: " + e.getMessage());
                            }
                        }
                    });
                    botonAbrir.setVisible(true);
                    
                    panelAcciones.add(botonLimpiar);
                    panelAcciones.add(botonGuardar);
                    panelAcciones.add(botonAbrir);
                    
                    panelBotones.add(panelAcciones);
                    
                
            add(panelImagenes, BorderLayout.CENTER);
            add(panelBotones, SOUTH);

        setTitle("Proyecto");
        setSize(1020, 650);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private BufferedImage cargarImagen() {
        BufferedImage imagen = null;
        
        JFileChooser archivo = new JFileChooser();
        archivo.setDialogTitle("Seleccione una imágen");
        FileNameExtensionFilter imagenesPermitidas = new FileNameExtensionFilter("JPG, PGN, BMP", "jpg", "png", "bmp");
        archivo.setFileFilter(imagenesPermitidas);
        int flag = archivo.showOpenDialog(null);
        
        if(flag == JFileChooser.APPROVE_OPTION) {
            try {
                File imagenSeleccionada = archivo.getSelectedFile();
                ruta = imagenSeleccionada.getParent();
                nombre = imagenSeleccionada.getName();
                imagen = ImageIO.read(imagenSeleccionada);
            } catch (Exception e) {
                System.err.println(e.toString());
            }
        }
        return imagen;
    }

    private BufferedImage convertir(ImageIcon imagen) {
        BufferedImage temporal = new BufferedImage(imagen.getIconWidth(), imagen.getIconHeight(), BufferedImage.TYPE_INT_RGB);
        imagen.paintIcon(null, temporal.createGraphics(), 0, 0);
        return temporal;
    }

    public void limpiarCanvas() {
        BufferedImage limpiar = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        limpiar.setRGB(0, 0, Color.black.getRGB());
        canvaProcesada.getGraphics().drawImage(limpiar, 0, 0, 500, 500, this);
    }

    public static void main(String[] args) {
        int puerto = 5252;

        try {
            Registry registro = LocateRegistry.getRegistry("localhost", puerto);
            Interfaz interfaz = (Interfaz) registro.lookup("procesamiento");

            System.out.println("-!- Conexión establecida.");

            new Principal(interfaz);

        } catch (Exception e) {
            System.err.println("-!- Ocurrió un error al tratar de conectar con el servidor: " + e.getMessage());
        }
    }
}
