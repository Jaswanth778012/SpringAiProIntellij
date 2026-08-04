# 🤖 Spring AI + Google Gemini Chat Application

A Spring Boot application that demonstrates how to integrate **Spring AI with Google Gemini** to build an AI-powered chat service.

This project explores several important Spring AI concepts including **ChatClient, prompt templates, chat memory, advisors, streaming responses, and Google Gemini integration**.

---

## 🚀 Features

* 🤖 Google Gemini integration using Spring AI
* 💬 AI-powered chat API
* 🧠 Persistent conversation memory using JDBC
* 👤 Conversation tracking using a `userId`
* 📝 Prompt templates using external `.st` files
* 🌊 Streaming AI responses using `Flux<String>`
* 🛡️ Safe Guard Advisor for filtering configured topics
* 📋 Simple Logger Advisor for request/response logging
* 🔢 Token-related advisor support
* ⚙️ Centralized `ChatClient` configuration
* 🌡️ Configurable Gemini temperature
* 🗄️ MySQL database integration for chat memory

---

## 🛠️ Technologies Used

| Technology     | Version / Details             |
| -------------- | ----------------------------- |
| Java           | 17                            |
| Spring Boot    | 4.1.0                         |
| Spring AI      | 2.0.0                         |
| Google Gemini  | Gemini 3.5 Flash              |
| Spring Web MVC | REST APIs                     |
| MySQL          | 8.x                           |
| Maven          | Build & dependency management |
| Reactor        | Reactive streaming            |

The project dependencies and versions are defined in `pom.xml`.

---

## 🏗️ Project Architecture

The application follows a simple layered architecture:

```text
Client
   │
   ▼
ChatController
   │
   ▼
ChatService
   │
   ▼
ChatServiceImpl
   │
   ▼
Spring AI ChatClient
   │
   ├── Message Chat Memory Advisor
   ├── Simple Logger Advisor
   ├── Safe Guard Advisor
   │
   ▼
Google Gemini
   │
   ▼
AI Response
```

For streaming requests:

```text
Client
   │
   ▼
/AI/Stream-chat
   │
   ▼
ChatServiceImpl
   │
   ▼
ChatClient.stream()
   │
   ▼
Flux<String>
   │
   ▼
Client receives response chunks
```

---

## 📁 Project Structure

```text
SpringAiProIntellij/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── kodnest/
│   │   │           └── springaiprointellij/
│   │   │               │
│   │   │               ├── advisors/
│   │   │               │   └── TokenPrintAdvisor.java
│   │   │               │
│   │   │               ├── configuration/
│   │   │               │   └── ChatConfig.java
│   │   │               │
│   │   │               ├── controller/
│   │   │               │   └── ChatController.java
│   │   │               │
│   │   │               ├── service/
│   │   │               │   ├── ChatService.java
│   │   │               │   └── ChatServiceImpl.java
│   │   │               │
│   │   │               └── SpringAiProIntellijApplication.java
│   │   │
│   │   └── resources/
│   │       ├── prompt/
│   │       │   └── userPrompt.st
│   │       │
│   │       └── application.properties
│   │
│   └── test/
│
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

The repository currently contains separate packages for controllers, services, configuration, and advisors.

---

# 🔑 Configuration

The application requires a **Google Gemini API key**.

The project reads the API key from an environment variable:

```properties
spring.ai.google.genai.api-key=${GOOGLE_GENAI_API_KEY}
```

The application is configured to use:

```properties
spring.ai.google.genai.chat.options.model=gemini-3.5-flash
spring.ai.google.genai.chat.options.temperature=0.7
```

It also uses MySQL for Spring AI JDBC chat memory.

---

## 🔐 Environment Variables

Before running the application, configure:

```text
GOOGLE_GENAI_API_KEY=your_google_gemini_api_key
```

### Windows

PowerShell:

```powershell
$env:GOOGLE_GENAI_API_KEY="your_api_key"
```

Command Prompt:

```cmd
set GOOGLE_GENAI_API_KEY=your_api_key
```

### IntelliJ IDEA

You can configure the environment variable from:

```text
Run
  → Edit Configurations
  → Environment variables
