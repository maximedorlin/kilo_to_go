
http://localhost:8080/swagger-ui.html

http://localhost:8080/swagger-ui/index.html


@JsonIgnore

@JsonBackReference



FROM maven:3.9.5-eclipse-temurin-21

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn package -DskipTests

ENTRYPOINT ["java", "-jar", "target/your-app-name.jar"]



Pour la production locale :

bash
docker-compose -f docker-compose.prod.yml build
docker-compose -f docker-compose.prod.yml up -d
Pour le déploiement :

bash
# Build et push de l'image
docker build -t your-registry/springboot-prod:latest -f Dockerfile.prod .
docker push your-registry/springboot-prod:latest

# Déploiement avec variables d'environnement
export DB_PASSWORD=StrongPassword123!
export DB_USER=deploy_admin
export TAG=v1.2.0

docker-compose -f docker-compose.deploy.yml up -d
Arrêt et nettoyage :

bash
# Production locale
docker-compose -f docker-compose.prod.yml down -v

# Déploiement
docker-compose -f docker-compose.deploy.yml down -v









Initiation du paiement :

bash
POST /api/paiements/initiate
{
  "transactionId": 123,
  "methode": "MOMO",
  "montant": 15000.0,
  "phoneNumber": "+237699999999"
}
Réponse :

json
{
  "id": 456,
  "referencePaiement": "MOMO_1a2b3c4d5e6f",
  "statut": "en_attente",
  "informationsSupplementaires": "{\"phone\":\"+237699999999\"}"
}
Webhook de confirmation :

bash
POST /api/paiements/webhook/momo
X-Signature: hmac_signature
{
  "reference": "MOMO_1a2b3c4d5e6f",
  "status": "SUCCESSFUL",
  "amount": 15000.0,
  "transactionId": "MOMO_TRX_123456"
}