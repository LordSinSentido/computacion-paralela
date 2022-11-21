package Servidor;

import java.rmi.Remote;
import java.rmi.RemoteException;

import javax.swing.ImageIcon;

public interface Interfaz extends Remote {
    boolean cargarImagen(ImageIcon imagen) throws RemoteException;
    ImageIcon secuencial(int modo) throws RemoteException;
    ImageIcon forkJoin(int modo) throws RemoteException;
    ImageIcon executorService(int modo) throws RemoteException;
    boolean limpiar() throws RemoteException;
}
