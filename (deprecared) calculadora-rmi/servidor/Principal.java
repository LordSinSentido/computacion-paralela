package servidor;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Principal {

    public static void main(String[] args) {
        int puerto = 1111;

        System.out.println("Iniciando el servidor...");

        try {
            ClaseCalculadora calculadora = new ClaseCalculadora();
            InterfaceCalculadora stub = (InterfaceCalculadora) UnicastRemoteObject.exportObject(calculadora, puerto);

            Registry registro = LocateRegistry.createRegistry(puerto);

            registro.rebind("calculadora", stub);

            System.out.println("¡Listo! Servidor corriendo en el puerto: " + puerto);
        } catch (Exception e) {
            System.err.println("Ocurrió un error.");
        }
    }
}