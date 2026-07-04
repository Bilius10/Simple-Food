# 🍏 Simple Food

Um sistema completo, inteligente para rastreamento de calorias. Esqueça a digitação manual de alimentos: basta enviar uma foto ou texto para o WhatsApp, e nossa IA integrada faz todo o trabalho de identificar os alimentos, calcular as calorias e registrar no seu dashboard.

## 🚀 Arquitetura e Tecnologias

Este projeto foi desenhado para ser fácil de rodar em qualquer ambiente, utilizando arquitetura baseada em microsserviços com Docker.

* **Frontend:** Thymeleaf (Dashboard para visualização de dados e gestão da conta).
* **Backend:** Java com Spring Boot (API RESTful).
* **Banco de Dados:** PostgreSQL.
* **Mensageria/WhatsApp:** WAHA (WhatsApp HTTP API).
* **Orquestração e IA:** n8n (Gerencia o fluxo de recebimento via WAHA, processamento da IA e integração com a API externa).
* **Base de Dados de Alimentos:** Base de dados da TACO.

## ⚙️ Pré-requisitos

Para rodar este projeto na sua máquina, você precisará de:

1. [Docker](https://www.docker.com/) e [Docker Compose](https://docs.docker.com/compose/) instalados.
2. Uma chave de API para o modelo de IA (ex: OpenAI, Gemini ou Groq) para o processamento de imagens e texto no n8n.
3. [WSL 2](https://learn.microsoft.com/en-us/windows/wsl/install) com Ubuntu configurado (para usuários Windows).

## 🛠️ Passo a Passo para Rodar Localmente

### 1. Configurar o WSL com Ubuntu (Windows)

Se você estiver usando Windows, siga os passos abaixo para configurar o WSL:

1. Habilite o WSL no Windows:
   * Abra o PowerShell como administrador e execute:
     ```powershell
     wsl --install
     ```
2. Reinicie o computador, se necessário.
3. Instale o Ubuntu:
   * Abra a Microsoft Store, procure por "Ubuntu" e instale a versão desejada.
4. Configure o Ubuntu:
   * Abra o Ubuntu instalado e siga as instruções para criar um usuário e senha.

### 2. Clonar o Repositório

No terminal do Ubuntu ou no terminal do Windows com WSL, clone o repositório do projeto:
```bash
git clone <URL_DO_REPOSITORIO>
cd Simple-Food
```

### 3. Subir os Contêineres com Docker Compose

Certifique-se de estar na raiz do projeto e execute o comando abaixo para subir os serviços:
```bash
docker-compose up -d
```
Isso irá iniciar os contêineres do backend, banco de dados e outros serviços necessários.

### 4. Restaurar o Banco de Dados

Ap��s os contêineres estarem em execução, restaure o dump do banco de dados:

1. Acesse o contêiner do PostgreSQL:
   ```bash
   docker exec -it <NOME_DO_CONTAINER_POSTGRES> bash
   ```
2. Dentro do contêiner, execute o comando para restaurar o dump:
   ```bash
   psql -U <USUARIO> -d <NOME_DO_BANCO> -f /caminho/para/dump_dados.sql
   ```
   Substitua `<USUARIO>` e `<NOME_DO_BANCO>` pelos valores corretos configurados no `docker-compose.yml`.

### 5. Acessar o Sistema

Com todos os serviços em execução, você pode acessar o sistema:

* **Frontend (Dashboard):** Acesse `http://localhost:4200` no navegador.
* **Backend (API):** Acesse `http://localhost:8080` para verificar a API.

### 6. Parar os Contêineres

Quando terminar de usar o sistema, você pode parar os contêineres com:
```bash
docker-compose down
```

## 📝 Notas Adicionais

* Certifique-se de que as portas necessárias (4200, 8080, etc.) estão livres antes de iniciar os contêineres.
* Para mais informações sobre o projeto, consulte a documentação interna ou entre em contato com os desenvolvedores.
