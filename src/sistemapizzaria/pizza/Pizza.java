package sistemapizzaria.pizza;

import java.util.ArrayList;
import java.util.List;

import sistemapizzaria.excecoes.*;
import sistemapizzaria.formas.*;

public class Pizza {    
    
    private long id;
    private Forma forma;
    private List<Sabor> sabores;

    public Pizza() {
        this.sabores = new ArrayList<>();
    }

    public Pizza(long id, Forma forma) {
        this.id = id;
        this.forma = forma;
        this.sabores = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void adicionarSabor(Sabor sabor) {
        // Valida se a referência recebida é válida
        if (sabor != null) {
            // Verifica se a estrutura de dados já atingiu a capacidade máxima estipulada pela regra
            if (this.sabores.size() >= 2) {
                // Lança uma exceção customizada para sinalizar a quebra da restrição
                throw new QuantidadeSaboresInvalidaException("Uma pizza pode ter no máximo 2 sabores.");
            }
            // Insere o objeto sabor na estrutura caso as condições prévias sejam atendidas.
            this.sabores.add(sabor);
        }
    }

    public void removerSabor(Sabor sabor) {
        // Verifica se a referência apontada não é nula antes de solicitar a remoção à lista.
        if (sabor != null) {
            this.sabores.remove(sabor);
        }
    }

    // Calcula o preço final da pizza processando a área total e a média do valor dos sabores escolhidos.
    public double calcularPreco() {
        // Verifica se os dados essenciais estão ausentes para prevenir falhas de execução
        // COmo divisão por zero ou falha de referência
        if (this.sabores.isEmpty() || this.forma == null) {
            // Retorna o valor zero absoluto como medida de segurança.
            return 0.0; 
        }

        // Reinicializa a variável acumuladora para somatório de preços.
        double somaPrecoPorCm2 = 0.0;
        
        // Acumula o preço do centímetro quadrado recuperado indiretamente de cada sabor cadastrado.
        for (Sabor sabor : this.sabores) {
            somaPrecoPorCm2 += sabor.getTipo().getPrecoPorCm2();
        }

        // Calcula a média aritmética dividindo o custo total acumulado pela quantidade real de sabores.
        double mediaPrecoCm2 = somaPrecoPorCm2 / this.sabores.size();

        // Calcula a forma da pizza com método polimórfico de acordo com formato selecionado
        double areaDaPizza = this.forma.calcularArea();

        // Retorna o produto final resultante da multiplicação da área total pelo custo médio por centímetro quadrado.
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
