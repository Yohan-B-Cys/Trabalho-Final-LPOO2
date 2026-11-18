package sistemapizzaria.pizza;

public class Sabor {  
    
    private long id;
    private String nome;
    private TipoPizza tipo;

    public Sabor(String nome, TipoPizza tipo) {
        this(0L, nome, tipo);
    }

    public Sabor(long id, String nome, TipoPizza tipo) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
