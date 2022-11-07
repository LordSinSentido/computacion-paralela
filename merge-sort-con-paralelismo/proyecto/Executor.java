
package proyecto;

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Executor implements Callable<int[]> {
    ExecutorService ejecutor = Executors.newFixedThreadPool(2);
    int[] lista;
    int inicio, fin;
    
    public Executor(int[] lista) {
        this.lista = lista;
        this.inicio = 0;
        this.fin = lista.length - 1;
    }
    
    public Executor(int[] lista, int inicio, int fin) {
        this.lista = lista;
        this.inicio = inicio;
        this.fin = fin;
    }
     
    void merge(int array[], int p, int q, int r) {
        int n1 = q - p + 1;
        int n2 = r - q;

        int L[] = new int[n1];
        int M[] = new int[n2];

        for (int i = 0; i < n1; i++)
          L[i] = array[p + i];
        for (int j = 0; j < n2; j++)
          M[j] = array[q + 1 + j];

        int i, j, k;
        i = 0;
        j = 0;
        k = p;

        while (i < n1 && j < n2) {
          if (L[i] <= M[j]) {
            array[k] = L[i];
            i++;
          } else {
            array[k] = M[j];
            j++;
          }
          k++;
        }

        while (i < n1) {
          array[k] = L[i];
          i++;
          k++;
        }

        while (j < n2) {
          array[k] = M[j];
          j++;
          k++;
        }
    }
    
    public void mergeSort(int[] lista, int inicio, int fin) {
        if(inicio < fin) {
            int medio = (inicio  + fin) / 2;
            
            mergeSort(lista, inicio, medio);
            mergeSort(lista, medio + 1, fin);
            
            merge(lista, inicio, medio, fin);
        }
    }
    
    @Override
    public int[] call() {            
        int medio = (inicio  + fin) / 2;
        
        if((fin - inicio) > 100) {
            
            try {       
                Future<?> izquierda = ejecutor.submit(new Executor(lista, inicio, medio));
                Future<?> derecha = ejecutor.submit(new Executor(lista, medio, fin));

                while(!izquierda.isDone() && !derecha.isDone()) {
                }
                
            } finally {
                ejecutor.shutdown();
            }
        } else {
            mergeSort(lista, inicio, medio);
            mergeSort(lista, medio + 1, fin);
        }
        
        merge(lista, inicio, medio, fin);
        
        return lista;
    }
}