package sistemapizzaria;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sistemapizzaria.cliente.Cliente;
import sistemapizzaria.cliente.Pedido;
import sistemapizzaria.dao.DaoFactory;
import sistemapizzaria.pizza.CategoriaTipo;
import sistemapizzaria.pizza.Sabor;
import sistemapizzaria.pizza.TipoPizza;

/**
 * Fachada de compatibilidade que delega as operações para a camada DAO (SQLite).
 */
public class SistemaPizzaria {

    public SistemaPizzaria() {
    }

    public void cadastrarCliente(Cliente c) {
        if (c != null) {
            try {
                DaoFactory.getClienteDao().add(c);
            } catch (Exception e) {
                throw new RuntimeException("Erro ao cadastrar cliente: " + e.getMessage(), e);
            }
        }
    }

    public void removerCliente(Cliente c) {
        if (c != null) {
            try {
                DaoFactory.getClienteDao().delete(c);
            } catch (Exception e) {
                throw new RuntimeException("Erro ao remover cliente: " + e.getMessage(), e);
            }
        }
    }

    public List<Cliente> buscarClientes(String filtro) {
        try {
            return DaoFactory.getClienteDao().buscarPorFiltro(filtro);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public Cliente buscarClientePorTelefone(String telefone) {
        try {
            return DaoFactory.getClienteDao().getByTelefone(telefone);
        } catch (Exception e) {
            return null;
        }
    }

    public void cadastrarSabor(Sabor s) {
        if (s != null) {
            try {
                DaoFactory.getSaborDao().add(s);
            } catch (Exception e) {
                throw new RuntimeException("Erro ao cadastrar sabor: " + e.getMessage(), e);
            }
        }
    }

    public void atualizarPreco(CategoriaTipo categoria, double novoPreco) {
        try {
            TipoPizza tp = new TipoPizza(categoria, 0.0);
            tp.atualizarPrecoPorCm2(novoPreco);
            DaoFactory.getTipoPizzaDao().update(tp);
            DaoFactory.getPedidoDao().recalcularPrecosPedidosAbertos();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar preço: " + e.getMessage(), e);
        }
    }

    public List<Pedido> listarTodosPedidos() {
        List<Pedido> todosPedidos = new ArrayList<>();
        for (Cliente cliente : getClientes()) {
            if (cliente.getPedidos() != null) {
                todosPedidos.addAll(cliente.getPedidos());
            }
        }
        return todosPedidos;
    }

    public List<Cliente> getClientes() {
        try {
            return DaoFactory.getClienteDao().getAll();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public List<Sabor> getSaboresDisponiveis() {
        try {
            return DaoFactory.getSaborDao().getAll();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public List<TipoPizza> getTabelaPrecos() {
        try {
            return DaoFactory.getTipoPizzaDao().getAll();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
