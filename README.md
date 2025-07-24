Voici les requêtes Postman pour tester votre API Spring Boot

1. Employee Endpoints
   Create Employee
   POST http://localhost:8080/api/employees

{
"nom": "Doe",
"prenom": "John",
"email": "john.doe@example.com"
}
Get All Employees
GET http://localhost:8080/api/employees

Get Employee by ID
GET http://localhost:8080/api/employees/{id}

Update Employee
PUT http://localhost:8080/api/employees/{id}

{
"nom": "Doe Updated",
"prenom": "John",
"email": "updated.john@example.com"
}
Delete Employee
DELETE http://localhost:8080/api/employees/{id}

2. Formateur Endpoints
   Create Formateur
   POST http://localhost:8080/api/formateurs

{
"nom": "Martin",
"specialite": "Spring Boot",
"anneesExperience": 5
}
Get All Formateurs
GET http://localhost:8080/api/formateurs

Get Formateur by ID
GET http://localhost:8080/api/formateurs/{id}

Update Formateur
PUT http://localhost:8080/api/formateurs/{id}

{
"nom": "Martin Updated",
"specialite": "Advanced Spring",
"anneesExperience": 7
}

Delete Formateur
DELETE http://localhost:8080/api/formateurs/{id}

3. Formation Endpoints
   Create Formation
   POST http://localhost:8080/api/formations

{
"titre": "Spring Boot Fundamentals",
"description": "Introduction to Spring Boot",
"dateDebut": "2025-09-01",
"dateFin": "2025-09-05"
}

Get All Formations
GET http://localhost:8080/api/formations

Get Formation by ID
GET http://localhost:8080/api/formations/{id}

Update Formation
PUT http://localhost:8080/api/formations/{id}

{
"titre": "Advanced Spring Boot",
"description": "Deep dive into Spring Boot",
"dateDebut": "2025-10-01",
"dateFin": "2025-10-05"
}

4. Relationships Management
   Delete Formation
   DELETE http://localhost:8080/api/formations/{id}

Add Employee to Formation
POST http://localhost:8080/api/formations/{formationId}/employees/{employeeId}

Remove Employee from Formation
DELETE http://localhost:8080/api/formations/{formationId}/employees/{employeeId}

Update Formation Employees (Replace all)
PUT http://localhost:8080/api/formations/{formationId}/employees

Assign Formateur to Formation
POST http://localhost:8080/api/formations/{formationId}/formateur/{formateurId}

Remove Formateur from Formation
DELETE http://localhost:8080/api/formations/{formationId}/formateur

5. Scénario complet de test
   Créez 2 employees et 1 formateur

Créez 1 formation

Assignez le formateur à la formation :

POST /api/formations/1/formateur/1
Ajoutez les employés à la formation :

POST /api/formations/1/employees/1
POST /api/formations/1/employees/2
Vérifiez avec GET /api/formations/1

Mettez à jour la liste des employés :

PUT /api/formations/1/employees
[2]  // Garde seulement l'employé 2
Supprimez le formateur :

DELETE /api/formations/1/formateur
Configuration Postman :
Utilisez l'onglet Body > raw > JSON

Pour les IDs dynamiques, utilisez des variables Postman :

http
http://localhost:8080/api/formations/{{formationId}}/employees/{{employeeId}}
Sauvegardez les IDs dans des variables après création :



http://localhost:8080/swagger-ui.html

http://localhost:8080/swagger-ui/index.html


@JsonIgnore

@JsonBackReference