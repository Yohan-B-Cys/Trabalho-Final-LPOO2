package sistemapizzaria.pizza;

public class TipoPizza {
    
    private CategoriaTipo categoria;
    private double precoPorCm2;

    public TipoPizza(CategoriaTipo categoria, double precoPorCm2) {
        this.categoria = categoria;
        this.precoPorCm2 = precoPorCm2;
    }

    public void atualizarPrecoPorCm2(double preco) {
        if (preco >= 0.0) {
            this.precoPorCm2 = preco;
        } else {
            throw new IllegalArgumentException("O preço por centímetro quadrado não pode ser negativo.");
        }
    }

    public CategoriaTipo getCategoria() {
        return categoria;
    }

    public double getPrecoPorCm2() {
        return precoPorCm2;
    }

    @Override
    public String toString() {
        return categoria.name() + " - R$ " + String.format("%.2f", precoPorCm2) + " / cm²";
    }
}
