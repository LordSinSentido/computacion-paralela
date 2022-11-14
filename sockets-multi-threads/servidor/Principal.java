package servidor;

import java.io.IOException;
import java.net.Socket;

import conexion.Conexion;

public class Principal extends Conexion {

    public Principal(String tipo) throws IOException {
        super(tipo);

        System.out.println("--> Iniciando servidor en " + host + ":" + String.valueOf(puerto) + "...");

        try {
            System.out.println("-!- Servidor inicializado");
            //System.out.println("--> Esperando conexión de algún cliente...");

            // Se crea el identificador de sesión
            int id = 1;

            while(true) {
                // Se crea un nuevo socket por cada usuario conectado
                Socket nuevaConexion;
                System.out.println("Esperando conexión");
                nuevaConexion = socketServidor.accept();
                System.out.println("-!- ¡Cliente conectado!");
                
                // Se inicializa el hilo para el clinete conectado
                Thread instanciaThread = new Thread(new Instancia(nuevaConexion, id));
                instanciaThread.start();
                System.out.println("Hilo iniciado");

                // Se incrementa el identificador
                id++;
            }
        } catch (Exception e) {
            System.err.println("-!- Ocurrió un error al iniciarlizar el servidor: " + e);
        }
    }

    public static void main(String[] args) throws IOException {
        new Principal("servidor");
    }
}