package servidor;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;

import conexion.Conexion;

public class Principal extends Conexion {

    public Principal(String tipo) throws IOException {
        super(tipo);

        System.out.println("--> Iniciando servidor en " + host + ":" + String.valueOf(puerto) + "...");

        try {
            System.out.println("-!- Servidor inicializado");
            System.out.println("--> Esperando conexión de algún cliente...");
            
            socketCliente = socketServidor.accept();
            System.out.println("-!- ¡Cliente conectado!");

            salidaCliente = new DataOutputStream(socketCliente.getOutputStream());
            salidaCliente.writeUTF("-!- Petición recibida");

            BufferedReader entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));

            System.out.println("<-- Mensaje del cliente -->\n");
            while((mensaje = entrada.readLine()) != null) {
                System.out.println(mensaje);
            }
            System.out.println("\n<------------------------->");

            socketServidor.close();
            System.out.println("-!- Sesión finalizada, servidor cerrado.");

        } catch (Exception e) {
            System.err.println("-!- Ocurrió un error: " + e);
        }
    }

    public static void main(String[] args) throws IOException {
        new Principal("servidor");
    }
}