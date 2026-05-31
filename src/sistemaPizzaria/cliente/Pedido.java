package sistemapizzaria;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private int identificador;
    private double precoTotal;
    private StatusPedido status;
    private List<Pizza> pizzas;

    // Variável estática para gerar IDs únicos automaticamente, 
    // caso não esteja usando um banco de dados
    private static int contadorId = 1;

    public Pedido() {
        this.identificador = contadorId++;
        this.precoTotal = 0.0;
        this.status = StatusPedido.ABERTO; // Estado inicial padrão
        this.pizzas = new ArrayList<>();
    }

    public void adicionarPizza(Pizza pizza) {
        if (pizza != null) {
            this.pizzas.add(pizza);
            this.calcularPrecoTotal(); // Mantém o valor atualizado
        }
    }

    public void removerPizza(Pizza pizza) {
        if (pizza != null) {
            this.pizzas.remove(pizza);
            this.calcularPrecoTotal(); // Mantém o valor atualizado
        }
    }

    public double calcularPrecoTotal() {
        double soma = 0.0;
        for (Pizza pizza : this.pizzas) {
            // Invoca o cálculo de preço de cada pizza
            soma += pizza.calcularPreco(); 
        }
        this.precoTotal = soma;
        return this.precoTotal;
    }

    public void atualizarStatus(StatusPedido novoStatus) {
        if (novoStatus != null) {
            this.status = novoStatus;
        }
    }

    public int getIdentificador() {
        return identificador;
    }

    public double getPrecoTotal() {
        // Garante que o preço retornado esteja sempre atualizado
        return calcularPrecoTotal(); 
    }

    public StatusPedido getStatus() {
        return status;
    }

    public List<Pizza> getPizzas() {
        return new ArrayList<>(this.pizzas);
    }
}
