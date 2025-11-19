# Hexagonal Architecture Maven Archetype

Maven archetype para projetos com arquitetura hexagonal usando Spring Boot 3.5, WebFlux, R2DBC, H2 e Flyway.

## Tecnologias Incluídas

- **Spring Boot 3.5** - Framework principal
- **Spring WebFlux** - Programação reativa
- **Spring Data R2DBC** - Acesso reativo ao banco
- **H2 Database** - Banco em memória
- **Flyway** - Migração de banco de dados
- **Spring Boot Actuator** - Monitoramento
- **Lombok** - Redução de boilerplate
- **MapStruct** - Mapeamento entre objetos
- **JaCoCo** - Coverage de código (80% mínimo)
- **OpenTelemetry** - Observabilidade (traces, metrics, logs)
- **Java 21** - Versão LTS mais recente

## Instalação do Archetype

```bash
# 1. Compilar e instalar o archetype localmente
cd hexagonal-archetype
mvn clean install

# 2. Verificar instalação
mvn archetype:generate -DarchetypeCatalog=local
```

## Uso do Archetype

```bash
mvn archetype:generate \
  -DarchetypeGroupId=com.example \
  -DarchetypeArtifactId=hexagonal-archetype \
  -DarchetypeVersion=1.0.0 \
  -DgroupId=com.mycompany \
  -DartifactId=my-hexagonal-project \
  -Dversion=1.0.0-SNAPSHOT \
  -Dpackage=com.mycompany.myhexagonalproject
```

## Estrutura Gerada

```
my-hexagonal-project/
├── src/main/java/com/mycompany/myhexagonalproject/
│   ├── domain/
│   │   ├── model/Product.java
│   │   ├── port/
│   │   │   ├── in/CreateProductUseCase.java
│   │   │   └── out/ProductRepository.java
│   │   ├── service/ProductDomainService.java
│   │   └── exception/ProductNotFoundException.java
│   ├── application/
│   │   └── usecase/CreateProductUseCaseImpl.java
│   ├── infrastructure/
│   │   ├── adapter/
│   │   │   ├── in/
│   │   │   │   ├── ProductController.java
│   │   │   │   ├── dto/
│   │   │   │   │   ├── CreateProductRequest.java
│   │   │   │   │   └── ProductResponse.java
│   │   │   │   └── mapper/ProductControllerMapper.java
│   │   │   └── out/
│   │   │       ├── ProductRepositoryImpl.java
│   │   │       └── mapper/ProductPersistenceMapper.java
│   │   └── persistence/
│   │       ├── ProductEntity.java
│   │       └── ProductR2dbcRepository.java
│   └── Application.java
├── src/main/resources/
│   ├── db/migration/V1__Create_products_table.sql
│   └── application.yml
└── pom.xml
```

## Executar Projeto Gerado

```bash
cd my-hexagonal-project
mvn spring-boot:run
```

## Testes e Coverage

```bash
# Executar testes
mvn test

# Gerar relatório de coverage
mvn verify

# Ver relatório HTML
open target/site/jacoco/index.html
```

**Coverage mínimo configurado: 80%**

O build falhará se o coverage for menor que 80%.

## Endpoints Disponíveis

- **POST** `/api/products` - Criar produto
- **GET** `/actuator/health` - Health check
- **GET** `/actuator/metrics` - Métricas
- **GET** `/h2-console` - Console H2 (desenvolvimento)

## Exemplo de Uso da API

```bash
# Criar produto
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{"name": "Produto Teste", "price": 29.99}'

# Health check
curl http://localhost:8080/actuator/health
```

## Características da Arquitetura

### Camada de Domínio
- **Model**: Entidades de negócio puras (com Lombok)
- **Ports**: Interfaces que definem contratos
- **Services**: Lógica de negócio complexa
- **Exceptions**: Exceções específicas do domínio

### Camada de Aplicação
- **Use Cases**: Orquestração de casos de uso
- Implementa as portas de entrada do domínio

### Camada de Infraestrutura
- **Adapters In**: Controllers REST (com Lombok)
- **Adapters Out**: Implementações de repositórios (com Lombok)
- **Persistence**: Entidades JPA e repositórios Spring Data (com Lombok)
- **Config**: Configurações do Spring

### Lombok Features Utilizadas
- `@Data` - Getters, setters, toString, equals, hashCode
- `@NoArgsConstructor` / `@AllArgsConstructor` - Construtores
- `@RequiredArgsConstructor` - Injeção de dependência via construtor

### MapStruct Features Utilizadas
- **ProductControllerMapper** - Mapeia entre entidades de domínio e DTOs REST
- **ProductPersistenceMapper** - Mapeia entre entidades de domínio e entidades JPA
- Geração automática de código em tempo de compilação
- Integração com Lombok via `lombok-mapstruct-binding`

## Configurações Padrão

### Banco H2
- URL: `r2dbc:h2:mem:///testdb`
- Console: `http://localhost:8080/h2-console`
- User: `sa` / Password: (vazio)

### Flyway
- Migrations em: `src/main/resources/db/migration/`
- Padrão: `V{version}__{description}.sql`

### Actuator
- Endpoints expostos: `health`, `info`, `metrics`
- Health details: sempre visível

## Próximos Passos

1. Adicionar mais entidades de domínio
2. Implementar novos casos de uso
3. Configurar banco de dados real
4. Adicionar testes automatizados
5. Configurar CI/CD

## Observabilidade com OpenTelemetry

### Stack de Observabilidade
- **OpenTelemetry** - Traces, métricas e logs
- **Jaeger** - Distributed tracing
- **Prometheus** - Coleta de métricas
- **Grafana** - Visualização e dashboards

### Executar Stack de Observabilidade

```bash
# Subir stack completa
docker-compose up -d

# Verificar serviços
docker-compose ps
```

### Acessar Interfaces

- **Aplicação**: http://localhost:8080
- **Jaeger UI**: http://localhost:16686
- **Prometheus**: http://localhost:9090
- **Grafana**: http://localhost:3000 (admin/admin)

### Métricas Disponíveis

- **Traces**: Rastreamento de requests end-to-end
- **Métricas**: Performance, latência, throughput
- **Logs**: Logs estruturados com trace correlation

### Exemplo de Uso

```bash
# Criar produto (gera traces)
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{"name": "Produto Observado", "price": 99.99}'

# Ver traces no Jaeger: http://localhost:16686
# Ver métricas no Grafana: http://localhost:3000
```
