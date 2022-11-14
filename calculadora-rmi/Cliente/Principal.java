package Cliente;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.*;
import javax.swing.BoxLayout;
import javax.swing.JFrame;

import java.awt.*;
import java.awt.event.ActionEvent;

import Servidor.InterfaceCalculadora;

public class Principal extends JFrame {

    public static void main(String[] args) {
        int puerto = 1212;

        System.out.println("--> Inicializando el servidor...");
        
        try {
            /* Se establece la conexión */
            Registry registro = LocateRegistry.getRegistry("localhost", puerto);
            InterfaceCalculadora implementacion = (InterfaceCalculadora) registro.lookup("calculadora");
            
            /* Se implementa la interfaz gráfica */
            Principal principal = new Principal();

            // Se crean los layouts
            BorderLayout general = new BorderLayout(10, 10);
            GridBagLayout layoutPantalla = new GridBagLayout();
            GridBagConstraints constraints = new GridBagConstraints();

            // Paneles
            JPanel panelCalculadora = new JPanel(layoutPantalla);
            
            
            // Componentes de la pantalla
            JTextField entrada = new JTextField();
            constraints.gridx = 0;
            constraints.gridy = 0;
            constraints.gridwidth = 3;
            constraints.gridheight = 1;
            constraints.fill = GridBagConstraints.BOTH;
            layoutPantalla.setConstraints(entrada, constraints);
            entrada.setVisible(true);
            
            JButton enviar = new JButton("Enviar");
            enviar.addActionListener(arg0 -> {
                try {
                    implementacion.guardarNumero(Integer.valueOf(entrada.getText()));
                    entrada.setEditable(false);
                } catch (Exception e) {
                    System.err.println("Ocurrió un error al mandar la información: " + e);
                }
            });
            constraints.gridx = 2;
            constraints.gridy = 1;
            constraints.gridwidth = 1;
            constraints.gridheight = 1;
            constraints.fill = GridBagConstraints.BOTH;
            layoutPantalla.setConstraints(enviar, constraints);
            enviar.setVisible(true);

            JButton sumar = new JButton("Sumar");
            sumar.addActionListener((arg0 -> {
                try {
                    entrada.setText(String.valueOf(implementacion.realizarOperacion("suma")));
                } catch (Exception e) {
                    System.err.println(e);
                }
            }));
            constraints.gridx = 0;
            constraints.gridy = 1;
            constraints.gridwidth = 1;
            constraints.gridheight = 1;
            constraints.fill = GridBagConstraints.BOTH;
            layoutPantalla.setConstraints(sumar, constraints);
            sumar.setVisible(true);

            JButton restar = new JButton("Restar");
            restar.addActionListener((arg0 -> {
                try {
                    entrada.setText(String.valueOf(implementacion.realizarOperacion("resta")));
                } catch (Exception e) {
                    System.err.println(e);
                }
            }));
            constraints.gridx = 1;
            constraints.gridy = 1;
            constraints.gridwidth = 1;
            constraints.gridheight = 1;
            constraints.fill = GridBagConstraints.BOTH;
            layoutPantalla.setConstraints(restar, constraints);
            restar.setVisible(true);

            JButton multiplicar = new JButton("Multiplicar");
            multiplicar.addActionListener((arg0 -> {
                try {
                    entrada.setText(String.valueOf(implementacion.realizarOperacion("multiplicacion")));
                } catch (Exception e) {
                    System.err.println(e);
                }
            }));
            constraints.gridx = 0;
            constraints.gridy = 2;
            constraints.gridwidth = 1;
            constraints.gridheight = 1;
            constraints.fill = GridBagConstraints.BOTH;
            layoutPantalla.setConstraints(multiplicar, constraints);
            multiplicar.setVisible(true);

            JButton dividir = new JButton("Dividir");
            dividir.addActionListener((arg0 -> {
                try {
                    entrada.setText(String.valueOf(implementacion.realizarOperacion("division")));
                } catch (Exception e) {
                    System.err.println(e);
                }
            }));
            constraints.gridx = 1;
            constraints.gridy = 2;
            constraints.gridwidth = 1;
            constraints.gridheight = 1;
            constraints.fill = GridBagConstraints.BOTH;
            layoutPantalla.setConstraints(dividir, constraints);
            dividir.setVisible(true);

            JButton limpiar = new JButton("Limpiar");
            limpiar.addActionListener((arg0 -> {
                try {
                    implementacion.limipiarMemoria();
                    entrada.setText("");
                    entrada.setEditable(true);
                } catch (Exception e) {
                    System.err.println(e);
                }
            }));
            constraints.gridx = 2;
            constraints.gridy = 2;
            constraints.gridwidth = 1;
            constraints.gridheight = 1;
            constraints.fill = GridBagConstraints.BOTH;
            layoutPantalla.setConstraints(limpiar, constraints);
            limpiar.setVisible(true);


            panelCalculadora.add(entrada);
            panelCalculadora.add(enviar);
            panelCalculadora.add(sumar);
            panelCalculadora.add(restar);
            panelCalculadora.add(multiplicar);
            panelCalculadora.add(dividir);
            panelCalculadora.add(limpiar);
            
            principal.setLayout(general);
            principal.add(panelCalculadora, BorderLayout.CENTER);


            // Se genera la ventana
            principal.setTitle("Calculadora RMI");
            principal.pack();
            //principal.setSize(450, 300);
            principal.setResizable(false);
            principal.setVisible(true);
            principal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        } catch (Exception e) {
            System.err.println("-!- Ocurrió un error: " + e);
        }
    }
}
