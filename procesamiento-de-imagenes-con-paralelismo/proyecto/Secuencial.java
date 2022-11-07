package proyecto;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Secuencial implements Runnable {
    BufferedImage imagen = null;
    
    public Secuencial(BufferedImage imagen) {
        this.imagen = imagen;
    }

    public BufferedImage getImagen() {
        return imagen;
    }
    
    public BufferedImage convertirAGrises(BufferedImage imagenEntrada) {
        BufferedImage imagenSalida = imagenEntrada;
        
        for(int x = 0; x < imagenEntrada.getWidth(); x++) {
            for(int y = 0; y < imagenEntrada.getHeight(); y++) {
                
                Color colorActual = new Color(imagenEntrada.getRGB(x, y));
                int luma = (int) (colorActual.getRed() * 0.299 + colorActual.getGreen() * 0.557 + colorActual.getBlue() * 0.114);
                imagenSalida.setRGB(x, y, new Color(luma, luma, luma).getRGB());
                
            }
        }
        
        return imagenSalida;
    }
    
    public BufferedImage filtroGaussiano(BufferedImage imagenEntrada) {
        BufferedImage imagenSalida = imagenEntrada;
        int pixelProcesado = 0, colorR, colorG, colorB;
        int[][] mascara = {{1, 4, 7, 4, 1}, {4, 16, 26, 16, 4}, {7, 26, 41, 26, 7}, {4, 16, 26, 16, 4}, {1, 4, 7, 4, 1}};
        
        for(int x = 2; x < imagenEntrada.getWidth() - 3; x++) {
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
    public void run() {
        try {
            imagen = convertirAGrises(imagen);
            imagen = filtroGaussiano(imagen);
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }
}
