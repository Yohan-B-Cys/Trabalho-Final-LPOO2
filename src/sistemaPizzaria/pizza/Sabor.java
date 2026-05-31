package sistemapizzaria;

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

    @Override
    public String toString() {
        // Exibição amigável para JLists e JComboBox no WindowBuilder
        return nome + " (" + tipo.getCategoria() + ")";
    }
}
