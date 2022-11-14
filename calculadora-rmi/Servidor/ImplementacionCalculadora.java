package Servidor;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class ImplementacionCalculadora implements InterfaceCalculadora {
    ArrayList<Integer> datos = new ArrayList<>();

    // Función para guadar los datos de entrada en la lista
    @Override
    public void guardarNumero(int numero) throws RemoteException {
        datos.add(numero);
        System.out.println("--> Se ha agreado " + String.valueOf(numero) + " a lista de operaciones.");
    }

    // Función para realizar la operación que el cliente pida
    @Override
    public int realizarOperacion(String operacion) throws RemoteException {
        int resultado = 0;
        boolean primeraVez = true;

        switch (operacion) {
            case "suma":
                for (Integer numero : datos) {
                    if(primeraVez) {
                        resultado = numero;
                        primeraVez = false;
                    } else {
                        resultado = resultado + numero;
                    }
                }
                break;

            case "resta":
                for (Integer numero : datos) {
                    if(primeraVez) {
                        resultado = numero;
                        primeraVez = false;
                    } else {
                        resultado = resultado - numero;
                    }
                }
                break;

            case "multiplicacion":
                for (Integer numero : datos) {
                    if(primeraVez) {
                        resultado = numero;
                        primeraVez = false;
                    } else {
                        resultado = resultado * numero;
                    }
                }
                break;

            case "division":
                for (Integer numero : datos) {
                    if(primeraVez) {
                        resultado = numero;
                        primeraVez = false;
                    } else {
                        resultado = resultado / numero;
                    }
                }
                break;
        
            default:
                break;
        }

        return resultado;
    }

    // Función que limpia la lista de datos
    @Override
    public void limipiarMemoria() throws RemoteException {
        datos.removeAll(datos);
        System.out.println("-!- Se ha limpiado la memoria");
    }
    
}
