package sistemapizzaria;

import java.awt.EventQueue;

import sistemapizzaria.telas.TelaClientes;

public class Aplicacao {
	public static void main(String[] args) {
		// Instancia SistemaPizzaria que funciona como o núcleo de dados da aplicação
		// Todas as telas irão acessar o mesmo núcleo e irão compartilhar os mesmos dados
		// Boa prática do Java Swing: Iniciar a interface gráfica na fila de eventos correta
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // 1. Instancia o núcleo de dados real (agora não é mais null!)
                    SistemaPizzaria sistema = new SistemaPizzaria();
                    
                    // 2. Passa o sistema para a tela inicial
                    TelaClientes telaInicial = new TelaClientes(sistema);
                    
                    // 3. Centraliza a tela no monitor e a torna visível
                    telaInicial.setLocationRelativeTo(null);
                    telaInicial.setVisible(true);
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
	}
}
