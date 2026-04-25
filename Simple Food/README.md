# 🍏 Simple Food

Um sistema completo, inteligente para rastreamento de calorias. Esqueça a digitação manual de alimentos: basta enviar uma foto ou texto para o WhatsApp, e nossa IA integrada faz todo o trabalho de identificar os alimentos, calcular as calorias e registrar no seu dashboard.

## 🚀 Arquitetura e Tecnologias

Este projeto foi desenhado para ser fácil de rodar em qualquer ambiente, utilizando arquitetura baseada em microsserviços com Docker.

* **Frontend:** Angular (Dashboard para visualização de dados e gestão da conta).
* **Backend:** Java com Spring Boot (API RESTful).
* **Banco de Dados:** PostgreSQL.
* **Mensageria/WhatsApp:** WAHA (WhatsApp HTTP API).
* **Orquestração e IA:** n8n (Gerencia o fluxo de recebimento via WAHA, processamento da IA e integração com a API externa).
* **Base de Dados de Alimentos:** Open Food Facts (API global e open-source para consulta nutricional).

## ⚙️ Pré-requisitos

Para rodar este projeto na sua máquina, você precisará apenas de:
* [Docker](https://www.docker.com/) e [Docker Compose](https://docs.docker.com/compose/) instalados.
* Uma chave de API para o modelo de IA (ex: OpenAI, Gemini ou Groq) para o processamento de imagens e texto no n8n.

