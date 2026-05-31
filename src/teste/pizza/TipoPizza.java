package sistemapizzaria.pizza;

// Representa a configuração financeira associada a uma categoria de pizza, definindo o custo base por área geométrica.
public class TipoPizza {
    
    // Armazena a categoria da pizza utilizando uma enumeração para restringir os tipos padronizados pelo sistema.
    private CategoriaTipo categoria;
    
    // Registra o valor monetário cobrado por cada centímetro quadrado correspondente a esta categoria.
    private double precoPorCm2;

    // Constrói a instância do tipo de pizza estabelecendo os dados essenciais para a composição de preços no domínio.
    public TipoPizza(CategoriaTipo categoria, double precoPorCm2) {
        this.categoria = categoria;
        this.precoPorCm2 = precoPorCm2;
    }

    // Atualiza o valor monetário do centímetro quadrado submetendo o novo dado a uma validação de consistência.
    public void atualizarPrecoPorCm2(double preco) {
        // Verifica se o valor financeiro fornecido é maior ou igual a zero, assegurando a validade matemática da operação.
        if (preco >= 0.0) {
            this.precoPorCm2 = preco;
        } else {
            // Lança uma exceção de argumento inválido para abortar a execução e impedir a persistência de preços negativos.
            throw new IllegalArgumentException("O preço por centímetro quadrado não pode ser negativo.");
        }
    }

    // Retorna a enumeração constante que identifica a categoria vinculada a este objeto.
    public CategoriaTipo getCategoria() {
        return categoria;
    }

    // Retorna o valor financeiro corrente do preço cobrado por unidade de área (cm²).
    public double getPrecoPorCm2() {
        return precoPorCm2;
    }

    // Sobrescreve a conversão estrutural em texto padronizada pela classe mãe (Object).
    // Formata a cadeia de caracteres para exibição otimizada em componentes visuais (como JComboBox ou JTable), concatenando a categoria com o preço em exibição monetária de duas casas decimais.
    @Override
    public String toString() {
        return categoria.name() + " - R$ " + String.format("%.2f", precoPorCm2) + " / cm²";
    }
}
