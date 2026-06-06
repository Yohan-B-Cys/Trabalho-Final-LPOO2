package sistemapizzaria.telas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import sistemapizzaria.SistemaPizzaria;
import sistemapizzaria.cliente.Cliente;
import sistemapizzaria.cliente.Pedido;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ButtonGroup;

public class TelaPedidos extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtBusca;
    private JTextField txtTamanho;
    private SistemaPizzaria sistema;
    private JTable tabelaClientesBusca;
    private javax.swing.table.DefaultTableModel modeloTabelaClientes;
    private JTable tabelaPedidos;
    private javax.swing.table.DefaultTableModel modeloTabelaPedidos;
    private JPanel panelPizza;
    private final ButtonGroup buttonGroup = new ButtonGroup();
    private JButton btnBuscar;

    // PROMOVIDOS para poder controlar visibilidade automaticamente
    private JButton btnEditarPedido;
    private JButton btnExcluirPedido;

    private JComboBox<String> comboBoxFormaPizza;
    private JRadioButton radioLadoRaio;
    private JRadioButton radioArea;
    private JLabel lblTamanhoLadodiametro;
    private JLabel lblrea;
    private JLabel lblPre;
    private JLabel lblObs;
    private double areaCalculadaGlobal = 0.0;
    private JComboBox<String> comboBox_1;
    private JComboBox<String> comboBox_2;

    private JTable tabelaCarrinho;
    private javax.swing.table.DefaultTableModel modeloTabelaCarrinho;
    private double precoTotalDoPedido = 0.0;
    private JLabel lblPreoTotal;

    private java.util.List<sistemapizzaria.pizza.Pizza> carrinhoVirtual = new java.util.ArrayList<>();

    // CONTROLE DE EDIÇÃO
    private Pedido pedidoEmEdicao = null;

    // BOTÃO GLOBAL PARA MUDAR O TEXTO
    private JButton btnFinalizarSalvar;

    /**
     * Create the frame.
     */
    public TelaPedidos(SistemaPizzaria sistema) {
        this.sistema = sistema;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 1100);
        setTitle("Pedidos");
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // ==========================
        // CAMPO BUSCA CLIENTE
        // ==========================
        txtBusca = new JTextField();
        txtBusca.setBounds(276, 51, 164, 21);
        contentPane.add(txtBusca);
        txtBusca.setColumns(10);

        txtBusca.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                carregarClientesPorTelefone(txtBusca.getText());
            }

            @Override
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar())) {
                    e.consume();
                }
            }
        });

        JLabel lblCliente = new JLabel("Telefone do Cliente : ");
        lblCliente.setBounds(143, 52, 126, 19);
        contentPane.add(lblCliente);

        // ==========================
        // TABELA CLIENTES
        // ==========================
        JScrollPane scrollPaneClientes = new JScrollPane();
        scrollPaneClientes.setBounds(75, 84, 482, 100);
        contentPane.add(scrollPaneClientes);

        String[] colunasCliente = {"Telefone", "Nome", "Sobrenome"};
        modeloTabelaClientes = new javax.swing.table.DefaultTableModel(null, colunasCliente) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaClientesBusca = new JTable(modeloTabelaClientes);
        tabelaClientesBusca.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        scrollPaneClientes.setViewportView(tabelaClientesBusca);

        // ==========================
        // TABELA PEDIDOS
        // ==========================
        JScrollPane scrollPanePedidos = new JScrollPane();
        scrollPanePedidos.setBounds(61, 228, 594, 100);
        contentPane.add(scrollPanePedidos);

        String[] colunasPedido = {"ID do Pedido", "Qtd. Pizzas", "Preço Total", "Status"};
        modeloTabelaPedidos = new javax.swing.table.DefaultTableModel(null, colunasPedido) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaPedidos = new JTable(modeloTabelaPedidos);
        tabelaPedidos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        scrollPanePedidos.setViewportView(tabelaPedidos);

        // QUANDO SELECIONA O CLIENTE, CARREGA OS PEDIDOS AUTOMATICAMENTE
        tabelaClientesBusca.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;
            carregarPedidosDoClienteSelecionado();
        });

        // ==========================
        // PAINEL DE PIZZA
        // ==========================
        panelPizza = new JPanel();
        panelPizza.setBounds(61, 330, 684, 305);
        panelPizza.setLayout(null);
        panelPizza.setVisible(false);
        contentPane.add(panelPizza);

        JButton btnAdicionarPedido = new JButton("Adicionar Pedido");
        btnAdicionarPedido.setBounds(75, 196, 147, 27);
        btnAdicionarPedido.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int linhaSelecionada = tabelaClientesBusca.getSelectedRow();

                if (linhaSelecionada == -1) {
                    javax.swing.JOptionPane.showMessageDialog(
                            null,
                            "Por favor, selecione um cliente na tabela superior antes de adicionar um pedido.",
                            "Aviso",
                            javax.swing.JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                pedidoEmEdicao = null;
                btnFinalizarSalvar.setText("Finalizar Pedido");
                limparCarrinhoEFormulario();
                panelPizza.setVisible(true);
            }
        });
        contentPane.add(btnAdicionarPedido);

        btnEditarPedido = new JButton("Editar Pedido");
        btnEditarPedido.setBounds(232, 196, 147, 27);
        btnEditarPedido.setVisible(false);
        btnEditarPedido.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int linhaPedido = tabelaPedidos.getSelectedRow();

                if (linhaPedido == -1) {
                    javax.swing.JOptionPane.showMessageDialog(
                            null,
                            "Selecione um pedido na tabela superior para editar.",
                            "Aviso",
                            javax.swing.JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                int linhaCliente = tabelaClientesBusca.getSelectedRow();
                if (linhaCliente == -1) {
                    javax.swing.JOptionPane.showMessageDialog(
                            null,
                            "Selecione um cliente na tabela superior.",
                            "Aviso",
                            javax.swing.JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                String telDigitado = modeloTabelaClientes.getValueAt(linhaCliente, 0).toString();
                Cliente clienteSelecionado = TelaPedidos.this.sistema.buscarClientePorTelefone(telDigitado);

                if (clienteSelecionado == null) {
                    javax.swing.JOptionPane.showMessageDialog(
                            null,
                            "Cliente não encontrado.",
                            "Erro",
                            javax.swing.JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                Pedido pedidoSelecionado = clienteSelecionado.getPedidos().get(linhaPedido);

                pedidoEmEdicao = pedidoSelecionado;
                btnFinalizarSalvar.setText("Salvar Alterações");

                carregarPedidoNoCarrinho(pedidoSelecionado);
                panelPizza.setVisible(true);
            }
        });
        contentPane.add(btnEditarPedido);

        btnExcluirPedido = new JButton("Excluir Pedido");
        btnExcluirPedido.setBounds(590, 196, 138, 27);
        btnExcluirPedido.setVisible(false);
        btnExcluirPedido.setForeground(Color.BLACK);
        btnExcluirPedido.setBackground(Color.RED);
        btnExcluirPedido.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int linhaPedido = tabelaPedidos.getSelectedRow();

                if (linhaPedido == -1) {
                    javax.swing.JOptionPane.showMessageDialog(
                            null,
                            "Selecione um pedido na tabela para excluir.",
                            "Aviso",
                            javax.swing.JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                int linhaCliente = tabelaClientesBusca.getSelectedRow();
                if (linhaCliente == -1) {
                    javax.swing.JOptionPane.showMessageDialog(
                            null,
                            "Selecione um cliente na tabela superior.",
                            "Aviso",
                            javax.swing.JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                int confirmacao = javax.swing.JOptionPane.showConfirmDialog(
                        null,
                        "Deseja realmente excluir este pedido?",
                        "Confirmar exclusão",
                        javax.swing.JOptionPane.YES_NO_OPTION
                );

                if (confirmacao != javax.swing.JOptionPane.YES_OPTION) {
                    return;
                }

                String telDigitado = modeloTabelaClientes.getValueAt(linhaCliente, 0).toString();
                Cliente clienteSelecionado = TelaPedidos.this.sistema.buscarClientePorTelefone(telDigitado);

                if (clienteSelecionado != null) {
                    Pedido pedidoSelecionado = clienteSelecionado.getPedidos().get(linhaPedido);
                    clienteSelecionado.removerPedido(pedidoSelecionado);

                    if (pedidoEmEdicao == pedidoSelecionado) {
                        pedidoEmEdicao = null;
                        btnFinalizarSalvar.setText("Finalizar Pedido");
                        limparCarrinhoEFormulario();
                        panelPizza.setVisible(false);
                    }

                    atualizarTabelaPedidos();

                    javax.swing.JOptionPane.showMessageDialog(
                            null,
                            "Pedido excluído com sucesso!",
                            "Sucesso",
                            javax.swing.JOptionPane.INFORMATION_MESSAGE
                    );
                }
            }
        });
        contentPane.add(btnExcluirPedido);

        btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(452, 48, 105, 27);
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                carregarPedidosDoClienteSelecionado();
            }
        });
        contentPane.add(btnBuscar);

        // ==========================================
        // ITENS DO PAINEL DA PIZZA
        // ==========================================
        comboBoxFormaPizza = new JComboBox<String>();
        comboBoxFormaPizza.setBounds(119, 10, 199, 26);
        comboBoxFormaPizza.addItem("Círculo");
        comboBoxFormaPizza.addItem("Quadrado");
        comboBoxFormaPizza.addItem("Triângulo Equilátero");
        panelPizza.add(comboBoxFormaPizza);

        radioLadoRaio = new JRadioButton("Definir tamanho por raio/lado");
        buttonGroup.add(radioLadoRaio);
        radioLadoRaio.setBounds(14, 44, 219, 25);
        radioLadoRaio.setSelected(true);
        panelPizza.add(radioLadoRaio);

        JLabel lblTipoDaPizza = new JLabel("Tipo da Pizza :");
        lblTipoDaPizza.setBounds(14, 15, 97, 17);
        panelPizza.add(lblTipoDaPizza);

        radioArea = new JRadioButton("Definir tamanho por área");
        buttonGroup.add(radioArea);
        radioArea.setBounds(262, 44, 188, 25);
        panelPizza.add(radioArea);

        ActionListener radioListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                atualizarLabelObs();
                atualizarMedidasNaTela();
            }
        };
        radioLadoRaio.addActionListener(radioListener);
        radioArea.addActionListener(radioListener);

        txtTamanho = new JTextField();
        txtTamanho.setBounds(151, 87, 199, 21);
        panelPizza.add(txtTamanho);
        txtTamanho.setColumns(10);

        txtTamanho.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                atualizarMedidasNaTela();
            }
        });

        txtTamanho.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                atualizarMedidasNaTela();
            }
        });

        KeyAdapter garanteNumero = new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char caractere = e.getKeyChar();
                if (!Character.isDigit(caractere) && caractere != '.' && caractere != ',') {
                    e.consume();
                }
            }
        };
        txtTamanho.addKeyListener(garanteNumero);

        JLabel lblDefinirTamanho = new JLabel("Definir lado /raio/ area :");
        lblDefinirTamanho.setBounds(0, 89, 142, 17);
        panelPizza.add(lblDefinirTamanho);

        lblTamanhoLadodiametro = new JLabel("Tamanho Lado/Raio : -- cm ");
        lblTamanhoLadodiametro.setBounds(14, 120, 219, 17);
        panelPizza.add(lblTamanhoLadodiametro);

        lblObs = new JLabel("Obs: lado deve ter entre 7 a 23 cm");
        lblObs.setBounds(368, 89, 304, 17);
        panelPizza.add(lblObs);

        comboBoxFormaPizza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String formaSelecionada = comboBoxFormaPizza.getSelectedItem().toString();
                if (formaSelecionada.equals("Círculo")) {
                    radioLadoRaio.setText("Definir tamanho por raio");
                    lblDefinirTamanho.setText("Definir Raio (cm) :");
                    lblObs.setText("Obs: raio deve ter entre 7 a 23 cm");
                } else {
                    radioLadoRaio.setText("Definir tamanho por lado");
                    lblDefinirTamanho.setText("Definir Lado (cm):");
                    if (formaSelecionada.equals("Quadrado")) {
                        lblObs.setText("Obs: lado deve ter entre 10 a 40 cm");
                    } else {
                        lblObs.setText("Obs: lado deve ter entre 20 a 60 cm");
                    }
                }
                atualizarMedidasNaTela();
            }
        });

        lblrea = new JLabel("Área : -- cm²");
        lblrea.setBounds(240, 120, 130, 17);
        panelPizza.add(lblrea);

        JLabel lblSabores = new JLabel("Sabores :");
        lblSabores.setBounds(14, 167, 60, 17);
        panelPizza.add(lblSabores);

        // COMBO 1
        comboBox_1 = new JComboBox<String>();
        comboBox_1.setBounds(81, 162, 228, 26);
        panelPizza.add(comboBox_1);

        if (this.sistema != null) {
            comboBox_1.addItem("Selecione um sabor...");

            java.util.List<sistemapizzaria.pizza.Sabor> listaSabores = this.sistema.getSaboresDisponiveis();
            for (sistemapizzaria.pizza.Sabor sabor : listaSabores) {
                comboBox_1.addItem(sabor.getNome() + " - " + sabor.getTipo().getCategoria());
            }
        }

        // COMBO 2
        comboBox_2 = new JComboBox<String>();
        comboBox_2.setBounds(321, 162, 258, 26);
        comboBox_2.addItem("Nenhuma (Pizza de 1 sabor)");

        if (this.sistema != null) {
            java.util.List<sistemapizzaria.pizza.Sabor> listaSabores = this.sistema.getSaboresDisponiveis();
            for (sistemapizzaria.pizza.Sabor sabor : listaSabores) {
                comboBox_2.addItem(sabor.getNome() + " - " + sabor.getTipo().getCategoria());
            }
        }
        panelPizza.add(comboBox_2);

        ActionListener saborListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calcularPrecoFinal();
            }
        };
        comboBox_1.addActionListener(saborListener);
        comboBox_2.addActionListener(saborListener);

        lblPre = new JLabel("Preço : R$ 0,00");
        lblPre.setBounds(11, 214, 250, 17);
        panelPizza.add(lblPre);

        JButton btnAdicionarPizza = new JButton("Adicionar pizza");
        btnAdicionarPizza.setBounds(14, 258, 164, 27);
        btnAdicionarPizza.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (areaCalculadaGlobal <= 0) {
                    javax.swing.JOptionPane.showMessageDialog(null, "Calcule o tamanho da pizza primeiro!");
                    return;
                }

                int idx1 = comboBox_1.getSelectedIndex();

                if (idx1 <= 0) {
                    javax.swing.JOptionPane.showMessageDialog(null, "Escolha o sabor principal!");
                    return;
                }

                String textoPreco = lblPre.getText().replace("Preço : R$ ", "").replace(",", ".");
                double precoPizza = 0;
                try {
                    precoPizza = Double.parseDouble(textoPreco);
                } catch (Exception ex) {
                }

                if (precoPizza <= 0) {
                    javax.swing.JOptionPane.showMessageDialog(
                            null,
                            "O preço da pizza está zerado. Verifique se os sabores foram escolhidos e se a tabela de preços foi configurada."
                    );
                    return;
                }

                String formato = comboBoxFormaPizza.getSelectedItem().toString();
                String areaStr = String.format("%.2f", areaCalculadaGlobal);
                String sabores = comboBox_1.getSelectedItem().toString();
                if (comboBox_2.getSelectedIndex() > 0) {
                    sabores += " e " + comboBox_2.getSelectedItem().toString();
                }

                sistemapizzaria.pizza.Pizza novaPizza = new sistemapizzaria.pizza.Pizza();

                sistemapizzaria.formas.Forma formaReal = null;
                if ("Círculo".equals(formato)) formaReal = new sistemapizzaria.formas.Circulo();
                else if ("Quadrado".equals(formato)) formaReal = new sistemapizzaria.formas.Quadrado();
                else if ("Triângulo Equilátero".equals(formato)) formaReal = new sistemapizzaria.formas.TrianguloEquilatero();

                double valorDigitado = Double.parseDouble(txtTamanho.getText().replace(",", "."));
                try {
                    if (radioLadoRaio.isSelected()) formaReal.definirPorDimensao(valorDigitado);
                    else formaReal.definirPorArea(valorDigitado);
                } catch (Exception ex) {
                    return;
                }

                novaPizza.setForma(formaReal);

                // SABOR 1
                novaPizza.adicionarSabor(
                        TelaPedidos.this.sistema.getSaboresDisponiveis().get(idx1 - 1)
                );

                // SABOR 2
                int idx2 = comboBox_2.getSelectedIndex();
                if (idx2 > 0) {
                    novaPizza.adicionarSabor(
                            TelaPedidos.this.sistema.getSaboresDisponiveis().get(idx2 - 1)
                    );
                }

                carrinhoVirtual.add(novaPizza);

                modeloTabelaCarrinho.addRow(new Object[]{
                        formato,
                        areaStr + " cm²",
                        sabores,
                        String.format("R$ %.2f", precoPizza)
                });

                precoTotalDoPedido += precoPizza;
                lblPreoTotal.setText(String.format("Preço Total : R$ %.2f", precoTotalDoPedido));

                limparFormularioPizza();
            }
        });
        panelPizza.add(btnAdicionarPizza);

        // ==========================
        // CARRINHO
        // ==========================
        JLabel lblCarrinho = new JLabel("CARRINHO");
        lblCarrinho.setBounds(276, 670, 80, 17);
        contentPane.add(lblCarrinho);

        JButton btnEditarPizza = new JButton("Editar Pizza");
        btnEditarPizza.setBounds(251, 706, 105, 27);
        btnEditarPizza.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int linha = tabelaCarrinho.getSelectedRow();
                if (linha == -1) {
                    javax.swing.JOptionPane.showMessageDialog(null, "Selecione uma pizza no carrinho para editar.");
                    return;
                }

                String formato = modeloTabelaCarrinho.getValueAt(linha, 0).toString();
                String areaComTexto = modeloTabelaCarrinho.getValueAt(linha, 1).toString();
                String precoComTexto = modeloTabelaCarrinho.getValueAt(linha, 3).toString();

                comboBoxFormaPizza.setSelectedItem(formato);
                radioArea.setSelected(true);

                String areaPura = areaComTexto.replace(" cm²", "").replace(",", ".");
                txtTamanho.setText(areaPura);

                double precoPizza = Double.parseDouble(precoComTexto.replace("R$ ", "").replace(",", "."));
                precoTotalDoPedido -= precoPizza;
                lblPreoTotal.setText(String.format("Preço Total : R$ %.2f", precoTotalDoPedido));

                carrinhoVirtual.remove(linha);
                modeloTabelaCarrinho.removeRow(linha);

                atualizarLabelObs();
                atualizarMedidasNaTela();
            }
        });
        contentPane.add(btnEditarPizza);

        JButton btnExcluirPizza = new JButton("Excluir Pizza");
        btnExcluirPizza.setBounds(368, 706, 138, 27);
        btnExcluirPizza.setForeground(Color.BLACK);
        btnExcluirPizza.setBackground(Color.RED);
        btnExcluirPizza.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int linha = tabelaCarrinho.getSelectedRow();
                if (linha == -1) {
                    javax.swing.JOptionPane.showMessageDialog(null, "Selecione uma pizza no carrinho para excluir.");
                    return;
                }

                String precoComTexto = modeloTabelaCarrinho.getValueAt(linha, 3).toString();
                double precoPizza = Double.parseDouble(precoComTexto.replace("R$ ", "").replace(",", "."));

                precoTotalDoPedido -= precoPizza;
                lblPreoTotal.setText(String.format("Preço Total : R$ %.2f", precoTotalDoPedido));

                carrinhoVirtual.remove(linha);
                modeloTabelaCarrinho.removeRow(linha);
            }
        });
        contentPane.add(btnExcluirPizza);

        lblPreoTotal = new JLabel("Preço Total :  R$ 0,00");
        lblPreoTotal.setBounds(80, 893, 250, 17);
        contentPane.add(lblPreoTotal);

        // BOTÃO FINALIZAR / SALVAR
        btnFinalizarSalvar = new JButton("Finalizar Pedido");
        btnFinalizarSalvar.setBounds(75, 942, 268, 27);
        btnFinalizarSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                int linhaCliente = tabelaClientesBusca.getSelectedRow();
                if (linhaCliente == -1) {
                    javax.swing.JOptionPane.showMessageDialog(
                            null,
                            "Selecione um cliente na tabela superior para salvar o pedido."
                    );
                    return;
                }

                if (carrinhoVirtual.isEmpty()) {
                    javax.swing.JOptionPane.showMessageDialog(
                            null,
                            "O carrinho está vazio! Adicione pizzas antes de finalizar."
                    );
                    return;
                }

                String telDigitado = modeloTabelaClientes.getValueAt(linhaCliente, 0).toString();
                Cliente clienteSelecionado = TelaPedidos.this.sistema.buscarClientePorTelefone(telDigitado);

                if (pedidoEmEdicao == null) {
                    Pedido novoPedido = new Pedido();

                    for (sistemapizzaria.pizza.Pizza p : carrinhoVirtual) {
                        novoPedido.adicionarPizza(p);
                    }

                    clienteSelecionado.adicionarPedido(novoPedido);

                    javax.swing.JOptionPane.showMessageDialog(
                            null,
                            "Pedido " + novoPedido.getIdentificador() + " salvo com sucesso!",
                            "Sucesso",
                            javax.swing.JOptionPane.INFORMATION_MESSAGE
                    );
                } else {
                    clienteSelecionado.removerPedido(pedidoEmEdicao);

                    Pedido pedidoAtualizado = new Pedido();

                    for (sistemapizzaria.pizza.Pizza p : carrinhoVirtual) {
                        pedidoAtualizado.adicionarPizza(p);
                    }

                    clienteSelecionado.adicionarPedido(pedidoAtualizado);

                    javax.swing.JOptionPane.showMessageDialog(
                            null,
                            "Alterações salvas com sucesso!",
                            "Sucesso",
                            javax.swing.JOptionPane.INFORMATION_MESSAGE
                    );
                }

                atualizarTabelaPedidos();
                tabelaPedidos.repaint();

                modeloTabelaCarrinho.setRowCount(0);
                carrinhoVirtual.clear();
                precoTotalDoPedido = 0.0;
                lblPreoTotal.setText("Preço Total :  R$ 0,00");
                panelPizza.setVisible(false);

                pedidoEmEdicao = null;
                btnFinalizarSalvar.setText("Finalizar Pedido");
                limparFormularioPizza();
            }
        });
        contentPane.add(btnFinalizarSalvar);

        // ==========================
        // TABELA CARRINHO
        // ==========================
        JScrollPane scrollPaneCarrinho = new JScrollPane();
        scrollPaneCarrinho.setBounds(23, 745, 765, 132);
        contentPane.add(scrollPaneCarrinho);

        String[] colunasCarrinho = {"Formato", "Área", "Sabores", "Preço da Pizza"};
        modeloTabelaCarrinho = new javax.swing.table.DefaultTableModel(null, colunasCarrinho) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaCarrinho = new JTable(modeloTabelaCarrinho);
        tabelaCarrinho.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabelaCarrinho.getColumnModel().getColumn(0).setPreferredWidth(70);
        tabelaCarrinho.getColumnModel().getColumn(1).setPreferredWidth(80);
        tabelaCarrinho.getColumnModel().getColumn(2).setPreferredWidth(330);
        tabelaCarrinho.getColumnModel().getColumn(3).setPreferredWidth(100);

        scrollPaneCarrinho.setViewportView(tabelaCarrinho);
        
        JButton btnGerenciarPedidos = new JButton("Gerenciar Pedidos");
        btnGerenciarPedidos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                int linhaCliente = tabelaClientesBusca.getSelectedRow();

                if (linhaCliente == -1) {
                    javax.swing.JOptionPane.showMessageDialog(
                        null,
                        "Selecione um cliente na tabela superior primeiro.",
                        "Aviso",
                        javax.swing.JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                String telefone = modeloTabelaClientes.getValueAt(linhaCliente, 0).toString();

                TelaGerenciarPedidos tela = new TelaGerenciarPedidos(sistema);
                tela.abrirComCliente(telefone);
                tela.setVisible(true);
            }
        });
        btnGerenciarPedidos.setBounds(391, 196, 187, 27);
        contentPane.add(btnGerenciarPedidos);
    }
    // =====================================================
    // MÉTODOS AUXILIARES DE FLUXO CLIENTE / PEDIDOS
    // =====================================================

    private void carregarClientesPorTelefone(String telefoneBusca) {
        modeloTabelaClientes.setRowCount(0);

        if (telefoneBusca == null || telefoneBusca.trim().isEmpty() || this.sistema == null) {
            return;
        }

        java.util.List<Cliente> encontrados = this.sistema.buscarClientes(telefoneBusca);

        for (Cliente c : encontrados) {
            modeloTabelaClientes.addRow(new Object[]{
                    c.getTelefone(),
                    c.getNome(),
                    c.getSobrenome()
            });
        }
    }

    private void carregarPedidosDoClienteSelecionado() {
        int linha = tabelaClientesBusca.getSelectedRow();

        modeloTabelaPedidos.setRowCount(0);
        btnEditarPedido.setVisible(false);
        btnExcluirPedido.setVisible(false);

        if (linha == -1) return;

        String tel = modeloTabelaClientes.getValueAt(linha, 0).toString();
        Cliente cliente = sistema.buscarClientePorTelefone(tel);

        if (cliente == null) return;

        java.util.List<Pedido> historicoPedidos = cliente.getPedidos();

        if (historicoPedidos == null || historicoPedidos.isEmpty()) {
            return;
        }

        btnEditarPedido.setVisible(true);
        btnExcluirPedido.setVisible(true);

        for (Pedido pedido : historicoPedidos) {
            modeloTabelaPedidos.addRow(new Object[]{
                    pedido.getIdentificador(),
                    pedido.getPizzas().size(),
                    String.format("R$ %.2f", pedido.getPrecoTotal()),
                    pedido.getStatus()
            });
        }
    }

    public void preencherCliente(String telefone) {
        txtBusca.setText(telefone);
        carregarClientesPorTelefone(telefone);

        if (tabelaClientesBusca.getRowCount() > 0) {
            tabelaClientesBusca.setRowSelectionInterval(0, 0);
            carregarPedidosDoClienteSelecionado();
        }
    }

    // ==========================
    // MÉTODOS AUXILIARES DA PIZZA / CARRINHO
    // ==========================

    private void limparFormularioPizza() {
        txtTamanho.setText("");
        areaCalculadaGlobal = 0.0;
        lblTamanhoLadodiametro.setText("Tamanho Lado/Raio : -- cm ");
        lblrea.setText("Área : -- cm²");
        lblPre.setText("Preço : R$ 0,00");

        if (comboBox_1.getItemCount() > 0) comboBox_1.setSelectedIndex(0);
        if (comboBox_2.getItemCount() > 0) comboBox_2.setSelectedIndex(0);

        radioLadoRaio.setSelected(true);
        atualizarLabelObs();
    }

    private void limparCarrinhoEFormulario() {
        modeloTabelaCarrinho.setRowCount(0);
        carrinhoVirtual.clear();
        precoTotalDoPedido = 0.0;
        lblPreoTotal.setText("Preço Total : R$ 0,00");
        limparFormularioPizza();
    }

    private void carregarPedidoNoCarrinho(Pedido pedido) {
        limparCarrinhoEFormulario();

        if (pedido == null || pedido.getPizzas() == null) {
            return;
        }

        for (sistemapizzaria.pizza.Pizza pizza : pedido.getPizzas()) {
            carrinhoVirtual.add(pizza);

            String formato = obterNomeForma(pizza);
            double area = obterAreaDaPizza(pizza);
            String sabores = obterTextoSaboresDaPizza(pizza);
            double preco = calcularPrecoDaPizzaReal(pizza);

            modeloTabelaCarrinho.addRow(new Object[]{
                    formato,
                    String.format("%.2f cm²", area),
                    sabores,
                    String.format("R$ %.2f", preco)
            });

            precoTotalDoPedido += preco;
        }

        lblPreoTotal.setText(String.format("Preço Total : R$ %.2f", precoTotalDoPedido));
    }

    private String obterNomeForma(sistemapizzaria.pizza.Pizza pizza) {
        try {
            String classe = pizza.getForma().getClass().getSimpleName();

            if ("Circulo".equals(classe)) return "Círculo";
            if ("Quadrado".equals(classe)) return "Quadrado";
            if ("TrianguloEquilatero".equals(classe)) return "Triângulo Equilátero";

            return classe;
        } catch (Exception e) {
            return "Forma";
        }
    }

    private double obterAreaDaPizza(sistemapizzaria.pizza.Pizza pizza) {
        try {
            return pizza.getForma().calcularArea();
        } catch (Exception e) {
            return 0.0;
        }
    }

    private String obterTextoSaboresDaPizza(sistemapizzaria.pizza.Pizza pizza) {
        try {
            java.util.List<sistemapizzaria.pizza.Sabor> sabores = pizza.getSabores();

            if (sabores == null || sabores.isEmpty()) {
                return "Sem sabores";
            }

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < sabores.size(); i++) {
                sistemapizzaria.pizza.Sabor s = sabores.get(i);
                sb.append(s.getNome()).append(" - ").append(s.getTipo().getCategoria());

                if (i < sabores.size() - 1) {
                    sb.append(" e ");
                }
            }

            return sb.toString();
        } catch (Exception e) {
            return "Sabores";
        }
    }

    private double calcularPrecoDaPizzaReal(sistemapizzaria.pizza.Pizza pizza) {
        try {
            double area = pizza.getForma().calcularArea();
            java.util.List<sistemapizzaria.pizza.Sabor> sabores = pizza.getSabores();

            if (sabores == null || sabores.isEmpty()) {
                return 0.0;
            }

            double somaPrecoCm2 = 0.0;

            for (sistemapizzaria.pizza.Sabor sabor : sabores) {
                somaPrecoCm2 += obterPrecoCm2(sabor.getTipo().getCategoria());
            }

            double mediaPrecoCm2 = somaPrecoCm2 / sabores.size();
            return area * mediaPrecoCm2;

        } catch (Exception e) {
            return 0.0;
        }
    }

    private void atualizarMedidasNaTela() {
        try {
            String textoValor = txtTamanho.getText().replace(",", ".");
            if (textoValor.trim().isEmpty()) {
                return;
            }

            lblTamanhoLadodiametro.setText("Calculando...");
            lblrea.setText("");

            double valorDigitado = Double.parseDouble(textoValor);
            String formaEscolhida = (String) comboBoxFormaPizza.getSelectedItem();
            sistemapizzaria.formas.Forma formaPizza = null;

            if ("Círculo".equals(formaEscolhida)) formaPizza = new sistemapizzaria.formas.Circulo();
            else if ("Quadrado".equals(formaEscolhida)) formaPizza = new sistemapizzaria.formas.Quadrado();
            else if ("Triângulo Equilátero".equals(formaEscolhida)) formaPizza = new sistemapizzaria.formas.TrianguloEquilatero();

            if (formaPizza == null) return;

            double areaFinal = 0;
            double dimensaoFinal = 0;

            if (radioLadoRaio.isSelected()) {
                formaPizza.definirPorDimensao(valorDigitado);
                areaFinal = formaPizza.calcularArea();
                dimensaoFinal = valorDigitado;
            } else if (radioArea.isSelected()) {
                formaPizza.definirPorArea(valorDigitado);
                areaFinal = valorDigitado;

                if (formaPizza instanceof sistemapizzaria.formas.Circulo)
                    dimensaoFinal = ((sistemapizzaria.formas.Circulo) formaPizza).getRaio();
                else if (formaPizza instanceof sistemapizzaria.formas.Quadrado)
                    dimensaoFinal = ((sistemapizzaria.formas.Quadrado) formaPizza).getLado();
                else if (formaPizza instanceof sistemapizzaria.formas.TrianguloEquilatero)
                    dimensaoFinal = ((sistemapizzaria.formas.TrianguloEquilatero) formaPizza).getLado();
            }

            String nomeMedida = "Círculo".equals(formaEscolhida) ? "Raio" : "Lado";
            lblTamanhoLadodiametro.setText(String.format("Tamanho %s: %.2f cm", nomeMedida, dimensaoFinal));
            lblrea.setText(String.format("Área: %.2f cm²", areaFinal));

            areaCalculadaGlobal = areaFinal;
            calcularPrecoFinal();

        } catch (sistemapizzaria.excecoes.DimensaoInvalidaException |
                 sistemapizzaria.excecoes.AreaInvalidaException ex) {
            lblTamanhoLadodiametro.setText("Tamanho Inválido!");
            lblrea.setText("Área Inválida!");
            lblPre.setText("Preço : R$ 0,00");
            areaCalculadaGlobal = 0;

        } catch (Exception ex) {
            // ignora
        }
    }

    private void atualizarLabelObs() {
        if (radioArea.isSelected()) {
            lblObs.setText("Obs: a área deve ter entre 100 e 1600 cm²");
        } else {
            String formaSelecionada = comboBoxFormaPizza.getSelectedItem().toString();
            if (formaSelecionada.equals("Círculo")) lblObs.setText("Obs: raio deve ter entre 7 a 23 cm");
            else if (formaSelecionada.equals("Quadrado")) lblObs.setText("Obs: lado deve ter entre 10 a 40 cm");
            else lblObs.setText("Obs: lado deve ter entre 20 a 60 cm");
        }
    }

    private void calcularPrecoFinal() {
        if (areaCalculadaGlobal <= 0 || this.sistema == null) {
            lblPre.setText("Preço : R$ 0,00");
            return;
        }

        try {
            double precoTotalCm2 = 0.0;
            int divisor = 1;

            int idx1 = comboBox_1.getSelectedIndex();

            if (idx1 > 0) {
                sistemapizzaria.pizza.Sabor s1 =
                        this.sistema.getSaboresDisponiveis().get(idx1 - 1);
                precoTotalCm2 += obterPrecoCm2(s1.getTipo().getCategoria());
            } else {
                lblPre.setText("Preço : R$ 0,00");
                return;
            }

            int idx2 = comboBox_2.getSelectedIndex();
            if (idx2 > 0) {
                sistemapizzaria.pizza.Sabor s2 =
                        this.sistema.getSaboresDisponiveis().get(idx2 - 1);
                precoTotalCm2 += obterPrecoCm2(s2.getTipo().getCategoria());
                divisor = 2;
            }

            double precoMediaCm2 = precoTotalCm2 / divisor;
            double precoFinal = areaCalculadaGlobal * precoMediaCm2;

            lblPre.setText(String.format("Preço : R$ %.2f", precoFinal));
        } catch (Exception e) {
            lblPre.setText("Preço : R$ 0,00");
        }
    }

    private double obterPrecoCm2(sistemapizzaria.pizza.CategoriaTipo categoria) {
        for (sistemapizzaria.pizza.TipoPizza tipo : this.sistema.getTabelaPrecos()) {
            if (tipo.getCategoria() == categoria) {
                return tipo.getPrecoPorCm2();
            }
        }
        return 0.0;
    }

    private void atualizarTabelaPedidos() {
        carregarPedidosDoClienteSelecionado();
        modeloTabelaPedidos.fireTableDataChanged();
    }
}