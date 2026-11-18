package sistemapizzaria.telas;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import sistemapizzaria.SistemaPizzaria;
import sistemapizzaria.dao.DaoFactory;
import sistemapizzaria.formas.Circulo;
import sistemapizzaria.formas.Forma;
import sistemapizzaria.formas.Quadrado;
import sistemapizzaria.formas.TrianguloEquilatero;
import sistemapizzaria.pizza.Pizza;
import sistemapizzaria.pizza.Sabor;

/**
 * Componente View para montagem polimórfica de Pizza (Forma + Sabores) (Padrão MVC - Aula 06).
 */
public class PainelMontagemPizza extends JPanel {

    private static final long serialVersionUID = 1L;

    private JComboBox<String> comboBoxFormaPizza;
    private JRadioButton radioLadoRaio;
    private JRadioButton radioArea;
    private ButtonGroup buttonGroup;
    private JTextField txtTamanho;
    private JLabel lblDefinirTamanho;
    private JLabel lblObs;
    private JLabel lblTamanhoLadodiametro;
    private JLabel lblrea;
    private JComboBox<String> comboBox_1;
    private JComboBox<String> comboBox_2;
    private JLabel lblPre;
    private JButton btnAdicionarPizza;

    private List<Sabor> listaSaboresCache = new ArrayList<>();
    private final PizzaAdicionadaListener listener;

    public interface PizzaAdicionadaListener {
        void onPizzaAdicionada(Pizza pizza);
    }

