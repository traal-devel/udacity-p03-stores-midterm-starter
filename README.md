# Reviews API 
Supports operations for writing reviews and listing reviews for a product but with no sorting or filtering.

### Prerequisites
MySQL needs to be installed and configured. Instructions provided separately.

### Getting Started
* Configure the MySQL Datasource in application.properties.
* Add Flyway scripts in src/main/resources/db/migration.
* Define JPA Entities and relationships.
* Define Spring Data JPA Repositories.
* Add tests for JPA Repositories.

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)

### Guides
The following guides illustrate how to use some features concretely:

* [Accessing JPA Data with REST](https://spring.io/guides/gs/accessing-data-rest/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)


### Swagger

Swagger-UI was added so the interface can be easily tested with the url:

* http://localhost:8080/swagger-ui.html

### Commands

#### For mysql only solution
```sql
CREATE DATABASE JDND_C3_MIDTERM;
GRANT ALL PRIVILEGES ON JDND_C3_MIDTERM.* TO 'udacity'@'localhost' IDENTIFIED BY 'udacity';
GRANT ALL PRIVILEGES ON JDND_C3_MIDTERM.* TO 'udacity'@'%' IDENTIFIED BY 'udacity';
FLUSH PRIVILEGES;
```

### For mysql and mongodb solution
```sql
CREATE DATABASE JDND_C3_MIDTERM_V2;
GRANT ALL PRIVILEGES ON JDND_C3_MIDTERM_V2.* TO 'udacity'@'localhost' IDENTIFIED BY 'udacity';
GRANT ALL PRIVILEGES ON JDND_C3_MIDTERM_V2.* TO 'udacity'@'%' IDENTIFIED BY 'udacity';
FLUSH PRIVILEGES;
```
