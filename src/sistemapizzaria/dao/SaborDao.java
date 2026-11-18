package sistemapizzaria.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import sistemapizzaria.pizza.Sabor;

public interface SaborDao {
    void add(Sabor sabor) throws SQLException, IOException;
    List<Sabor> getAll() throws SQLException, IOException;
    Sabor getById(long id) throws SQLException, IOException;
    Sabor getByNome(String nome) throws SQLException, IOException;
}
