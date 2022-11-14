package cliente;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import conexion.Conexion;

public class Principal extends Conexion {

    public Principal(String tipo) throws IOException, InterruptedException {
        super(tipo);

        ArrayList<Thread> clientes = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            Socket servidor = new Socket(host, puerto);
            clientes.add(new Thread(new Instancia(servidor, i)));
        }

        for (Thread cliente : clientes) {
            cliente.start();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        new Principal("cliente");
    }
}