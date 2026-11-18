package sistemapizzaria;

import java.awt.EventQueue;
import java.sql.Connection;
import javax.swing.JOptionPane;

import sistemapizzaria.dao.ConnectionFactory;
import sistemapizzaria.telas.TelaClientes;

public class Aplicacao {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // Inicializa a conexão e garante a criação das tabelas no SQLite
                    try (Connection con = ConnectionFactory.getConnection()) {
                        // Banco inicializado com sucesso
                    }

                    TelaClientes telaInicial = new TelaClientes();
                    telaInicial.setLocationRelativeTo(null);
                    telaInicial.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(
                        null,
                        "Erro ao inicializar o banco de dados: " + e.getMessage(),
                        "Erro de Inicialização",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
    }
}
