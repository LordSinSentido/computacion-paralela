package Servidor;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceChat extends Remote {
    void registro(InterfaceChatCliente cliente) throws RemoteException;
    void mensaje(String mensaje) throws RemoteException;
}
