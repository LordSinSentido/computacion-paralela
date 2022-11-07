package cliente;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JLabel;

import servidor.InterfaceCalculadora;

public class Calculadora extends UnicastRemoteObject implements Runnable {
    InterfaceCalculadora calculadora;
    JLabel ventana;

    protected Calculadora(InterfaceCalculadora calculadora, JLabel ventana) throws RemoteException {
        this.calculadora = calculadora;
        this.ventana = ventana;
    }

    @Override
    public void run() {
        while (true) {
            try {
                ventana.setText(calculadora.recuperarDatos());
                Thread.sleep(100);
            } catch (Exception e) {
                System.err.println("Ocurrió un error al recuperar los datos: " + e);
            }
        }
    }
    
}
