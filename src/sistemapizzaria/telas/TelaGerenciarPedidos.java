package sistemapizzaria.telas;

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

import java.awt.Component;
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
    private JScrollPane scroll;

    public TelaGerenciarPedidos(SistemaPizzaria sistema) {
        this.sistema = sistema;

        setTitle("Gerenciar Pedidos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 1120, 700);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // ======================
        // FILTRO STATUS
        // ======================
        JLabel lblStatus = new JLabel("Filtrar por Status:");
        lblStatus.setBounds(30, 20, 150, 20);
        contentPane.add(lblStatus);

        comboFiltroStatus = new JComboBox<>();
        comboFiltroStatus.addItem(null); // Todos
        comboFiltroStatus.addItem(StatusPedido.ABERTO);
        comboFiltroStatus.addItem(StatusPedido.A_CAMINHO);
        comboFiltroStatus.addItem(StatusPedido.ENTREGUE);
        comboFiltroStatus.setBounds(160, 20, 160, 25);
        contentPane.add(comboFiltroStatus);

        // ======================
        // BUSCA TELEFONE
        // ======================
        JLabel lblBuscar = new JLabel("Telefone:");
        lblBuscar.setBounds(30, 60, 100, 20);
        contentPane.add(lblBuscar);

        txtBusca = new JTextField();
        txtBusca.setBounds(160, 60, 160, 25);
        contentPane.add(txtBusca);

        JButton btnBuscar = new JButton("Pesquisar");
        btnBuscar.setBounds(340, 60, 120, 25);
        contentPane.add(btnBuscar);

        // ======================
        // TABELA
        // ======================
        scroll = new JScrollPane();
        scroll.setBounds(30, 110, 1045, 330);
        contentPane.add(scroll);

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
        scroll.setViewportView(tabela);

        // tabela só para seleção
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setRowHeight(78);

        // MUITO IMPORTANTE:
        // desliga o auto-resize para as larguras definidas serem respeitadas
        tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Renderer multiline na coluna Itens
        tabela.getColumnModel().getColumn(5).setCellRenderer(new TextAreaRenderer());

        // esconder coluna OBJ
        tabela.getColumnModel().getColumn(6).setMinWidth(0);
        tabela.getColumnModel().getColumn(6).setMaxWidth(0);
        tabela.getColumnModel().getColumn(6).setPreferredWidth(0);
        tabela.getColumnModel().getColumn(6).setWidth(0);

        // ======================
        // AJUSTE DE LARGURA DAS COLUNAS
        // ======================
        // ID
        tabela.getColumnModel().getColumn(0).setPreferredWidth(70);
        tabela.getColumnModel().getColumn(0).setMinWidth(70);
        tabela.getColumnModel().getColumn(0).setMaxWidth(70);

        // Cliente
        tabela.getColumnModel().getColumn(1).setPreferredWidth(110);
        tabela.getColumnModel().getColumn(1).setMinWidth(110);
        tabela.getColumnModel().getColumn(1).setMaxWidth(110);

        // Telefone
        tabela.getColumnModel().getColumn(2).setPreferredWidth(110);
        tabela.getColumnModel().getColumn(2).setMinWidth(110);
        tabela.getColumnModel().getColumn(2).setMaxWidth(110);

        // Total
        tabela.getColumnModel().getColumn(3).setPreferredWidth(90);
        tabela.getColumnModel().getColumn(3).setMinWidth(90);
        tabela.getColumnModel().getColumn(3).setMaxWidth(90);

        // Status -> MAIS LARGA
        tabela.getColumnModel().getColumn(4).setPreferredWidth(150);
        tabela.getColumnModel().getColumn(4).setMinWidth(150);
        tabela.getColumnModel().getColumn(4).setMaxWidth(150);

        // Itens -> cerca de 20% menor que os 700 anteriores
        tabela.getColumnModel().getColumn(5).setPreferredWidth(560);
        tabela.getColumnModel().getColumn(5).setMinWidth(560);

        // ======================
        // ALTERAR STATUS
        // ======================
        JLabel lblNovoStatus = new JLabel("Alterar Status:");
        lblNovoStatus.setBounds(30, 470, 140, 20);
        contentPane.add(lblNovoStatus);

        comboNovoStatus = new JComboBox<>();
        comboNovoStatus.addItem(StatusPedido.ABERTO);
        comboNovoStatus.addItem(StatusPedido.A_CAMINHO);
        comboNovoStatus.addItem(StatusPedido.ENTREGUE);
        comboNovoStatus.setBounds(160, 470, 160, 25);
        contentPane.add(comboNovoStatus);

        JButton btnAtualizar = new JButton("Atualizar Pedido");
        btnAtualizar.setBounds(340, 468, 180, 30);
        contentPane.add(btnAtualizar);

        // ======================
        // EVENTO BUSCAR
        // ======================
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                atualizarTabela();
            }
        });

        // Enter no campo telefone também pesquisa
        txtBusca.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                atualizarTabela();
            }
        });

        // ======================
        // EVENTO SELECIONAR LINHA
        // ======================
        tabela.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) return;

                int linha = tabela.getSelectedRow();
                if (linha == -1) return;

                Pedido pedido = (Pedido) modelo.getValueAt(linha, 6);
                if (pedido != null) {
                    comboNovoStatus.setSelectedItem(pedido.getStatus());
                }
            }
        });

        // ======================
        // EVENTO ATUALIZAR
        // ======================
        btnAtualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

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

                JOptionPane.showMessageDialog(null, "Status atualizado!");
            }
        });
    }

    // ======================
    // ATUALIZA TABELA
    // ======================
    private void atualizarTabela() {
        modelo.setRowCount(0);

        if (sistema == null) return;

        String telefone = txtBusca.getText().trim();
        StatusPedido filtro = (StatusPedido) comboFiltroStatus.getSelectedItem();

        for (Cliente c : sistema.getClientes()) {

            if (!telefone.isEmpty()
                    && (c.getTelefone() == null || !c.getTelefone().contains(telefone))) {
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

    // ======================
    // TEXTO DOS ITENS
    // ======================
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

            sb.append("Pizza ")
              .append(forma)
              .append(" ")
              .append(String.format("%.0f cm²", area));

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

    // ======================
    // RENDERER DE TEXTO MULTILINHA
    // ======================
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