package br.edu.exemplo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ContaBancariaTest {

    @Test
    void deveDepositarValorNaConta() {
        ContaBancaria conta = new ContaBancaria();
        conta.depositar(100);
        assertEquals(100, conta.getSaldo(), 0.001);
    }

    @Test
    void deveAcumularDepositos() {
        ContaBancaria conta = new ContaBancaria();
        conta.depositar(100);
        conta.depositar(50);
        assertEquals(150, conta.getSaldo(), 0.001);
    }

    @Test
    void deveSacarValorDaConta() {
        ContaBancaria conta = new ContaBancaria();
        conta.depositar(100);
        conta.sacar(40);
        assertEquals(60, conta.getSaldo(), 0.001);
    }

    @Test
    void naoDevePermitirSaqueMaiorQueSaldo() {
        ContaBancaria conta = new ContaBancaria();
        conta.depositar(100);
        conta.sacar(150);
        assertEquals(100, conta.getSaldo(), 0.001);
    }

    // ------------------------------------------------------------------
    // Casos adicionais (bordas) escritos pelo aluno
    // ------------------------------------------------------------------

    @Test
    void contaDeveIniciarComSaldoZero() {
        ContaBancaria conta = new ContaBancaria();
        assertEquals(0, conta.getSaldo(), 0.001);
    }

    @Test
    void devePermitirSacarExatamenteOSaldo() {
        ContaBancaria conta = new ContaBancaria();
        conta.depositar(100);
        conta.sacar(100);
        assertEquals(0, conta.getSaldo(), 0.001);
    }

    @Test
    void naoDevePermitirSaqueEmContaSemSaldo() {
        ContaBancaria conta = new ContaBancaria();
        conta.sacar(10);
        assertEquals(0, conta.getSaldo(), 0.001);
    }

    @Test
    void naoDeveDepositarValorNegativo() {
        ContaBancaria conta = new ContaBancaria();
        conta.depositar(100);
        conta.depositar(-50);
        assertEquals(100, conta.getSaldo(), 0.001);
    }

    @Test
    void naoDeveDepositarValorZero() {
        ContaBancaria conta = new ContaBancaria();
        conta.depositar(0);
        assertEquals(0, conta.getSaldo(), 0.001);
    }

    @Test
    void naoDeveSacarValorNegativo() {
        ContaBancaria conta = new ContaBancaria();
        conta.depositar(100);
        conta.sacar(-30);
        assertEquals(100, conta.getSaldo(), 0.001);
    }

    @Test
    void naoDeveSacarValorZero() {
        ContaBancaria conta = new ContaBancaria();
        conta.depositar(100);
        conta.sacar(0);
        assertEquals(100, conta.getSaldo(), 0.001);
    }
}
