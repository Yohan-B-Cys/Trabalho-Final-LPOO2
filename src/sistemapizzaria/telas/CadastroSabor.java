package sistemapizzaria.telas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import sistemapizzaria.SistemaPizzaria;
import sistemapizzaria.pizza.CategoriaTipo;
import sistemapizzaria.pizza.Sabor;
import sistemapizzaria.pizza.TipoPizza;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class CadastroSabor extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private SistemaPizzaria sistema;

	/**
	 * Launch the application.
	 */


	/**
	 * Create the frame.
	 */
	public CadastroSabor(SistemaPizzaria sistema) {
		this.sistema = sistema;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
		
	
		
		JLabel lblCategoria = new JLabel("Categoria:");
		lblCategoria.setBounds(94, 118, 69, 17);
		contentPane.add(lblCategoria);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Simples\r\n");
		buttonGroup.add(rdbtnNewRadioButton);
		rdbtnNewRadioButton.setBounds(168, 115, 109, 23);
		contentPane.add(rdbtnNewRadioButton);
		
		JRadioButton rdbtnEspecial = new JRadioButton("Especial\r\n");
		buttonGroup.add(rdbtnEspecial);
		rdbtnEspecial.setBounds(280, 115, 109, 23);
		contentPane.add(rdbtnEspecial);
		
		JRadioButton rdbtnPremiun = new JRadioButton("Premium");
		buttonGroup.add(rdbtnPremiun);
		rdbtnPremiun.setBounds(391, 115, 109, 23);
		contentPane.add(rdbtnPremiun);
		
		JButton btnCadastrarSabor = new JButton("Cadastrar Sabor");
		btnCadastrarSabor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					String nome = textField.getText();
					CategoriaTipo tipo ;
					
					
					if (nome.trim().isEmpty()) {
				        throw new Exception("O nome do sabor não pode estar vazio!");
				    }
					
					if (rdbtnNewRadioButton.isSelected()) {
						tipo = CategoriaTipo.SIMPLES;
					} else if (rdbtnEspecial.isSelected()) {
						tipo = CategoriaTipo.ESPECIAL;
					} else if (rdbtnPremiun.isSelected()) {
						tipo = CategoriaTipo.PREMIUM;
					} else {
						throw new Exception("Escolher categoria de Sabor é obrigatorio");
					}
				
					
					TipoPizza objetoTipo = null;
					
					for (TipoPizza tp : sistema.getTabelaPrecos()) {
						if (tp.getCategoria() == tipo) {
							objetoTipo = tp ;
							break;
						}
					}
					
					Sabor saborCadastro = new Sabor(nome, objetoTipo);
					
					
					sistema.cadastrarSabor(saborCadastro);
					
					javax.swing.JOptionPane.showMessageDialog(null, "Sabor '" + nome + "' cadastrado com sucesso!");
					textField.setText("");

					
				} catch (Exception e2) {
					javax.swing.JOptionPane.showMessageDialog(null, e2.getMessage(), "Aviso", javax.swing.JOptionPane.WARNING_MESSAGE);
				}
				
			}
		});
		btnCadastrarSabor.setBounds(194, 189, 214, 27);
		contentPane.add(btnCadastrarSabor);

	}
}
