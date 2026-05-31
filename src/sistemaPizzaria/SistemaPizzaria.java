package sistemapizzaria;

import java.util.ArrayList;
import java.util.List;

import sistemapizzaria.cliente.*;
import sistemapizzaria.pizza.*;

public class SistemaPizzaria {
    private List<Cliente> clientes;
    private List<Sabor> saboresDisponiveis;
    private List<TipoPizza> tabelaPrecos;

    public SistemaPizzaria() {
        this.clientes = new ArrayList<>();
        this.saboresDisponiveis = new ArrayList<>();
        this.tabelaPrecos = new ArrayList<>();
        
        // Inicializa a tabela contendo exatamente as 3 categorias obrigatórias 
        this.tabelaPrecos.add(new TipoPizza(CategoriaTipo.SIMPLES, 0.0));
        this.tabelaPrecos.add(new TipoPizza(CategoriaTipo.ESPECIAL, 0.0));
        this.tabelaPrecos.add(new TipoPizza(CategoriaTipo.PREMIUM, 0.0));
    }

    public void cadastrarCliente(Cliente c) {
        if (c != null) {
            this.clientes.add(c);
        }
    }

    public void removerCliente(Cliente c) {
        if (c != null) {
            this.clientes.remove(c);
        }
    }

    public List<Cliente> buscarClientes(String filtro) {
        List<Cliente> resultado = new ArrayList<>();
        
        // Se o filtro for nulo ou vazio, retorna a lista completa para preenchimento da JTable 
        if (filtro == null || filtro.trim().isEmpty()) {
            return new ArrayList<>(this.clientes);
        }
        
        String filtroFormatado = filtro.trim().toLowerCase();
        
        for (Cliente cliente : this.clientes) {
            boolean matchesSobrenome = cliente.getSobrenome() != null && 
                                       cliente.getSobrenome().toLowerCase().contains(filtroFormatado);
            boolean matchesTelefone = cliente.getTelefone() != null && 
                                      cliente.getTelefone().contains(filtroFormatado);
            
            if (matchesSobrenome || matchesTelefone) {
                resultado.add(cliente);
            }
        }
        return resultado;
    }

    public Cliente buscarClientePorTelefone(String telefone) {
        if (telefone == null || telefone.trim().isEmpty()) {
            return null;
        }
        
        for (Cliente cliente : this.clientes) {
            if (cliente.getTelefone() != null && cliente.getTelefone().equals(telefone.trim())) {
                return cliente;
            }
        }
        return null;
    }

    public void cadastrarSabor(Sabor s) {
        if (s != null) {
            this.saboresDisponiveis.add(s);
        }
    }

    public void atualizarPreco(CategoriaTipo categoria, double novoPreco) {
        for (TipoPizza tipo : this.tabelaPrecos) {
            if (tipo.getCategoria() == categoria) {
                tipo.atualizarPrecoPorCm2(novoPreco);
                break; // Categoria encontrada e atualizada, interrompe a busca
            }
        }
    }

    public List<Pedido> listarTodosPedidos() {
        List<Pedido> todosPedidos = new ArrayList<>();
        for (Cliente cliente : this.clientes) {
            if (cliente.getPedidos() != null) {
                todosPedidos.addAll(cliente.getPedidos());
            }
        }
        return todosPedidos;
    }

    // Métodos seletores (Getters) para permitir que as JTables das telas Swing consumam os dados diretamente
    public List<Cliente> getClientes() {
        return new ArrayList<>(this.clientes);
    }

    public List<Sabor> getSaboresDisponiveis() {
        return new ArrayList<>(this.saboresDisponiveis);
    }

    public List<TipoPizza> getTabelaPrecos() {
        return new ArrayList<>(this.tabelaPrecos);
    }
}
