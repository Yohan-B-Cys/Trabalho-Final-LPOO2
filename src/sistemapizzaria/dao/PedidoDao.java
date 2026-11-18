package sistemapizzaria.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import sistemapizzaria.cliente.Pedido;
import sistemapizzaria.cliente.StatusPedido;

public interface PedidoDao {
    void add(long idCliente, Pedido pedido) throws SQLException, IOException;
    void update(Pedido pedido) throws SQLException, IOException;
    void updateStatus(int idPedido, StatusPedido novoStatus) throws SQLException, IOException;
    void delete(int idPedido) throws SQLException, IOException;
    List<Pedido> getByClienteId(long idCliente) throws SQLException, IOException;
    Pedido getById(int idPedido) throws SQLException, IOException;
    void recalcularPrecosPedidosAbertos() throws SQLException, IOException;
}
