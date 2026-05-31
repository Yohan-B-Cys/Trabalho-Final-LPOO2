package sistemapizzaria;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private String nome;
    private String sobrenome;
    private String telefone;
    
    // Lista que guarda a composição de todos os pedidos feitos pelo cliente
    private List<Pedido> pedidos;

    public Cliente(String nome, String sobrenome, String telefone) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.telefone = telefone;
        this.pedidos = new ArrayList<>();
    }

    public void adicionarPedido(Pedido p) {
        if (p != null) {
            this.pedidos.add(p);
        }
    }

    public void removerPedido(Pedido p) {
        if (p != null) {
            this.pedidos.remove(p);
        }
    }

    public Pedido obterPedidoAberto() {
        for (Pedido p : this.pedidos) {
            if (p.getStatus() == StatusPedido.ABERTO) {
                return p;
            }
        }

        return null; // Nenhum pedido em aberto encontrado
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public List<Pedido> getPedidos() {
        return new ArrayList<>(this.pedidos);
    }
}
