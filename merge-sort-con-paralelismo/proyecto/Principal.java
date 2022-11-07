
package proyecto;

import java.awt.BorderLayout;
import static java.awt.BorderLayout.*;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Principal extends JFrame {
    int lista[];
    float duracion;
    long inicio, fin;
    
    public Principal() {

        /* Layouts */
        BorderLayout areaDeElementos = new BorderLayout(10, 10);
        GridLayout layoutCreacionArreglos = new GridLayout(3, 1, 0, 0);
        FlowLayout layoutDerecha = new FlowLayout(FlowLayout.LEFT, 5, 5);
        GridLayout layoutMetodos = new GridLayout(3, 1, 0, 0);
        
        setLayout(areaDeElementos);
        
        
        /* Contenedores */
        JPanel panelCreacionArreglo = new JPanel(layoutCreacionArreglos);
        JPanel panelTitulo = new JPanel(layoutDerecha);
        JPanel panelEntrada = new JPanel(layoutDerecha);
        JPanel panelEstado = new JPanel(layoutDerecha);
        JPanel panelMetodos = new JPanel(layoutMetodos);
        JPanel panelSecuencial = new JPanel(layoutDerecha);
        JPanel panelForkJoin = new JPanel(layoutDerecha);
        JPanel panelExecutor = new JPanel(layoutDerecha);


        /* Panel Creacion del arreglo */
        
            /* Panel Titulo */
                
                // Label de Titulo
                JLabel titulo = new JLabel("Ingrese la cantidad de ítems que tendrá el arreglo:");
                titulo.setVisible(true);
                panelTitulo.add(titulo);
            
            /* Panel de estado */
            
                // Label de Titulo
                JLabel estado = new JLabel("Preparado.");
                estado.setVisible(true);
                panelEstado.add(estado);
                
            /* Panel de Entrada */
                 
                // TextField de entrada
                JTextField entrada = new JTextField("0", 20);
                entrada.setVisible(true);
    
                // Botón para la creación del arreglo
                JButton botonCrearArreglo = new JButton("Crear arreglo");
                botonCrearArreglo.addActionListener((ActionEvent ae) -> {
                    estado.setText("Generando arreglo... ");
                    try {
                        crearArreglo(Long.valueOf(entrada.getText()));
                    } catch (Exception e) {
                    }
                    estado.setText(estado.getText() + "¡Listo!");
                });
                botonCrearArreglo.setVisible(true);
                
                panelEntrada.add(entrada);
                panelEntrada.add(botonCrearArreglo);

            panelCreacionArreglo.add(panelTitulo);
            panelCreacionArreglo.add(panelEntrada);
            panelCreacionArreglo.add(panelEstado);
            
        /* Panel Metodos */
        
            /* Panel Secuencial */
            
                // Label de resultado
                JLabel resultadoSecuencial = new JLabel();
                resultadoSecuencial.setVisible(true);
                
                // Boton para la función secuencial
                JButton botonSecuencial = new JButton("Secuencial");
                botonSecuencial.addActionListener((ActionEvent ae) -> {
                    ejecutarSecuencial();
                    resultadoSecuencial.setText("Esta operación demoró " + String.valueOf(duracion) + " milisegundos.");
                });
                botonSecuencial.setVisible(true);

                panelSecuencial.add(botonSecuencial);
                panelSecuencial.add(resultadoSecuencial);                
                
                panelMetodos.add(panelSecuencial);
            
            /* Panel Fork/Join */
            
                // Label de resultado
                JLabel resultadoForkJoin = new JLabel();
                resultadoForkJoin.setVisible(true);
                
                // Boton para la función fork/join
                JButton botonForkJoin = new JButton("Fork/Join");
                botonForkJoin.addActionListener((ActionEvent ae) -> {
                    ejecutarFokJoin();
                    resultadoForkJoin.setText("Esta operación demoró " + String.valueOf(duracion) + " milisegundos.");
                });
                botonForkJoin.setVisible(true);
                
                panelForkJoin.add(botonForkJoin);
                panelForkJoin.add(resultadoForkJoin);
                
                panelMetodos.add(panelForkJoin);
                
            /* Panel Fork/Join */
            
                // Label de resultado
                JLabel resultadoExecutor = new JLabel();
                resultadoExecutor.setVisible(true);
                
                // Boton para la función fork/join
                JButton botonExecutor = new JButton("ExecutorService");
                botonExecutor.addActionListener((ActionEvent ae) -> {
                    ejecutarExecutor();
                    resultadoExecutor.setText("Esta operación demoró " + String.valueOf(duracion) + " milisegundos.");
                });
                botonExecutor.setVisible(true);
                
                panelExecutor.add(botonExecutor);
                panelExecutor.add(resultadoExecutor);
                
                panelMetodos.add(panelExecutor);
            
        add(panelCreacionArreglo, NORTH);
        add(panelMetodos, SOUTH);
    }
    
    public void crearArreglo(long cantidad) {
        lista = new int[(int) cantidad];
        for(int i = 0; i < lista.length; i++) {
            lista[i] = (int) (Math.random() * ((256 - 0) + 1)) + 0;
        }
    }
    
    
    
    public void ejecutarSecuencial() {
        //System.out.println("Main " + Arrays.toString(lista));
        Secuencial secuencial = new Secuencial(lista.clone());
        Thread hiloSecuencial = new Thread(secuencial);

        try {
            inicio = System.nanoTime();
            try {
                hiloSecuencial.start();
                hiloSecuencial.join();
            } catch (InterruptedException e) {
                System.err.println(e);
            }
        } finally {
            fin = System.nanoTime();
        }
        
        duracion = (float) ((fin - inicio) / Math.pow(10, 6));
        //System.out.println("El ordenamiento de manera secuencial demoró " + String.valueOf(duracion) + " milisegundos.");
        
        /*if(lista.length <= 100) {
            mostrarLista(lista);
        }*/
    }
    
    public void ejecutarFokJoin() {
        //System.out.println("Main " + Arrays.toString(lista));
        ForkJoinPool Pool = ForkJoinPool.commonPool();
        ForkJoin forkjoin = new ForkJoin(lista.clone());
        
        inicio = System.nanoTime();
        Pool.invoke(forkjoin);
        fin = System.nanoTime();
        
        duracion = (float) ((fin - inicio) / Math.pow(10, 6));
        //System.out.println("El ordenamiento de manera fork/join demoró " + String.valueOf(duracion) + " milisegundos.");
        if(lista.length <= 100) {
            mostrarLista(forkjoin.getLista());
        }
    }
    
    public void ejecutarExecutor() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Executor ejecutor = new Executor(lista.clone());
        inicio = System.nanoTime();
        
        try {
            Future<?> tarea = executorService.submit(ejecutor);
            while(!tarea.isDone()) {
            }
            executorService.shutdown();
        } catch (Exception e) {
            System.err.println(e.toString());
        } finally {
            fin = System.nanoTime();
        }
        
        duracion = (float) ((fin - inicio) / Math.pow(10, 6));
        //System.out.println("El ordenamiento de manera executorservice demoró " + String.valueOf(duracion) + " milisegundos.");
        /*if(lista.length <= 100) {
            mostrarLista(lista);
        }*/
    }
    
    public void mostrarLista(int[] listaDesordenada) {
        System.out.println("Lista desordenada: " + Arrays.toString(listaDesordenada));
        //System.out.println("Lista ordenada: " + Arrays.toString(listaOrdenada));
    }
    
    public static void main(String[] args) {
        Principal principal = new Principal();
        principal.setTitle("Proyecto");
        principal.setSize(600, 400);
        principal.setResizable(true);
        principal.setVisible(true);
        principal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
