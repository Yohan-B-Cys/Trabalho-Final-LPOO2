package sistemapizzaria.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import sistemapizzaria.cliente.Cliente;

public interface ClienteDao {
    void add(Cliente cliente) throws SQLException, IOException;
    void update(Cliente cliente) throws SQLException, IOException;
    void delete(Cliente cliente) throws SQLException, IOException;
    List<Cliente> getAll() throws SQLException, IOException;
    Cliente getById(long id) throws SQLException, IOException;
    Cliente getByTelefone(String telefone) throws SQLException, IOException;
    List<Cliente> buscarPorFiltro(String filtro) throws SQLException, IOException;
    List<Cliente> buscarPorSobrenomeETelefone(String sobrenome, String telefone) throws SQLException, IOException;
}
