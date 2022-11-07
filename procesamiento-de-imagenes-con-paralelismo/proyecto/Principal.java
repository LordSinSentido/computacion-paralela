
package proyecto;

import java.awt.BorderLayout;
import static java.awt.BorderLayout.SOUTH;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Principal extends JFrame {
    Canvas canvaOriginal = new Canvas();
    Canvas canvaProcesada = new Canvas();
    BufferedImage imagenOriginal = null;
    BufferedImage imagenProcesada = null;
    
    
    float duracion;
    long inicio, fin;
    String ruta, nombre, metodo;

    public Principal() {

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
                    limpiarResultado();
                    
                    inicio = System.nanoTime();
                    imagenProcesada = modoSecuencial();
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
                    limpiarResultado();
                    
                    inicio = System.nanoTime();
                    imagenProcesada = modoForkJoin();
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
                    limpiarResultado();
                    
                    inicio = System.nanoTime();
                    imagenProcesada = modoExecutor();
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
                    limpiarResultado();
                });
                botonLimpiar.setVisible(true);
                
                // Boton para guardar imagen
                JButton botonGuardar = new JButton("Guardar resultado");
                botonGuardar.addActionListener((ActionEvent ae) -> {
                    guardarImagen();
                });
                botonGuardar.setVisible(true);
                
                // Boton para abrir imagen
                JButton botonAbrir = new JButton("Abrir imágen");
                botonAbrir.addActionListener((ActionEvent ae) -> {
                    imagenOriginal = cargarImagen();
                    
                    int alto = 500;
                    int ancho = 500;
                    
                    if(imagenOriginal.getWidth() > imagenOriginal.getHeight()) {
                        ancho = 500 * imagenOriginal.getWidth() / imagenOriginal.getHeight();
                    } else {
                        alto = 500 * imagenOriginal.getHeight() / imagenOriginal.getWidth();
                    }
                    canvaOriginal.getGraphics().drawImage(imagenOriginal, 0, 0, ancho, alto, this);
                });
                botonAbrir.setVisible(true);
                
                panelAcciones.add(botonLimpiar);
                panelAcciones.add(botonGuardar);
                panelAcciones.add(botonAbrir);
                
                panelBotones.add(panelAcciones);
                
            
        add(panelImagenes, BorderLayout.CENTER);
        add(panelBotones, SOUTH);
        
    }
    
    public BufferedImage cargarImagen() {
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
    
    public void guardarImagen() {
        //JFileChooser archivo = new JFileChooser();
        //archivo.setDialogTitle("Seleccione una imágen");
        
        try {
            String[] partes = nombre.split("\\.");
            File outputfile = new File(ruta + "/" + partes[0] + " procesada mediante " + metodo + "." + partes[1]);
            ImageIO.write(imagenProcesada, partes[1], outputfile);                    
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }
    
    public void limpiarResultado() {
        BufferedImage limpiar = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        limpiar.setRGB(0, 0, Color.black.getRGB());
        canvaProcesada.getGraphics().drawImage(limpiar, 0, 0, 500, 500, this);
    }
    
    public BufferedImage copia(BufferedImage imagen) {
        BufferedImage copia = new BufferedImage(imagen.getWidth(), imagen.getHeight(), imagen.getType());
        copia.setData(imagen.getData());
        return copia;
    }
    
    public BufferedImage modoSecuencial() {
        
        Secuencial secuencial = new Secuencial(copia(imagenOriginal));
        Thread iniciarSecuencial = new Thread(secuencial);
        
        try {
            iniciarSecuencial.start();
            iniciarSecuencial.join();
        } catch (Exception e) {
            System.err.println(e.toString());
        }
        
        return secuencial.getImagen();
    }
    
    public BufferedImage modoForkJoin() {
        ForkJoinPool Pool = ForkJoinPool.commonPool();
        ForkJoin forkjoin = new ForkJoin(copia(imagenOriginal));
        
        try {
            Pool.invoke(forkjoin);
        } finally {
            return forkjoin.getImagen();
        }
    }
    
    public BufferedImage modoExecutor() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Executor executor = new Executor(copia(imagenOriginal));
        BufferedImage resulato = null;
        try {
            Future<BufferedImage> tarea = executorService.submit(executor);
            while(!tarea.isDone()) {
            }
            resulato = tarea.get();
            executorService.shutdown();
        } catch (Exception e) {
            System.err.println(e.toString());
        } finally {
            return resulato;
        }
        
    }
    
    public static void main(String[] args) {
        Principal principal = new Principal();
        principal.setTitle("Proyecto");
        principal.setSize(1020, 650);
        //principal.pack();
        principal.setResizable(false);
        principal.setVisible(true);
        principal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
}
