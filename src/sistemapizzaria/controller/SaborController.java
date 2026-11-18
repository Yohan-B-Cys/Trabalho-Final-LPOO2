package sistemapizzaria.controller;

import java.util.Collections;
import java.util.List;

import sistemapizzaria.dao.DaoFactory;
import sistemapizzaria.dao.SaborDao;
import sistemapizzaria.dao.TipoPizzaDao;
import sistemapizzaria.pizza.CategoriaTipo;
import sistemapizzaria.pizza.Sabor;
import sistemapizzaria.pizza.TipoPizza;
import sistemapizzaria.telas.CadastroSabor;

/**
 * Controlador responsável pelo cadastro de sabores e vínculo com o tipo da pizza (Padrão MVC - Aula 06).
 */
public class SaborController {

    private final CadastroSabor view;
    private final SaborDao saborDao;
    private final TipoPizzaDao tipoPizzaDao;

    public SaborController(CadastroSabor view) {
        this.view = view;
        this.saborDao = DaoFactory.getSaborDao();
        this.tipoPizzaDao = DaoFactory.getTipoPizzaDao();
    }

    public void cadastrarSabor(String nome, CategoriaTipo categoria) {
        try {
            if (nome == null || nome.trim().isEmpty()) {
                throw new IllegalArgumentException("O nome do sabor não pode estar vazio!");
            }
            if (categoria == null) {
                throw new IllegalArgumentException("Escolher a categoria de Sabor é obrigatório.");
            }

            String nomeLimpo = nome.trim();
            Sabor existente = saborDao.getByNome(nomeLimpo);
            if (existente != null) {
                throw new IllegalArgumentException("Já existe um sabor cadastrado com o nome '" + nomeLimpo + "'!");
            }

            TipoPizza tipoPizza = tipoPizzaDao.getByCategoria(categoria);
            if (tipoPizza == null) {
                tipoPizza = new TipoPizza(categoria, 0.0);
            }

            Sabor novoSabor = new Sabor(nomeLimpo, tipoPizza);
            saborDao.add(novoSabor);

            view.exibirSucesso("Sabor '" + nomeLimpo + "' cadastrado com sucesso!");
            view.limparFormulario();
            carregarSabores();
        } catch (IllegalArgumentException ex) {
            view.exibirAviso(ex.getMessage());
        } catch (Exception ex) {
            view.exibirAviso("Erro ao cadastrar sabor no banco de dados: " + ex.getMessage());
        }
    }

    public void carregarSabores() {
        try {
            List<Sabor> sabores = saborDao.getAll();
            view.atualizarListaSabores(sabores);
        } catch (Exception e) {
            view.atualizarListaSabores(Collections.emptyList());
        }
    }
}
