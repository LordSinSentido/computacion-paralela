package Cliente;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

import Servidor.InterfaceChat;
import Servidor.InterfaceChatCliente;

public class ImplementacionChatCliente extends UnicastRemoteObject implements InterfaceChatCliente, Runnable {
    InterfaceChat servidor;
    public String nombre = null;

    ImplementacionChatCliente(String nombre, InterfaceChat servidor) throws RemoteException {
        this.nombre = nombre;
        this.servidor = servidor;
        servidor.registro(this);

    }

    @Override
    public void mensajeCliente(String mensaje) throws RemoteException {
        System.out.println(mensaje);
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        String mensaje;

        while (true) {
            mensaje = scanner.nextLine();

            try {
                servidor.mensaje(nombre + ": " + mensaje);
            } catch (Exception e) {
                System.err.println("Ocurrió un error al mandar el mensaje: " + e);
            }
        }

    }

}
