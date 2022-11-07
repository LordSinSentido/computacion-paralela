package Servidor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ImplementacionChat extends UnicastRemoteObject implements InterfaceChat {
    public ArrayList<InterfaceChatCliente> clientes;

    protected ImplementacionChat() throws RemoteException {
        clientes = new ArrayList<InterfaceChatCliente>();

    }

    @Override
    public void registro(InterfaceChatCliente cliente) throws RemoteException {
        this.clientes.add(cliente);
        
    }

    @Override
    public void mensaje(String mensaje) throws RemoteException {
        int cantidad = 0;
        while (cantidad < clientes.size()) {
            clientes.get(cantidad++).mensajeCliente(mensaje);
        }
        
    }
    
}
