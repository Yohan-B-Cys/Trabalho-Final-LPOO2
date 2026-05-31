package telas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class TelaAtualizarPreco extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaAtualizarPreco frame = new TelaAtualizarPreco();
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
	public TelaAtualizarPreco() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 300);
		contentPane = new JPanel();
		setTitle("Atualizar Preços");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setText("$0,50");
		textField.setBounds(168, 77, 57, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Preço Sabor Simples :");
		lblNewLabel_1.setBounds(22, 79, 128, 17);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblCms = new JLabel("cms2");
		lblCms.setBounds(233, 79, 60, 17);
		contentPane.add(lblCms);
		
		textField_1 = new JTextField();
		textField_1.setText("$1,25");
		textField_1.setColumns(10);
		textField_1.setBounds(168, 120, 57, 21);
		contentPane.add(textField_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Preço Sabor Especial:");
		lblNewLabel_1_1.setBounds(22, 122, 138, 17);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblCms_1 = new JLabel("cms2");
		lblCms_1.setBounds(233, 122, 60, 17);
		contentPane.add(lblCms_1);
		
		textField_2 = new JTextField();
		textField_2.setText("$2,00");
		textField_2.setColumns(10);
		textField_2.setBounds(168, 170, 57, 21);
		contentPane.add(textField_2);
		
		JLabel lblNewLabel_1_2 = new JLabel("Preço Sabor Premium :");
		lblNewLabel_1_2.setBounds(22, 172, 138, 17);
		contentPane.add(lblNewLabel_1_2);
		
		JLabel lblCms_2 = new JLabel("cms2");
		lblCms_2.setBounds(233, 172, 60, 17);
		contentPane.add(lblCms_2);
		
		JButton btnAtualizarValores = new JButton("Atualizar Valores");
		btnAtualizarValores.setBounds(53, 223, 155, 27);
		contentPane.add(btnAtualizarValores);

	}

}
