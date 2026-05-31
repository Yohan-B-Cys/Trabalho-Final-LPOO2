package sistemapizzaria.pizza;

import java.util.ArrayList;
import java.util.List;

import sistemapizzaria.excecoes.*;
import sistemapizzaria.formas.*;

// Representa a entidade pizza no domínio da aplicação, compondo a sua estrutura geométrica e os sabores selecionados.
public class Pizza {    
    
    // Armazena a referência para a forma geométrica da pizza, delegando a ela a responsabilidade sobre dimensões.
    private Forma forma;
    
    // Mantém a coleção de sabores atribuídos a esta pizza específica.
    private List<Sabor> sabores;

    // Constrói a instância da pizza inicializando a lista de sabores para evitar exceções de referência nula.
    public Pizza() {
        this.sabores = new ArrayList<>();
    }

    // Adiciona um novo sabor à composição da pizza, validando as restrições da regra de negócio.
    public void adicionarSabor(Sabor sabor) {
        // Valida se a referência recebida é íntegra antes de processar a inclusão.
        if (sabor != null) {
            // Verifica se a estrutura de dados já atingiu a capacidade máxima estipulada pela regra de domínio.
            if (this.sabores.size() >= 2) {
                // Lança uma exceção customizada para sinalizar ao chamador a quebra da restrição de cardinalidade.
                throw new QuantidadeSaboresInvalidaException("Uma pizza pode ter no máximo 2 sabores.");
            }
            // Insere o objeto sabor na estrutura caso as condições prévias sejam atendidas.
            this.sabores.add(sabor);
        }
    }

    // Remove um sabor existente da coleção atual vinculada à pizza.
    public void removerSabor(Sabor sabor) {
        // Verifica se a referência apontada não é nula antes de solicitar a remoção à lista.
        if (sabor != null) {
            this.sabores.remove(sabor);
        }
    }

    // Calcula o preço final da pizza processando a área total e a média do valor dos sabores escolhidos.
    public double calcularPreco() {
        // Verifica se os dados essenciais estão ausentes para prevenir falhas de execução (divisão por zero ou falha de referência).
        if (this.sabores.isEmpty() || this.forma == null) {
            // Retorna o valor zero absoluto como medida de segurança.
            return 0.0; 
        }

        // Inicializa a variável acumuladora utilizada para compor o somatório de preços.
        double somaPrecoPorCm2 = 0.0;
        
        // Acumula o preço do centímetro quadrado recuperado indiretamente de cada sabor cadastrado.
        for (Sabor sabor : this.sabores) {
            somaPrecoPorCm2 += sabor.getTipo().getPrecoPorCm2();
        }

        // Calcula a média aritmética dividindo o custo total acumulado pela quantidade real de sabores.
        double mediaPrecoCm2 = somaPrecoPorCm2 / this.sabores.size();

        // Calcula a superfície espacial da pizza delegando a execução ao método polimórfico da forma associada.
        double areaDaPizza = this.forma.calcularArea();

        // Retorna o produto final resultante da multiplicação da área total pelo custo médio por centímetro quadrado.
        return mediaPrecoCm2 * areaDaPizza;
    }

    // Retorna a referência do objeto que define a forma geométrica da pizza.
    public Forma getForma() {
        return forma;
    }

    // Atualiza a forma geométrica designada para compor a pizza.
    public void setForma(Forma forma) {
        this.forma = forma;
    }

    // Retorna uma cópia independente da lista de sabores para assegurar o encapsulamento e bloquear modificações externas diretas.
    public List<Sabor> getSabores() {
        return new ArrayList<>(this.sabores);
    }
}
