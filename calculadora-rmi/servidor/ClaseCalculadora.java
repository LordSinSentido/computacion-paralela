package servidor;

import java.rmi.RemoteException;

public class ClaseCalculadora implements InterfaceCalculadora {
    String expresionActual = "0";
    int primeraExpresion = 0;
    int segundaExpresion = 0;
    int operacionPorRealizar = 0;

    @Override
    public void agregarNumero(int numero) {
        if (expresionActual == "0") {
            expresionActual = "";
        }
        if (expresionActual.length() < 10) {
            expresionActual = expresionActual + String.valueOf(numero);
        }
    }

    @Override
    public void agregarOperacion(int operacion) {
        primeraExpresion = Integer.valueOf(expresionActual);
        expresionActual = "0";
        operacionPorRealizar = operacion;
    }

    @Override
    public void realizarOperacion() {
        segundaExpresion = Integer.valueOf(expresionActual);

        switch (operacionPorRealizar) {
            case 1:
                expresionActual = String.valueOf(primeraExpresion + segundaExpresion);
                break;

            case 2:
                expresionActual = String.valueOf(primeraExpresion - segundaExpresion);
                break;
        
            case 3:
                expresionActual = String.valueOf(primeraExpresion * segundaExpresion);
                break;

            case 4:
                expresionActual = String.valueOf(primeraExpresion / segundaExpresion);
                break;

            default:
                break;
        }

        primeraExpresion = 0;
        segundaExpresion = 0;
        operacionPorRealizar = 0;
    }

    @Override
    public void limpiar() throws RemoteException {
        primeraExpresion = 0;
        segundaExpresion = 0;
        operacionPorRealizar = 0;
        expresionActual = "0";
    }

    @Override
    public String recuperarDatos() throws RemoteException {
        return expresionActual;
    }

}