```

Add:

```text
GOOGLE_GENAI_API_KEY=your_api_key
```

> ⚠️ Never commit your real Gemini API key to GitHub.

---

# 🗄️ MySQL Configuration

The application uses MySQL for Spring AI JDBC chat memory.

Create the database:

```sql
CREATE DATABASE spring_ai;
```

Configure your MySQL credentials in `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/spring_ai
spring.datasource.username=root
spring.datasource.password=your_password
```

The application also enables automatic initialization of the Spring AI chat-memory schema:

```properties
spring.ai.chat.memory.repository.jdbc.initialize-schema=ALWAYS
```

The repository currently contains MySQL configuration and JDBC chat-memory support.

---

# ▶️ Running the Application

## 1. Clone the repository

```bash
git clone https://github.com/Jaswanth778012/SpringAiProIntellij.git
```

## 2. Navigate into the project

```bash
cd SpringAiProIntellij
```

## 3. Configure MySQL

Create the database:

```sql
CREATE DATABASE spring_ai;
```

Then configure your database username and password.

## 4. Configure Gemini API Key

Set:

```text
GOOGLE_GENAI_API_KEY
```

## 5. Start the application

Using Maven:

```bash
mvn spring-boot:run
```

Or using the Maven wrapper:

### Windows

```cmd
mvnw.cmd spring-boot:run
```

### Linux / macOS

```bash
./mvnw spring-boot:run
```

The application runs on:

```text
http://localhost:8085
```

The configured server port is `8085`.

---

# 🔌 API Endpoints

## 1. Chat API

### Endpoint

```http
GET /AI/Chat
```

### Parameters

| Parameter | Type   | Description                         |
| --------- | ------ | ----------------------------------- |
| `q`       | String | User's question                     |
| `userId`  | Header | Unique conversation/user identifier |

### Example

```http
GET http://localhost:8085/AI/Chat?q=Explain%20Java%20Streams
userId: user123
```

### Example cURL

```bash
curl -X GET "http://localhost:8085/AI/Chat?q=Explain%20Java%20Streams" \
  -H "userId: user123"
```

The `userId` is passed to Spring AI's chat-memory advisor as the conversation ID, allowing the application to maintain conversation context.

---

# 📝 2. Chat Template API

### Endpoint

```http
GET /AI/ChatTemplate
```

### Example

```http
GET http://localhost:8085/AI/ChatTemplate
```

This endpoint demonstrates using an external prompt template together with Spring AI's `ChatClient`.

The current implementation loads:

```text
classpath:prompt/userPrompt.st
```

and supplies a topic to the template.

---

# 🌊 3. Streaming Chat API

### Endpoint

```http
GET /AI/Stream-chat
```

### Parameter

| Parameter | Type   | Description                   |
| --------- | ------ | ----------------------------- |
| `q`       | String | Question/query sent to Gemini |

### Example

```http
GET http://localhost:8085/AI/Stream-chat?q=Explain%20Spring%20Boot
```

The service uses:

```java
chatClient
    .prompt()
    .stream()
    .content();
```

and returns:

```java
Flux<String>
```

This allows the response to be delivered incrementally rather than waiting for the complete AI response.

---

# 🧠 Chat Memory

One of the important concepts demonstrated in this project is **conversation memory**.

The application uses Spring AI's:

```text
MessageChatMemoryAdvisor
```

with JDBC-backed chat memory.

The conversation ID is associated with the `userId` supplied to the chat endpoint:

```java
.advisors(advisorSpec ->
    advisorSpec.param(
        ChatMemory.CONVERSATION_ID,
        userId
    )
)
```

This allows different users/conversations to maintain separate context.

---

# 🧩 Spring AI Advisors

The project demonstrates multiple advisors through `ChatConfig`.

Currently configured advisors include:

### MessageChatMemoryAdvisor

Maintains conversation history.

### SimpleLoggerAdvisor

Provides logging around AI interactions.

### SafeGuardAdvisor

The project configures a safeguard for the topic:

```text
game
```

This demonstrates how advisors can be used to add behavior around AI requests without putting everything directly into the service layer.

---

# ⚙️ ChatClient Configuration

The `ChatClient` is configured centrally inside:

```text
configuration/ChatConfig.java
```

The configuration creates a `ChatClient` with:

* JDBC chat memory
* MessageChatMemoryAdvisor
* SimpleLoggerAdvisor
* SafeGuardAdvisor
* Google Gemini chat options
* Gemini model configuration
* Temperature configuration

The current Gemini model configuration is:

```text
gemini-3.5-flash
```

with temperature:

```text
0.7
```

---

# 📄 Prompt Templates

The project keeps AI prompt instructions outside Java code using:

```text
src/main/resources/prompt/userPrompt.st
```

This approach makes prompts easier to modify without changing the service implementation.

The service loads the prompt as a Spring `Resource`:

```java
@Value("classpath:prompt/userPrompt.st")
private Resource userPrompt;
```

and passes parameters into the prompt template.

---

# 🔄 Request Flow

For a normal chat request:

```text
HTTP Request
     │
     ▼
