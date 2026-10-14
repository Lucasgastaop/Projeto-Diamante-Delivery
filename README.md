# delivery-system

Sistema de pedidos de delivery com microsserviços (Projeto Diamante 02).

## Integrantes

- Lucas Silva Gastão Pinheiro — RM 563960
- Geovanne Coneglian Passos — RM 562673
- Guilherme Soares de Almeida — RM 563143

## Como subir

Pré-requisitos: Docker, JDK 25 (o Gradle baixa o JDK 25 pela toolchain se ele ainda não estiver instalado) e o Ollama para o assistente.

1. Na raiz do repositório, suba o RabbitMQ:

```bash
docker compose up -d
```

2. Em terminais separados, suba os quatro serviços:

```bash
./gradlew :eureka-server:bootRun
./gradlew :order-service:bootRun
./gradlew :payment-service:bootRun
./gradlew :payment-service:bootRun --args='--server.port=8082'
./gradlew :review-service:bootRun
```

No Windows, troque `./gradlew` por `gradlew.bat`.

A segunda instância do pagamento usa a mesma aplicação na porta 8082. As duas se registram no Eureka com o nome `payment-service`.

## Portas

| Serviço | Porta |
| --- | --- |
| eureka-server | 8761 |
| order-service | 8080 |
| payment-service | 8081 e 8082 |
| review-service | 8083 |
| RabbitMQ | 5672 (management 15672) |

O painel do Eureka fica em http://localhost:8761. O management do RabbitMQ fica em http://localhost:15672 (guest/guest).

Chamadas entre serviços usam o nome registrado no Eureka (`http://PAYMENT-SERVICE/payments`). `localhost` aparece só na URL do Eureka e do RabbitMQ, porque esses processos rodam na máquina de desenvolvimento.

## Assistente

O assistente usa um modelo local gratuito via Ollama. Nenhuma chave de API vai para o repositório. O padrão já aponta para `http://localhost:11434/v1` e para o modelo `llama3.2`.

Antes de subir o `order-service`:

```bash
ollama pull llama3.2
```

No Windows, o aplicativo do Ollama sobe o servidor sozinho. Veja `.env.example` se quiser trocar o modelo.

O app React Native do professor (https://github.com/joaocarloslima/diamante-delivery) consome as portas 8080 e 8083.
