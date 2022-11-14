package cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Instancia implements Runnable {
    Socket servidor;
    int id;

    public Instancia(Socket servidor, int id) {
        this.servidor = servidor;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            DataOutputStream salida = new DataOutputStream(servidor.getOutputStream());
            DataInputStream entrada = new DataInputStream(servidor.getInputStream());

            System.out.println(entrada.readUTF());
            
            for (int i = 1; i <= 3; i++) {
                salida.writeUTF("Mensaje #" + String.valueOf(i) + " del cliente #" + String.valueOf(id) +"\n");
            }

            salida.close();
            entrada.close();
            servidor.close();
        } catch (Exception e) {
            System.err.println("-!- Ocurrió un error al envíar la información al servidor: " + e.getMessage());
        }
    }
}