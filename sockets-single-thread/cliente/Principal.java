package cliente;

import java.io.DataOutputStream;
import java.io.IOException;

import conexion.Conexion;

public class Principal extends Conexion {
    
    public Principal(String tipo) throws IOException {
        super(tipo);
        System.out.println("--> Estableciendo conexión con el servidor en " + host + ":" + String.valueOf(puerto) + "...");
        try {
            salidaServidor = new DataOutputStream(socketCliente.getOutputStream());
            System.out.println("-!- Conexión realizada");

            System.out.println("--> Transmitiendo mensaje...");
            for (int i = 0; i < 10; i++) {
                salidaServidor.writeUTF("Mensaje con el número: " + String.valueOf(i) + "\n");
                Thread.sleep(2000);
            }
            System.out.println("-!- Transmisión concluida, conexión cerrada");

            socketCliente.close();

        } catch (Exception e) {
            System.err.println("-!- Ocurrió un error: " + e.getMessage());
        }

    }

    public static void main(String[] args) throws IOException {
        new Principal("cliente");
    }
}
