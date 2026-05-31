package sistemapizzaria.telas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;

public class CadastroSabor extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadastroSabor frame = new CadastroSabor();
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
	public CadastroSabor() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 300);
		setTitle("Cadastro de Sabor");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(168, 86, 304, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNomeDoSabor = new JLabel("Nome do Sabor: ");
		lblNomeDoSabor.setBounds(66, 88, 97, 17);
		contentPane.add(lblNomeDoSabor);
		
		JLabel lblTipoDaPizza = new JLabel("Tipo da pizza:");
		lblTipoDaPizza.setBounds(76, 147, 97, 17);
		contentPane.add(lblTipoDaPizza);
		
		JButton btnCadastrarSabor = new JButton("Cadastrar Sabor");
		btnCadastrarSabor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCadastrarSabor.setBounds(194, 189, 214, 27);
		contentPane.add(btnCadastrarSabor);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("Pizza Circular");
		chckbxNewCheckBox.setBounds(178, 143, 114, 25);
		contentPane.add(chckbxNewCheckBox);
		
		JCheckBox chckbxNewCheckBox_1 = new JCheckBox("Pizza Quadrada");
		chckbxNewCheckBox_1.setBounds(294, 143, 127, 25);
		contentPane.add(chckbxNewCheckBox_1);
		
		JCheckBox chckbxNewCheckBox_2 = new JCheckBox("Pizza Triangular\n");
		chckbxNewCheckBox_2.setBounds(420, 143, 138, 25);
		contentPane.add(chckbxNewCheckBox_2);
		
		JLabel lblCategoria = new JLabel("Categoria:");
		lblCategoria.setBounds(94, 118, 69, 17);
		contentPane.add(lblCategoria);
		
		JCheckBox chckbxTradicional = new JCheckBox("Simples");
		chckbxTradicional.setBounds(178, 114, 114, 25);
		contentPane.add(chckbxTradicional);
		
		JCheckBox chckbxNewCheckBox_1_1 = new JCheckBox("Especial");
		chckbxNewCheckBox_1_1.setBounds(294, 114, 127, 25);
		contentPane.add(chckbxNewCheckBox_1_1);
		
		JCheckBox chckbxNewCheckBox_2_1 = new JCheckBox("Premium\n");
		chckbxNewCheckBox_2_1.setBounds(420, 114, 138, 25);
		contentPane.add(chckbxNewCheckBox_2_1);

	}
}
