package sistemapizzaria.cliente;

import java.util.ArrayList;
import java.util.List;

// Representa a entidade cliente no domínio do sistema, agrupando informações pessoais e o histórico de pedidos vinculados.
public class Cliente {
    
    // Armazena o primeiro nome do cliente.
    private String nome;
    
    // Armazena o sobrenome do cliente, frequentemente utilizado em buscas textuais.
    private String sobrenome;
    
    // Armazena o número de telefone utilizado como contato primário e chave principal de busca no sistema.
    private String telefone;
    
    // Mantém a coleção de todos os pedidos, independentemente do status, associados a este cliente específico.
    private List<Pedido> pedidos;

    // Constrói a instância do cliente exigindo os dados de identificação obrigatórios no momento da criação.
    public Cliente(String nome, String sobrenome, String telefone) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.telefone = telefone;
        
        // Inicializa a estrutura de dados da lista de pedidos para prevenir exceções de referência nula (NullPointerException).
        this.pedidos = new ArrayList<>();
    }

    // Adiciona um objeto pedido à coleção de pedidos deste cliente.
    public void adicionarPedido(Pedido p) {
        // Verifica se a referência recebida é válida antes de efetuar a inclusão na lista.
        if (p != null) {
            this.pedidos.add(p);
        }
    }

    // Remove um objeto pedido específico da coleção do cliente.
    public void removerPedido(Pedido p) {
        // Confirma se o objeto passado como parâmetro não é nulo antes de tentar a remoção da estrutura.
        if (p != null) {
            this.pedidos.remove(p);
        }
    }

    // Busca e retorna o primeiro pedido da lista que se encontre no estado de montagem (aberto).
    public Pedido obterPedidoAberto() {
        // Itera sobre a coleção de pedidos para inspecionar o status individual de cada um.
        for (Pedido p : this.pedidos) {
            // Verifica se o enumerador de status do pedido atual corresponde à opção ABERTO.
            if (p.getStatus() == StatusPedido.ABERTO) {
                // Retorna a referência do pedido imediatamente após encontrá-lo.
                return p;
            }
        }

        // Retorna valor nulo caso a repetição termine sem localizar nenhum pedido com status adequado.
        return null; 
    }

    // Retorna o valor atualizado do atributo nome.
    public String getNome() {
        return nome;
    }

    // Atualiza o conteúdo do atributo nome com uma nova string.
    public void setNome(String nome) {
        this.nome = nome;
    }

    // Retorna o valor atualizado do atributo sobrenome.
    public String getSobrenome() {
        return sobrenome;
    }

    // Atualiza o conteúdo do atributo sobrenome.
    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    // Retorna o número de telefone cadastrado para o cliente.
    public String getTelefone() {
        return telefone;
    }

    // Atualiza o número de telefone do cliente.
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    // Retorna uma cópia exata da lista de pedidos para proteger a estrutura original contra modificações externas indevidas.
    public List<Pedido> getPedidos() {
        return new ArrayList<>(this.pedidos);
    }
}
