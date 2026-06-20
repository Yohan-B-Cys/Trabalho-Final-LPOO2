package sistemapizzaria.telas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;

import sistemapizzaria.pizza.CategoriaTipo;
import sistemapizzaria.SistemaPizzaria;
import sistemapizzaria.pizza.TipoPizza;

public class TelaAtualizarPreco extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private JTextField txtPrecoSimples;
	private JTextField txtPrecoEspecial;
	private JTextField txtPrecoPremium;

	private SistemaPizzaria sistema;
	
	/**
	 * Create the frame.
	 */
	public TelaAtualizarPreco(SistemaPizzaria sistema) {
		this.sistema = sistema;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Atualizar Preços");
		setMinimumSize(new Dimension(350, 250));
		setLocationRelativeTo(null);
		
		contentPane = new JPanel(new BorderLayout());
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		
		JPanel pnlForm = new JPanel(new GridBagLayout());
		pnlForm.setBorder(BorderFactory.createTitledBorder("Tabela de Preços por cm²"));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.anchor = GridBagConstraints.WEST;
		
		// Simples
		gbc.gridx = 0; gbc.gridy = 0;
		pnlForm.add(new JLabel("Preço Sabor Simples:"), gbc);
		
		txtPrecoSimples = new JTextField(8);
		gbc.gridx = 1;
		pnlForm.add(txtPrecoSimples, gbc);
		
		gbc.gridx = 2;
		pnlForm.add(new JLabel("R$/cm²"), gbc);
		
		// Especial
		gbc.gridx = 0; gbc.gridy = 1;
		pnlForm.add(new JLabel("Preço Sabor Especial:"), gbc);
		
		txtPrecoEspecial = new JTextField(8);
		gbc.gridx = 1;
		pnlForm.add(txtPrecoEspecial, gbc);
		
		gbc.gridx = 2;
		pnlForm.add(new JLabel("R$/cm²"), gbc);
		
		// Premium
		gbc.gridx = 0; gbc.gridy = 2;
		pnlForm.add(new JLabel("Preço Sabor Premium:"), gbc);
		
		txtPrecoPremium = new JTextField(8);
		gbc.gridx = 1;
		pnlForm.add(txtPrecoPremium, gbc);
		
		gbc.gridx = 2;
		pnlForm.add(new JLabel("R$/cm²"), gbc);
		
		contentPane.add(pnlForm, BorderLayout.CENTER);
		
		// Botões
		JPanel pnlBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JButton btnAtualizarValores = new JButton("Atualizar Valores");
		pnlBotoes.add(btnAtualizarValores);
		contentPane.add(pnlBotoes, BorderLayout.SOUTH);
		
		// Carrega os valores iniciais do sistema para a tela
		carregarPrecosAtuais();

		// Captura ação de clique do botão
		btnAtualizarValores.addActionListener(e -> {
			try {
				// Pega os textos, tira o $ se o usuário digitar, e troca vírgula por ponto
				double precoSimples = extrairValor(txtPrecoSimples.getText());
				double precoEspecial = extrairValor(txtPrecoEspecial.getText());
				double precoPremium = extrairValor(txtPrecoPremium.getText());
				
				// Manda o sistema atualizar os valores de cada categoria
				sistema.atualizarPreco(CategoriaTipo.SIMPLES, precoSimples);
				sistema.atualizarPreco(CategoriaTipo.ESPECIAL, precoEspecial);
				sistema.atualizarPreco(CategoriaTipo.PREMIUM, precoPremium);
				
				JOptionPane.showMessageDialog(null, "Preços atualizados com sucesso!");
				dispose(); // Fecha a tela de preços após salvar
				
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(null, "Por favor, digite apenas números válidos.", "Erro de Digitação", JOptionPane.ERROR_MESSAGE);
			} catch (IllegalArgumentException ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage(), "Valor Inválido", JOptionPane.ERROR_MESSAGE);
			}
		});
		
		pack();
	}
	
	// Varre a tabela de preços do sistema e preenche os campos de texto com os valores reais
	private void carregarPrecosAtuais() {
		for (TipoPizza tipo : sistema.getTabelaPrecos()) {
			if (tipo.getCategoria() == CategoriaTipo.SIMPLES) {
				txtPrecoSimples.setText(String.valueOf(tipo.getPrecoPorCm2()));
			} else if (tipo.getCategoria() == CategoriaTipo.ESPECIAL) {
				txtPrecoEspecial.setText(String.valueOf(tipo.getPrecoPorCm2()));
			} else if (tipo.getCategoria() == CategoriaTipo.PREMIUM) {
				txtPrecoPremium.setText(String.valueOf(tipo.getPrecoPorCm2()));
			}
		}
	}
	
	// Evita que valores decimais sejam tratados como "," caso o usuário não digitar "."
	private double extrairValor(String texto) {
		String limpo = texto.replace(",", ".").trim();
		return Double.parseDouble(limpo);
	}

}
