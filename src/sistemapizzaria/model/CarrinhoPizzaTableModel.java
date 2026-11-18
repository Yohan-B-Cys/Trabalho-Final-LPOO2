package sistemapizzaria.model;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

import sistemapizzaria.pizza.Pizza;
import sistemapizzaria.pizza.Sabor;

/**
 * Modelo de tabela para exibição das Pizzas de um Pedido na tela de pedidos (Padrão MVC - Aula 06).
 */
public class CarrinhoPizzaTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;
    private final String[] colunas = {"Forma", "Área", "Sabores", "Preço da Pizza"};
    private List<Pizza> lista = new ArrayList<>();

    public void setListaPizzas(List<Pizza> pizzas) {
        this.lista = (pizzas != null) ? new ArrayList<>(pizzas) : new ArrayList<>();
        fireTableDataChanged();
    }

    public Pizza getPizza(int rowIndex) {
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
        Pizza pizza = lista.get(rowIndex);
        switch (columnIndex) {
            case 0:
                String classe = pizza.getForma().getClass().getSimpleName();
                if ("Circulo".equals(classe)) return "Círculo";
                if ("Quadrado".equals(classe)) return "Quadrado";
                if ("TrianguloEquilatero".equals(classe)) return "Triângulo Equilátero";
                return classe;
            case 1:
                return String.format("%.2f cm²", pizza.getForma().calcularArea());
            case 2:
                StringBuilder sabores = new StringBuilder();
                List<Sabor> listaSabores = pizza.getSabores();
                for (int i = 0; i < listaSabores.size(); i++) {
                    sabores.append(listaSabores.get(i).getNome());
                    if (i < listaSabores.size() - 1) {
                        sabores.append(", ");
                    }
                }
                return sabores.toString();
            case 3:
                return String.format("R$ %.2f", pizza.calcularPreco());
            default:
                return "";
        }
    }
}
