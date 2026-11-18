package sistemapizzaria.dao;

/**
 * Fábrica de DAOs (Padrão Factory + Singleton - Aula 05, Slide 8).
 */
public class DaoFactory {

    private DaoFactory() {
    }

    public static ClienteDao getClienteDao() {
        return ClienteDaoSql.getClienteDaoSql();
    }

    public static PedidoDao getPedidoDao() {
        return PedidoDaoSql.getPedidoDaoSql();
    }

    public static SaborDao getSaborDao() {
        return SaborDaoSql.getSaborDaoSql();
    }

    public static TipoPizzaDao getTipoPizzaDao() {
        return TipoPizzaDaoSql.getTipoPizzaDaoSql();
    }
}
