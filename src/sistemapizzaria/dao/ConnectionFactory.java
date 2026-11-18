package sistemapizzaria.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import sistemapizzaria.pizza.CategoriaTipo;

/**
 * Fábrica de Conexões JDBC (Padrão Factory - Aula 04).
 * Lê as configurações do arquivo DataBase.properties e inicializa o schema SQLite.
 */
public class ConnectionFactory {

    private static Properties properties;
    private static boolean schemaInicializado = false;

    // Construtor privado para garantir que não haverá instâncias de ConnectionFactory
    private ConnectionFactory() {
    }

    public static Connection getConnection() throws SQLException, IOException {
        readProperties();
        String url = properties.getProperty("db.url", "jdbc:sqlite:pizzaria.db");
        String user = properties.getProperty("db.user", "");
        String pwd = properties.getProperty("db.pwd", "");

        Connection con;
        if (user == null || user.trim().isEmpty()) {
            con = DriverManager.getConnection(url);
        } else {
            con = DriverManager.getConnection(url, user, pwd);
        }

        // Ativa suporte a chaves estrangeiras (ON DELETE CASCADE) no SQLite
        if (url.startsWith("jdbc:sqlite:")) {
            try (Statement stmt = con.createStatement()) {
                stmt.execute("PRAGMA foreign_keys = ON;");
            }
        }

        if (!schemaInicializado) {
            schemaInicializado = true;
            inicializarSchema(con);
        }

        return con;
    }

    private static void readProperties() throws IOException {
        if (properties == null) {
            Properties props = new Properties();
            try (InputStream file = new FileInputStream("./src/sistemapizzaria/dao/DataBase.properties")) {
                props.load(file);
            } catch (IOException e) {
                // Fallback para leitura via Classpath (quando executado a partir do arquivo .jar)
                try (InputStream cpStream = ConnectionFactory.class.getResourceAsStream("/sistemapizzaria/dao/DataBase.properties")) {
                    if (cpStream != null) {
                        props.load(cpStream);
                    } else {
                        props.setProperty("db.url", "jdbc:sqlite:pizzaria.db");
                    }
                }
            }
            properties = props;
        }
    }

    private static void inicializarSchema(Connection con) throws SQLException {
        try (Statement stmt = con.createStatement()) {
            // 1. Tabela de Tipos de Pizza (Simples, Especial, Premium)
            stmt.execute(
                "CREATE TABLE IF NOT EXISTS tipos_pizza (" +
                "  categoria VARCHAR(20) PRIMARY KEY," +
                "  preco_cm2 DOUBLE NOT NULL" +
                ");"
            );

            // 2. Tabela de Sabores (Relacionamento N:1 com tipos_pizza)
            stmt.execute(
                "CREATE TABLE IF NOT EXISTS sabores (" +
                "  id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "  nome VARCHAR(100) NOT NULL UNIQUE," +
                "  categoria VARCHAR(20) NOT NULL," +
                "  FOREIGN KEY (categoria) REFERENCES tipos_pizza(categoria)" +
                ");"
            );

            // 3. Tabela de Clientes
            stmt.execute(
                "CREATE TABLE IF NOT EXISTS clientes (" +
                "  id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "  nome VARCHAR(100) NOT NULL," +
                "  sobrenome VARCHAR(100) NOT NULL," +
                "  telefone VARCHAR(20) NOT NULL UNIQUE" +
                ");"
            );

            // 4. Tabela de Pedidos (Relacionamento 1:N com clientes; ON DELETE CASCADE para requisito 1.c)
            stmt.execute(
                "CREATE TABLE IF NOT EXISTS pedidos (" +
                "  id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "  id_cliente INTEGER NOT NULL," +
                "  preco_total DOUBLE NOT NULL," +
                "  status VARCHAR(20) NOT NULL," +
                "  FOREIGN KEY (id_cliente) REFERENCES clientes(id) ON DELETE CASCADE" +
                ");"
            );

            // 5. Tabela de Pizzas (Mapeamento de Herança de Forma em tabela única com tipo_forma e dimensao - Aula 05)
            stmt.execute(
                "CREATE TABLE IF NOT EXISTS pizzas (" +
                "  id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "  id_pedido INTEGER NOT NULL," +
                "  tipo_forma VARCHAR(30) NOT NULL," +
                "  dimensao DOUBLE NOT NULL," +
                "  FOREIGN KEY (id_pedido) REFERENCES pedidos(id) ON DELETE CASCADE" +
                ");"
            );

            // 6. Tabela associativa pizzas_sabores (Relacionamento N:N entre Pizza e Sabor - Aula 05)
            stmt.execute(
                "CREATE TABLE IF NOT EXISTS pizzas_sabores (" +
                "  id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "  id_pizza INTEGER NOT NULL," +
                "  id_sabor INTEGER NOT NULL," +
                "  FOREIGN KEY (id_pizza) REFERENCES pizzas(id) ON DELETE CASCADE," +
                "  FOREIGN KEY (id_sabor) REFERENCES sabores(id) ON DELETE RESTRICT" +
                ");"
            );
        }

        // Garante que as três categorias obrigatórias existam na tabela tipos_pizza
        String sqlVerifica = "SELECT COUNT(*) FROM tipos_pizza WHERE categoria = ?";
        String sqlInsere = "INSERT INTO tipos_pizza (categoria, preco_cm2) VALUES (?, ?)";
        for (CategoriaTipo cat : CategoriaTipo.values()) {
            try (PreparedStatement stmtVerifica = con.prepareStatement(sqlVerifica)) {
                stmtVerifica.setString(1, cat.name());
                try (ResultSet rs = stmtVerifica.executeQuery()) {
                    if (rs.next() && rs.getInt(1) == 0) {
                        double precoInicial = (cat == CategoriaTipo.SIMPLES) ? 0.05
                                            : (cat == CategoriaTipo.ESPECIAL) ? 0.08 : 0.12;
                        try (PreparedStatement stmtInsere = con.prepareStatement(sqlInsere)) {
                            stmtInsere.setString(1, cat.name());
                            stmtInsere.setDouble(2, precoInicial);
                            stmtInsere.executeUpdate();
                        }
                    }
                }
            }
        }

        // Popula sabores iniciais padrão caso a tabela sabores esteja vazia
        try (Statement stmtCount = con.createStatement();
             ResultSet rsCount = stmtCount.executeQuery("SELECT COUNT(*) FROM sabores")) {
            if (rsCount.next() && rsCount.getInt(1) == 0) {
                String[][] saboresIniciais = {
                    {"Mussarela", CategoriaTipo.SIMPLES.name()},
                    {"Calabresa", CategoriaTipo.SIMPLES.name()},
                    {"Marguerita", CategoriaTipo.SIMPLES.name()},
                    {"Portuguesa", CategoriaTipo.ESPECIAL.name()},
                    {"Frango com Catupiry", CategoriaTipo.ESPECIAL.name()},
                    {"Quatro Queijos", CategoriaTipo.ESPECIAL.name()},
                    {"Camarão", CategoriaTipo.PREMIUM.name()},
                    {"Filé Mignon com Cheddar", CategoriaTipo.PREMIUM.name()}
                };
                String sqlInsereSabor = "INSERT INTO sabores (nome, categoria) VALUES (?, ?)";
                try (PreparedStatement stmtSabor = con.prepareStatement(sqlInsereSabor)) {
                    for (String[] item : saboresIniciais) {
                        stmtSabor.setString(1, item[0]);
                        stmtSabor.setString(2, item[1]);
                        stmtSabor.executeUpdate();
                    }
                }
            }
        }
    }
}
