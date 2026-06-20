package sistemapizzaria.telas;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import sistemapizzaria.SistemaPizzaria;
import sistemapizzaria.cliente.Cliente;
import sistemapizzaria.cliente.Pedido;
import sistemapizzaria.cliente.StatusPedido;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaGerenciarPedidos extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel contentPane;
    private JTextField txtBusca;
    private JTable tabela;
    private DefaultTableModel modelo;

    private JComboBox<StatusPedido> comboFiltroStatus;
    private JComboBox<StatusPedido> comboNovoStatus;

    private SistemaPizzaria sistema;

    public TelaGerenciarPedidos(SistemaPizzaria sistema) {
        this.sistema = sistema;

        setTitle("Gerenciar Pedidos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(1000, 600));
        setLocationRelativeTo(null);

        contentPane = new JPanel(new BorderLayout(10, 10));
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);

        // ======================
        // PAINEL SUPERIOR (Filtros)
        // ======================
        JPanel pnlNorte = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        pnlNorte.setBorder(BorderFactory.createTitledBorder("Filtros de Busca"));

        pnlNorte.add(new JLabel("Filtrar por Status:"));
        comboFiltroStatus = new JComboBox<>();
        comboFiltroStatus.addItem(null); // Todos
        comboFiltroStatus.addItem(StatusPedido.ABERTO);
        comboFiltroStatus.addItem(StatusPedido.A_CAMINHO);
        comboFiltroStatus.addItem(StatusPedido.ENTREGUE);
        pnlNorte.add(comboFiltroStatus);

        pnlNorte.add(new JLabel("Telefone:"));
        txtBusca = new JTextField(15);
        pnlNorte.add(txtBusca);

        JButton btnBuscar = new JButton("Pesquisar");
        pnlNorte.add(btnBuscar);
        
        contentPane.add(pnlNorte, BorderLayout.NORTH);

        // ======================
        // TABELA (Centro)
        // ======================
        modelo = new DefaultTableModel(
                new Object[]{"ID", "Cliente", "Telefone", "Total", "Status", "Itens", "OBJ"}, 0
        ) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(modelo);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setRowHeight(78);
        tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        tabela.getColumnModel().getColumn(5).setCellRenderer(new TextAreaRenderer());

        // Esconder a coluna OBJ
        tabela.getColumnModel().getColumn(6).setMinWidth(0);
        tabela.getColumnModel().getColumn(6).setMaxWidth(0);
        tabela.getColumnModel().getColumn(6).setPreferredWidth(0);
        tabela.getColumnModel().getColumn(6).setWidth(0);

        // Larguras das colunas
        tabela.getColumnModel().getColumn(0).setPreferredWidth(70);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(110);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(110);
        tabela.getColumnModel().getColumn(3).setPreferredWidth(90);
        tabela.getColumnModel().getColumn(4).setPreferredWidth(150);
        tabela.getColumnModel().getColumn(5).setPreferredWidth(560);
        
        JScrollPane scroll = new JScrollPane(tabela);
        contentPane.add(scroll, BorderLayout.CENTER);

        // ======================
        // PAINEL INFERIOR (Alterar Status)
        // ======================
        JPanel pnlSul = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        pnlSul.setBorder(BorderFactory.createTitledBorder("Ações"));

        pnlSul.add(new JLabel("Alterar Status para:"));
        comboNovoStatus = new JComboBox<>();
        comboNovoStatus.addItem(StatusPedido.ABERTO);
        comboNovoStatus.addItem(StatusPedido.A_CAMINHO);
        comboNovoStatus.addItem(StatusPedido.ENTREGUE);
        pnlSul.add(comboNovoStatus);

        JButton btnAtualizar = new JButton("Atualizar Pedido");
        pnlSul.add(btnAtualizar);
        
        contentPane.add(pnlSul, BorderLayout.SOUTH);

        // ======================
        // LÓGICA E EVENTOS
        // ======================
        btnBuscar.addActionListener(e -> atualizarTabela());
        txtBusca.addActionListener(e -> atualizarTabela());
        comboFiltroStatus.addActionListener(e -> atualizarTabela());

        tabela.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;
            int linha = tabela.getSelectedRow();
            if (linha == -1) return;

            Pedido pedido = (Pedido) modelo.getValueAt(linha, 6);
            if (pedido != null) {
                comboNovoStatus.setSelectedItem(pedido.getStatus());
            }
        });

        btnAtualizar.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(null, "Selecione um pedido.");
                return;
            }

            Pedido pedido = (Pedido) modelo.getValueAt(linha, 6);
            StatusPedido novo = (StatusPedido) comboNovoStatus.getSelectedItem();

            if (pedido == null || novo == null) {
                JOptionPane.showMessageDialog(null, "Não foi possível atualizar o pedido.");
                return;
            }

            pedido.atualizarStatus(novo);
            atualizarTabela();
            JOptionPane.showMessageDialog(null, "Status atualizado com sucesso!");
        });
        
        pack(); // Ajusta a janela ao conteúdo
    }

    private void atualizarTabela() {
        modelo.setRowCount(0);
        if (sistema == null) return;

        String telefone = txtBusca.getText().trim();
        StatusPedido filtro = (StatusPedido) comboFiltroStatus.getSelectedItem();

        for (Cliente c : sistema.getClientes()) {
            if (!telefone.isEmpty() && (c.getTelefone() == null || !c.getTelefone().contains(telefone))) {
                continue;
            }

            for (Pedido p : c.getPedidos()) {
                if (filtro != null && p.getStatus() != filtro) {
                    continue;
                }
                modelo.addRow(new Object[]{
                        p.getIdentificador(),
                        c.getNome(),
                        c.getTelefone(),
                        String.format("R$ %.2f", p.getPrecoTotal()),
                        p.getStatus(),
                        gerarItens(p),
                        p
                });
            }
        }
    }

    private String gerarItens(Pedido p) {
        StringBuilder sb = new StringBuilder();
        for (sistemapizzaria.pizza.Pizza pizza : p.getPizzas()) {
            String forma = obterNomeForma(pizza);
            double area = 0.0;
            try {
                area = pizza.getForma().calcularArea();
            } catch (Exception e) {
                area = 0.0;
            }

            sb.append("Pizza ").append(forma).append(" ").append(String.format("%.0f cm²", area));

            java.util.List<sistemapizzaria.pizza.Sabor> sabores = pizza.getSabores();
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

    private String obterNomeForma(sistemapizzaria.pizza.Pizza pizza) {
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

    class TextAreaRenderer extends JTextArea implements TableCellRenderer {
        private static final long serialVersionUID = 1L;

        public TextAreaRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            setText(value == null ? "" : value.toString());
            if (isSelected) {
                setBackground(table.getSelectionBackground());
                setForeground(table.getSelectionForeground());
            } else {
                setBackground(table.getBackground());
                setForeground(table.getForeground());
            }
            setFont(table.getFont());
            setBorder(null);
            return this;
        }
    }
    
    public void abrirComCliente(String telefone) {
        txtBusca.setText(telefone);
        atualizarTabela();
    }
}