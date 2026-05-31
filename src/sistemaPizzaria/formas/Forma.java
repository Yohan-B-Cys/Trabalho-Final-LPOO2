package sistemapizzaria;

public abstract class Forma {
    // Limites de área
    public static final double MIN_AREA = 100.0; // 
    public static final double MAX_AREA = 1600.0; // 

    public abstract double calcularArea();

    public abstract void definirPorArea(double area);

    public abstract void definirPorDimensao(double dimensao);
}
