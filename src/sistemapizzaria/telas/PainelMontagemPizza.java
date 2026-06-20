package sistemapizzaria.telas;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import sistemapizzaria.SistemaPizzaria;
import sistemapizzaria.pizza.Pizza;
import sistemapizzaria.pizza.Sabor;
import sistemapizzaria.formas.Circulo;
import sistemapizzaria.formas.Forma;
import sistemapizzaria.formas.Quadrado;
import sistemapizzaria.formas.TrianguloEquilatero;

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

    private SistemaPizzaria sistema;
    private PizzaAdicionadaListener listener;

    // Interface para comunicar à TelaPedidos que uma pizza foi adicionada
    public interface PizzaAdicionadaListener {
        void onPizzaAdicionada(Pizza pizza);
    }

    public PainelMontagemPizza(SistemaPizzaria sistema, PizzaAdicionadaListener listener) {
        this.sistema = sistema;
        this.listener = listener;

        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createTitledBorder("Montar Pizza"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Linha 0: Tipo da Pizza
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Tipo da Pizza:"), gbc);
        comboBoxFormaPizza = new JComboBox<>(new String[]{"Círculo", "Quadrado", "Triângulo Equilátero"});
        gbc.gridx = 1; gbc.gridwidth = 2;
        add(comboBoxFormaPizza, gbc);

        // Linha 1: Radios de definição
        radioLadoRaio = new JRadioButton("Definir tamanho por raio/lado");
        radioArea = new JRadioButton("Definir tamanho por área");
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
        add(new JLabel("Sabores:"), gbc);
        
        comboBox_1 = new JComboBox<>();
        gbc.gridx = 1; gbc.gridwidth = 1;
        add(comboBox_1, gbc);
        
        comboBox_2 = new JComboBox<>();
        gbc.gridx = 2; gbc.gridwidth = 2;
        add(comboBox_2, gbc);

        if (this.sistema != null) {
            comboBox_1.addItem("Selecione um sabor...");
            comboBox_2.addItem("Nenhuma (Pizza de 1 sabor)");
            java.util.List<Sabor> listaSabores = this.sistema.getSaboresDisponiveis();
            for (Sabor sabor : listaSabores) {
                String desc = sabor.getNome() + " - " + sabor.getTipo().getCategoria();
                comboBox_1.addItem(desc);
                comboBox_2.addItem(desc);
            }
        }

        // Linha 5: Preço e Adicionar
        lblPre = new JLabel("Preço : R$ 0,00");
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        add(lblPre, gbc);
        
        btnAdicionarPizza = new JButton("Adicionar pizza");
        gbc.gridx = 2; gbc.gridwidth = 1;
        add(btnAdicionarPizza, gbc);

        configurarEventos();
    }

    private void configurarEventos() {
        ActionListener radioListener = e -> { atualizarLabelObs(); atualizarPreview(); };
        radioLadoRaio.addActionListener(radioListener);
        radioArea.addActionListener(radioListener);
        
        txtTamanho.addActionListener(e -> atualizarPreview());
        txtTamanho.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent e) { atualizarPreview(); }
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
            String formaSelecionada = comboBoxFormaPizza.getSelectedItem().toString();
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
            atualizarPreview();
        });

        ActionListener saborListener = e -> atualizarPreview();
        comboBox_1.addActionListener(saborListener);
        comboBox_2.addActionListener(saborListener);

        btnAdicionarPizza.addActionListener(e -> {
            try {
                Pizza novaPizza = construirPizzaLocal();
                if (novaPizza.calcularPreco() <= 0) {
                    javax.swing.JOptionPane.showMessageDialog(this, "A pizza calculada possui valor R$ 0,00. Verifique os tamanhos e sabores.");
                    return;
                }
                
                if (listener != null) {
                    listener.onPizzaAdicionada(novaPizza);
                }
                limparFormulario();
            } catch (Exception ex) {
                javax.swing.JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro na Pizza", javax.swing.JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    private void atualizarLabelObs() {
        if (radioArea.isSelected()) {
            lblObs.setText(String.format("Obs: a área deve ter entre %.0f e %.0f cm²", Forma.MIN_AREA, Forma.MAX_AREA));
        } else {
            String formaSelecionada = comboBoxFormaPizza.getSelectedItem().toString();
            if (formaSelecionada.equals("Círculo")) lblObs.setText(String.format("Obs: raio deve ter entre %.0f a %.0f cm", Circulo.MIN_RAIO, Circulo.MAX_RAIO));
            else if (formaSelecionada.equals("Quadrado")) lblObs.setText(String.format("Obs: lado deve ter entre %.0f a %.0f cm", Quadrado.MIN_LADO, Quadrado.MAX_LADO));
            else lblObs.setText(String.format("Obs: lado deve ter entre %.0f a %.0f cm", TrianguloEquilatero.MIN_LADO, TrianguloEquilatero.MAX_LADO));
        }
    }

    // Cria o objeto pizza em memória sem salvá-lo definitivamente, apenas para exibir o preço/área de preview
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

    // Fábrica da Pizza: Centraliza a criação e delegação para o domínio
    public Pizza construirPizzaLocal() throws Exception {
        String textoTamanho = txtTamanho.getText().replace(",", ".");
        if (textoTamanho.trim().isEmpty()) {
            throw new Exception("Informe o tamanho da pizza.");
        }
        
        double valorDigitado;
        try {
            valorDigitado = Double.parseDouble(textoTamanho);
        } catch (NumberFormatException e) {
            throw new Exception("Valor numérico inválido.");
        }

        String formato = comboBoxFormaPizza.getSelectedItem().toString();
        Forma formaReal = null;
        if ("Círculo".equals(formato)) formaReal = new Circulo();
        else if ("Quadrado".equals(formato)) formaReal = new Quadrado();
        else if ("Triângulo Equilátero".equals(formato)) formaReal = new TrianguloEquilatero();

        if (radioLadoRaio.isSelected()) {
            formaReal.definirPorDimensao(valorDigitado);
        } else {
            formaReal.definirPorArea(valorDigitado);
        }

        int idx1 = comboBox_1.getSelectedIndex();
        if (idx1 <= 0) {
            throw new Exception("Escolha o sabor principal!");
        }

        Pizza novaPizza = new Pizza();
        novaPizza.setForma(formaReal);
        
        novaPizza.adicionarSabor(sistema.getSaboresDisponiveis().get(idx1 - 1));
        
        int idx2 = comboBox_2.getSelectedIndex();
        if (idx2 > 0) {
            novaPizza.adicionarSabor(sistema.getSaboresDisponiveis().get(idx2 - 1));
        }

        return novaPizza;
    }

    public void carregarPizzaParaEdicao(Pizza pizzaOriginal) {
        Forma f = pizzaOriginal.getForma();

        if (f instanceof Circulo) comboBoxFormaPizza.setSelectedItem("Círculo");
        else if (f instanceof Quadrado) comboBoxFormaPizza.setSelectedItem("Quadrado");
        else if (f instanceof TrianguloEquilatero) comboBoxFormaPizza.setSelectedItem("Triângulo Equilátero");

        // Sempre restaura por área — a área é sempre matematicamente correta
        radioArea.setSelected(true);
        txtTamanho.setText(String.format("%.0f", f.calcularArea()));

        // Selecionar os sabores
        java.util.List<Sabor> sabores = pizzaOriginal.getSabores();
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
