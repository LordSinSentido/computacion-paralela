package Servidor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Implementacion implements Interfaz {
    ArrayList<BufferedImage> imagenesPorProcesar = new ArrayList<>();

    BufferedImage imagenOriginal;
    BufferedImage imagenProcesada;

    /* Métodos de la clase */

    // Función para juntar las imágenes
    protected BufferedImage juntarImagenes(int desplazamiento) { // Desplazamiento: 0 = horizontal, 1 = vertical;
        BufferedImage resultado;
        int alto = 0, ancho = 0;
        int desplazamientoEnX = 0, desplazamientoEnY = 0;

        for (BufferedImage imagen : imagenesPorProcesar) {
            switch (desplazamiento) {
                case 0:
                    alto = imagen.getHeight();
                    ancho = ancho + imagen.getWidth();
                    break;

                case 1:
                    alto = alto + imagen.getHeight();
                    ancho = imagen.getWidth();
                    break;
            
                default:
                    break;
            }
        }

        resultado = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);

        for (BufferedImage imagen : imagenesPorProcesar) {
            resultado.getGraphics().drawImage(imagen, desplazamientoEnX, desplazamientoEnY, null);
            switch (desplazamiento) {
                case 0:
                    desplazamientoEnX = desplazamientoEnX + imagen.getWidth();
                    break;
                
                case 1:
                    desplazamientoEnY = desplazamientoEnY + imagen.getHeight();
                    break;
                default:
                    break;
            }
        }

        try {
            ImageIO.write(resultado, "png", new File("Imágen resultante"));
        } catch (IOException e) {
            System.err.println("Ocurrió un error al guardar la imágen resultante: " + e.getMessage());
        }

        return resultado;
    }

    /* Metodos de la interfaz */

    @Override
    public boolean cargarImagen(ImageIcon imagen) throws RemoteException {
        boolean exito;

        BufferedImage temporal = new BufferedImage(imagen.getIconWidth(), imagen.getIconHeight(), BufferedImage.TYPE_INT_RGB);
        imagen.paintIcon(null, temporal.createGraphics(), 0, 0);

        try {
            imagenesPorProcesar.add(temporal);
            exito = true;
            System.out.println("-!- Imágen guradada.");
        } catch (Exception e) {
            System.err.println("-!- Ocurrió un error al guardar la imágen: " + e.getMessage());
            exito = false;
        }

        return exito;
    }

    @Override
    public ImageIcon secuencial(int modo) throws RemoteException {
        System.out.println("--> Procesando imágen (Secuencial)...");

        Secuencial secuencial = new Secuencial(juntarImagenes(modo));
        Thread iniciarSecuencial = new Thread(secuencial);
        
        try {
            iniciarSecuencial.start();
            iniciarSecuencial.join();
        } catch (Exception e) {
            System.err.println("Ocurrió un error al procesar las imágenes (Secuencial): " + e.getMessage());
        }
        
        System.out.println("-!- Tarea terminada.");
        return new ImageIcon(secuencial.getImagen());
    }

    @Override
    public ImageIcon forkJoin(int modo) throws RemoteException {
        System.out.println("--> Procesando imágen (Fork/Join)...");

        ForkJoinPool Pool = ForkJoinPool.commonPool();
        ForkJoin forkjoin = new ForkJoin(juntarImagenes(modo));
        
        try {
            Pool.invoke(forkjoin);
        } catch (Exception e) {
            System.err.println("-!- Ocurrió un error al procesar las imágenes (Fork/Join): " + e.getMessage());
        } finally {
            System.out.println("-!- Tarea terminada.");
        }

        return new ImageIcon(forkjoin.getImagen());
    }

    @Override
    public ImageIcon executorService(int modo) throws RemoteException {
        System.out.println("--> Procesando imágen (Executor Service)...");

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Executor executor = new Executor(juntarImagenes(modo));
        BufferedImage resultado = null;
        try {
            Future<BufferedImage> tarea = executorService.submit(executor);
            while(!tarea.isDone()) {
            }
            resultado = tarea.get();
            executorService.shutdown();
            Thread.sleep(1000);
        } catch (Exception e) {
            System.err.println("-!- Ocurrió un error al procesar las imágenes (Fork/Join): " + e.getMessage());
        } finally {
            System.out.println("-!- Tarea terminada.");
        }

        
        return new ImageIcon(resultado);
    }

    @Override
    public boolean limpiar() throws RemoteException {
        boolean exito;
        try {
            imagenesPorProcesar.clear();
            exito = true;
        } catch (Exception e) {
            exito = false;
            System.err.println("-!- Ocurrió un error al limpiar la cola de imágenes: " + e.getMessage());
        }
        return exito;
    }
    
}
