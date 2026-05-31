package sistemapizzaria.telas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JTable;

public class TelaGerenciarPedidos extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaGerenciarPedidos frame = new TelaGerenciarPedidos();
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
	public TelaGerenciarPedidos() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1100, 1000);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Filtar por Status :");
		lblNewLabel.setBounds(70, 100, 116, 17);
		contentPane.add(lblNewLabel);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(195, 95, 79, 26);
		contentPane.add(comboBox);
		
		JLabel lblNewLabel_1 = new JLabel("| ID_Pedido | Cliente | Telefone | total R$  | Status |                            Itens do Pedido                                       |");
		lblNewLabel_1.setBounds(70, 194, 624, 17);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("| 12345 | Rodofo | 41 99380123 | 80 | Aberto |  1 Pizza Circular 20cms R  Sabores : Quatro Queijos , null |");
		lblNewLabel_1_1.setBounds(70, 235, 650, 17);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblAlterarStatusPara = new JLabel("Alterar Status para :");
		lblAlterarStatusPara.setBounds(712, 235, 136, 17);
		contentPane.add(lblAlterarStatusPara);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(836, 235, 85, 17);
		contentPane.add(comboBox_1);
		
		JButton btnNewButton = new JButton("Atualizar Pedido");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(939, 230, 136, 27);
		contentPane.add(btnNewButton);
		
		JLabel lblPesquisarPedidos = new JLabel("Pesquisar Pedidos : ");
		lblPesquisarPedidos.setBounds(70, 147, 125, 17);
		contentPane.add(lblPesquisarPedidos);
		
		textField = new JTextField();
		textField.setBounds(194, 145, 179, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		table = new JTable();
		table.setBounds(281, 372, 0, 26);
		contentPane.add(table);

	}
}
