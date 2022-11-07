package Servidor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ImplementacionInterface extends UnicastRemoteObject implements InterfaceRMI {

    protected ImplementacionInterface() throws RemoteException {
        super();
        
    }

    @Override
    public String nombre(String nombre) throws RemoteException {
        return nombre;

    }
    
}
