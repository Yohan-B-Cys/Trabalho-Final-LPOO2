package sistemapizzaria;

import sistemapizzaria.telas.TelaClientes;

public class Aplicacao {
	public static void main(String[] args) {
		// Instancia SistemaPizzaria que funciona como o núcleo de dados da aplicação
		// Todas as telas irão acessar o mesmo núcleo e irão compartilhar os mesmos dados
		SistemaPizzaria sistema = new SistemaPizzaria();
		
		// Exibe a tela inicial do sistema
		TelaClientes telaInicial = new TelaClientes(sistema);
		telaInicial.setVisible(true);
	}
}
