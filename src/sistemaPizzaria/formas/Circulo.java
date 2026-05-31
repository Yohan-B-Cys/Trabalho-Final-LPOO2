package sistemapizzaria;

public class Circulo extends Forma {
    private double raio;
    
    // Limites da dimensão do raio específicos para o formato circular
    public static final double MIN_RAIO = 7.0;
    public static final double MAX_RAIO = 23.0;

    @Override
    public void definirPorDimensao(double dimensao) {
        if (dimensao < MIN_RAIO || dimensao > MAX_RAIO) {
            throw new DimensaoInvalidaException("O raio do círculo deve ter entre 7 e 23 cm.");
        }
        this.raio = dimensao;
    }

    @Override
    public void definirPorArea(double area) {
        // Validação da área global
        if (area < MIN_AREA || area > MAX_AREA) {
            throw new AreaInvalidaException("A área da pizza deve ter entre 100 e 1600 cm².");
        }
        
        // Fórmula reversa: Raio = raiz quadrada de (Área / PI)
        double raioCalculado = Math.sqrt(area / Math.PI);
        
        // Verifica se o raio descoberto não quebra a regra da forma
        if (raioCalculado < MIN_RAIO || raioCalculado > MAX_RAIO) {
            throw new DimensaoInvalidaException("A área informada resulta em um raio fora dos limites do círculo (7 a 23 cm).");
        }
        
        this.raio = raioCalculado;
    }

    @Override
    public double calcularArea() {
        // Fórmula da área do círculo: PI * raio²
        return Math.PI * Math.pow(this.raio, 2);
    }

    public double getRaio() {
        return this.raio;
    }
}
