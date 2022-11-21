
package Servidor;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.concurrent.RecursiveAction;

public class ForkJoin extends RecursiveAction {
    BufferedImage imagen = null;
    int inicio, fin;
    
    public ForkJoin(BufferedImage imagen) {
        this.imagen = imagen;
        this.inicio = 0;
        this.fin = imagen.getWidth();
    }
    
    public ForkJoin(BufferedImage imagen, int inicio, int fin) {
        this.imagen = imagen;
        this.inicio = inicio;
        this.fin = fin;
    }

    public BufferedImage getImagen() {
        return imagen;
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
    protected void compute() {
        if(fin - inicio <= 1000) {
            try {
                imagen = filtroGaussiano(imagen, inicio, fin);
                imagen = convertirAGrises(imagen, inicio, fin);
            } catch (Exception e) {
                System.err.println(e);
            }
            return;
        } else {
            int medio = (inicio  + fin) / 2;
            
            ForkJoin izquierda = new ForkJoin(imagen, inicio, medio);
            ForkJoin derecha = new ForkJoin(imagen, medio, fin);
            
            invokeAll(izquierda, derecha);   
        }
    }
}
