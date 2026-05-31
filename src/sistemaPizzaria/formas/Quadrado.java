package sistemapizzaria.formas;

import sistemapizzaria.excecoes.*;

public class Quadrado extends Forma {
    private double lado; 
    
    // Limites da dimensão
    public static final double MIN_LADO = 10.0; 
    public static final double MAX_LADO = 40.0; 

    @Override
    public void definirPorDimensao(double dimensao) {
        if (dimensao < MIN_LADO || dimensao > MAX_LADO) {
            throw new DimensaoInvalidaException("O lado do quadrado deve ter entre 10 e 40 cm.");
        }
        this.lado = dimensao;
    }

    @Override
    public void definirPorArea(double area) {
        // Validação da área global
        if (area < MIN_AREA || area > MAX_AREA) {
            throw new AreaInvalidaException("A área da pizza deve ter entre 100 e 1600 cm².");
        }
        
        // Fórmula reversa: Lado = raiz quadrada da Área
        double ladoCalculado = Math.sqrt(area);
        
        // Verifica se o lado descoberto não quebra a regra da forma
        if (ladoCalculado < MIN_LADO || ladoCalculado > MAX_LADO) {
            throw new DimensaoInvalidaException("A área informada resulta em um lado fora dos limites do quadrado (10 a 40 cm).");
        }
        
        this.lado = ladoCalculado;
    }

    @Override
    public double calcularArea() {
        // Fórmula da área do quadrado: lado²
        return Math.pow(this.lado, 2);
    }

    public double getLado() {
        return this.lado; 
    }
}
