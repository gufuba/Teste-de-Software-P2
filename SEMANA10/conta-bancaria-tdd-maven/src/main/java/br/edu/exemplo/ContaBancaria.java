package br.edu.exemplo;

public class ContaBancaria {
    private double saldo;

    public ContaBancaria() {
        this.saldo = 0;
    }

    public double getSaldo() {
        return saldo;
    }

    /** Deposita o valor informado. Valores menores ou iguais a zero sao ignorados. */
    public void depositar(double valor) {
        if (valor <= 0) {
            return;
        }
        saldo += valor;
    }

    /**
     * Saca o valor informado. A operacao e ignorada (saldo inalterado) se o
     * valor for menor ou igual a zero ou maior que o saldo disponivel.
     */
    public void sacar(double valor) {
        if (valor <= 0 || valor > saldo) {
            return;
        }
        saldo -= valor;
    }
}
