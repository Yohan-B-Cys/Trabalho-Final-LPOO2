package sistemapizzaria.cliente;

import java.util.ArrayList;
import java.util.List;

import sistemapizzaria.pizza.*;

// Representa a entidade de negócio que consolida a transação de venda, agrupando as pizzas solicitadas e controlando o estado e o custo total do agrupamento.
public class Pedido {
    
    // Armazena o código numérico único e intransferível de identificação do pedido dentro do sistema.
    private int identificador;
    
    // Armazena o montante financeiro resultante da soma do preço de todas as pizzas contidas no pedido.
    private double precoTotal;
    
    // Registra a fase de ciclo de vida atual em que o pedido se encontra.
    private StatusPedido status;
    
    // Mantém a coleção de pizzas vinculadas a este respectivo pedido.
    private List<Pizza> pizzas;

    // Controla a geração sequencial e global de chaves de identificação, suprindo temporariamente a função de uma chave primária de banco de dados.
    private static int contadorId = 1;

    // Constrói a instância do pedido, orquestrando a atribuição do identificador e o preparo das estruturas de dados.
    public Pedido() {
        // Atribui o valor atual do contador ao identificador da instância e incrementa a variável estática em seguida (pós-incremento).
        this.identificador = contadorId++;
        this.precoTotal = 0.0;
        
        // Define o estado inicial padronizado da entidade recém-criada.
        this.status = StatusPedido.ABERTO; 
        this.pizzas = new ArrayList<>();
    }

    // Insere um objeto pizza válido na estrutura do pedido e dispara o recálculo automático do valor financeiro.
    public void adicionarPizza(Pizza pizza) {
        // Valida a consistência da referência recebida antes de processar a modificação da estrutura.
        if (pizza != null) {
            this.pizzas.add(pizza);
            // Invoca a sub-rotina para manter a sincronia entre a lista de itens e o atributo numérico de preço.
            this.calcularPrecoTotal(); 
        }
    }

    // Remove um objeto pizza específico do agrupamento atual e reajusta o custo global da transação.
    public void removerPizza(Pizza pizza) {
        // Verifica se a referência apontada é autêntica antes de solicitar a remoção.
        if (pizza != null) {
            this.pizzas.remove(pizza);
            // Atualiza o atributo dependente para refletir a ausência do item removido.
            this.calcularPrecoTotal(); 
        }
    }

    // Processa a totalização financeira do pedido percorrendo iterativamente todos os itens encapsulados.
    public double calcularPrecoTotal() {
        // Inicializa a variável local responsável por acumular a somatória dos preços.
        double soma = 0.0;
        
        // Itera sobre a coleção iterável de pizzas extraindo dinamicamente o valor individual.
        for (Pizza pizza : this.pizzas) {
            // Invoca o método polimórfico de cálculo contido no objeto pizza e acumula o resultado.
            soma += pizza.calcularPreco(); 
        }
        
        // Atualiza o estado interno do atributo com o resultado da computação matemática.
        this.precoTotal = soma;
        
        // Retorna o valor exato calculado.
        return this.precoTotal;
    }

    // Altera o estágio de acompanhamento do pedido no sistema.
    public void atualizarStatus(StatusPedido novoStatus) {
        // Previne a atribuição de estados inconsistentes através da verificação de nulidade.
        if (novoStatus != null) {
            this.status = novoStatus;
        }
    }

    // Retorna o valor unívoco de identificação referente a este pedido.
    public int getIdentificador() {
        return identificador;
    }

    // Retorna o montante financeiro invocando o método de recálculo dinâmico para garantir a precisão máxima dos dados devolvidos.
    public double getPrecoTotal() {
        return calcularPrecoTotal(); 
    }

    // Retorna o estado gerencial vigente do pedido em questão.
    public StatusPedido getStatus() {
        return status;
    }

    // Retorna uma listagem recriada (cópia defensiva) dos itens inseridos para blindar a integridade da estrutura original.
    public List<Pizza> getPizzas() {
        return new ArrayList<>(this.pizzas);
    }
}
