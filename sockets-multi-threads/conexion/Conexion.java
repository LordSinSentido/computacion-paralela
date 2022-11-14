package conexion;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Conexion {
    protected final int puerto = 2020;
    protected final String host = "localhost";
    protected String mensaje;
    protected ServerSocket socketServidor;
    protected Socket socketCliente;
    protected DataOutputStream salidaServidor, salidaCliente;
    
    public Conexion(String tipo) throws IOException {
        if (tipo.equalsIgnoreCase("servidor")) {
            socketServidor = new ServerSocket(puerto);
            socketCliente = new Socket();
        } /*else {
            socketCliente = new Socket(host, puerto);
        }*/
    }    
}