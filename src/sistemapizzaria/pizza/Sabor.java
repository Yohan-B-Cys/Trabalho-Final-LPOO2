package sistemapizzaria.pizza;

public class Sabor {  
    
    private String nome;
    private TipoPizza tipo;

    public Sabor(String nome, TipoPizza tipo) {
        this.nome = nome;
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoPizza getTipo() {
        return tipo;
    }

    public void setTipo(TipoPizza tipo) {
        this.tipo = tipo;
    }

    // Formata a exibição amigável dos dados para permitir o consumo por componentes visuais do Java Swing
    @Override
    public String toString() {
        return nome + " (" + tipo.getCategoria() + ")";
    }
}
