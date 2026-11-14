package br.edu.exemplo;

public class BubbleSort {

    private int[] numeros;

    public BubbleSort(int[] numeros) {
        this.numeros = numeros;
    }

    /**
     * Ordena o vetor em ordem crescente (Bubble Sort).
     * A cada passada o maior elemento "sobe" para o fim; se uma passada
     * nao realizar nenhuma troca, o vetor ja esta ordenado e o algoritmo para.
     */
    public void ordenar() {
        boolean houveTroca;
        int limite = numeros.length - 1;

        do {
            houveTroca = false;
            for (int j = 0; j < limite; j++) {
                if (numeros[j] > numeros[j + 1]) {
                    trocar(j, j + 1);
                    houveTroca = true;
                }
            }
            limite--;
        } while (houveTroca);
    }

    private void trocar(int i, int j) {
        int temp = numeros[i];
        numeros[i] = numeros[j];
        numeros[j] = temp;
    }

    public int[] getNumeros() {
        return numeros;
    }
}
