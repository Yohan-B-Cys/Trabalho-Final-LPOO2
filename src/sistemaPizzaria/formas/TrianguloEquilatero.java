package sistemapizzaria.formas;

import sistemapizzaria.excecoes.*;

public class TrianguloEquilatero extends Forma {
    private double lado;
    
    // Limites da dimensão do lado específicos para o formato triângulo
    public static final double MIN_LADO = 20.0;
    public static final double MAX_LADO = 60.0;

    @Override
    public void definirPorDimensao(double dimensao) {
        if (dimensao < MIN_LADO || dimensao > MAX_LADO) {
            throw new DimensaoInvalidaException("O lado do triângulo deve ter entre 20 e 60 cm.");
        }
        this.lado = dimensao;
    }

    @Override
    public void definirPorArea(double area) {
        // Validação da área global
        if (area < MIN_AREA || area > MAX_AREA) {
            throw new AreaInvalidaException("A área da pizza deve ter entre 100 e 1600 cm².");
        }
        
        // Fórmula reversa: Lado = raiz quadrada de ((4 * Área) / raiz de 3)
        double ladoCalculado = Math.sqrt((4 * area) / Math.sqrt(3));
        
        // Verifica se o lado descoberto não quebra a regra da forma
        if (ladoCalculado < MIN_LADO || ladoCalculado > MAX_LADO) {
            throw new DimensaoInvalidaException("A área informada resulta em um lado fora dos limites do triângulo (20 a 60 cm).");
        }
        
        this.lado = ladoCalculado;
    }

    @Override
    public double calcularArea() {
        // Fórmula da área do triângulo equilátero: (lado² * raiz de 3) / 4
        return (Math.pow(this.lado, 2) * Math.sqrt(3)) / 4.0;
    }

    public double getLado() {
        return this.lado;
    }
}
