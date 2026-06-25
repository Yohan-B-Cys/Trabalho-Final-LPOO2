package sistemapizzaria.formas;

import sistemapizzaria.excecoes.*;

public class Circulo extends Forma {
    
    private double raio;
    // Estabelece o limite mínimo aceitável para o raio do círculo.
    public static final double MIN_RAIO = 7.0;
    // Estabelece o limite máximo aceitável para o raio do círculo.
    public static final double MAX_RAIO = 23.0;

    // Configura o valor do raio do círculo mediante validação prévia dos limites dimensionais.
    @Override
    public void definirPorDimensao(double dimensao) {
        // Verifica se a dimensão fornecida ultrapassa os limites mínimo ou máximo estabelecidos.
        if (dimensao < MIN_RAIO || dimensao > MAX_RAIO) {
            // Lança uma exceção para interromper a operação e notificar a camada superior sobre a dimensão inválida.
            throw new DimensaoInvalidaException("O raio do círculo deve ter entre 7 e 23 cm.");
        }
        // Atribui o valor validado ao atributo interno da classe.
        this.raio = dimensao;
    }

    // Calcula o raio necessário para atingir a área informada e configura a dimensão se o resultado estiver dentro dos limites.
    @Override
    public void definirPorArea(double area) {
        // Verifica se a área solicitada respeita as restrições globais estipuladas na superclasse.
        if (area < MIN_AREA || area > MAX_AREA) {
            // Lança uma exceção indicando que a área solicitada está fora do padrão permitido.
            throw new AreaInvalidaException("A área da pizza deve ter entre 100 e 1600 cm².");
        }
        
        double raioCalculado = Math.sqrt(area / Math.PI);
        
        // Avalia se o raio resultante do cálculo se enquadra nas restrições específicas da forma circular.
        if (raioCalculado < MIN_RAIO || raioCalculado > MAX_RAIO) {
            // Lança uma exceção caso o raio calculado viole as regras físicas impostas ao formato.
            throw new DimensaoInvalidaException("A área informada resulta em um raio fora dos limites do círculo (7 a 23 cm).");
        }
        
        // Atribui o valor validado ao atributo interno da classe.
        this.raio = raioCalculado;
    }

    // Processa e devolve o valor da área atual do círculo
    @Override
    public double calcularArea() {
        return Math.PI * Math.pow(this.raio, 2);
    }

    // Retorna o valor atualizado do raio do círculo.
    public double getRaio() {
        return this.raio;
    }
}
