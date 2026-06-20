package sistemapizzaria.telas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;

import sistemapizzaria.SistemaPizzaria;
import sistemapizzaria.pizza.CategoriaTipo;
import sistemapizzaria.pizza.Sabor;
import sistemapizzaria.pizza.TipoPizza;

public class CadastroSabor extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private SistemaPizzaria sistema;

	/**
	 * Create the frame.
	 */
	public CadastroSabor(SistemaPizzaria sistema) {
		this.sistema = sistema;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Cadastro de Sabor");
		setMinimumSize(new Dimension(450, 250));
		setLocationRelativeTo(null);
		
		contentPane = new JPanel(new BorderLayout());
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		
		JPanel pnlForm = new JPanel(new GridBagLayout());
		pnlForm.setBorder(BorderFactory.createTitledBorder("Novo Sabor"));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.anchor = GridBagConstraints.WEST;
		
		// Linha 0: Nome
		gbc.gridx = 0; gbc.gridy = 0;
		pnlForm.add(new JLabel("Nome do Sabor:"), gbc);
		
		textField = new JTextField(20);
		gbc.gridx = 1; gbc.gridwidth = 3;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		pnlForm.add(textField, gbc);
		
		// Linha 1: Categoria
		gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.NONE;
		pnlForm.add(new JLabel("Categoria:"), gbc);
		
		JRadioButton rdbtnSimples = new JRadioButton("Simples");
		buttonGroup.add(rdbtnSimples);
		gbc.gridx = 1;
		pnlForm.add(rdbtnSimples, gbc);
		
		JRadioButton rdbtnEspecial = new JRadioButton("Especial");
		buttonGroup.add(rdbtnEspecial);
		gbc.gridx = 2;
		pnlForm.add(rdbtnEspecial, gbc);
		
		JRadioButton rdbtnPremium = new JRadioButton("Premium");
		buttonGroup.add(rdbtnPremium);
		gbc.gridx = 3;
		pnlForm.add(rdbtnPremium, gbc);
		
		contentPane.add(pnlForm, BorderLayout.CENTER);
		
		// Botão Cadastrar
		JPanel pnlBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JButton btnCadastrarSabor = new JButton("Cadastrar Sabor");
		pnlBotoes.add(btnCadastrarSabor);
		contentPane.add(pnlBotoes, BorderLayout.SOUTH);
		
		btnCadastrarSabor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String nome = textField.getText();
					CategoriaTipo tipo;
					
					if (nome.trim().isEmpty()) {
				        throw new Exception("O nome do sabor não pode estar vazio!");
				    }
					
					if (rdbtnSimples.isSelected()) {
						tipo = CategoriaTipo.SIMPLES;
					} else if (rdbtnEspecial.isSelected()) {
						tipo = CategoriaTipo.ESPECIAL;
					} else if (rdbtnPremium.isSelected()) {
						tipo = CategoriaTipo.PREMIUM;
					} else {
						throw new Exception("Escolher categoria de Sabor é obrigatório.");
					}
					
					TipoPizza objetoTipo = null;
					for (TipoPizza tp : sistema.getTabelaPrecos()) {
						if (tp.getCategoria() == tipo) {
							objetoTipo = tp;
							break;
						}
					}
					
					Sabor saborCadastro = new Sabor(nome, objetoTipo);
					sistema.cadastrarSabor(saborCadastro);
					
					javax.swing.JOptionPane.showMessageDialog(null, "Sabor '" + nome + "' cadastrado com sucesso!");
					textField.setText("");
					buttonGroup.clearSelection();
					
				} catch (Exception e2) {
					javax.swing.JOptionPane.showMessageDialog(null, e2.getMessage(), "Aviso", javax.swing.JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		pack(); // Ajusta tamanho da janela com base nos componentes
	}
}
