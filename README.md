## Considerations
- The application does not need security
- Does not need DTO(Data Transfer Objects) because the data is simple and does not contain sensitive information
- For an API is common to all endpoints start with "/api" that is why the servlet.context is set with "api"
- I will be using SQL database to store the data because will consider all data coming going to be structured
- I'm using MVC pattern to separate the concerns
- I'm using JPA repository for easier data manipulation, but for larger projects, I would use JDBC template to better control and faster queries
- I'm using the H2 database for testing purposes, but for real applications, I would use MySQL or PostgreSQL for better solid data storage

## Details
- I replaced the type of "price" variable from Integer to Double because is more correct
- I added the "id" variable to the Product class to be used by the JPA repository, since barcode is not ideal for PK
- I created a GitHub Actions (CI/CD) to run the tests and build the project from every commit on main branch (obviously reasons)
- I created a docker-compose to run the application in a containerized environment for easier setup

## What you need to have to run
- Docker && docker-compose

## How to run
