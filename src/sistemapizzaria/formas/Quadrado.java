package sistemapizzaria.formas;

import sistemapizzaria.excecoes.*;

public class Quadrado extends Forma {

    private double lado;     
    // Estabelece o limite mínimo aceitável para o lado do quadrado.
    public static final double MIN_LADO = 10.0; 
    // Estabelece o limite máximo aceitável para o lado do quadrado.
    public static final double MAX_LADO = 40.0; 

    // Ajusta a medida do lado do quadrado.
    @Override
    public void definirPorDimensao(double dimensao) {
        // Verifica a conformidade da dimensão recebida em relação aos limites estipulados para a forma.
        if (dimensao < MIN_LADO || dimensao > MAX_LADO) {
            // Interrompe o fluxo de execução lançando um erro de dimensão inválida.
            throw new DimensaoInvalidaException("O lado do quadrado deve ter entre 10 e 40 cm.");
        }
        // Efetiva a gravação do dado validado no atributo da instância.
        this.lado = dimensao;
    }

    @Override
    public void definirPorArea(double area) {
        // Confirma se a área submetida obedece aos limites genéricos definidos pela superclasse.
        if (area < MIN_AREA || area > MAX_AREA) {
            // Lança um erro de área inválida para evitar cálculos desnecessários e estados inconsistentes.
            throw new AreaInvalidaException("A área da pizza deve ter entre 100 e 1600 cm².");
        }
        
        // Extrai o valor do lado
        double ladoCalculado = Math.sqrt(area);
        
        // Submete a dimensão calculada à validação de limites do quadrado.
        if (ladoCalculado < MIN_LADO || ladoCalculado > MAX_LADO) {
            // Emite uma sinalização de erro caso a dimensão reversa transborde os limites geométricos previstos.
            throw new DimensaoInvalidaException("A área informada resulta em um lado fora dos limites do quadrado (10 a 40 cm).");
        }
        
        // Armazena definitivamente o valor do lado após atestar a sua validade.
        this.lado = ladoCalculado;
    }

    // Calcula a forma geométrica do quadrado
    @Override
    public double calcularArea() {
        return Math.pow(this.lado, 2);
    }

    // Retorna a medida atual configurada para o lado do quadrado.
    public double getLado() {
        return this.lado; 
    }
}
