package servidor;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceCalculadora extends Remote {
    void agregarNumero(int numero) throws RemoteException;
    void agregarOperacion(int operacion) throws RemoteException;
    void realizarOperacion() throws RemoteException;
    void limpiar() throws RemoteException;
    String recuperarDatos() throws RemoteException;
}
