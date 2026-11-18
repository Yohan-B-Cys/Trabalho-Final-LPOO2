package sistemapizzaria.telas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import sistemapizzaria.SistemaPizzaria;
import sistemapizzaria.controller.PrecoController;

/**
 * View responsável pela atualização do preço por cm² de cada tipo de pizza (Padrão MVC - Aula 06).
 */
public class TelaAtualizarPreco extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    private JTextField txtPrecoSimples;
    private JTextField txtPrecoEspecial;
    private JTextField txtPrecoPremium;

    private final PrecoController controller;

    public TelaAtualizarPreco() {
        this.controller = new PrecoController(this);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Atualizar Preços");
        setMinimumSize(new Dimension(380, 260));
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

        // Carrega os valores iniciais via Controller
        controller.carregarPrecosAtuais();

        // Delega ação de clique ao Controller
        btnAtualizarValores.addActionListener(e -> {
            controller.atualizarPrecos(
                txtPrecoSimples.getText(),
                txtPrecoEspecial.getText(),
                txtPrecoPremium.getText()
            );
        });

        pack();
    }

    public TelaAtualizarPreco(SistemaPizzaria sistema) {
        this();
    }

    public void preencherCamposPreco(double simples, double especial, double premium) {
        txtPrecoSimples.setText(String.valueOf(simples));
        txtPrecoEspecial.setText(String.valueOf(especial));
        txtPrecoPremium.setText(String.valueOf(premium));
    }

    public void exibirSucesso(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    public void exibirErro(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Valor Inválido", JOptionPane.ERROR_MESSAGE);
    }

    public void fecharTela() {
        dispose();
    }
}
