package sistemapizzaria.telas;

import java.awt.EventQueue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

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
		setBounds(100, 100, 300, 300);
		contentPane = new JPanel();
		setTitle("Atualizar Preços");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtPrecoSimples = new JTextField();
		txtPrecoSimples.setBounds(168, 77, 57, 21);
		contentPane.add(txtPrecoSimples);
		txtPrecoSimples.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Preço Sabor Simples :");
		lblNewLabel_1.setBounds(22, 79, 128, 17);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblCms = new JLabel("cms2");
		lblCms.setBounds(233, 79, 60, 17);
		contentPane.add(lblCms);
		
		txtPrecoEspecial = new JTextField();
		txtPrecoEspecial.setColumns(10);
		txtPrecoEspecial.setBounds(168, 120, 57, 21);
		contentPane.add(txtPrecoEspecial);
		
		JLabel lblNewLabel_1_1 = new JLabel("Preço Sabor Especial:");
		lblNewLabel_1_1.setBounds(22, 122, 138, 17);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblCms_1 = new JLabel("cms2");
		lblCms_1.setBounds(233, 122, 60, 17);
		contentPane.add(lblCms_1);
		
		txtPrecoPremium = new JTextField();
		txtPrecoPremium.setColumns(10);
		txtPrecoPremium.setBounds(168, 170, 57, 21);
		contentPane.add(txtPrecoPremium);
		
		JLabel lblNewLabel_1_2 = new JLabel("Preço Sabor Premium :");
		lblNewLabel_1_2.setBounds(22, 172, 138, 17);
		contentPane.add(lblNewLabel_1_2);
		
		JLabel lblCms_2 = new JLabel("cms2");
		lblCms_2.setBounds(233, 172, 60, 17);
		contentPane.add(lblCms_2);
		
		JButton btnAtualizarValores = new JButton("Atualizar Valores");
		btnAtualizarValores.setBounds(53, 223, 155, 27);
		contentPane.add(btnAtualizarValores);
		
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