ChatController
     │
     ▼
ChatService
     │
     ▼
ChatServiceImpl
     │
     ▼
ChatClient
     │
     ├── Conversation Memory
     ├── Logger Advisor
     └── Safe Guard Advisor
     │
     ▼
Google Gemini
     │
     ▼
AI Response
     │
     ▼
HTTP Response
```

For streaming:

```text
HTTP Request
     │
     ▼
ChatController
     │
     ▼
ChatServiceImpl
     │
     ▼
ChatClient.stream()
     │
     ▼
Google Gemini
     │
     ▼
Flux<String>
     │
     ▼
Client receives chunks
```

---

# 🎯 Learning Objectives

This project is useful for learning:

* Spring AI fundamentals
* Google Gemini integration
* `ChatClient`
* Prompt engineering with Spring AI
* Prompt templates
* Chat memory
* Conversation IDs
* JDBC-based AI memory
* Spring AI Advisors
* Logger advisors
* Guard/safety advisors
* Streaming AI responses
* Reactive programming with `Flux`
* AI application configuration
* REST API development with Spring Boot

---

# 🧪 Testing the APIs

You can test the endpoints using tools such as:

* Postman
* cURL
* IntelliJ HTTP Client
* Browser for simple GET requests

Example:

```text
GET http://localhost:8085/AI/Chat?q=What%20is%20Spring%20AI
Header:
userId: jaswanth
```

For streaming:

```text
GET http://localhost:8085/AI/Stream-chat?q=Explain%20Java%20multithreading
```

---

# 🔒 Security Notes

Do not commit sensitive credentials.

Instead of:

```properties
spring.ai.google.genai.api-key=my-real-api-key
```

use:

```properties
spring.ai.google.genai.api-key=${GOOGLE_GENAI_API_KEY}
```

Also avoid committing production database passwords.

For production deployments, use environment variables or a secure secrets-management solution.

---

# 📌 Future Improvements

Possible enhancements for this project:

* [ ] Add a frontend chat interface using React
* [ ] Add authentication and authorization
* [ ] Add conversation history APIs
* [ ] Add user-specific conversation management
* [ ] Add exception handling with `@ControllerAdvice`
* [ ] Add API validation
* [ ] Add Swagger / OpenAPI documentation
* [ ] Add unit and integration tests
* [ ] Add Docker support
* [ ] Add Docker Compose for MySQL
* [ ] Add RAG using vector databases
* [ ] Add document upload and AI-based document querying
* [ ] Add more AI providers such as OpenAI or Ollama
* [ ] Add production-ready logging and monitoring

---

# 📚 Key Spring AI Concepts Demonstrated

```text
Spring Boot
     │
     └── Spring AI
           │
           ├── ChatClient
           │
           ├── Chat Options
           │
           ├── Prompt Templates
           │
           ├── Chat Memory
           │
           ├── Advisors
           │     ├── MessageChatMemoryAdvisor
           │     ├── SimpleLoggerAdvisor
           │     └── SafeGuardAdvisor
           │
           └── Streaming
                 └── Flux<String>
```

---

# 👨‍💻 Author

**Jaswanth**

GitHub:
https://github.com/Jaswanth778012

---

# ⭐ Support

If you find this project useful for learning Spring AI and Google Gemini, consider giving the repository a ⭐.

Happy coding! 🚀
