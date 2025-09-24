## NewsSearch Backend Application

This project exposes an API which is built on Reactive Spring Boot, that searches news via an upstream API, caches results in Redis.

### Tech Stack
- Java 21, Gradle
- Spring Boot WebFlux
- Spring Data Redis Reactive (cache)
- Resilience4j (Retry)
- Actuator
- springdoc-openapi for Swagger UI

### Project Structure
- `src/main/java/com/sapient/newssearch` – application code
- `src/test/java/com/sapient/newssearch` – tests
- `resources/ui` – React frontend
- `resources/k8s` – Kubernetes manifests
- `resources/jenkins/Jenkinsfile` – CI pipeline for Backend
- `resources/jenkins/Jenkinsfile` – CI pipeline for UI
- `compose.yaml` – for local testing

### Prerequisites
- JDK 21+
- Redis (local or Docker)
- Gradle

### Configuration Properties
Property files are present are under `src/main/resources`.
- Active profile defaults to `dev`
- Redis: `spring.data.redis.host`, `spring.data.redis.port` (defaults set for docker-compose: host `newssearch-cache`, port `6379`).
- External News API:
  - `news.service.search.url`
  - `news.service.search.api-key`

Profiles:
- `application-dev.properties` – for local development only
- `application-test.properties` – Test
- `application-prod.properties` – Production

### API
- Base path: `/v1/api`
- Search endpoint: `GET /v1/api/news/search`

Query parameters:
- `query` (required): search keyword
- `rangeFrom` (YYYY-MM-DD)
- `rangeTo` (YYYY-MM-DD)
- `sortBy` (relevancy|popularity|publishedAt)
- `pageSize` (1–100)
- `page` (1+)

Example:
```
GET http://localhost:8080/v1/api/news/search?query=bitcoin&sortBy=publishedAt&pageSize=20&page=1
```

OpenAPI/Swagger UI (for local testing):
```
http://localhost:8080/swagger-ui/index.html
```

Actuator (for heartbeat/health info):
```
http://localhost:8080/actuator/health
```

Run with Docker Compose (API + Cache + UI):
```bash
docker compose up -d --build
```

- API will be available at `http://localhost:8080`, with Swagger url: `http://localhost:8080/swagger-ui/index.html`
- UI will be available at `http://localhost:8081`.


### Build & Run (without cache)
```bash
.\gradlew clean build
java -jar build/libs/newssearch-0.0.1-SNAPSHOT.jar
```
### Kubernetes (optional)
Manifests are under `resources/k8s`:
- `newssearch-app.yaml` – deployment/service for the app
- `newssearch-cache.yaml` – Redis
- `newssearch-ui.yaml` – React UI
- `kubeconfig.yaml` – example kubeconfig placeholder

Apply (example):
```bash
kubectl apply -f resources/k8s/newssearch-cache.yaml
kubectl apply -f resources/k8s/newssearch-app.yaml
kubectl apply -f resources/k8s/newssearch-ui.yaml
```

### CI/CD
- Sample API Jenkins pipeline at `resources/jenkins/Jenkinsfile` covering build, test, image creation and publishing steps.
- Sample UI Jenkins pipeline at `resources/jenkins/JenkinsfileUI` covering build, test, image creation and publishing steps.

### Frontend
The React app is under `resources/ui`. To run it:
```bash
cd resources/ui
npm install
npm start
```
