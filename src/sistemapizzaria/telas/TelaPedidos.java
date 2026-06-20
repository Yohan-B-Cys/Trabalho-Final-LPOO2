package sistemapizzaria.telas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import sistemapizzaria.SistemaPizzaria;
import sistemapizzaria.cliente.Cliente;
import sistemapizzaria.cliente.Pedido;
import sistemapizzaria.cliente.StatusPedido;
import sistemapizzaria.pizza.Pizza;

public class TelaPedidos extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    
    // UI Busca Clientes
    private JTextField txtBusca;
    private JTable tabelaClientesBusca;
    private javax.swing.table.DefaultTableModel modeloTabelaClientes;
    
    // UI Histórico Pedidos
    private JTable tabelaPedidos;
    private javax.swing.table.DefaultTableModel modeloTabelaPedidos;
    private JButton btnEditarPedido;
    private JButton btnExcluirPedido;
    
    // UI Carrinho
    private JTable tabelaCarrinho;
    private javax.swing.table.DefaultTableModel modeloCarrinho;
    private JLabel lblPrecoTotal;
    private JButton btnFinalizarSalvar;
    
    // Painel Extraído
    private PainelMontagemPizza painelPizza;
    
    // Estado do Pedido
    private Pedido pedidoEmEdicao;
    private Pedido pedidoEmAndamento;

    private SistemaPizzaria sistema;
    private TelaClientes telaClientesRef;

    public TelaPedidos(SistemaPizzaria sistema, TelaClientes telaClientes) {
        this.sistema = sistema;
        this.telaClientesRef = telaClientes;

        setTitle("Pedidos de Clientes");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(850, 700));
        setLocationRelativeTo(null);

        contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);

        // Painel Principal que terá Scroll
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        JScrollPane mainScroll = new JScrollPane(mainPanel);
        mainScroll.getVerticalScrollBar().setUnitIncrement(16);
        contentPane.add(mainScroll, BorderLayout.CENTER);

        // 1. BUSCA DE CLIENTES
        JPanel pnlBuscaWrapper = new JPanel(new BorderLayout(5, 5));
        pnlBuscaWrapper.setBorder(BorderFactory.createTitledBorder("Buscar Cliente (Telefone)"));
        pnlBuscaWrapper.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));

        JPanel pnlBuscaTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlBuscaTop.add(new JLabel("Telefone:"));
        txtBusca = new JTextField(15);
        pnlBuscaTop.add(txtBusca);
        JButton btnBuscar = new JButton("Buscar");
        pnlBuscaTop.add(btnBuscar);
        pnlBuscaWrapper.add(pnlBuscaTop, BorderLayout.NORTH);

        String[] colunasBusca = {"Telefone", "Nome", "Sobrenome"};
        modeloTabelaClientes = new javax.swing.table.DefaultTableModel(null, colunasBusca) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabelaClientesBusca = new JTable(modeloTabelaClientes);
        tabelaClientesBusca.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPaneBusca = new JScrollPane(tabelaClientesBusca);
        pnlBuscaWrapper.add(scrollPaneBusca, BorderLayout.CENTER);
        mainPanel.add(pnlBuscaWrapper);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // 2. HISTÓRICO DE PEDIDOS
        JPanel pnlPedidosWrapper = new JPanel(new BorderLayout());
        pnlPedidosWrapper.setBorder(BorderFactory.createTitledBorder("Histórico de Pedidos"));
        pnlPedidosWrapper.setPreferredSize(new Dimension(750, 150));

        String[] colunasPedido = {"ID do Pedido", "Qtd. Pizzas", "Preço Total", "Status"};
        modeloTabelaPedidos = new javax.swing.table.DefaultTableModel(null, colunasPedido) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabelaPedidos = new JTable(modeloTabelaPedidos);
        tabelaPedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPanePedidos = new JScrollPane(tabelaPedidos);
        pnlPedidosWrapper.add(scrollPanePedidos, BorderLayout.CENTER);
        
        JPanel pnlAcoesPedido = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAdicionarPedido = new JButton("Adicionar Novo Pedido");
        btnEditarPedido = new JButton("Editar Pedido Selecionado");
        btnExcluirPedido = new JButton("Excluir Pedido Selecionado");
        JButton btnGerenciarPedidos = new JButton("Gerenciar Pedidos");

        btnEditarPedido.setVisible(false);
        btnExcluirPedido.setVisible(false);
        btnExcluirPedido.setForeground(Color.WHITE);
        btnExcluirPedido.setBackground(new Color(220, 53, 69)); // Vermelho Bootstrap

        pnlAcoesPedido.add(btnAdicionarPedido);
        pnlAcoesPedido.add(btnEditarPedido);
        pnlAcoesPedido.add(btnExcluirPedido);
        pnlAcoesPedido.add(btnGerenciarPedidos);
        pnlPedidosWrapper.add(pnlAcoesPedido, BorderLayout.SOUTH);
        
        mainPanel.add(pnlPedidosWrapper);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // 3. PAINEL DE MONTAGEM DE PIZZA (Componentizado)
        painelPizza = new PainelMontagemPizza(sistema, novaPizza -> {
            if (pedidoEmAndamento == null) {
                pedidoEmAndamento = new Pedido();
            }
            pedidoEmAndamento.adicionarPizza(novaPizza);
            atualizarCarrinho();
        });
        painelPizza.setVisible(false);
        mainPanel.add(painelPizza);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // 4. CARRINHO DE COMPRAS
        JPanel pnlCarrinho = new JPanel(new BorderLayout());
        pnlCarrinho.setBorder(BorderFactory.createTitledBorder("Carrinho de Pizzas"));
        pnlCarrinho.setPreferredSize(new Dimension(750, 150));
        
        String[] colunasCarrinho = {"Forma", "Área", "Sabores", "Preço da Pizza"};
        modeloCarrinho = new javax.swing.table.DefaultTableModel(null, colunasCarrinho) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabelaCarrinho = new JTable(modeloCarrinho);
        pnlCarrinho.add(new JScrollPane(tabelaCarrinho), BorderLayout.CENTER);

        JPanel pnlCarrinhoBottom = new JPanel(new BorderLayout());
        JPanel pnlAcoesCarrinho = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnRemoverDaLista = new JButton("Remover Pizza do Carrinho");
        btnRemoverDaLista.setForeground(Color.WHITE);
        btnRemoverDaLista.setBackground(new Color(220, 53, 69));
        JButton btnEditarPizza = new JButton("Editar Pizza");
        pnlAcoesCarrinho.add(btnEditarPizza);
        pnlAcoesCarrinho.add(btnRemoverDaLista);
        
        JPanel pnlTotal = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        lblPrecoTotal = new JLabel("Preço Total: R$ 0,00");
        btnFinalizarSalvar = new JButton("Finalizar Pedido");
        pnlTotal.add(lblPrecoTotal);
        pnlTotal.add(btnFinalizarSalvar);
        
        pnlCarrinhoBottom.add(pnlAcoesCarrinho, BorderLayout.WEST);
        pnlCarrinhoBottom.add(pnlTotal, BorderLayout.EAST);
        pnlCarrinho.add(pnlCarrinhoBottom, BorderLayout.SOUTH);

        pnlCarrinho.setVisible(false);
        mainPanel.add(pnlCarrinho);

        // ======================================
        // LÓGICA DE EVENTOS DA TELA
        // ======================================
        
        txtBusca.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar())) e.consume();
            }
        });

        Runnable buscarAcao = () -> {
            modeloTabelaClientes.setRowCount(0);
            Cliente c = TelaPedidos.this.sistema.buscarClientePorTelefone(txtBusca.getText());
            if (c != null) {
                modeloTabelaClientes.addRow(new Object[]{c.getTelefone(), c.getNome(), c.getSobrenome()});
                tabelaClientesBusca.setRowSelectionInterval(0, 0);
            } else if (!txtBusca.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Cliente não encontrado.");
            }
        };

        btnBuscar.addActionListener(e -> buscarAcao.run());
        txtBusca.addActionListener(e -> buscarAcao.run());

        tabelaClientesBusca.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) atualizarTabelaPedidos();
        });

        tabelaPedidos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                boolean selecionou = tabelaPedidos.getSelectedRow() != -1;
                btnEditarPedido.setVisible(selecionou);
                btnExcluirPedido.setVisible(selecionou);
            }
        });

        btnAdicionarPedido.addActionListener(e -> {
            if (tabelaClientesBusca.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(null, "Selecione um cliente primeiro.");
                return;
            }
            pedidoEmEdicao = null;
            pedidoEmAndamento = new Pedido();
            btnFinalizarSalvar.setText("Finalizar Pedido");
            painelPizza.limparFormulario();
            atualizarCarrinho();
            painelPizza.setVisible(true);
            pnlCarrinho.setVisible(true);
            contentPane.revalidate();
        });

        btnEditarPedido.addActionListener(e -> {
            int linhaP = tabelaPedidos.getSelectedRow();
            int linhaC = tabelaClientesBusca.getSelectedRow();
            if (linhaP == -1 || linhaC == -1) return;

            Cliente c = sistema.buscarClientePorTelefone(modeloTabelaClientes.getValueAt(linhaC, 0).toString());
            Pedido p = c.getPedidos().get(linhaP);

            if (p.getStatus() != StatusPedido.ABERTO) {
                JOptionPane.showMessageDialog(null, "Não é possível alterar um pedido que já está a caminho ou foi entregue.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            pedidoEmEdicao = p;
            pedidoEmAndamento = new Pedido(); // Cria um clone temporário para edição
            for (Pizza pizza : p.getPizzas()) {
                pedidoEmAndamento.adicionarPizza(pizza);
            }
            
            btnFinalizarSalvar.setText("Salvar Alterações");
            atualizarCarrinho();
            painelPizza.setVisible(true);
            pnlCarrinho.setVisible(true);
            
            // Carrega a primeira pizza no formulário como conveniência
            if (!p.getPizzas().isEmpty()) {
                painelPizza.carregarPizzaParaEdicao(p.getPizzas().get(0));
            }
            contentPane.revalidate();
        });

        btnExcluirPedido.addActionListener(e -> {
            int linhaP = tabelaPedidos.getSelectedRow();
            int linhaC = tabelaClientesBusca.getSelectedRow();
            if (linhaP == -1 || linhaC == -1) return;

            int confirm = JOptionPane.showConfirmDialog(null, "Excluir este pedido?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) return;

            Cliente c = sistema.buscarClientePorTelefone(modeloTabelaClientes.getValueAt(linhaC, 0).toString());
            Pedido p = c.getPedidos().get(linhaP);

            if (p.getStatus() != StatusPedido.ABERTO) {
                JOptionPane.showMessageDialog(null, "Não é possível excluir um pedido que já está a caminho ou foi entregue.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            c.removerPedido(p);
            if (pedidoEmEdicao == p) {
                pedidoEmEdicao = null;
                pedidoEmAndamento = null;
                painelPizza.setVisible(false);
                pnlCarrinho.setVisible(false);
            }
            atualizarTabelaPedidos();
            JOptionPane.showMessageDialog(null, "Pedido excluído!");
        });

        btnGerenciarPedidos.addActionListener(e -> {
            TelaGerenciarPedidos gerenciar = new TelaGerenciarPedidos(sistema);
            int linhaC = tabelaClientesBusca.getSelectedRow();
            if (linhaC != -1) {
                String tel = modeloTabelaClientes.getValueAt(linhaC, 0).toString();
                gerenciar.abrirComCliente(tel);
            }
            gerenciar.setVisible(true);
        });

        btnEditarPizza.addActionListener(e -> {
            int row = tabelaCarrinho.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Selecione uma pizza na tabela para editar.");
                return;
            }
            if (pedidoEmAndamento != null && row < pedidoEmAndamento.getPizzas().size()) {
                Pizza p = pedidoEmAndamento.getPizzas().get(row);
                painelPizza.carregarPizzaParaEdicao(p);
                pedidoEmAndamento.removerPizza(p);
                atualizarCarrinho();
            }
        });

        btnRemoverDaLista.addActionListener(e -> {
            int row = tabelaCarrinho.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Selecione uma pizza na tabela para remover.");
                return;
            }
            if (pedidoEmAndamento != null && row < pedidoEmAndamento.getPizzas().size()) {
                Pizza p = pedidoEmAndamento.getPizzas().get(row);
                pedidoEmAndamento.removerPizza(p);
                atualizarCarrinho();
            }
        });

        btnFinalizarSalvar.addActionListener(e -> {
            if (pedidoEmAndamento == null || pedidoEmAndamento.getPizzas().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Adicione pelo menos uma pizza!");
                return;
            }

            int linhaC = tabelaClientesBusca.getSelectedRow();
            Cliente c = sistema.buscarClientePorTelefone(modeloTabelaClientes.getValueAt(linhaC, 0).toString());

            if (pedidoEmEdicao != null) {
                // Remove as pizzas antigas e adiciona as novas
                for (Pizza antiga : pedidoEmEdicao.getPizzas()) {
                    pedidoEmEdicao.removerPizza(antiga);
                }
                for (Pizza nova : pedidoEmAndamento.getPizzas()) {
                    pedidoEmEdicao.adicionarPizza(nova);
                }
                JOptionPane.showMessageDialog(null, "Pedido atualizado com sucesso!");
            } else {
                c.adicionarPedido(pedidoEmAndamento);
                JOptionPane.showMessageDialog(null, "Pedido criado com sucesso!");
            }

            pedidoEmEdicao = null;
            pedidoEmAndamento = null;
            painelPizza.setVisible(false);
            pnlCarrinho.setVisible(false);
            atualizarTabelaPedidos();
            contentPane.revalidate();
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (telaClientesRef != null) telaClientesRef.atualizarTabela();
            }
        });
        
        pack(); // Ajusta janela após inicializar
    }

    private void atualizarTabelaPedidos() {
        modeloTabelaPedidos.setRowCount(0);
        btnEditarPedido.setVisible(false);
        btnExcluirPedido.setVisible(false);
        
        int linhaSelecionada = tabelaClientesBusca.getSelectedRow();
        if (linhaSelecionada == -1) return;

        String tel = modeloTabelaClientes.getValueAt(linhaSelecionada, 0).toString();
        Cliente c = sistema.buscarClientePorTelefone(tel);

        if (c != null) {
            for (Pedido p : c.getPedidos()) {
                modeloTabelaPedidos.addRow(new Object[]{
                    p.getIdentificador(),
                    p.getPizzas().size(),
                    String.format("R$ %.2f", p.getPrecoTotal()),
                    p.getStatus()
                });
            }
        }
    }

    private void atualizarCarrinho() {
        modeloCarrinho.setRowCount(0);
        if (pedidoEmAndamento == null) {
            lblPrecoTotal.setText("Preço Total: R$ 0,00");
            return;
        }

        for (Pizza pizza : pedidoEmAndamento.getPizzas()) {
            String forma = pizza.getForma().getClass().getSimpleName();
            double area = pizza.getForma().calcularArea();
            
            StringBuilder sabores = new StringBuilder();
            for (int i = 0; i < pizza.getSabores().size(); i++) {
                sabores.append(pizza.getSabores().get(i).getNome());
                if (i < pizza.getSabores().size() - 1) sabores.append(", ");
            }

            modeloCarrinho.addRow(new Object[]{
                forma,
                String.format("%.2f cm²", area),
                sabores.toString(),
                String.format("R$ %.2f", pizza.calcularPreco())
            });
        }
        
        lblPrecoTotal.setText(String.format("Preço Total: R$ %.2f", pedidoEmAndamento.calcularPrecoTotal()));
    }
    
    public void buscarClienteAutomaticamente(String telefone) {
        txtBusca.setText(telefone);
        for (java.awt.event.ActionListener a : txtBusca.getActionListeners()) {
            a.actionPerformed(new java.awt.event.ActionEvent(txtBusca, java.awt.event.ActionEvent.ACTION_PERFORMED, null));
        }
    }
}