## Microservice Architecture Demo

# Introduction
Welcome to Microservice Architecture Demo Project. This project demonstrates how to build 
multiple microservices communicating with each other. The project contains the following 
modules:
1. A Spring Cloud Config server that is deployed as Docker container and can manage a services configuration information using a file system or GitHub-based repository.
2. A Eureka server running as a Spring-Cloud based service. This service will allow multiple service instances to register with it. Clients that need to call a service will use Eureka to lookup the physical location of the target service.
3. A API Gateway. All of our microservices can be routed through the gateway and have pre, response and post policies enforced on the calls.
4. A song service
5. A resource service.
6. A resource processor service, used to process song metadata.
7. A Postgres SQL database used to hold the data for song service.
8. A postgres SQL database used to hold the data for resource service.

## Initial Configuration
1.	Apache Maven (http://maven.apache.org)  All of the code in this repository have been compiled with Java version 11.
2.	Git Client (http://git-scm.com)
3.  Docker(https://www.docker.com/products/docker-desktop)


## How To Use

To clone and run this application, you'll need [Git](https://git-scm.com), [Maven](https://maven.apache.org/), [Java 11](https://www.oracle.com/technetwork/java/javase/downloads/jdk11-downloads-5066655.html). From your command line:

```bash
# Clone this repository
$ git clone https://github.com/Gbakradze94/microservice-architecture-demo.git

# Navigate to the parent directory 
# to build and run docker image
$ cd microservice-architecture-demo

# To build the code examples as a docker image, open a command-line 
# window and execute the following command:
$ mvn clean package dockerfile:build

# Now we are going to use docker-compose to start the actual image.  To start the docker image, stay in the directory containing  your chapter 8 source code and  Run the following command: 
$ docker-compose -f docker/docker-compose.yml up
```

# The build command

Will execute the [Spotify dockerfile plugin](https://github.com/spotify/dockerfile-maven) defined in the pom.xml file.  

 Running the above command at the root of the project directory will build all of the projects.  If everything builds successfully you should see a message indicating that the build was successful.

# The Run command

This command will run our services using the docker-compose.yml file located in the /docker directory. 

If everything starts correctly you should see a bunch of Spring Boot information fly by on standard out.  At this point all of the services needed for the chapter code examples will be running.

# Localstack
## To start localstack, from the main project directory run:
docker-compose -f docker-compose-localstack.yml up -d --build

To create a bucket on localstack aws s3 instance:
aws --endpoint-url=http://localhost:4572 s3 mb s3://resource-service

## To put public acl on bucket:
awslocal s3api put-bucket-acl --acl=public-read-write --bucket=resource-service

# Database
You can find the database script as well in the docker directory.

## Contact

I'd like you to send me an email on <Bakradzegeorge17@gmail.com> about anything you'd want to say about this software.

### Contributing
Feel free to file an issue if it doesn't work for your code sample. Thanks.