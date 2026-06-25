package sistemapizzaria.formas;

import sistemapizzaria.excecoes.*;

public class TrianguloEquilatero extends Forma {
    
    private double lado;    
    // Estabelece a restrição mínima para a dimensão do lado do triângulo.
    public static final double MIN_LADO = 20.0;
    // Estabelece a restrição máxima para a dimensão do lado do triângulo.
    public static final double MAX_LADO = 60.0;

    // Define o comprimento do lado mediante checagem de conformidade com os limites exigidos.
    @Override
    public void definirPorDimensao(double dimensao) {
        // Avalia se o parâmetro encontra-se fora do intervalo permitido.
        if (dimensao < MIN_LADO || dimensao > MAX_LADO) {
            // Aborta a execução e notifica a violação da restrição.
            throw new DimensaoInvalidaException("O lado do triângulo deve ter entre 20 e 60 cm.");
        }
        // Atribui o dado consistente à variável de escopo global da classe.
        this.lado = dimensao;
    }

    // Deriva a dimensão do lado utilizando a área requerida
    @Override
    public void definirPorArea(double area) {
        // Valida as restrições globais de área herdadas
        if (area < MIN_AREA || area > MAX_AREA) {
            throw new AreaInvalidaException("A área da pizza deve ter entre 100 e 1600 cm².");
        }
        
        // Isola a variável lado na fórmula da área do triângulo equilátero para realizar a obtenção reversa da dimensão.
        double ladoCalculado = Math.sqrt((4 * area) / Math.sqrt(3));
        
        // Verifica se a medida deduzida respeita as fronteiras dimensionais próprias do triângulo.
        if (ladoCalculado < MIN_LADO || ladoCalculado > MAX_LADO) {
            // Repassa a exceção alertando que a dimensão é incorreta.
            throw new DimensaoInvalidaException("A área informada resulta em um lado fora dos limites do triângulo (20 a 60 cm).");
        }
        
        // Confirma a gravação do valor após a aprovação em todos os crivos de validação.
        this.lado = ladoCalculado;
    }

    // Calcula área do triângulo equilátero utilizando o valor armazenado no atributo de lado.
    @Override
    public double calcularArea() {
        // Fórmula da geometria para triângulos equiláteros
        return (Math.pow(this.lado, 2) * Math.sqrt(3)) / 4.0;
    }

    // Retorna a medida registrada para o lado do triângulo.
    public double getLado() {
        return this.lado;
    }
}
