package servidor;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Instancia implements Runnable {
    Socket cliente;
    DataOutputStream salidaCliente;
    int id;
    String mensaje;

    public Instancia(Socket cliente, int id) {
        this.cliente = cliente;
        this.id = id;
    }

    @Override
    public void run() {     
        try {
            // Se le manda un mensaje al cliente conectado para avisarle que la conexión se realizó exitosamente
            salidaCliente = new DataOutputStream(cliente.getOutputStream());
            salidaCliente.writeUTF("-!- Conexión establecida");
        } catch (Exception e) {
            System.err.println(e);
        }

        try {

            BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));

            while ((mensaje = entrada.readLine()) != null) {
                System.out.println(String.valueOf(id) + "@" + cliente.getLocalAddress().toString() + ": " + mensaje);
            }

            cliente.close();
        } catch (IOException e) {
            System.err.println("-!- Ocurrió un error al recuperar la información del cliente #" + String.valueOf(id) + ": " + e.getMessage());
        }
    }
}