package sistemapizzaria;

// Exceção lançada quando a área informada pelo usuário está fora dos limites permitidos
public class AreaInvalidaException extends RuntimeException {
    public AreaInvalidaException(String mensagem) {
        super(mensagem);
    }
}
