package Servidor;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Principal {
    
    public static void main(String[] args) {
        int puerto = 1212;

        System.out.println("--> Inicializando el servidor...");

        try {
            ImplementacionCalculadora implementacion = new ImplementacionCalculadora();
            InterfaceCalculadora slub = (InterfaceCalculadora) UnicastRemoteObject.exportObject(implementacion, puerto);
            Registry registro = LocateRegistry.createRegistry(puerto);
            registro.rebind("calculadora", slub);

            System.out.println("--> ¡Listo! Servidor inicializado en el puerto " + String.valueOf(puerto));
        } catch (Exception e) {
            System.err.println("-!- Ocurrió un error: " + e);
        }
    }
}