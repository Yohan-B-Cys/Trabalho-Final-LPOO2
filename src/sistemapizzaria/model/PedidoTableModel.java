package sistemapizzaria.model;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

import sistemapizzaria.cliente.Pedido;

/**
 * Modelo de tabela para exibição do histórico de Pedidos de um Cliente (Padrão MVC - Aula 06).
 */
public class PedidoTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;
    private final String[] colunas = {"ID do Pedido", "Qtd. Pizzas", "Preço Total", "Status"};
    private List<Pedido> lista = new ArrayList<>();

    public void setListaPedidos(List<Pedido> pedidos) {
        this.lista = (pedidos != null) ? new ArrayList<>(pedidos) : new ArrayList<>();
        fireTableDataChanged();
    }

    public Pedido getPedido(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < lista.size()) {
            return lista.get(rowIndex);
        }
        return null;
    }

    @Override
    public int getRowCount() {
        return lista.size();
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
        Pedido p = lista.get(rowIndex);
        switch (columnIndex) {
            case 0: return p.getIdentificador();
            case 1: return p.getPizzas().size();
            case 2: return String.format("R$ %.2f", p.getPrecoTotal());
            case 3: return p.getStatus();
            default: return "";
        }
    }
}
