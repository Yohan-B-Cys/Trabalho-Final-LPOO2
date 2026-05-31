package sistemapizzaria;

// Exceção lançada quando a dimensão (lado ou raio) está fora dos limites da forma.
public class DimensaoInvalidaException extends RuntimeException {
    public DimensaoInvalidaException(String mensagem) {
        super(mensagem);
    }
}
