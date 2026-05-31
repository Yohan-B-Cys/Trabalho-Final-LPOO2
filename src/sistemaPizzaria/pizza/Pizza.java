package sistemapizzaria.pizza;

import java.util.ArrayList;
import java.util.List;

import sistemapizzaria.excecoes.*;
import sistemapizzaria.formas.*;

public class Pizza {    
    private Forma forma;
    private List<Sabor> sabores;

    public Pizza() {
        this.sabores = new ArrayList<>();
    }

    public void adicionarSabor(Sabor sabor) {
        if (sabor != null) {
            if (this.sabores.size() >= 2) {
                throw new QuantidadeSaboresInvalidaException("Uma pizza pode ter no máximo 2 sabores.");
            }
            this.sabores.add(sabor);
        }
    }

    public void removerSabor(Sabor sabor) {
        if (sabor != null) {
            this.sabores.remove(sabor);
        }
    }

    public double calcularPreco() {
        // Validações para não quebrar o cálculo caso a tela não tenha passado os dados
        if (this.sabores.isEmpty() || this.forma == null) {
            return 0.0; 
        }

        double somaPrecoPorCm2 = 0.0;
        
        // Soma o valor do cm² de cada sabor inserido
        for (Sabor sabor : this.sabores) {
            somaPrecoPorCm2 += sabor.getTipo().getPrecoPorCm2();
        }

        // Calcula a média dos tipos de sabores 
        double mediaPrecoCm2 = somaPrecoPorCm2 / this.sabores.size();

        double areaDaPizza = this.forma.calcularArea();

        return mediaPrecoCm2 * areaDaPizza;
    }

    public Forma getForma() {
        return forma;
    }

    public void setForma(Forma forma) {
        this.forma = forma;
    }

    public List<Sabor> getSabores() {
        return new ArrayList<>(this.sabores);
    }
}
