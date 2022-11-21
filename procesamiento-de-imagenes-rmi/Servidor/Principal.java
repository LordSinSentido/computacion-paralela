package Servidor;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Principal {
    public static void main(String[] args) {
        int puerto = 5252;

        try {
            Implementacion implementacion = new Implementacion();
            Interfaz skeleton = (Interfaz) UnicastRemoteObject.exportObject(implementacion, puerto);
            Registry registro = LocateRegistry.createRegistry(puerto);
            registro.rebind("procesamiento", skeleton);

            System.out.println("-!- Servidor en línea.");
        } catch (Exception e) {
            System.err.println("-!- Ocurrió un error el levantar el servidor: " + e.getMessage());
        }
    }    
}