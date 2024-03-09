# monywell
Money well Take home

Swagger docs: http://localhost:2024/swagger-ui/index.html
Application.properties field:
spring:
  datasource:
    url: "jdbc:postgresql://localhost:5432/mony"
    username: (required)
    password: (required)

  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect

server:
  port: 2024

springdoc:
  api-docs:
    path: /swagger-ui/api-docs
