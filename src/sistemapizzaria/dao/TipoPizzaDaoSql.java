package sistemapizzaria.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sistemapizzaria.pizza.CategoriaTipo;
import sistemapizzaria.pizza.TipoPizza;

public class TipoPizzaDaoSql implements TipoPizzaDao {

    private static TipoPizzaDaoSql instance;

    private final String selectAll = "SELECT categoria, preco_cm2 FROM tipos_pizza";
    private final String selectByCategoria = "SELECT categoria, preco_cm2 FROM tipos_pizza WHERE categoria = ?";
    private final String update = "UPDATE tipos_pizza SET preco_cm2 = ? WHERE categoria = ?";

    private TipoPizzaDaoSql() {
    }

    public static synchronized TipoPizzaDaoSql getTipoPizzaDaoSql() {
        if (instance == null) {
            instance = new TipoPizzaDaoSql();
        }
        return instance;
    }

    @Override
    public List<TipoPizza> getAll() throws SQLException, IOException {
        List<TipoPizza> lista = new ArrayList<>();
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(selectAll);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                CategoriaTipo cat = CategoriaTipo.valueOf(rs.getString("categoria"));
                double preco = rs.getDouble("preco_cm2");
                lista.add(new TipoPizza(cat, preco));
            }
        }
        return lista;
    }

    @Override
    public TipoPizza getByCategoria(CategoriaTipo categoria) throws SQLException, IOException {
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(selectByCategoria)) {
            stmt.setString(1, categoria.name());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new TipoPizza(
                        CategoriaTipo.valueOf(rs.getString("categoria")),
                        rs.getDouble("preco_cm2")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public void update(TipoPizza tipoPizza) throws SQLException, IOException {
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(update)) {
            stmt.setDouble(1, tipoPizza.getPrecoPorCm2());
            stmt.setString(2, tipoPizza.getCategoria().name());
            stmt.executeUpdate();
        }
    }
}