    public PainelMontagemPizza(PizzaAdicionadaListener listener) {
        this.listener = listener;

        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createTitledBorder("Montar Pizza"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Linha 0: Forma da Pizza
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Forma da Pizza:"), gbc);
        comboBoxFormaPizza = new JComboBox<>(new String[]{"Círculo", "Quadrado", "Triângulo Equilátero"});
        gbc.gridx = 1; gbc.gridwidth = 2;
        add(comboBoxFormaPizza, gbc);

        // Linha 1: Radios de definição
        radioLadoRaio = new JRadioButton("Definir tamanho por raio/lado");
        radioArea = new JRadioButton("Definir tamanho por área (cm²)");
        buttonGroup = new ButtonGroup();
        buttonGroup.add(radioLadoRaio);
        buttonGroup.add(radioArea);
        radioLadoRaio.setSelected(true);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        add(radioLadoRaio, gbc);
        gbc.gridx = 2; gbc.gridwidth = 2;
        add(radioArea, gbc);

        // Linha 2: Tamanho e Obs
        lblDefinirTamanho = new JLabel("Definir Raio (cm) :");
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1;
        add(lblDefinirTamanho, gbc);

        txtTamanho = new JTextField(10);
        gbc.gridx = 1;
        add(txtTamanho, gbc);

        lblObs = new JLabel(String.format("Obs: raio deve ter entre %.0f a %.0f cm", Circulo.MIN_RAIO, Circulo.MAX_RAIO));
        gbc.gridx = 2; gbc.gridwidth = 2;
        add(lblObs, gbc);

        // Linha 3: Labels de Resultados
        lblTamanhoLadodiametro = new JLabel("Tamanho Lado/Raio : -- cm ");
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        add(lblTamanhoLadodiametro, gbc);

        lblrea = new JLabel("Área : -- cm²");
        gbc.gridx = 2; gbc.gridwidth = 2;
        add(lblrea, gbc);

        // Linha 4: Sabores
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 1;
        add(new JLabel("Sabores (até 2):"), gbc);

        comboBox_1 = new JComboBox<>();
        gbc.gridx = 1; gbc.gridwidth = 1;
        add(comboBox_1, gbc);

        comboBox_2 = new JComboBox<>();
        gbc.gridx = 2; gbc.gridwidth = 2;
        add(comboBox_2, gbc);

        recarregarSaboresDoBanco();

        // Linha 5: Preço e Adicionar
        lblPre = new JLabel("Preço : R$ 0,00");
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        add(lblPre, gbc);

        btnAdicionarPizza = new JButton("Adicionar pizza");
        gbc.gridx = 2; gbc.gridwidth = 1;
        add(btnAdicionarPizza, gbc);

        configurarEventos();
    }

    public PainelMontagemPizza(SistemaPizzaria sistema, PizzaAdicionadaListener listener) {
        this(listener);
    }

    public void recarregarSaboresDoBanco() {
        try {
            listaSaboresCache = DaoFactory.getSaborDao().getAll();
        } catch (Exception e) {
            listaSaboresCache = new ArrayList<>();
        }
        comboBox_1.removeAllItems();
        comboBox_2.removeAllItems();
        comboBox_1.addItem("Selecione um sabor...");
        comboBox_2.addItem("Nenhuma (Pizza de 1 sabor)");
        for (Sabor sabor : listaSaboresCache) {
            String desc = sabor.getNome() + " - " + sabor.getTipo().getCategoria();
            comboBox_1.addItem(desc);
            comboBox_2.addItem(desc);
        }
    }

    private void configurarEventos() {
        ActionListener radioListener = e -> {
            atualizarLabelObs();
            atualizarPreview();
        };
        radioLadoRaio.addActionListener(radioListener);
        radioArea.addActionListener(radioListener);

        txtTamanho.addActionListener(e -> atualizarPreview());
        txtTamanho.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                atualizarPreview();
            }
        });
        txtTamanho.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                atualizarPreview();
            }

            @Override
            public void keyTyped(KeyEvent e) {
                char caractere = e.getKeyChar();
                if (!Character.isDigit(caractere) && caractere != '.' && caractere != ',') {
                    e.consume();
                }
            }
        });

        comboBoxFormaPizza.addActionListener(e -> {
            atualizarLabelObs();
            atualizarPreview();
        });

        ActionListener saborListener = e -> atualizarPreview();
        comboBox_1.addActionListener(saborListener);
        comboBox_2.addActionListener(saborListener);

        btnAdicionarPizza.addActionListener(e -> {
            try {
                Pizza novaPizza = construirPizzaLocal();
                if (novaPizza.calcularPreco() <= 0) {
                    JOptionPane.showMessageDialog(this, "A pizza calculada possui valor R$ 0,00. Verifique os tamanhos, sabores e a tabela de preços.");
                    return;
                }
                if (listener != null) {
                    listener.onPizzaAdicionada(novaPizza);
                }
                limparFormulario();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro na Pizza", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    private void atualizarLabelObs() {
        String formaSelecionada = comboBoxFormaPizza.getSelectedItem().toString();
        if (radioArea.isSelected()) {
            lblDefinirTamanho.setText("Definir Área (cm²):");
            lblObs.setText(String.format("Obs: a área deve ter entre %.0f e %.0f cm²", Forma.MIN_AREA, Forma.MAX_AREA));
        } else {
            if (formaSelecionada.equals("Círculo")) {
                radioLadoRaio.setText("Definir tamanho por raio");
                lblDefinirTamanho.setText("Definir Raio (cm) :");
                lblObs.setText(String.format("Obs: raio deve ter entre %.0f a %.0f cm", Circulo.MIN_RAIO, Circulo.MAX_RAIO));
            } else {
                radioLadoRaio.setText("Definir tamanho por lado");
                lblDefinirTamanho.setText("Definir Lado (cm):");
                if (formaSelecionada.equals("Quadrado")) {
                    lblObs.setText(String.format("Obs: lado deve ter entre %.0f a %.0f cm", Quadrado.MIN_LADO, Quadrado.MAX_LADO));
                } else {
                    lblObs.setText(String.format("Obs: lado deve ter entre %.0f a %.0f cm", TrianguloEquilatero.MIN_LADO, TrianguloEquilatero.MAX_LADO));
                }
            }
        }
    }

    private void atualizarPreview() {
        try {
            Pizza preview = construirPizzaLocal();
            Forma forma = preview.getForma();
            double dimensaoReal = 0;
            if (forma instanceof Circulo) dimensaoReal = ((Circulo) forma).getRaio();
            else if (forma instanceof Quadrado) dimensaoReal = ((Quadrado) forma).getLado();
            else if (forma instanceof TrianguloEquilatero) dimensaoReal = ((TrianguloEquilatero) forma).getLado();

            String nomeMedida = (forma instanceof Circulo) ? "Raio" : "Lado";
            lblTamanhoLadodiametro.setText(String.format("Tamanho %s: %.2f cm", nomeMedida, dimensaoReal));
            lblrea.setText(String.format("Área: %.2f cm²", forma.calcularArea()));

            double precoCalculado = preview.calcularPreco();
            if (precoCalculado > 0) {
                lblPre.setText(String.format("Preço: R$ %.2f", precoCalculado));
            } else {
                lblPre.setText("Preço : R$ 0,00");
            }
        } catch (Exception e) {
            lblTamanhoLadodiametro.setText("Tamanho/Sabores Inválidos");
            lblrea.setText("");
            lblPre.setText("Preço : R$ 0,00");
        }
    }

    public Pizza construirPizzaLocal() throws Exception {
        String textoTamanho = txtTamanho.getText().replace(",", ".");
        if (textoTamanho.trim().isEmpty()) {
            throw new Exception("Informe a dimensão ou área da pizza.");
        }

        double valorDigitado;
        try {
            valorDigitado = Double.parseDouble(textoTamanho);
        } catch (NumberFormatException e) {
            throw new Exception("Valor numérico inválido.");
        }

        String formato = comboBoxFormaPizza.getSelectedItem().toString();
        Forma formaReal;
        if ("Círculo".equals(formato)) formaReal = new Circulo();
        else if ("Quadrado".equals(formato)) formaReal = new Quadrado();
        else formaReal = new TrianguloEquilatero();

        if (radioLadoRaio.isSelected()) {
            formaReal.definirPorDimensao(valorDigitado);
        } else {
            formaReal.definirPorArea(valorDigitado);
        }

        int idx1 = comboBox_1.getSelectedIndex();
        if (idx1 <= 0 || idx1 - 1 >= listaSaboresCache.size()) {
            throw new Exception("Escolha o sabor principal!");
        }

        Pizza novaPizza = new Pizza();
        novaPizza.setForma(formaReal);
        novaPizza.adicionarSabor(listaSaboresCache.get(idx1 - 1));

        int idx2 = comboBox_2.getSelectedIndex();
        if (idx2 > 0 && idx2 - 1 < listaSaboresCache.size()) {
            novaPizza.adicionarSabor(listaSaboresCache.get(idx2 - 1));
        }

        return novaPizza;
    }

    public void carregarPizzaParaEdicao(Pizza pizzaOriginal) {
        recarregarSaboresDoBanco();
        if (pizzaOriginal == null) return;
        Forma f = pizzaOriginal.getForma();

        radioLadoRaio.setSelected(true);
        if (f instanceof Circulo) {
            comboBoxFormaPizza.setSelectedItem("Círculo");
            txtTamanho.setText(String.format("%.2f", ((Circulo) f).getRaio()));
        } else if (f instanceof Quadrado) {
            comboBoxFormaPizza.setSelectedItem("Quadrado");
            txtTamanho.setText(String.format("%.2f", ((Quadrado) f).getLado()));
        } else if (f instanceof TrianguloEquilatero) {
            comboBoxFormaPizza.setSelectedItem("Triângulo Equilátero");
            txtTamanho.setText(String.format("%.2f", ((TrianguloEquilatero) f).getLado()));
        }

        List<Sabor> sabores = pizzaOriginal.getSabores();
        if (!sabores.isEmpty()) {
            String sabor1Desc = sabores.get(0).getNome() + " - " + sabores.get(0).getTipo().getCategoria();
            comboBox_1.setSelectedItem(sabor1Desc);
        }
        if (sabores.size() > 1) {
            String sabor2Desc = sabores.get(1).getNome() + " - " + sabores.get(1).getTipo().getCategoria();
            comboBox_2.setSelectedItem(sabor2Desc);
        } else {
            comboBox_2.setSelectedIndex(0);
        }

        atualizarLabelObs();
        atualizarPreview();
    }

    public void limparFormulario() {
        recarregarSaboresDoBanco();
        txtTamanho.setText("");
        lblTamanhoLadodiametro.setText("Tamanho Lado/Raio : -- cm ");
        lblrea.setText("Área : -- cm²");
        lblPre.setText("Preço : R$ 0,00");

        if (comboBox_1.getItemCount() > 0) comboBox_1.setSelectedIndex(0);
        if (comboBox_2.getItemCount() > 0) comboBox_2.setSelectedIndex(0);

        radioLadoRaio.setSelected(true);
        atualizarLabelObs();
    }
}
