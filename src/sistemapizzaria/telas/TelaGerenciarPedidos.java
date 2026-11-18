package sistemapizzaria.telas;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

import sistemapizzaria.SistemaPizzaria;
import sistemapizzaria.cliente.Pedido;
import sistemapizzaria.cliente.StatusPedido;
import sistemapizzaria.controller.GerenciarPedidosController;
import sistemapizzaria.model.GerenciarPedidosTableModel;

/**
 * View responsável pela visualização de todos os pedidos e alteração de seus estados (Padrão MVC - Aula 06).
 */
public class TelaGerenciarPedidos extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel contentPane;
    private JTextField txtBusca;
    private JTable tabela;
    private final GerenciarPedidosTableModel modelo;
    private final GerenciarPedidosController controller;

    private JComboBox<StatusPedido> comboFiltroStatus;
    private JComboBox<StatusPedido> comboNovoStatus;

    public TelaGerenciarPedidos() {
        this.modelo = new GerenciarPedidosTableModel();
        this.controller = new GerenciarPedidosController(this, this.modelo);

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
        tabela = new JTable(modelo);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setRowHeight(78);
        tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        tabela.getColumnModel().getColumn(5).setCellRenderer(new TextAreaRenderer());

        // Larguras das colunas
        tabela.getColumnModel().getColumn(0).setPreferredWidth(70);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(150);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(120);
        tabela.getColumnModel().getColumn(3).setPreferredWidth(100);
        tabela.getColumnModel().getColumn(4).setPreferredWidth(130);
        tabela.getColumnModel().getColumn(5).setPreferredWidth(540);

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
        // EVENTOS -> CONTROLLER
        // ======================
        btnBuscar.addActionListener(e -> atualizarTabela());
        txtBusca.addActionListener(e -> atualizarTabela());
        comboFiltroStatus.addActionListener(e -> atualizarTabela());

        tabela.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;
            int linha = tabela.getSelectedRow();
            if (linha == -1) return;

            Pedido pedido = modelo.getPedido(linha);
            if (pedido != null) {
                comboNovoStatus.setSelectedItem(pedido.getStatus());
            }
        });

        btnAtualizar.addActionListener(e -> {
            controller.alterarStatusPedido(
                tabela.getSelectedRow(),
                (StatusPedido) comboNovoStatus.getSelectedItem(),
                txtBusca.getText(),
                (StatusPedido) comboFiltroStatus.getSelectedItem()
            );
        });

        atualizarTabela();
        pack();
    }

    public TelaGerenciarPedidos(SistemaPizzaria sistema) {
        this();
    }

    public void atualizarTabela() {
        controller.atualizarTabela(
            txtBusca.getText(),
            (StatusPedido) comboFiltroStatus.getSelectedItem()
        );
    }

    public void abrirComCliente(String telefone) {
        txtBusca.setText(telefone);
        atualizarTabela();
    }

    public void exibirSucesso(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    public void exibirAviso(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Aviso", JOptionPane.WARNING_MESSAGE);
    }

    static class TextAreaRenderer extends JTextArea implements TableCellRenderer {
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
}
