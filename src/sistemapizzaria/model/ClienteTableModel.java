package sistemapizzaria.model;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

import sistemapizzaria.cliente.Cliente;

/**
 * Modelo de tabela para exibição de Clientes na JTable (Padrão MVC - Aula 06).
 */
public class ClienteTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;
    private final String[] colunas = {"Telefone", "Nome", "Sobrenome"};
    private List<Cliente> lista = new ArrayList<>();

    public void setListaClientes(List<Cliente> clientes) {
        this.lista = (clientes != null) ? new ArrayList<>(clientes) : new ArrayList<>();
        fireTableDataChanged();
    }

    public Cliente getCliente(int rowIndex) {
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
        Cliente c = lista.get(rowIndex);
        switch (columnIndex) {
            case 0: return c.getTelefone();
            case 1: return c.getNome();
            case 2: return c.getSobrenome();
            default: return "";
        }
    }
}
