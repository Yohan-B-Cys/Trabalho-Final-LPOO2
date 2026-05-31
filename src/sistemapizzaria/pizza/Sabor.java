package sistemapizzaria.pizza;

// Representa a entidade de domínio responsável por definir as características de um sabor específico.
// Associa um nome descritivo a um tipo de pizza, o qual ditará a regra de precificação deste sabor.
public class Sabor {  
    
    // Armazena a nomenclatura textual que identifica o sabor.
    private String nome;
    
    // Mantém a referência ao objeto que encapsula a categoria e o custo por centímetro quadrado atrelados a este sabor.
    private TipoPizza tipo;

    // Constrói a instância do sabor, exigindo a injeção dos dados obrigatórios no momento de sua alocação na memória.
    public Sabor(String nome, TipoPizza tipo) {
        this.nome = nome;
        this.tipo = tipo;
    }

    // Retorna a cadeia de caracteres que representa o nome atual do sabor.
    public String getNome() {
        return nome;
    }

    // Atualiza a nomenclatura do sabor sobrescrevendo o valor da variável de instância.
    public void setNome(String nome) {
        this.nome = nome;
    }

    // Retorna o objeto correspondente ao tipo da pizza associado ao sabor.
    public TipoPizza getTipo() {
        return tipo;
    }

    // Modifica a categoria de precificação vinculada a este sabor específico.
    public void setTipo(TipoPizza tipo) {
        this.tipo = tipo;
    }

    // Sobrescreve o comportamento padrão de representação textual da classe mãe (Object).
    // Formata a exibição amigável dos dados para permitir o consumo direto e legível por componentes visuais do Java Swing (como JComboBox e JList).
    @Override
    public String toString() {
        return nome + " (" + tipo.getCategoria() + ")";
    }
}
