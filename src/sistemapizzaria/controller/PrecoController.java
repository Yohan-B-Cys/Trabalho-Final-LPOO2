package sistemapizzaria.controller;

import java.util.List;

import sistemapizzaria.dao.DaoFactory;
import sistemapizzaria.dao.PedidoDao;
import sistemapizzaria.dao.TipoPizzaDao;
import sistemapizzaria.pizza.CategoriaTipo;
import sistemapizzaria.pizza.TipoPizza;
import sistemapizzaria.telas.TelaAtualizarPreco;

/**
 * Controlador responsável pela tela de atualização de preços por cm² (Padrão MVC - Aula 06).
 */
public class PrecoController {

    private final TelaAtualizarPreco view;
    private final TipoPizzaDao tipoPizzaDao;
    private final PedidoDao pedidoDao;

    public PrecoController(TelaAtualizarPreco view) {
        this.view = view;
        this.tipoPizzaDao = DaoFactory.getTipoPizzaDao();
        this.pedidoDao = DaoFactory.getPedidoDao();
    }

    public void carregarPrecosAtuais() {
        try {
            List<TipoPizza> tabela = tipoPizzaDao.getAll();
            double simples = 0.0, especial = 0.0, premium = 0.0;
            for (TipoPizza tipo : tabela) {
                if (tipo.getCategoria() == CategoriaTipo.SIMPLES) {
                    simples = tipo.getPrecoPorCm2();
                } else if (tipo.getCategoria() == CategoriaTipo.ESPECIAL) {
                    especial = tipo.getPrecoPorCm2();
                } else if (tipo.getCategoria() == CategoriaTipo.PREMIUM) {
                    premium = tipo.getPrecoPorCm2();
                }
            }
            view.preencherCamposPreco(simples, especial, premium);
        } catch (Exception e) {
            view.exibirErro("Erro ao carregar preços do banco de dados: " + e.getMessage());
        }
    }

    public void atualizarPrecos(String txtSimples, String txtEspecial, String txtPremium) {
        try {
            double precoSimples = extrairValor(txtSimples, "Simples");
            double precoEspecial = extrairValor(txtEspecial, "Especial");
            double precoPremium = extrairValor(txtPremium, "Premium");

            TipoPizza tpSimples = new TipoPizza(CategoriaTipo.SIMPLES, 0.0);
            tpSimples.atualizarPrecoPorCm2(precoSimples);

            TipoPizza tpEspecial = new TipoPizza(CategoriaTipo.ESPECIAL, 0.0);
            tpEspecial.atualizarPrecoPorCm2(precoEspecial);

            TipoPizza tpPremium = new TipoPizza(CategoriaTipo.PREMIUM, 0.0);
            tpPremium.atualizarPrecoPorCm2(precoPremium);

            tipoPizzaDao.update(tpSimples);
            tipoPizzaDao.update(tpEspecial);
            tipoPizzaDao.update(tpPremium);

            // Recalcula o preço total dos pedidos que ainda estão em aberto
            pedidoDao.recalcularPrecosPedidosAbertos();

            view.exibirSucesso("Preços atualizados com sucesso no banco de dados!");
            view.fecharTela();
        } catch (NumberFormatException ex) {
            view.exibirErro("Por favor, digite apenas números válidos para os preços.");
        } catch (IllegalArgumentException ex) {
            view.exibirErro(ex.getMessage());
        } catch (Exception ex) {
            view.exibirErro("Erro ao salvar preços no banco de dados: " + ex.getMessage());
        }
    }

    private double extrairValor(String texto, String nomeCampo) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException("O preço da categoria " + nomeCampo + " não pode estar vazio.");
        }
        String limpo = texto.replace("R$", "").replace(",", ".").trim();
        double valor = Double.parseDouble(limpo);
        if (valor <= 0.0) {
            throw new IllegalArgumentException("O preço da categoria " + nomeCampo + " deve ser maior que zero.");
        }
        return valor;
    }
}
