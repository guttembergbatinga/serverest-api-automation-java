# 🚀 API Automation Framework - ServeRest 🛒

Framework de automação de testes de alta performance desenvolvido para a API [ServeRest](https://serverest.dev/). O projeto cobre os fluxos críticos de **Usuários, Login, Produtos e Carrinhos**, utilizando uma arquitetura escalável e resiliente.

## 🧠 Arquitetura e Decisões Técnicas

Este projeto não é apenas uma sequência de scripts; ele foi desenhado seguindo padrões de engenharia de software para garantir facilidade de manutenção e legibilidade:

### 1. Client Pattern (Abstração de Requisições)
Em vez de espalhar detalhes de HTTP nos testes, cada recurso (Usuario, Produto, Carrinho) possui sua própria classe `Client`.
* **Benefício:** Se o endpoint da API mudar amanhã, você altera em um só lugar.

### 2. Data Factory & Java Faker (Massa Dinâmica)
Utilizamos fábricas de dados (`UsuarioDataFactory`, `ProdutoDataFactory`) integradas à biblioteca **Java Faker**.
* **Estratégia:** Cada execução gera dados únicos. Isso evita falhas por "e-mail já cadastrado" e permite rodar os testes em paralelo sem conflitos.

### 3. JSON Schema Validation (Testes de Contrato)
Implementação de validação de contrato para o recurso de Carrinhos.
* **Diferencial:** O teste garante que a estrutura do JSON (tipos de dados, campos obrigatórios) permaneça íntegra, detectando "breaking changes" no Back-end antes que afetem o Front-end.

### 4. Ciclo de Vida e Hooks (Setup/TearDown)
* **BaseSpec**: Centraliza a `RequestSpecification` com URL base e Content-Type.
* **Hooks Inteligentes**: Uso de `@BeforeMethod` para preparar o cenário (Login/Token) e `@AfterMethod` para realizar o *Cleanup* (limpeza de massa de dados), mantendo a independência entre os testes.

## 🛠️ Tecnologias Utilizadas
- **Java 21**: Recursos modernos de linguagem.
- **RestAssured**: Automação fluida de APIs.
- **TestNG**: Gerenciamento de suítes de teste e concorrência.
- **Lombok**: Redução de código boilerplate nos Models.
- **Jackson**: Serialização e desserialização eficiente de objetos.
- **Maven**: Gestão de dependências e build.

## 📊 Cobertura de Testes

### 🛡️ Testes de Contrato (JSON Schema)
- [x] Validação de Contrato: Cadastro de Carrinho (POST)
- [x] Validação de Contrato: Consulta de Carrinho (GET)

### 🛒 Carrinhos
- [x] Cadastro de carrinho com sucesso.
- [x] Regra de negócio: Proibição de múltiplos carrinhos por usuário.
- [x] Fluxo de conclusão de compra com exclusão automática de carrinho.

### 📦 Produtos & Usuários
- [x] CRUD completo de produtos (necessita privilégios de Admin).
- [x] Gestão de usuários dinâmicos.
- [x] Login e captura de token de autorização.

## 🚀 Como rodar o projeto

1. Clone o repositório:
   ```bash
   git clone [https://github.com/seu-usuario/seu-repositorio.git](https://github.com/seu-usuario/seu-repositorio.git)