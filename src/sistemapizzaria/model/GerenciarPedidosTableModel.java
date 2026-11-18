package sistemapizzaria.model;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

import sistemapizzaria.cliente.Cliente;
import sistemapizzaria.cliente.Pedido;
import sistemapizzaria.pizza.Pizza;
import sistemapizzaria.pizza.Sabor;

/**
 * Modelo de tabela para o gerenciamento global de pedidos e seus estados (Padrão MVC - Aula 06).
 */
public class GerenciarPedidosTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;
    private final String[] colunas = {"ID", "Cliente", "Telefone", "Total", "Status", "Itens"};

    public static class LinhaPedidoCliente {
        private final Cliente cliente;
        private final Pedido pedido;

        public LinhaPedidoCliente(Cliente cliente, Pedido pedido) {
            this.cliente = cliente;
            this.pedido = pedido;
        }

        public Cliente getCliente() {
            return cliente;
        }

        public Pedido getPedido() {
            return pedido;
        }
    }

    private List<LinhaPedidoCliente> linhas = new ArrayList<>();

    public void setLinhas(List<LinhaPedidoCliente> novasLinhas) {
        this.linhas = (novasLinhas != null) ? new ArrayList<>(novasLinhas) : new ArrayList<>();
        fireTableDataChanged();
    }

    public Pedido getPedido(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < linhas.size()) {
            return linhas.get(rowIndex).getPedido();
        }
        return null;
    }

    @Override
    public int getRowCount() {
        return linhas.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        LinhaPedidoCliente item = linhas.get(rowIndex);
        Cliente c = item.getCliente();
        Pedido p = item.getPedido();
        switch (columnIndex) {
            case 0: return p.getIdentificador();
            case 1: return c.getNome() + " " + c.getSobrenome();
            case 2: return c.getTelefone();
            case 3: return String.format("R$ %.2f", p.getPrecoTotal());
            case 4: return p.getStatus();
            case 5: return gerarDescricaoItens(p);
            default: return "";
        }
    }

    private String gerarDescricaoItens(Pedido p) {
        StringBuilder sb = new StringBuilder();
        for (Pizza pizza : p.getPizzas()) {
            String forma = obterNomeForma(pizza);
            double area = 0.0;
            try {
                area = pizza.getForma().calcularArea();
            } catch (Exception e) {
                area = 0.0;
            }
            sb.append("Pizza ").append(forma).append(" ").append(String.format("%.0f cm²", area));
            List<Sabor> sabores = pizza.getSabores();
            if (sabores != null && !sabores.isEmpty()) {
                sb.append(" [");
                for (int i = 0; i < sabores.size(); i++) {
                    sb.append(sabores.get(i).getNome());
                    if (i < sabores.size() - 1) {
                        sb.append(", ");
                    }
                }
                sb.append("]");
            }
            sb.append("\n");
        }
        return sb.toString().trim();
    }

    private String obterNomeForma(Pizza pizza) {
        try {
            String classe = pizza.getForma().getClass().getSimpleName();
            if ("Circulo".equals(classe)) return "Círculo";
            if ("Quadrado".equals(classe)) return "Quadrado";
            if ("TrianguloEquilatero".equals(classe)) return "Triângulo";
            return classe;
        } catch (Exception e) {
            return "Forma";
        }
    }
}
