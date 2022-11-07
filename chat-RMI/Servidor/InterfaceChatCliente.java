package Servidor;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceChatCliente extends Remote {
    void mensajeCliente(String mensaje) throws RemoteException;
}
