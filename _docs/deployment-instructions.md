# Deployment Instructions

## Requirements

You will need the following to run this application:
1. Server running MySQL version 14.14 (database)
1. Server running Java version 1.8 (web application)
1. Server running Ubuntu, Java version 1.8 and MySQL version 14.14 (marking server)

## Configure GitLab OAuth2

Follow the instructions provided on this [website](https://docs.gitlab.com/ee/integration/oauth_provider.html). Set the callback URL to `http://{DOMAIN_NAME}/login/oauth2/code/gitlab`, replacing {DOMAIN_NAME} with the appropriate domain name. Tick the `api` scope box.

## Configure application.yml

This file contains the properties of the deployment of the application.
Create a new file `application.yml` with the following contents, replacing {} with the appropriate details:

`{HOSTNAME}` = Hostname of the sql server e.g. mysql.mcscw3.le.ac.uk  
`{PORT}` = Port of the sql server e.g. 3306  
`{USERNAME}` = Username for the sql server  
`{PASSWORD}` = Password for the sql server  
`{CLIENT_ID}` = GitLab Application Id  
`{CLIENT_SECRET}` = GitLab Secret  
`{PORT_NUMBER}` = Port you would like the application to run on (Note: Make sure this is different to the port you used for the MySQL database)  
`{ENCRYPTION_PASSWORD}` = The password used to encrypt the user access tokens with

```yaml
spring:
  datasource:
    platform: mysql
    url: jdbc:mysql://{HOSTNAME}:{PORT}/groupthree
    username: {USERNAME}
    password: {PASSWORD}
    driverClassName: com.mysql.jdbc.Driver
  jpa:
    hibernate: 
      ddl-auto: create
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQL5InnoDBDialect
  mvc:
    view:
      prefix: /WEB-INF/views/
      suffix: .jsp
  security:
    oauth2:
      client:
        registration:
          gitlab:
            client-id: {CLIENT_ID}
            client-secret: {CLIENT_SECRET}
            authorization-grant-type: authorization_code
            # Below property should not be changed
            redirect-uri-template: '{baseUrl}/login/oauth2/code/{registrationId}'
            scope: api
            clientName: GitLab
        provider:
          gitlab:
            authorization-uri: https://gitlab.com/oauth/authorize   
            token-uri: https://gitlab.com/oauth/token
            user-info-uri: https://gitlab.com/api/v4/user
            jwk-set-uri: https://gitlab.com/oauth/discovery/keys
            user-name-attribute: name
server:
  port: {PORT_NUMBER}
jasypt:
  encryptor:
    password: {ENCRYPTION_PASSWORD}
```

## Installation

1. Setup the MySQL database on the database server.

    ```bash
    mysql -u USERNAME -h HOSTNAME -P PORT -p -e "create database groupthree" 
    ```

1. Download the `co2015-1819-group-03-0.0.2-SNAPSHOT.war` from [Releases](https://github.com/UOL-CS/co2015-1819-group-03/releases) and place in web server.
1. Download the `server` folder from the repository and place in marking server. 
1. Download the `gitruler.jar` from [Releases](https://github.com/UOL-CS/co2015-1819-group-03/releases) and place in the `server` folder.

## Configure mysql.cnf

This file contains the properties for the MySQL database connection.

Replace the following with the MySQL configuration details used when setting up the MySQL database above:

`USERNAME` = Username for the sql server  
`PASSWORD` = Password for the sql server 
`DATABASE` = Name of the database ("groupthree" used above)
`HOSTNAME` = Hostname of the sql server e.g. mysql.mcscw3.le.ac.uk  
`PORT` = Port of the sql server e.g. 3306

```cnf
[client]
user=USERNAME
password=PASSWORD
database=DATABASE_NAME
host=HOSTNAME
port=PORT
```

## Running the web application

```bash
java -jar /path/to/co2015-1819-group-03-0.0.2-SNAPSHOT.war --spring.config.location=file:relative/path/to/application.yml
```

## Running the marking server

```bash
./gitruler-server.sh /path/to/mysql.cnf
```