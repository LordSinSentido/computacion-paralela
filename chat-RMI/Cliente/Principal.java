package Cliente;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JOptionPane;

import Servidor.InterfaceChat;

public class Principal {
    public static void main(String[] args) {
        int puerto = 1111;

        try {
            String dialogo = JOptionPane.showInputDialog("Ingresa tu nombre");
            String nombre = dialogo;

            Registry rmic = LocateRegistry.getRegistry("localhost", puerto);
            InterfaceChat servidor = (InterfaceChat) rmic.lookup("Chat");
            new Thread(new ImplementacionChatCliente(nombre, servidor)).start();
        } catch (Exception e) {
            System.err.println("Ocurrió un error: " + e);
        }
    }
}
