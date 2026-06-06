package sistemapizzaria.telas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.Rectangle;
import java.awt.Color;

import sistemapizzaria.Aplicacao;
import sistemapizzaria.SistemaPizzaria;
import sistemapizzaria.cliente.Cliente;
import javax.swing.JTable;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

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

	/**
	 * Create the frame.
	 */
	public TelaClientes(SistemaPizzaria sistema) {
		this.sistema= sistema ;
		
		setBounds(new Rectangle(1000, 1000, 1000, 1000));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPesquisarPedidos = new JLabel("Pesquisar  Clientes");
		lblPesquisarPedidos.setBounds(59, 107, 174, 17);
		contentPane.add(lblPesquisarPedidos);
		
		txtPedido = new JTextField();
		txtPedido.setBounds(186, 106, 603, 19);
		contentPane.add(txtPedido);
		txtPedido.setColumns(10);
		
		// Supondo que seu campo ainda se chame txtPedido
        txtPedido.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                // 1. Pega o que o usuário digitou até o momento
                String textoPesquisa = txtPedido.getText();
                
                // 2. Usa o método que você já criou no SistemaPizzaria para filtrar
                java.util.List<Cliente> clientesFiltrados = sistema.buscarClientes(textoPesquisa);
                
                // 3. Manda a tabela se atualizar só com os filtrados
                atualizarTabela(clientesFiltrados);
            }
        });
		
		btnCadastrarCliente = new JButton( modoEdicao ?"Atualizar Informações" :"Cadastrar Cliente");
		btnCadastrarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
				    String nomeDigitado = txtNome.getText();
				    String sobrenomeDigitado = txtSobrenome.getText();
				   
				    String numeroDigitado = txtTelefone.getText();
				    
				    if (nomeDigitado.trim().isEmpty()) {
				        throw new Exception("O nome do cliente não pode estar vazio!");
				    }
				    
				    if (sobrenomeDigitado.trim().isEmpty()) {
				        throw new Exception("O nome do cliente não pode estar vazio!");
				    }
				  
				    if (numeroDigitado.trim().isEmpty()) {
				        throw new Exception("O telefone do cliente não pode estar vazio!");
				    }
				    
				    Cliente clienteExistente = sistema.buscarClientePorTelefone(numeroDigitado);
				    
				    if (modoEdicao) {
                        // Se estamos no MODO EDIÇÃO, nós atualizamos o cliente
                        if (clienteExistente != null) {
                            clienteExistente.setNome(nomeDigitado);
                            clienteExistente.setSobrenome(sobrenomeDigitado);
                            
                            javax.swing.JOptionPane.showMessageDialog(null, "Informações do cliente '" + nomeDigitado + "' atualizadas com sucesso!");
                        }
                    } else {
                        // Se estamos no MODO CADASTRO, não podemos deixar sobrescrever!
                        if (clienteExistente != null) {
                            // Joga um erro para a tela e interrompe o cadastro
                            throw new Exception("Erro: Já existe um cliente cadastrado com o telefone " + numeroDigitado + "!");
                        }
                        
                        // Se passou pelo erro acima, significa que o telefone é novo, então cria o cliente
                        Cliente novoCliente = new Cliente(nomeDigitado, sobrenomeDigitado, numeroDigitado);
                        sistema.cadastrarCliente(novoCliente);
                        
                        javax.swing.JOptionPane.showMessageDialog(null, "Cliente '" + nomeDigitado + "' cadastrado com sucesso!");
                    }
				    
				    // Limpa os campos do formulário após a operação
				    txtNome.setText("");
				    txtSobrenome.setText("");
				    txtTelefone.setText("");
				    
				    modoEdicao = false;
				    btnCadastrarCliente.setText(modoEdicao ? "Atualizar Informações" : "Cadastrar Cliente");
			        
			        // Libera o campo de telefone para novos cadastros
			        txtTelefone.setEditable(true); 
			        
			    
			      
				    
				    // Atualiza a tabela para mostrar as modificações na hora
				    atualizarTabela();
				    
				} catch (Exception e2) {
				    javax.swing.JOptionPane.showMessageDialog(null, e2.getMessage(), "Aviso", javax.swing.JOptionPane.WARNING_MESSAGE);
				}
				
			}
		});
		
	

		
		btnCadastrarCliente.setBounds(261, 213, 310, 19);
		contentPane.add(btnCadastrarCliente);
		
		btnNewButton_1 = new JButton("Adicionar Sabores");
		btnNewButton_1.setBounds(236, 24, 186, 27);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CadastroSabor telaSabor = new CadastroSabor(sistema);
				
				telaSabor.setVisible(true);
				telaSabor.setLocationRelativeTo(null);
			}
		});
		contentPane.add(btnNewButton_1);
		
		btnAtualizarPreos = new JButton("Atualizar preços");
		btnAtualizarPreos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaAtualizarPreco telaPreco = new TelaAtualizarPreco(sistema);
				telaPreco.setVisible(true);
				telaPreco.setLocationRelativeTo(null);
			}
		});
		btnAtualizarPreos.setBounds(442, 24, 164, 27);
		contentPane.add(btnAtualizarPreos);
		
		btnExcluirCliente = new JButton("Excluir Cliente\n");
		btnExcluirCliente.setForeground(new Color(0, 0, 0));
		btnExcluirCliente.setBackground(Color.RED);
		btnExcluirCliente.setBounds(new Rectangle(62, 31, 10, 10));
		btnExcluirCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int linhaSelecionada = tabelaClientes.getSelectedRow();
				
				// 1. verifica se o a janela de edição nao foi selecionada
				if (modoEdicao) {
                    javax.swing.JOptionPane.showMessageDialog(null, 
                        "Você não pode excluir clientes enquanto o modo de edição estiver ativo. Termine ou cancele a edição .", 
                        "Aviso", javax.swing.JOptionPane.WARNING_MESSAGE);
                    return; // Interrompe a execução do botão
                }
				
				// 2. Valida se o usuário realmente clicou em alguma linha
				if (linhaSelecionada == -1) {
					javax.swing.JOptionPane.showMessageDialog(null,
							"Por favor, selecione um cliente na tabela para Excluir.", "Aviso",
							javax.swing.JOptionPane.WARNING_MESSAGE);
					return;
				} 
				String telefone = modeloTabela.getValueAt(linhaSelecionada, 0).toString();
				String nome = modeloTabela.getValueAt(linhaSelecionada, 1).toString();
				
				Cliente clienteParaExcluir = sistema.buscarClientePorTelefone(telefone);
				
				sistema.removerCliente(clienteParaExcluir);
				
				javax.swing.JOptionPane.showMessageDialog(null, "Cliente '" + nome + "' excluido com sucesso!");
				atualizarTabela();
				
				
				
			}
		});
		btnExcluirCliente.setBounds(575, 280, 138, 27);
		contentPane.add(btnExcluirCliente);
		
		txtNome = new JTextField();
		txtNome.setBounds(217, 169, 151, 21);
		contentPane.add(txtNome);
		txtNome.setColumns(10);
		
		txtTelefone = new JTextField();
		txtTelefone.setBounds(689, 169, 114, 21);
		contentPane.add(txtTelefone);
		txtTelefone.setColumns(10); 
		
		KeyAdapter garanteNumero = new KeyAdapter() {

			@Override
			public void keyTyped(KeyEvent e) {
				char caractere = e.getKeyChar();

				if (!Character.isDigit(caractere)) {
					e.consume();
				}
			}
		};
		txtTelefone.addKeyListener(garanteNumero);
		
		txtSobrenome = new JTextField();
		txtSobrenome.setColumns(10);
		txtSobrenome.setBounds(460, 169, 151, 21);
		contentPane.add(txtSobrenome);
		
		btnPedidosDoCliente = new JButton("Pedidos do cliente");
		btnPedidosDoCliente.addActionListener(e -> {

		    int linha = tabelaClientes.getSelectedRow();

		    if (linha == -1) {
		        JOptionPane.showMessageDialog(null, "Selecione um cliente.");
		        return;
		    }

		    String telefone = modeloTabela.getValueAt(linha, 0).toString();

		    TelaPedidos tela = new TelaPedidos(sistema);

		    tela.setVisible(true);

		    // ✅ PASSA O CLIENTE
		    tela.preencherCliente(telefone);
		});

		btnPedidosDoCliente.setBounds(261, 280, 145, 27);
		contentPane.add(btnPedidosDoCliente);
		
		
		
		btnGerenciarPedidos = new JButton("Gerenciar Pedidos\n");
		btnGerenciarPedidos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				TelaGerenciarPedidos telaPedidos = new TelaGerenciarPedidos(sistema);
				telaPedidos.setVisible(true);
				telaPedidos.setLocationRelativeTo(null);
			}
		});
		btnGerenciarPedidos.setBounds(625, 24, 164, 27);
		contentPane.add(btnGerenciarPedidos);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(59, 319, 857, 583);
		contentPane.add(scrollPane);
		
		tabelaClientes = new JTable();
		scrollPane.setViewportView(tabelaClientes);
		
		String[] colunas = {"Telefone", "Nome", "Sobrenome"};
        modeloTabela = new javax.swing.table.DefaultTableModel(null, colunas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        tabelaClientes.setModel(modeloTabela);
        tabelaClientes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        
        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(163, 171, 45, 17);
        contentPane.add(lblNome);
        
        JLabel lblSobrenome = new JLabel("Sobrenome:");
        lblSobrenome.setBounds(380, 171, 71, 17);
        contentPane.add(lblSobrenome);
        
        lblTelefone = new JLabel("Telefone:");
        lblTelefone.setBounds(623, 171, 60, 17);
        contentPane.add(lblTelefone);
        atualizarTabela(); // Preenche a tabela ao abrir a tela
        
    	JButton btnCancelarEdio = new JButton("Cancelar Edição");
		btnCancelarEdio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				modoEdicao = false ;
				txtTelefone.setEditable(true);
				txtNome.setText("");
				txtSobrenome.setText("");
				txtTelefone.setText("");
				
				btnCadastrarCliente.setText("Cadastrar Cliente"); 
				btnCancelarEdio.setVisible(false);
				
			}
		});
		btnCancelarEdio.setBounds(584, 214, 164, 17);
		btnCancelarEdio.setVisible(false);
		contentPane.add(btnCancelarEdio);
        
        btnEditarCliente = new JButton("Editar Cliente");
		btnEditarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int linhaSelecionada = tabelaClientes.getSelectedRow();
		        
		        // 2. Valida se o usuário realmente clicou em alguma linha
		        if (linhaSelecionada == -1) {
		            javax.swing.JOptionPane.showMessageDialog(null, 
		                "Por favor, selecione um cliente na tabela para editar.", 
		                "Aviso", javax.swing.JOptionPane.WARNING_MESSAGE);
		            return;
		        } 
		        
		        // 3. Pega os dados diretamente das colunas da linha selecionada
		        String telefone = modeloTabela.getValueAt(linhaSelecionada, 0).toString();
		        String nome = modeloTabela.getValueAt(linhaSelecionada, 1).toString();
		        
		        Object objSobrenome = modeloTabela.getValueAt(linhaSelecionada, 2);
		        String sobrenome = (objSobrenome != null) ? objSobrenome.toString() : "";
		      
		        
		        // 4. Preenche os campos de texto do formulário com esses dados
		        txtTelefone.setText(telefone);
		        txtNome.setText(nome);
		        txtSobrenome.setText(sobrenome);
		     
		        
		        modoEdicao = true ;
		      
		        btnCancelarEdio.setVisible(true);
		        
		        txtTelefone.setEditable(false);
		        btnCadastrarCliente.setText(modoEdicao ? "Atualizar Informações" : "Cadastrar Cliente");
		    }
				
		});
		btnEditarCliente.setBounds(418, 280, 145, 27);
		contentPane.add(btnEditarCliente);
		
	

	}
	
	private void atualizarTabela(java.util.List<Cliente> listaClientes) {
        modeloTabela.setRowCount(0); 
        
        if (listaClientes != null) {
            for (Cliente c : listaClientes) {
                modeloTabela.addRow(new Object[]{
                    c.getTelefone(),
                    c.getNome(),
                    c.getSobrenome(),
                  //  c.getEndereco() 
                });
            }
        }
    }

    // Esse método atualiza pegando TODOS os clientes (usado ao abrir a tela)
    private void atualizarTabela() {
        if (sistema != null) {
            atualizarTabela(sistema.getClientes());
        }
    }
}
