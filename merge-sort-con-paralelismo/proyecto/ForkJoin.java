
package proyecto;

import java.util.concurrent.RecursiveAction;

    public class ForkJoin extends RecursiveAction {
    
    int[] lista;
    int inicio, fin;
    
    
    public ForkJoin(int[] lista) {
        this.lista = lista;
        this.inicio = 0;
        this.fin = lista.length - 1;
    }
    
    public ForkJoin(int[] lista, int inicio, int fin) {
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

    public int[] getLista() {
        return lista;
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
    protected void compute() {
        int medio = (inicio  + fin) / 2;
        if((fin - inicio) > 100) {
            
            ForkJoin izquierda = new ForkJoin(lista, inicio, medio);
            ForkJoin derecha = new ForkJoin(lista, medio + 1, fin);
            
            invokeAll(izquierda, derecha);
            
        } else {
            mergeSort(lista, inicio, medio);
            mergeSort(lista, medio + 1, fin);
        }
        
        merge(lista, inicio, medio, fin);
    }

}
