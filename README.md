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

## Observabilidade com OpenTelemetry e Dynatrace

### Stack de Observabilidade
- **OpenTelemetry** - Traces, métricas e logs
- **Dynatrace** - APM completo (substitui Jaeger, Prometheus e Grafana)
- **Instrumentação Manual** - Sem AspectJ, controle total sobre métricas

### Abordagem de Instrumentação

O archetype usa **instrumentação manual** ao invés de AspectJ para máximo controle e performance:

```java
// Exemplo de instrumentação manual
return Observation.createNotStarted("product.create", observationRegistry)
    .observe(() -> {
        // Sua lógica de negócio aqui
        return productRepository.save(product);
    });
```

**Vantagens:**
- Sem dependências do AspectJ
- Controle total sobre onde instrumentar
- Performance superior (sem proxies)
- Facilita debugging e manutenção

### Configuração do Dynatrace

#### 1. Configurar Variáveis de Ambiente

Copie o arquivo `.env.example` para `.env` e configure:

```bash
cp .env.example .env
```

Edite o arquivo `.env` com suas credenciais do Dynatrace:

```bash
# Dynatrace Configuration
DYNATRACE_API_TOKEN=dt0c01.ST2EY72KQINMH613...
DYNATRACE_URI=https://abc12345.live.dynatrace.com/api/v2/metrics/ingest
DYNATRACE_OTLP_ENDPOINT=https://abc12345.live.dynatrace.com/api/v2/otlp/v1/traces
DYNATRACE_ENVIRONMENT=production
DYNATRACE_SERVICE_VERSION=1.0.0
```

#### 2. Executar com Perfil Dynatrace

```bash
# Executar aplicação com Dynatrace
mvn spring-boot:run -Dspring-boot.run.profiles=dynatrace

# Ou definir via variável de ambiente
export SPRING_PROFILES_ACTIVE=dynatrace
mvn spring-boot:run
```

#### 3. Gerar API Token no Dynatrace

1. Acesse seu ambiente Dynatrace
2. Vá em **Settings > Integration > Dynatrace API**
3. Gere um token com as permissões:
   - `metrics.ingest` (Ingest metrics)
   - `openTelemetryTrace.ingest` (Ingest OpenTelemetry traces)
   - `logs.ingest` (Ingest logs)

#### 4. Configurar Endpoints

**Para SaaS:**
```
DYNATRACE_URI=https://{environment-id}.live.dynatrace.com/api/v2/metrics/ingest
DYNATRACE_OTLP_ENDPOINT=https://{environment-id}.live.dynatrace.com/api/v2/otlp/v1/traces
```

**Para Managed:**
```
DYNATRACE_URI=https://{domain}/e/{environment-id}/api/v2/metrics/ingest
DYNATRACE_OTLP_ENDPOINT=https://{domain}/e/{environment-id}/api/v2/otlp/v1/traces
```

### Métricas Disponíveis

- **product.create** - Criação de produtos (UseCase)
- **product.controller.create** - Endpoint REST
- **product.repository.save/findById/findAll** - Operações de persistência

### Exemplo de Uso

```bash
# Criar produto (gera traces e métricas)
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{"name": "Produto Observado", "price": 99.99}'

# Ver dados no Dynatrace: acesse seu ambiente configurado
# Traces, métricas e logs estarão disponíveis automaticamente
```
