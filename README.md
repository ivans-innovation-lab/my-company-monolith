# [projects](http://ivans-innovation-lab.github.io/projects)/my-company-monolith [![CircleCI](https://circleci.com/gh/ivans-innovation-lab/my-company-monolith.svg?style=svg)](https://circleci.com/gh/ivans-innovation-lab/my-company-monolith)
This version of the application is deployed as a single monolithic application. 

Domain Driven Design is applied through Event Sourcing and CQRS. How Event Sourcing enables deployment flexibility - the application can be migrated and deployed as a microservices.

## Table of Contents

   * [Application 'My Company' (Monolithic version)](#application-my-company-monolithic-version)
      * [Patterns and techniques:](#patterns-and-techniques)
      * [Technologies](#technologies)
      * [Key benefits](#key-benefits)
      * [How it works](#how-it-works)
      * [Running instructions](#running-instructions)
         * [Prerequisite](#prerequisite)
         * [Step 1: Clone the project](#step-1-clone-the-project)
         * [Step 2: Build the project](#step-2-build-the-project)
         * [Step 3: Run it](#step-3-run-it)
         * [Issuing Commands &amp; Queries with CURL](#issuing-commands--queries-with-curl)
            * [Get the JWT token ](#get-the-jwt-token )
            * [Create Blog post](#create-blog-post)
            * [Publish Blog post](#publish-blog-post)
            * [Query Blog posts](#query-blog-posts)
            * [Create Project](#create-project)
            * [Query Projects](#query-projects)
            * [Create Team](#create-team)
            * [Query Team](#query-team)
            * [Activate Team](#activate-team)
            * [Deactivate Team](#passivate-team)
            * [Add member to the team](#add-member-to-the-team)
            * [Remove member from the team](#remove-member-from-the-team)
         * [Angular application](#angular-application)
      * [References and further reading](#references-and-further-reading)


## Patterns and techniques:

1. Command and Query Responsibility Separation (CQRS)
2. Event Sourcing

## Technologies

- [Spring Boot](http://projects.spring.io/spring-boot/)
- [Spring Data](http://projects.spring.io/spring-data/)
- [Spring Data REST](http://projects.spring.io/spring-data-rest/)
- [Axon Framework](http://www.axonframework.org/)


## Key benefits
1. Simple to develop - the goal of current development tools and IDEs is to support the development of monolithic applications
2. Simple to deploy - you simply need to deploy the WAR/JAR file on the appropriate runtime
3. Simple to scale - you can scale the application by running multiple copies of the application behind a load balancer
4. Easy implementation of eventually consistent business transactions that could span multiple components
5. Automatic publishing of events whenever data changes
6. Reliable auditing for all updates

## How it works

The application is literally split into a *command-side (domain)* component and a *query-side (materialized view)* component (this is CQRS in its most literal form).

Communication between the two components is `event-driven` and the demo uses simple event store (Database in this case - JPA) as a means of passing the [events](https://github.com/ivans-innovation-lab/my-company-common) between components.

The **command-side (domain)** processes commands. Commands are actions which change state in some way. The execution of these commands results in `Events` being generated which are persisted by Axon, and propagated out to other components. In event-sourcing, events are the sole records in the system. They are used by the system to describe and re-build domain aggregates on demand, one event at a time.

- https://github.com/ivans-innovation-lab/my-company-blog-domain
- https://github.com/ivans-innovation-lab/my-company-project-domain

The **query-side (materialized view)** is an event-listener and processor. It listens for the `Events` and processes them in whatever way makes the most sense. In this application, the query-side just builds and maintains a *materialised view* which tracks the state of the individual agregates (Project, Blog, ...).

- https://github.com/ivans-innovation-lab/my-company-blog-materialized-view
- https://github.com/ivans-innovation-lab/my-company-project-materialized-view

This application have REST API's which can be used to access capabilities of a the domain and all materialized views.

## Development

This project is driven using [Maven][mvn].

[mvn]: https://maven.apache.org/

### Running instructions

#### Prerequisite

- [Java JDK 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Git](https://git-scm.com/) 

#### Step 1: Clone the project

```bash
$ git clone https://github.com/ivans-innovation-lab/my-company-monolithic-web.git
```

#### Step 2: Build the project

This application depends on other librariries (all available under http://maven.idugalic.pro/)

- [my-company-blog-domain (**command-side**)](https://github.com/ivans-innovation-lab/my-company-blog-domain.git)
- [my-company-project-domain (**command-side**)](https://github.com/ivans-innovation-lab/my-company-project-domain.git)
- [my-company-blog-materialized-view (**query-side**)](https://github.com/ivans-innovation-lab/my-company-blog-materialized-view.git)
- [my-company-project-materialized-view (**query-side**)](https://github.com/ivans-innovation-lab/my-company-project-materialized-view.git)


#### Step 3: Run it

```bash
$ cd my-company-monolithic-web
$ ./mvnw clean install
$ ./mvnw spring-boot:run
```

### Issuing Commands & Queries with CURL

#### Get the JWT token 

JWT (shortened from JSON Web Token) is the missing standardization for using tokens to authenticate on the web in general, not only for REST services. Currently, it is in draft status as RFC 7519. It is robust and can carry a lot of information, but is still simple to use even though its size is relatively small. Like any other token, JWT can be used to pass the identity of authenticated users between an identity provider and a service provider (which are not necessarily the same systems). It can also carry all the user’s claim, such as authorization data, so the service provider does not need to go into the database or external systems to verify user roles and permissions for each request; that data is extracted from the token.

Here is how JWT is designed to work:

 - Clients logs in by sending their credentials to the identity provider.
 - The identity provider verifies the credentials; if all is OK, it retrieves the user data, generates a JWT containing user details and permissions that will be used to access the services, and it also sets the expiration on the JWT (which might be unlimited).
 - Identity provider signs, and if needed, encrypts the JWT and sends it to the client as a response to the initial request with credentials.
 - Client stores the JWT for a limited or unlimited amount of time, depending on the expiration set by the identity provider.
 - Client sends the stored JWT in an Authorization header for every request to the service provider.
 - For each request, the service provider takes the JWT from the Authorization header and decrypts it, if needed, validates the signature, and if everything is OK, extracts the user data and permissions. Based on this data solely, and again without looking up further details in the database or contacting the identity provider, it can accept or deny the client request. The only requirement is that the identity and service providers have an agreement on encryption so that service can verify the signature or even decrypt which identity was encrypted.

```
$ curl testjwtclientid:MaYzkSjmkzPC57L@localhost:8080/oauth/token -d grant_type=password -d username=john.doe -d password=jwtpass

```
You'll receive a response similar to below
```
{
 "access_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MDM3MTQ2MTgsInVzZXJfbmFtZSI6ImpvaG4uZG9lIiwiYXV0aG9yaXRpZXMiOlsiU1RBTkRBUkRfVVNFUiJdLCJqdGkiOiJiMDI5MGI3MS0zOTY2LTQ4ZTEtYThhOC02MzkzZjJjMjM1ZTYiLCJjbGllbnRfaWQiOiJ0ZXN0and0Y2xpZW50aWQiLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXX0.HJLARs7Q2Fdpkl0CP5X1hV6jBtri9tkIof61w16_X7w",
 "token_type":"bearer",
 "expires_in":43199,
 "scope":"read write",
 "jti":"b0290b71-3966-48e1-a8a8-6393f2c235e6"
 }
```
#### Create Blog post

Make sure to include correct access token below.

```bash
$ curl -H "Content-Type: application/json" -H "Authorization: Bearer <TOKEN>" -X POST -d '{"title":"xyz","rawContent":"xyz","publicSlug": "publicslug","draft": true,"broadcast": true,"category": "ENGINEERING", "publishAt": "2018-12-23T14:30:00+00:00"}' http://127.0.0.1:8080/api/blogpostcommands
```

#### Publish Blog post

```bash
$ curl -H "Content-Type: application/json" -H "Authorization: Bearer <TOKEN>" -X POST -d '{"publishAt": "2016-12-23T14:30:00+00:00"}' http://127.0.0.1:8080/api/blogpostcommands/{id}/publishcommand

```

#### UnPublish Blog post

```bash
$ curl -H "Content-Type: application/json" -H "Authorization: Bearer <TOKEN>" -X POST  http://127.0.0.1:8080/api/blogpostcommands/{id}/unpublishcommand

```

#### Query Blog posts

```bash
$ curl -H "Authorization: Bearer <TOKEN>" http://127.0.0.1:8080/api/blogposts
```

#### Create Project

```bash
$ curl -H "Content-Type: application/json" -H "Authorization: Bearer <TOKEN>" -X POST -d '{"name":"Name","repoUrl":"URL","siteUrl": "siteUrl","description": "desc"}' http://127.0.0.1:8080/api/projectcommands

```
#### Update Project

```bash
$ curl -H "Content-Type: application/json" -H "Authorization: Bearer <TOKEN>" -X POST -d '{"name":"Name2","repoUrl":"URL2","siteUrl": "siteUrl2","description": "desc2"}' http://127.0.0.1:8080/api/projectcommands/{id}/updatecommand

```
#### Activate Project

```bash
$ curl -H "Content-Type: application/json" -H "Authorization: Bearer <TOKEN>" -X POST http://127.0.0.1:8080/api/projectcommands/{id}/activatecommand

```
#### DeActivate Project

```bash
$ curl -H "Content-Type: application/json" -H "Authorization: Bearer <TOKEN>" -X POST http://127.0.0.1:8080/api/projectcommands/{id}/deactivatecommand

```
#### Query Projects

```bash
$ curl curl -H "Authorization: Bearer <TOKEN>" http://127.0.0.1:8080/api/projects
```

#### Create team

```bash
$ curl  -H "Content-Type: application/json" -H "Authorization: Bearer <TOKEN>" -X POST -d '{"name":"Name","description": "sdfsdfsdf"}' http://localhost:8080/api/teamcommands
```

#### Query team

```bash
$ curl  -H "Content-Type: application/json" -H "Authorization: Bearer <TOKEN>"  http://localhost:8080/api/team
```

#### Activate team

```bash
$ curl  -H "Content-Type: application/json" -H "Authorization: Bearer <TOKEN>" -X POST  http://localhost:8080/api/teamcommands/<TEAM_ID>/activatecommand
```
#### Passivate team

```bash
$ curl  -H "Content-Type: application/json" -H "Authorization: Bearer <TOKEN>" -X POST  http://localhost:8080/api/teamcommands/<TEAM_ID>/passivatecommand
```
#### Add member to the team

```bash
$ curl  -H "Content-Type: application/json" -H "Authorization: Bearer <TOKEN>" -X POST  -d '{"userId":"user-id","weeklyHours": "100"}' http://localhost:8080/api/teamcommands/<TEAM_ID>/addmembercommand
```

#### Remove member from the team

```bash
$ curl  -H "Content-Type: application/json" -H "Authorization: Bearer <TOKEN>" -X POST  http://localhost:8080/api/teamcommands/<TEAM_ID>/removemembercommand/<MEMBER_ID>
```

### Angular application

[Angular 4 application](https://github.com/ivans-innovation-lab/my-company-angular-fe) is a consumer of this API and represents the Front-end part of the solution.

## References and further reading

  * http://microservices.io/
  * http://www.slideshare.net/chris.e.richardson/developing-eventdriven-microservices-with-event-sourcing-and-cqrs-phillyete
  * http://12factor.net/
  * http://pivotal.io/platform/migrating-to-cloud-native-application-architectures-ebook
  * http://pivotal.io/beyond-the-twelve-factor-app
  * http://www.axonframework.org
  * http://www.kennybastani.com/2016/04/event-sourcing-microservices-spring-cloud.html
  * https://benwilcock.wordpress.com/2016/06/20/microservices-with-spring-boot-axon-cqrses-and-docker/
  

