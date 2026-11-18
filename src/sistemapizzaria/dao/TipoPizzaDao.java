package sistemapizzaria.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import sistemapizzaria.pizza.CategoriaTipo;
import sistemapizzaria.pizza.TipoPizza;

public interface TipoPizzaDao {
    List<TipoPizza> getAll() throws SQLException, IOException;
    TipoPizza getByCategoria(CategoriaTipo categoria) throws SQLException, IOException;
    void update(TipoPizza tipoPizza) throws SQLException, IOException;
}
