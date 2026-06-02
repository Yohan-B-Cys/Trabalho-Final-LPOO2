package sistemapizzaria.telas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Rectangle;
import java.awt.Color;

import sistemapizzaria.Aplicacao;
import sistemapizzaria.SistemaPizzaria;

public class TelaClientes extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtPedido;
	private JButton btnNewButton;
	private JButton btnCadastrarCliente;
	private JButton btnNewButton_1;
	private JButton btnAtualizarPreos;
	private JButton btnExcluirPedido;
	private JLabel lblPedidoCliente;
	private JTextField txtNome;
	private JTextField txtEndereo;
	private JTextField txtTelefone;
	private JTextField txtSobrenome;
	private JButton btnPedidosDoCliente;
	private JLabel lblTelefoneNome;
	private JButton btnEditarCliente;
	private JButton btnGerenciarPedidos;
	private SistemaPizzaria sistema;

	/**
	 * Create the frame.
	 */
	public TelaClientes(SistemaPizzaria sistema) {
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
		txtPedido.setBounds(186, 106, 420, 19);
		contentPane.add(txtPedido);
		txtPedido.setColumns(10);
		
		btnNewButton = new JButton("Novo Pedido");
		btnNewButton.setBounds(629, 102, 145, 27);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaPedidos telaPedidos = new TelaPedidos();
				telaPedidos.setVisible(true);
				telaPedidos.setLocationRelativeTo(null);
			}
		});
		contentPane.add(btnNewButton);
		
		btnCadastrarCliente = new JButton("Cadastrar Cliente / Atualizar Informações");
		btnCadastrarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCadastrarCliente.setBounds(629, 172, 310, 19);
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
		
		btnExcluirPedido = new JButton("Excluir Cliente\n");
		btnExcluirPedido.setForeground(new Color(0, 0, 0));
		btnExcluirPedido.setBackground(Color.RED);
		btnExcluirPedido.setBounds(new Rectangle(62, 31, 10, 10));
		btnExcluirPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnExcluirPedido.setBounds(767, 305, 138, 27);
		contentPane.add(btnExcluirPedido);
		
		lblPedidoCliente = new JLabel("6769410 | Ciclano| Beltrano | : rua dos bruxao loco 92| ");
		lblPedidoCliente.setBounds(98, 303, 337, 31);
		contentPane.add(lblPedidoCliente);
		
		txtNome = new JTextField();
		txtNome.setText("Nome");
		txtNome.setBounds(98, 171, 114, 21);
		contentPane.add(txtNome);
		txtNome.setColumns(10);
		
		txtEndereo = new JTextField();
		txtEndereo.setText("endereço");
		txtEndereo.setBounds(350, 171, 114, 21);
		contentPane.add(txtEndereo);
		txtEndereo.setColumns(10);
		
		txtTelefone = new JTextField();
		txtTelefone.setText("telefone");
		txtTelefone.setBounds(492, 171, 114, 21);
		contentPane.add(txtTelefone);
		txtTelefone.setColumns(10);
		
		txtSobrenome = new JTextField();
		txtSobrenome.setText("Sobrenome");
		txtSobrenome.setColumns(10);
		txtSobrenome.setBounds(224, 171, 114, 21);
		contentPane.add(txtSobrenome);
		
		btnPedidosDoCliente = new JButton("Pedidos do cliente");
		btnPedidosDoCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				TelaPedidos telaPedidos2 = new TelaPedidos();
				telaPedidos2.setVisible(true);
				telaPedidos2.setLocationRelativeTo(null);
			}
		});
		btnPedidosDoCliente.setBounds(453, 303, 145, 27);
		contentPane.add(btnPedidosDoCliente);
		
		lblTelefoneNome = new JLabel("Telefone | Nome  |Sobrenome   | Endereço|");
		lblTelefoneNome.setBounds(98, 250, 472, 36);
		contentPane.add(lblTelefoneNome);
		
		btnEditarCliente = new JButton("Editar Cliente");
		btnEditarCliente.setBounds(608, 305, 145, 27);
		contentPane.add(btnEditarCliente);
		
		btnGerenciarPedidos = new JButton("Gerenciar Pedidos\n");
		btnGerenciarPedidos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				TelaGerenciarPedidos telaPedidos = new TelaGerenciarPedidos();
				telaPedidos.setVisible(true);
				telaPedidos.setLocationRelativeTo(null);
			}
		});
		btnGerenciarPedidos.setBounds(625, 24, 164, 27);
		contentPane.add(btnGerenciarPedidos);

	}
	
	
	
}
