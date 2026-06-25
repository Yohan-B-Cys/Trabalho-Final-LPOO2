package sistemapizzaria.cliente;

import java.util.ArrayList;
import java.util.List;

import sistemapizzaria.pizza.*;

public class Pedido {
    
    private int identificador;
    private double precoTotal;
    private StatusPedido status;
    
    // Mantém a coleção de pizzas vinculadas a este respectivo pedido.
    private List<Pizza> pizzas;

    // Controla a geração sequencial e global de chaves de identificação
    private static int contadorId = 1;

    public Pedido() {
        // Atribui o valor atual do contador ao identificador da instância e incrementa a variável estática em seguida
        this.identificador = contadorId++;
        this.precoTotal = 0.0;
        this.status = StatusPedido.ABERTO; 
        this.pizzas = new ArrayList<>();
    }

    public void adicionarPizza(Pizza pizza) {
        // Valida a consistência da referência recebida antes de processar a modificação da estrutura.
        if (pizza != null) {
            this.pizzas.add(pizza);
            // Atualiza preço total
            this.calcularPrecoTotal(); 
        }
    }

    public void removerPizza(Pizza pizza) {
        // Verifica se a referência apontada é autêntica antes de solicitar a remoção.
        if (pizza != null) {
            this.pizzas.remove(pizza);
            this.calcularPrecoTotal(); 
        }
    }

    public double calcularPrecoTotal() {
        // Necessário reinicializar a variável para cada novo cálculo
        double soma = 0.0;
        
        for (Pizza pizza : this.pizzas) {
            // Invoca o método polimórfico de cálculo e acumula o resultado.
            soma += pizza.calcularPreco(); 
        }
        
        this.precoTotal = soma;
        
        return this.precoTotal;
    }

    // Altera o estágio de acompanhamento do pedido no sistema.
    public void atualizarStatus(StatusPedido novoStatus) {
        // Previne a atribuição de estados inconsistentes
        if (novoStatus != null) {
            this.status = novoStatus;
        }
    }

    public int getIdentificador() {
        return identificador;
    }

    public double getPrecoTotal() {
        return calcularPrecoTotal(); 
    }

    public StatusPedido getStatus() {
        return status;
    }

    public List<Pizza> getPizzas() {
        return new ArrayList<>(this.pizzas);
    }
}
