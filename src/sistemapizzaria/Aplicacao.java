package sistemapizzaria;

import java.awt.EventQueue;

import sistemapizzaria.telas.TelaClientes;

public class Aplicacao {
	public static void main(String[] args) {
		// SistemaPizzaria é o núcleo de dados da aplicação
		// Todas as telas irão acessar o mesmo núcleo e irão compartilhar os mesmos dados

        // Iniciar GUI na fila de eventos correta para evitar problemas de sincronização 
        // devido os componentes não terem segurança de threads nativas
        EventQueue.invokeLater(new Runnable() { 
            public void run() {
                try {
                    // Instancia o núcleo de dados da aplicação
                    SistemaPizzaria sistema = new SistemaPizzaria();
                    
                    // Passa o sistema para a tela inicial
                    TelaClientes telaInicial = new TelaClientes(sistema);
                    
                    // Centraliza a tela no monitor e a torna visível
                    telaInicial.setLocationRelativeTo(null);
                    telaInicial.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
	}
}
