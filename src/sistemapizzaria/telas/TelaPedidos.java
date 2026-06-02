package sistemapizzaria.telas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import sistemapizzaria.SistemaPizzaria;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;

public class TelaPedidos extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private SistemaPizzaria sistema;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaPedidos frame = new TelaPedidos();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaPedidos() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 900);
		setTitle("Pedidos");
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(276, 51, 164, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblCliente = new JLabel("Telefone do \nCliente : ");
		lblCliente.setBounds(143, 52, 126, 19);
		contentPane.add(lblCliente);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(452, 48, 105, 27);
		contentPane.add(btnBuscar);
		
		JLabel lblClienteNomeDo = new JLabel("Cliente: Nome do Cliente");
		lblClienteNomeDo.setBounds(75, 140, 229, 17);
		contentPane.add(lblClienteNomeDo);
		
		JLabel lblPedido = new JLabel("|  Pedido id: 12312323 |  itens |  preço  |");
		lblPedido.setBounds(75, 201, 317, 17);
		contentPane.add(lblPedido);
		
		JButton btnAdicionarPedido = new JButton("Adicionar Pedido");
		btnAdicionarPedido.setBounds(507, 243, 147, 27);
		contentPane.add(btnAdicionarPedido);
		
		JButton btnExcluirPedido = new JButton("Excluir Pedido");
		btnExcluirPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnExcluirPedido.setForeground(Color.BLACK);
		btnExcluirPedido.setBounds(new Rectangle(62, 31, 10, 10));
		btnExcluirPedido.setBackground(Color.RED);
		btnExcluirPedido.setBounds(507, 196, 138, 27);
		contentPane.add(btnExcluirPedido);
		
		JButton btnEditarPedido = new JButton("Editar Pedido");
		btnEditarPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnEditarPedido.setBounds(348, 196, 147, 27);
		contentPane.add(btnEditarPedido);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(180, 320, 199, 26);
		contentPane.add(comboBox);
		
		JLabel lblTipoDaPizza = new JLabel("Tipo da Pizza :");
		lblTipoDaPizza.setBounds(75, 325, 97, 17);
		contentPane.add(lblTipoDaPizza);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Definir tamanho por área");
		rdbtnNewRadioButton.setBounds(323, 374, 188, 25);
		contentPane.add(rdbtnNewRadioButton);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("Definir tamanho por raio/lado");
		rdbtnNewRadioButton_1.setBounds(75, 374, 219, 25);
		contentPane.add(rdbtnNewRadioButton_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(241, 417, 199, 21);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblSabores = new JLabel("Sabores :");
		lblSabores.setBounds(75, 497, 60, 17);
		contentPane.add(lblSabores);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(142, 492, 97, 26);
		contentPane.add(comboBox_1);
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setBounds(263, 492, 97, 26);
		contentPane.add(comboBox_2);
		
		JButton btnNewButton = new JButton("Adicionar pizza");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(75, 588, 164, 27);
		contentPane.add(btnNewButton);
		
		JLabel lblDefinirTamanho = new JLabel("Definir lado /raio/ area :");
		lblDefinirTamanho.setBounds(61, 419, 162, 17);
		contentPane.add(lblDefinirTamanho);
		
		JLabel lblCarrinho = new JLabel("Carrinho");
		lblCarrinho.setBounds(122, 678, 60, 17);
		contentPane.add(lblCarrinho);
		
		JLabel lblPre = new JLabel("Preço : $0,00");
		lblPre.setBounds(72, 544, 100, 17);
		contentPane.add(lblPre);
		
		JLabel lblTipo = new JLabel("| Tipo  | Medida | Sabores | Preço ");
		lblTipo.setBounds(75, 710, 219, 17);
		contentPane.add(lblTipo);
		
		JButton btnEditarPizza = new JButton("Editar Pizza");
		btnEditarPizza.setBounds(295, 705, 105, 27);
		contentPane.add(btnEditarPizza);
		
		JButton btnExcluirPizza = new JButton("Excluir Pizza");
		btnExcluirPizza.setForeground(Color.BLACK);
		btnExcluirPizza.setBounds(new Rectangle(62, 31, 10, 10));
		btnExcluirPizza.setBackground(Color.RED);
		btnExcluirPizza.setBounds(415, 706, 138, 27);
		contentPane.add(btnExcluirPizza);
		
		JLabel lblPreoTotal = new JLabel("Preço Total :  $0,00");
		lblPreoTotal.setBounds(75, 764, 176, 17);
		contentPane.add(lblPreoTotal);
		
		JButton btnNewButton_1 = new JButton("Adicionar pizza");
		btnNewButton_1.setBounds(294, 744, 164, 27);
		contentPane.add(btnNewButton_1);
		
		JButton btnFinalizarSalvar = new JButton("Finalizar / Salvar alterações ");
		btnFinalizarSalvar.setBounds(75, 839, 268, 27);
		contentPane.add(btnFinalizarSalvar);
		
		JLabel lblTamanhoLadodiametro = new JLabel("Tamanho Lado/Diametro : 40 cm ");
		lblTamanhoLadodiametro.setBounds(75, 450, 219, 17);
		contentPane.add(lblTamanhoLadodiametro);
		
		JLabel lblrea = new JLabel("Àrea : 50 cms");
		lblrea.setBounds(276, 450, 103, 17);
		contentPane.add(lblrea);

	}
}
