
package Servidor;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Executor implements Callable<BufferedImage> {
    ExecutorService ejecutor = Executors.newFixedThreadPool(3);
    BufferedImage imagen = null;
    int inicio, fin;
    
    public Executor(BufferedImage imagen) {
        this.imagen = imagen;
        this.inicio = 0;
        this.fin = imagen.getWidth();
    }
    
    public Executor(BufferedImage imagen, int inicio, int fin) {
        this.imagen = imagen;
        this.inicio = inicio;
        this.fin = fin;
    }
    
    public BufferedImage convertirAGrises(BufferedImage imagenEntrada, int incio, int fin) {
        BufferedImage imagenSalida = imagenEntrada;
        
        for(int x = incio; x < fin; x++) {
            for(int y = 0; y < imagenEntrada.getHeight(); y++) {
                
                Color colorActual = new Color(imagenEntrada.getRGB(x, y));
                int luma = (int) (colorActual.getRed() * 0.299 + colorActual.getGreen() * 0.557 + colorActual.getBlue() * 0.114);
                imagenSalida.setRGB(x, y, new Color(luma, luma, luma).getRGB());
                
            }
        }    
        return imagenSalida;
    }
    
    public BufferedImage filtroGaussiano(BufferedImage imagenEntrada, int inicio, int fin) {
        BufferedImage imagenSalida = imagenEntrada;
        int pixelProcesado = 0, colorR, colorG, colorB;
        int[][] mascara = {{1, 4, 7, 4, 1}, {4, 16, 26, 16, 4}, {7, 26, 41, 26, 7}, {4, 16, 26, 16, 4}, {1, 4, 7, 4, 1}};
        
        if(inicio == 0) {
            inicio = inicio + 2;
        }
        
        if(fin == this.imagen.getWidth()) {
            fin = fin - 3;
        }
        
        for(int x = inicio; x < fin; x++) {
            for(int y = 2; y < imagenEntrada.getHeight() - 3; y++) {
                
                pixelProcesado = 0;
                colorR = 0;
                colorG = 0;
                colorB = 0;
                
                for(int i = 0; i < 5; i++) {
                    for(int j = 0; j < 5; j++) {
                        Color color = new Color(imagenEntrada.getRGB((x - 2) + i, (y - 2 + j)));
                        
                        colorR = color.getRed() * mascara[i][j] + colorR;
                        colorG = color.getGreen()* mascara[i][j] + colorG;
                        colorB = color.getBlue()* mascara[i][j] + colorB;
                        
                    }
                }
                
                Color colorFinal = new Color(colorR / 273, colorG / 273, colorB / 273);       
                imagenSalida.setRGB(x, y, colorFinal.getRGB());
            }
        }
        return imagenSalida;
    }
    
    @Override
    public BufferedImage call() {
        if(fin - inicio <= 2000) {
            try {
                imagen = filtroGaussiano(imagen, inicio, fin);
                imagen = convertirAGrises(imagen, inicio, fin);
            } catch (Exception e) {
                System.err.println(e);
            }
        } else {
            int medio = (inicio  + fin) / 2;
            
            try {
                Future<?> tarea1 = ejecutor.submit(new Executor(imagen, inicio, medio));
                Future<?> tarea2 = ejecutor.submit(new Executor(imagen, medio, fin));
                while(!tarea1.isDone() && !tarea2.isDone()) { 
                }   
            } finally {
                ejecutor.shutdown();
            }
            
            
        }
        return imagen;
    }
    
}


