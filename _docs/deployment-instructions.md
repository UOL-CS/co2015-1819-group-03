# Deployment Instructions

## Requirements

You will need the following to run this application:
1. Server running MySQL version 14.14
1. Java version 1.8

## Installation

Setup the MySQL database.

```bash
mysql -u USERNAME -h HOSTNAME -P PORT -p -e "create database groupthree" 
```

## Configure GitLab OAuth2

Follow the instructions provided on this [website](https://docs.gitlab.com/ee/integration/oauth_provider.html). Set the callback URL to `http://{DOMAIN_NAME}/login/oauth2/code/gitlab`, replacing {DOMAIN_NAME} with the appropriate domain name.


## Configure application.yml

This file contains the properties of the deployment of the application.
Create a new file `application.yml` with the following contents, replacing {} with the appropriate details:

`{HOSTNAME}` = Hostname of the sql server e.g. mysql.mcscw3.le.ac.uk  
`{PORT}` = Port of the sql server e.g. 3306  
`{USERNAME}` = Username for the sql server  
`{PASSWORD}` = Password for the sql server  
`{CLIENT_ID}` = GitLab Application Id  
`{CLIENT_SECRET}` = GitLab Secret  
`{PORT_NUMBER}` = Port you would like the application to run on  


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
            scope: openid
            clientName: GitLab
        provider:
          gitlab:
            authorization-uri: https://gitlab.com/oauth/authorize   
            token-uri: https://gitlab.com/oauth/token
            user-info-uri: https://gitlab.com/api/v4/user
            jwk-set-uri: https://gitlab.com/oauth/discovery/keys
server:
  port: {PORT_NUMBER}
```

## Run the application

```bash
java -jar /path/to/co2015-1819-group-03-0.0.1-SNAPSHOT.war --spring.config.location=file:relative/path/to/application.yml
```
