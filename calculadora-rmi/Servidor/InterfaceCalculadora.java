package Servidor;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceCalculadora extends Remote {
    void guardarNumero(int numero) throws RemoteException;
    int realizarOperacion(String operacion) throws RemoteException;
    void limipiarMemoria() throws RemoteException;
}
