# Relatório Final
# Introdução
O aplicativo "Gerenciar Compras" foi desenvolvido para facilitar o cadastro e a gestão de itens que o usuário deseja comprar. 
Com uma interface simples e intuitiva, ele permite que os usuários insiram informações sobre os produtos, como nome, 
quantidade e prioridade, ajudando na organização das compras e na tomada de decisões.

# Estrutura de Dados
Entidades:

ItemCompra: Representa um item que o usuário deseja comprar. Contém os seguintes atributos:
nome: Nome do item.
quantidades: Quantidade desejada do item.
prioridade: Nível de prioridade do item (1 para alta prioridade, 2 para média, etc.).
DAOs (Data Access Objects):

ItemCompraDao: Interface que define métodos para interagir com a base de dados:
salvarItemCompra(item: ItemCompra): Insere um novo item na base de dados.
buscarTodosItens(): Retorna todos os itens cadastrados.
atualizarItemCompra(item: ItemCompra): Atualiza um item existente.
excluirItemCompra(item: ItemCompra): Remove um item da base de dados.
Banco de Dados:

AppDatabase: Classe que configura o banco de dados utilizando Room. É responsável por fornecer 
acesso à instância do banco de dados e garantir que apenas uma instância seja criada (Singleton).
Fluxos de Operações
Inserção: O usuário insere o nome, quantidade e prioridade do item em campos de texto. 
Ao clicar no botão "Adicionar Item", o aplicativo cria um novo objeto ItemCompra e o salva no banco de dados. 
Após a inserção, a lista de itens é atualizada e um feedback é exibido ao usuário.
Consulta: Ao iniciar o aplicativo, todos os itens existentes são carregados do banco de dados e exibidos em uma lista (LazyColumn). 
Cada item é apresentado com seu nome, quantidade e prioridade.
Exclusão: Embora não esteja detalhado no código fornecido, a exclusão pode ser feita a partir de um 
método na interface DAO e um botão de exclusão na interface, permitindo que o usuário remova itens indesejados.
Soluções Criativas
Feedback ao Usuário: O aplicativo utiliza Toast para informar ao usuário sobre o sucesso ou falha nas operações, melhorando a experiência do usuário.
Prioridade: Implementar um sistema de prioridade permite que os usuários organizem melhor suas compras, focando primeiro nos itens mais importantes.
# Conclusão
O desenvolvimento do aplicativo "Gerenciar Compras" proporcionou uma oportunidade valiosa de aplicar conceitos de programação em Android, 
especialmente utilizando a biblioteca Room para persistência de dados. Os principais desafios enfrentados incluíram a configuração do 
banco de dados e a implementação de uma interface de usuário responsiva. O resultado final é um aplicativo funcional 
que pode ser expandido com mais recursos, como edição de itens e categorização de compras. Essa experiência fortaleceu o 
entendimento sobre desenvolvimento mobile e práticas de design de software.
