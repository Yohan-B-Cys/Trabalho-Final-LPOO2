package sistemapizzaria.formas;

// Representa a classe base abstrata para todas as formas geométricas de pizza suportadas pelo sistema.
// Centraliza as regras e contratos que as classes filhas devem obrigatoriamente implementar (polimorfismo).
public abstract class Forma {
    
    // Define o limite mínimo global permitido para a área da pizza em centímetros quadrados.
    public static final double MIN_AREA = 100.0; 
    
    // Define o limite máximo global permitido para a área da pizza em centímetros quadrados.
    public static final double MAX_AREA = 1600.0; 

    // Calcula e retorna a área da forma geométrica instanciada utilizando as fórmulas matemáticas específicas de cada subclasse.
    public abstract double calcularArea();

    // Configura as dimensões da forma com base no valor da área total fornecida.
    public abstract void definirPorArea(double area);

    // Configura a medida do lado ou do raio da forma geométrica a partir de uma dimensão linear fornecida.
    public abstract void definirPorDimensao(double dimensao);
}
