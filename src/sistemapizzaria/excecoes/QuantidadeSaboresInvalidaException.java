package sistemapizzaria.excecoes;

// Exceção lançada quando há tentativa de adicionar mais sabores do que o limite permitido na pizza.
public class QuantidadeSaboresInvalidaException extends RuntimeException {
    
    public QuantidadeSaboresInvalidaException(String mensagem) {
        super(mensagem);
    }
}
