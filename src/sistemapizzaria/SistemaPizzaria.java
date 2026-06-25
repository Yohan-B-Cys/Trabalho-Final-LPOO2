package sistemapizzaria;

import java.util.ArrayList;
import java.util.List;

import sistemapizzaria.cliente.*;
import sistemapizzaria.pizza.*;

// Representa a classe principal de gerenciamento do sistema

// Agrupa as instâncias de clientes, sabores e a tabela de preços do negócio.
public class SistemaPizzaria {

    private List<Cliente> clientes;
    private List<Sabor> saboresDisponiveis;
    // Armazena a relação de categorias de pizzas e seus respectivos preços por centímetro quadrado.
    private List<TipoPizza> tabelaPrecos;

    public SistemaPizzaria() {
        this.clientes = new ArrayList<>();
        this.saboresDisponiveis = new ArrayList<>();
        this.tabelaPrecos = new ArrayList<>();
        
        // Adiciona as três categorias obrigatórias de tipos de pizza à tabela de preços.
        this.tabelaPrecos.add(new TipoPizza(CategoriaTipo.SIMPLES, 0.0));
        this.tabelaPrecos.add(new TipoPizza(CategoriaTipo.ESPECIAL, 0.0));
        this.tabelaPrecos.add(new TipoPizza(CategoriaTipo.PREMIUM, 0.0));
    }

    public void cadastrarCliente(Cliente c) {
        if (c != null) {
            // Verifica se a referência passada não é nula para evitar falhas de consistência
            this.clientes.add(c);
        }
    }

    public void removerCliente(Cliente c) {
        if (c != null) {
            this.clientes.remove(c);
        }
    }

    public List<Cliente> buscarClientes(String filtro) {
        // Retorna uma lista de clientes que correspondem ao filtro especificado.
        List<Cliente> resultado = new ArrayList<>();
        
        // Retorna uma cópia integral da lista de clientes caso o filtro seja nulo ou composto apenas de espaços em branco.
        if (filtro == null || filtro.trim().isEmpty()) {
            return new ArrayList<>(this.clientes);
        }
        
        // Converte o termo de busca para letras minúsculas e remove espaços residuais nas extremidades.
        String filtroFormatado = filtro.trim().toLowerCase();
        
        // Percorre cada cliente na base de dados para testar a compatibilidade com o filtro.
        for (Cliente cliente : this.clientes) {
            
            // Verifica se o sobrenome existe e se contém o termo de busca especificado.
            boolean matchesSobrenome = cliente.getSobrenome() != null && 
                                       cliente.getSobrenome().toLowerCase().contains(filtroFormatado);
            
            // Verifica se o telefone existe e se contém o termo de busca especificado.
            boolean matchesTelefone = cliente.getTelefone() != null && 
                                      cliente.getTelefone().contains(filtroFormatado);
            
            // Adiciona o cliente à lista de resultados se ele atender a pelo menos uma das condições acima.
            if (matchesSobrenome || matchesTelefone) {
                resultado.add(cliente);
            }
        }
        // Retorna a lista contendo apenas os clientes filtrados.
        return resultado;
    }

    // Busca um cliente específico utilizando o número de telefone como chave de identificação exata.
    // Retorna a instância do cliente correspondente ou valor nulo se não houver correspondência.
    public Cliente buscarClientePorTelefone(String telefone) {
        // Verifica se a string fornecida é inválida ou está vazia
        if (telefone == null || telefone.trim().isEmpty()) {
            return null;
        }
        
        // Itera sobre a lista de clientes comparando o telefone exato, removendo espaços desnecessários.
        for (Cliente cliente : this.clientes) {
            if (cliente.getTelefone() != null && cliente.getTelefone().equals(telefone.trim())) {
                return cliente;
            }
        }
        // Retorna nulo caso a varredura termine sem encontrar um cliente com o telefone informado.
        return null;
    }
    
    // Insere um novo sabor na coleção de sabores disponíveis, garantindo que o objeto passado seja válido.
    public void cadastrarSabor(Sabor s) {
        if (s != null) {
            this.saboresDisponiveis.add(s);
        }
    }

    // Atualiza o valor do centímetro quadrado associado a uma categoria específica de pizza.
    public void atualizarPreco(CategoriaTipo categoria, double novoPreco) {
        // Percorre a tabela de preços para localizar a categoria correta.
        for (TipoPizza tipo : this.tabelaPrecos) {
            // Compara as enumerações para confirmar a igualdade de categorias.
            if (tipo.getCategoria() == categoria) {
                // Aplica a mudança de preço no objeto correspondente.
                tipo.atualizarPrecoPorCm2(novoPreco);
                break; 
            }
        }
    }

    // Retorna uma lista global contendo todos os pedidos realizados, independentemente do cliente.
    public List<Pedido> listarTodosPedidos() {
        List<Pedido> todosPedidos = new ArrayList<>();
        
        // Itera pela base de clientes cadastrados.
        for (Cliente cliente : this.clientes) {
            // Verifica se a lista de pedidos do cliente atual é válida.
            if (cliente.getPedidos() != null) {
                // Adiciona em massa todos os pedidos daquele cliente na lista unificada.
                todosPedidos.addAll(cliente.getPedidos());
            }
        }
        // Retorna a relação completa de pedidos do sistema.
        return todosPedidos;
    }

    // Retorna uma cópia da lista de clientes para ser consumida pela interface gráfica.
    // Evita modificações indevidas na estrutura original
    public List<Cliente> getClientes() {
        return new ArrayList<>(this.clientes);
    }

    // Retorna uma cópia da lista de sabores disponíveis para montagem de componentes visuais
    public List<Sabor> getSaboresDisponiveis() {
        return new ArrayList<>(this.saboresDisponiveis);
    }

    // Retorna uma cópia da tabela de preços para exibição ou consulta.
    public List<TipoPizza> getTabelaPrecos() {
        return new ArrayList<>(this.tabelaPrecos);
    }
}
