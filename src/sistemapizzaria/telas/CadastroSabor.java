package sistemapizzaria.telas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import sistemapizzaria.SistemaPizzaria;
import sistemapizzaria.controller.SaborController;
import sistemapizzaria.pizza.CategoriaTipo;
import sistemapizzaria.pizza.Sabor;

/**
 * View responsável pelo cadastro de sabores de pizza e vínculo com a categoria (Padrão MVC - Aula 06).
 */
public class CadastroSabor extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;
    private final ButtonGroup buttonGroup = new ButtonGroup();
    private JRadioButton rdbtnSimples;
    private JRadioButton rdbtnEspecial;
    private JRadioButton rdbtnPremium;
    private DefaultListModel<String> listModelSabores;

    private final SaborController controller;

    public CadastroSabor() {
        this.controller = new SaborController(this);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Sabor");
        setMinimumSize(new Dimension(500, 380));
        setLocationRelativeTo(null);

        contentPane = new JPanel(new BorderLayout(10, 10));
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

        rdbtnSimples = new JRadioButton("Simples");
        buttonGroup.add(rdbtnSimples);
        gbc.gridx = 1;
        pnlForm.add(rdbtnSimples, gbc);

        rdbtnEspecial = new JRadioButton("Especial");
        buttonGroup.add(rdbtnEspecial);
        gbc.gridx = 2;
        pnlForm.add(rdbtnEspecial, gbc);

        rdbtnPremium = new JRadioButton("Premium");
        buttonGroup.add(rdbtnPremium);
        gbc.gridx = 3;
        pnlForm.add(rdbtnPremium, gbc);

        contentPane.add(pnlForm, BorderLayout.NORTH);

        // Lista de sabores cadastrados no banco
        listModelSabores = new DefaultListModel<>();
        JList<String> listaSabores = new JList<>(listModelSabores);
        JScrollPane scrollSabores = new JScrollPane(listaSabores);
        scrollSabores.setBorder(BorderFactory.createTitledBorder("Sabores Cadastrados"));
        scrollSabores.setPreferredSize(new Dimension(450, 140));
        contentPane.add(scrollSabores, BorderLayout.CENTER);

        // Botão Cadastrar
        JPanel pnlBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnCadastrarSabor = new JButton("Cadastrar Sabor");
        pnlBotoes.add(btnCadastrarSabor);
        contentPane.add(pnlBotoes, BorderLayout.SOUTH);

        btnCadastrarSabor.addActionListener(e -> {
            CategoriaTipo tipo = null;
            if (rdbtnSimples.isSelected()) {
                tipo = CategoriaTipo.SIMPLES;
            } else if (rdbtnEspecial.isSelected()) {
                tipo = CategoriaTipo.ESPECIAL;
            } else if (rdbtnPremium.isSelected()) {
                tipo = CategoriaTipo.PREMIUM;
            }
            controller.cadastrarSabor(textField.getText(), tipo);
        });

        controller.carregarSabores();
        pack();
    }

    public CadastroSabor(SistemaPizzaria sistema) {
        this();
    }

    public void atualizarListaSabores(List<Sabor> sabores) {
        listModelSabores.clear();
        if (sabores != null) {
            for (Sabor s : sabores) {
                listModelSabores.addElement(s.toString());
            }
        }
    }

    public void limparFormulario() {
        textField.setText("");
        buttonGroup.clearSelection();
    }

    public void exibirSucesso(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    public void exibirAviso(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Aviso", JOptionPane.WARNING_MESSAGE);
    }
}
