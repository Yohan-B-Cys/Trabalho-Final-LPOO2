package sistemapizzaria.telas;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

import sistemapizzaria.SistemaPizzaria;
import sistemapizzaria.cliente.Cliente;

public class TelaClientes extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtPedido;
	private JButton btnCadastrarCliente;
	private JButton btnNewButton_1;
	private JButton btnAtualizarPreos;
	private JButton btnExcluirCliente;
	private JTextField txtNome;
	private JTextField txtTelefone;
	private JTextField txtSobrenome;
	private JButton btnPedidosDoCliente;
	private JButton btnEditarCliente;
	private JButton btnGerenciarPedidos;
	private SistemaPizzaria sistema;
	private JTable tabelaClientes;
	private javax.swing.table.DefaultTableModel modeloTabela;
	private JLabel lblTelefone;
	private boolean modoEdicao = false;
	private JButton btnCancelarEdio;

	/**
	 * Create the frame.
	 */
	public TelaClientes(SistemaPizzaria sistema) {
		this.sistema = sistema;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Gerenciamento de Clientes");
		setMinimumSize(new Dimension(900, 700));
		setLocationRelativeTo(null);
		
		contentPane = new JPanel(new BorderLayout(10, 10));
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		
		// ============================================
		// PAINEL SUPERIOR (Ações Globais, Busca e Cadastro)
		// ============================================
		JPanel pnlNorte = new JPanel();
		pnlNorte.setLayout(new BoxLayout(pnlNorte, BoxLayout.Y_AXIS));
		
		// --- Ações Globais ---
		JPanel pnlAcoesGlobais = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		btnNewButton_1 = new JButton("Adicionar Sabores");
		btnNewButton_1.addActionListener(e -> {
			CadastroSabor telaSabor = new CadastroSabor(sistema);
			telaSabor.setVisible(true);
			telaSabor.setLocationRelativeTo(null);
		});
		
		btnAtualizarPreos = new JButton("Atualizar Preços");
		btnAtualizarPreos.addActionListener(e -> {
			TelaAtualizarPreco telaPreco = new TelaAtualizarPreco(sistema);
			telaPreco.setVisible(true);
			telaPreco.setLocationRelativeTo(null);
		});
		
		btnGerenciarPedidos = new JButton("Gerenciar Pedidos");
		btnGerenciarPedidos.addActionListener(e -> {
			TelaGerenciarPedidos telaPedidos = new TelaGerenciarPedidos(sistema);
			telaPedidos.setVisible(true);
			telaPedidos.setLocationRelativeTo(null);
		});
		
		pnlAcoesGlobais.add(btnNewButton_1);
		pnlAcoesGlobais.add(btnAtualizarPreos);
		pnlAcoesGlobais.add(btnGerenciarPedidos);
		pnlNorte.add(pnlAcoesGlobais);
		
		// --- Busca de Clientes ---
		JPanel pnlBusca = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlBusca.setBorder(BorderFactory.createTitledBorder("Pesquisar Clientes"));
		pnlBusca.add(new JLabel("Pesquisar:"));
		txtPedido = new JTextField(40);
		pnlBusca.add(txtPedido);
		pnlNorte.add(pnlBusca);
		
		txtPedido.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String textoPesquisa = txtPedido.getText();
                java.util.List<Cliente> clientesFiltrados = TelaClientes.this.sistema.buscarClientes(textoPesquisa);
                atualizarTabela(clientesFiltrados);
            }
        });
		
		// --- Formulário de Cadastro ---
		JPanel pnlFormulario = new JPanel(new GridBagLayout());
		pnlFormulario.setBorder(BorderFactory.createTitledBorder("Dados do Cliente"));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		// Linha 1: Labels
		gbc.gridy = 0; gbc.gridx = 0;
		pnlFormulario.add(new JLabel("Nome:"), gbc);
		gbc.gridx = 1;
		pnlFormulario.add(new JLabel("Sobrenome:"), gbc);
		gbc.gridx = 2;
		lblTelefone = new JLabel("Telefone:");
		pnlFormulario.add(lblTelefone, gbc);
		
		// Linha 2: TextFields
		gbc.gridy = 1; gbc.gridx = 0;
		gbc.weightx = 1.0;
		txtNome = new JTextField();
		pnlFormulario.add(txtNome, gbc);
		
		gbc.gridx = 1;
		txtSobrenome = new JTextField();
		pnlFormulario.add(txtSobrenome, gbc);
		
		gbc.gridx = 2;
		txtTelefone = new JTextField();
		txtTelefone.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Character.isDigit(e.getKeyChar())) {
					e.consume();
				}
			}
		});
		pnlFormulario.add(txtTelefone, gbc);
		
		// Linha 3: Botões
		gbc.gridy = 2; gbc.gridx = 0; gbc.gridwidth = 3;
		gbc.weightx = 0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		
		JPanel pnlBotoesForm = new JPanel(new FlowLayout());
		btnCadastrarCliente = new JButton("Cadastrar Cliente");
		btnCancelarEdio = new JButton("Cancelar Edição");
		btnCancelarEdio.setVisible(false);
		pnlBotoesForm.add(btnCadastrarCliente);
		pnlBotoesForm.add(btnCancelarEdio);
		pnlFormulario.add(pnlBotoesForm, gbc);
		
		pnlNorte.add(pnlFormulario);
		contentPane.add(pnlNorte, BorderLayout.NORTH);
		
		// ============================================
		// PAINEL CENTRAL (Tabela)
		// ============================================
		String[] colunas = {"Telefone", "Nome", "Sobrenome"};
        modeloTabela = new javax.swing.table.DefaultTableModel(null, colunas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        tabelaClientes = new JTable(modeloTabela);
        tabelaClientes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tabelaClientes);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Lista de Clientes"));
        contentPane.add(scrollPane, BorderLayout.CENTER);
        
		// ============================================
		// PAINEL INFERIOR (Ações da Tabela)
		// ============================================
        JPanel pnlSul = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        
        btnPedidosDoCliente = new JButton("Pedidos do Cliente");
        btnEditarCliente = new JButton("Editar Cliente");
        btnExcluirCliente = new JButton("Excluir Cliente");
        btnExcluirCliente.setForeground(Color.BLACK);
        btnExcluirCliente.setBackground(Color.RED);
        
        pnlSul.add(btnPedidosDoCliente);
        pnlSul.add(btnEditarCliente);
        pnlSul.add(btnExcluirCliente);
        contentPane.add(pnlSul, BorderLayout.SOUTH);
        
		// ============================================
		// LÓGICA DOS BOTÕES
		// ============================================
        
		btnCadastrarCliente.addActionListener(e -> {
			try {
			    String nomeDigitado = txtNome.getText();
			    String sobrenomeDigitado = txtSobrenome.getText();
			    String numeroDigitado = txtTelefone.getText();
			    
			    if (nomeDigitado.trim().isEmpty()) throw new Exception("O nome do cliente não pode estar vazio!");
			    if (sobrenomeDigitado.trim().isEmpty()) throw new Exception("O sobrenome do cliente não pode estar vazio!");
			    if (numeroDigitado.trim().isEmpty()) throw new Exception("O telefone do cliente não pode estar vazio!");
			    
			    // Validação de formato: apenas dígitos, 10 ou 11 caracteres (DDD + número)
			    String telefoneLimpo = numeroDigitado.replaceAll("[^0-9]", "");
			    if (telefoneLimpo.length() < 10 || telefoneLimpo.length() > 11) {
			        throw new Exception("Telefone inválido! Use o formato: DDD + Número (ex: 11987654321).\nDeve conter 10 ou 11 dígitos numéricos.");
			    }
			    numeroDigitado = telefoneLimpo;
			    
			    Cliente clienteExistente = TelaClientes.this.sistema.buscarClientePorTelefone(numeroDigitado);
			    
			    if (modoEdicao) {
                    if (clienteExistente != null) {
                        clienteExistente.setNome(nomeDigitado);
                        clienteExistente.setSobrenome(sobrenomeDigitado);
                        JOptionPane.showMessageDialog(null, "Informações do cliente '" + nomeDigitado + "' atualizadas com sucesso!");
                    }
                } else {
                    if (clienteExistente != null) {
                        throw new Exception("Erro: Já existe um cliente cadastrado com o telefone " + numeroDigitado + "!");
                    }
                    Cliente novoCliente = new Cliente(nomeDigitado, sobrenomeDigitado, numeroDigitado);
                    TelaClientes.this.sistema.cadastrarCliente(novoCliente);
                    JOptionPane.showMessageDialog(null, "Cliente '" + nomeDigitado + "' cadastrado com sucesso!");
                }
			    
			    txtNome.setText("");
			    txtSobrenome.setText("");
			    txtTelefone.setText("");
			    
			    modoEdicao = false;
			    btnCadastrarCliente.setText("Cadastrar Cliente");
		        txtTelefone.setEditable(true); 
		        btnCancelarEdio.setVisible(false);
			    atualizarTabela();
			    
			} catch (Exception e2) {
			    JOptionPane.showMessageDialog(null, e2.getMessage(), "Aviso", JOptionPane.WARNING_MESSAGE);
			}
		});
		
		btnCancelarEdio.addActionListener(e -> {
			modoEdicao = false;
			txtTelefone.setEditable(true);
			txtNome.setText("");
			txtSobrenome.setText("");
			txtTelefone.setText("");
			btnCadastrarCliente.setText("Cadastrar Cliente"); 
			btnCancelarEdio.setVisible(false);
		});
		
		btnEditarCliente.addActionListener(e -> {
			int linhaSelecionada = tabelaClientes.getSelectedRow();
	        if (linhaSelecionada == -1) {
	            JOptionPane.showMessageDialog(null, "Por favor, selecione um cliente na tabela para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
	            return;
	        } 
	        String telefone = modeloTabela.getValueAt(linhaSelecionada, 0).toString();
	        String nome = modeloTabela.getValueAt(linhaSelecionada, 1).toString();
	        Object objSobrenome = modeloTabela.getValueAt(linhaSelecionada, 2);
	        String sobrenome = (objSobrenome != null) ? objSobrenome.toString() : "";
	        
	        txtTelefone.setText(telefone);
	        txtNome.setText(nome);
	        txtSobrenome.setText(sobrenome);
	        
	        modoEdicao = true;
	        btnCancelarEdio.setVisible(true);
	        txtTelefone.setEditable(false);
	        btnCadastrarCliente.setText("Atualizar Informações");
		});
		
		btnExcluirCliente.addActionListener(e -> {
			int linhaSelecionada = tabelaClientes.getSelectedRow();
			if (modoEdicao) {
                JOptionPane.showMessageDialog(null, "Você não pode excluir clientes enquanto o modo de edição estiver ativo. Termine ou cancele a edição.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
			if (linhaSelecionada == -1) {
				JOptionPane.showMessageDialog(null, "Por favor, selecione um cliente na tabela para Excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
				return;
			} 
			String telefone = modeloTabela.getValueAt(linhaSelecionada, 0).toString();
			String nome = modeloTabela.getValueAt(linhaSelecionada, 1).toString();
			Cliente clienteParaExcluir = TelaClientes.this.sistema.buscarClientePorTelefone(telefone);
			TelaClientes.this.sistema.removerCliente(clienteParaExcluir);
			
			JOptionPane.showMessageDialog(null, "Cliente '" + nome + "' excluido com sucesso!");
			atualizarTabela();
		});
		
		btnPedidosDoCliente.addActionListener(e -> {
		    int linha = tabelaClientes.getSelectedRow();
		    if (linha == -1) {
		        JOptionPane.showMessageDialog(null, "Selecione um cliente.");
		        return;
		    }
		    String telefone = modeloTabela.getValueAt(linha, 0).toString();
		    TelaPedidos tela = new TelaPedidos(TelaClientes.this.sistema, TelaClientes.this);
		    tela.setVisible(true);
		    tela.buscarClienteAutomaticamente(telefone);
		});

        atualizarTabela(); // Preenche a tabela ao abrir a tela
        pack(); // Ajusta o tamanho da janela automaticamente ao conteúdo e layouts responsivos
	}
	
	private void atualizarTabela(java.util.List<Cliente> listaClientes) {
        modeloTabela.setRowCount(0); 
        if (listaClientes != null) {
            for (Cliente c : listaClientes) {
                modeloTabela.addRow(new Object[]{
                    c.getTelefone(),
                    c.getNome(),
                    c.getSobrenome()
                });
            }
        }
    }

    public void atualizarTabela() {
        if (sistema != null) {
            atualizarTabela(sistema.getClientes());
        }
    }
}
