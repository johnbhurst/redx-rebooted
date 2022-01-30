# Redx Done Right

## Goals

* State of the Art JVM project
* REST - HATEOAS principles? Standard approach to sorting, filtering, pagination?
* DevOps - Build+Deploy+Configure
* Cloud - Abstraction for multi DB, multi cloud
* Security - Strong encryption of data, configurable security profile
* Auto documentation and tooling

## Technologies

* Java 17 runtime
* Kotlin
* Spring Boot
* Gradle
* Spock
* MyBatis? JPA?
* Multi-database support - Spring profile-driven?
* Multicloud support for BLOBs - Spring profile-driven?
* Lucene text search? (with filtering to avoid arbitrary numeric data) (how to reprocess large amounts of Cloud data?)
* Google Filestore service for Lucene index?
* Smart search for transaction IDs and the like
* Ansible to deploy server
* Terraform to deploy infrastructure
* Cloudflare for load balancer, DNS
* Okta for security (SAML?) Profile-driven security
* Google Cloud Run with secrets mounted
* Spring Cloud Config
* Vue.js front-end
* Command-line tools (GraalVM native)
* GraalVM Native Command-Line tools?
* Quarkus?
* Structurizr + PlantUML
* Async REST?
* GitHub
* Cloud CI - Travis?
* Swagger API
* Swagger docs (or Spring docs?)
* Postman collection, including authentication
* Swagger generated client? Java, Groovy, Kotlin, JS?
* Secure encryption with distinct key per item

## Notes

* Test client behaviour when back-end service is not reachable.
* Test client behaviour when back-end service is down.
* Test client behaviour when back-end service fails.

## HTTP client testing tools

* Postman Collections
* httpie
* VS Code REST Client
* what is that other one?