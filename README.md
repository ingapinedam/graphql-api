# Library GraphQL API con OpenFeign

Una API GraphQL desarrollada con Java 17, Spring Boot, H2, Lombok y OpenFeign para gestionar una biblioteca de libros y autores. Esta aplicación consume un servicio externo de fecha a través de OpenFeign.

## Características

- ✅ API GraphQL completa con Spring Boot
- ✅ Base de datos H2 en memoria
- ✅ Lombok para reducir código boilerplate
- ✅ Dockerizado para fácil despliegue
- ✅ Integración con servicio de fecha a través de OpenFeign
- ✅ Arquitectura de microservicios con comunicación entre contenedores

## Microservicios incluidos

El proyecto está compuesto por dos microservicios independientes:

1. **library-graphql-api**: API principal con GraphQL que gestiona libros y autores
2. **date-service**: API REST simple que proporciona información sobre la fecha actual

## Requisitos

- Java 17
- Maven 3.6+
- Docker y Docker Compose
- Red Docker compartida para comunicación entre servicios

## Estructura del proyecto

```
├── library-graphql-api/          # API GraphQL principal
│   ├── src/
│   │   └── main/
│   │       ├── java/com/example/librarygraphqlapi/
│   │       │   ├── client/       # Cliente Feign
│   │       │   ├── controller/   # Controladores GraphQL
│   │       │   ├── model/        # Entidades
│   │       │   ├── repository/   # Repositorios JPA
│   │       │   └── service/      # Servicios
│   │       └── resources/        # Recursos (properties, schemas)
│   ├── Dockerfile
│   └── docker-compose.yml
│
└── date-service/                 # Servicio de fecha
    ├── src/
    │   └── main/
    │       ├── java/com/example/dateservice/
    │       │   ├── controller/   # Controlador REST
    │       │   └── model/        # Modelos de datos
    │       └── resources/        # Recursos (properties)
    ├── Dockerfile
    └── docker-compose.yml
```

## Configuración de la red Docker

Antes de desplegar los servicios, es necesario crear una red compartida:

```bash
docker network create library-network
```

## Despliegue de los servicios

### 1. Desplegar el servicio de fecha:

```bash
cd date-service
mvn clean package -DskipTests
docker-compose up -d
```

### 2. Desplegar el API GraphQL:

```bash
cd ../library-graphql-api
mvn clean package -DskipTests
docker-compose up -d
```

## Acceso a los servicios

- **API GraphQL**: http://localhost:8080/graphql
- **Interfaz GraphiQL**: http://localhost:8080/graphiql
- **Consola H2**: http://localhost:8080/h2-console (Usuario: sa, Contraseña: password)
- **Servicio de fecha**: http://localhost:8081/api/date

## Documentación GraphQL

### Consultas (Queries)

#### Obtener la fecha actual
```graphql
query {
  currentDate {
    currentDate
    formattedDate
    timestamp
    timezone
  }
}
```

#### Obtener todos los libros
```graphql
query {
  allBooks {
    id
    title
    isbn
    pageCount
    publishedDate
    genre
    author {
      id
      firstName
      lastName
    }
  }
}
```

#### Buscar un libro por ID
```graphql
query {
  bookById(id: "1") {
    title
    publishedDate
    author {
      firstName
      lastName
    }
  }
}
```

#### Buscar libros por título
```graphql
query {
  booksByTitle(title: "harry") {
    id
    title
    genre
    author {
      firstName
      lastName
    }
  }
}
```

#### Obtener todos los autores con sus libros
```graphql
query {
  allAuthors {
    id
    firstName
    lastName
    birthDate
    books {
      id
      title
      genre
    }
  }
}
```

#### Buscar un autor por ID
```graphql
query {
  authorById(id: "1") {
    firstName
    lastName
    birthDate
    books {
      title
      publishedDate
    }
  }
}
```

### Mutaciones (Mutations)

#### Crear un nuevo autor
```graphql
mutation {
  createAuthor(author: {
    firstName: "Isabel"
    lastName: "Allende"
    birthDate: "1942-08-02"
  }) {
    id
    firstName
    lastName
  }
}
```

#### Crear un nuevo libro
```graphql
mutation {
  createBook(book: {
    title: "La casa de los espíritus"
    isbn: "9780553383805"
    pageCount: 448
    publishedDate: "1982-01-01"
    genre: "Realismo mágico"
    authorId: "4"
  }) {
    id
    title
    author {
      firstName
      lastName
    }
  }
}
```

#### Actualizar un libro existente
```graphql
mutation {
  updateBook(
    id: "1", 
    book: {
      title: "Cien años de soledad (Edición especial)"
      pageCount: 432
      genre: "Realismo mágico latinoamericano"
    }
  ) {
    id
    title
    pageCount
    genre
  }
}
```

#### Eliminar un libro
```graphql
mutation {
  deleteBook(id: "1")
}
```

## Tecnologías utilizadas

- **Spring Boot**: Framework para aplicaciones Java
- **Spring Data JPA**: Para persistencia de datos
- **Spring GraphQL**: Para implementar la API GraphQL
- **Spring Cloud OpenFeign**: Para comunicación entre servicios
- **H2 Database**: Base de datos relacional en memoria
- **Lombok**: Para reducir código boilerplate
- **Docker**: Para containerización
