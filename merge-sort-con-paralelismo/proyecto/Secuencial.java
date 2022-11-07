
package proyecto;

public class Secuencial implements Runnable {
    int[] lista;
    
    public Secuencial(int[] lista) {
        this.lista = lista;
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
    public void run() {
        mergeSort(lista, 0, lista.length - 1);
    }
}