# Cardápio Digital para Restaurantes

Sistema de gerenciamento de cardápio digital desenvolvido em Java com Quarkus, composto por dois microserviços que se comunicam entre si.

## 📋 Descrição do Projeto

Este projeto implementa um sistema de cardápio digital onde:
- **Serviço de Categorias** (porta 8080): Gerencia as categorias de pratos (entradas, pratos principais, sobremesas, etc.)
- **Serviço de Pratos** (porta 8081): Gerencia os pratos do cardápio e consome a API de categorias
- **Frontend** (porta 3000): Interface web simples para testar os serviços

## 🏗️ Arquitetura

```
┌─────────────────┐
│    Frontend     │
│   (Nginx:3000)  │
└────────┬────────┘
         │
    ┌────┴─────┐
    │          │
┌───▼────┐ ┌──▼──────┐
│Serviço │ │ Serviço │
│Categ.  │◄┤ Pratos  │
│(8080)  │ │ (8081)  │
└───┬────┘ └───┬─────┘
    │          │
┌───▼────┐ ┌──▼──────┐
│Postgres│ │Postgres │
│5432    │ │5433     │
└────────┘ └─────────┘
```

## 🛠️ Tecnologias Utilizadas

- Java 17
- Quarkus 3.6.4
- PostgreSQL 15
- Docker & Docker Compose
- Maven
- Hibernate/Panache
- REST Client (MicroProfile)

## 📁 Estrutura do Projeto

```
cardapio-digital/
├── docker-compose.yml
├── servico-categorias/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
│       └── main/
│           ├── java/br/edu/cardapio/
│           │   ├── model/
│           │   │   └── Categoria.java
│           │   ├── repository/
│           │   │   └── CategoriaRepository.java
│           │   ├── service/
│           │   │   └── CategoriaService.java
│           │   └── controller/
│           │       └── CategoriaController.java
│           └── resources/
│               └── application.properties
├── servico-pratos/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
│       └── main/
│           ├── java/br/edu/cardapio/
│           │   ├── model/
│           │   │   └── Prato.java
│           │   ├── dto/
│           │   │   └── PratoComCategoriaDTO.java
│           │   ├── repository/
│           │   │   └── PratoRepository.java
│           │   ├── service/
│           │   │   └── PratoService.java
│           │   ├── controller/
│           │   │   └── PratoController.java
│           │   └── client/
│           │       └── CategoriaClient.java
│           └── resources/
│               └── application.properties
└── frontend/
    └── index.html
```

## 🚀 Como Executar

### Pré-requisitos

- Docker
- Docker Compose

### Passos

1. **Clone ou crie a estrutura do projeto** conforme indicado acima

2. **Inicie todos os serviços:**
```bash
docker-compose up -d
```

3. **Aguarde o build e inicialização** (primeira execução pode levar alguns minutos)

4. **Acesse as aplicações:**
   - Frontend: http://localhost:3000
   - API Categorias: http://localhost:8080/categorias
   - API Pratos: http://localhost:8081/pratos

### Verificar logs

```bash
# Ver logs de todos os serviços
docker-compose logs -f

# Ver logs de um serviço específico
docker-compose logs -f servico-categorias
docker-compose logs -f servico-pratos
```

### Parar os serviços

```bash
docker-compose down
```

### Limpar volumes (apaga banco de dados)

```bash
docker-compose down -v
```

## 📡 Endpoints da API

### Serviço de Categorias (8080)

```
GET    /categorias           - Lista todas as categorias
GET    /categorias/{id}      - Busca categoria por ID
POST   /categorias           - Cria nova categoria
PUT    /categorias/{id}      - Atualiza categoria
DELETE /categorias/{id}      - Remove categoria
```

**Exemplo de body (POST/PUT):**
```json
{
  "nome": "Entradas",
  "descricao": "Pratos para começar a refeição"
}
```

### Serviço de Pratos (8081)

```
GET    /pratos                        - Lista todos os pratos
GET    /pratos?categoriaId={id}       - Filtra pratos por categoria
GET    /pratos?disponivel=true        - Filtra pratos disponíveis
GET    /pratos/{id}                   - Busca prato por ID
POST   /pratos                        - Cria novo prato
PUT    /pratos/{id}                   - Atualiza prato
DELETE /pratos/{id}                   - Remove prato
```

**Exemplo de body (POST/PUT):**
```json
{
  "nome": "Filé ao Molho Madeira",
  "descricao": "Filé mignon ao molho madeira com batatas",
  "preco": 45.90,
  "categoriaId": 1,
  "urlImagem": "https://exemplo.com/imagem.jpg",
  "disponivel": true
}
```

## 🧪 Testando com cURL

### Criar uma categoria
```bash
curl -X POST http://localhost:8080/categorias \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Pratos Principais",
    "descricao": "Refeições completas"
  }'
```

### Criar um prato
```bash
curl -X POST http://localhost:8081/pratos \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Lasanha Bolonhesa",
    "descricao": "Lasanha tradicional ao molho bolonhesa",
    "preco": 35.00,
    "categoriaId": 1,
    "disponivel": true
  }'
```

### Listar pratos com categoria
```bash
curl http://localhost:8081/pratos
```

## 🎯 Funcionalidades Implementadas

### Serviço de Categorias
- ✅ CRUD completo de categorias
- ✅ Validação de dados
- ✅ Persistência em PostgreSQL
- ✅ API RESTful com JSON

### Serviço de Pratos
- ✅ CRUD completo de pratos
- ✅ Comunicação com serviço de categorias via REST Client
- ✅ Validação de categoria antes de criar/atualizar prato
- ✅ Filtros por categoria e disponibilidade
- ✅ DTO para enriquecer resposta com dados da categoria
- ✅ Persistência em PostgreSQL separado

### Frontend
- ✅ Interface para gerenciar categorias
- ✅ Interface para gerenciar pratos
- ✅ Visualização do cardápio completo
- ✅ Filtros por categoria e disponibilidade
- ✅ Design responsivo

## 🔧 Configuração

### Variáveis de Ambiente

As configurações podem ser ajustadas no `docker-compose.yml`:

- Portas dos serviços
- Credenciais do PostgreSQL
- URLs de comunicação entre serviços

### application.properties

Cada serviço tem seu próprio arquivo de configuração em `src/main/resources/application.properties`.

## 📚 Padrões Utilizados

- **MVC (Model-View-Controller)**: Separação em camadas
- **Repository Pattern**: Acesso a dados
- **Service Layer**: Lógica de negócio
- **DTO Pattern**: Transferência de dados entre serviços
- **REST Client**: Comunicação entre microserviços

## 🐛 Troubleshooting

### Erro de conexão entre serviços

Certifique-se de que todos os containers estão na mesma rede:
```bash
docker network inspect cardapio-digital_cardapio-network
```

### Banco de dados não inicializa

Remova os volumes e recrie:
```bash
docker-compose down -v
docker-compose up -d
```

### Porta já em uso

Altere as portas no `docker-compose.yml` se necessário.

## 📝 Referências

- [Quarkus Documentation](https://quarkus.io/guides/)
- [Quarkus REST Client](https://quarkus.io/guides/rest-client-reactive)
- [Hibernate Panache](https://quarkus.io/guides/hibernate-orm-panache)

## 👨‍💻 Autor

Projeto desenvolvido para a disciplina de Programação para Web II

## 📄 Licença

Este projeto é de código aberto para fins educacionais.