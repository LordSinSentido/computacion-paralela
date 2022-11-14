package Servidor;

import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Principal
 */
public class Principal {

    public static void main(String[] args) {
        int puerto = 32768;

        try {
            System.out.println("Inicializando el servidor...");
            Registry rmi = LocateRegistry.createRegistry(puerto);
            rmi.rebind("Chat", (Remote) new ImplementacionChat());
            System.out.println("¡Listo! Servidor escuchando en el puerto: " + String.valueOf(puerto));
        } catch (Exception e) {
            System.err.println("Ocurrió un error: " + e);
        }
    }
}