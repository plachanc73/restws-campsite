# Start with a base image containing Java runtime
FROM java:8

# Add Maintainer Info
LABEL maintainer="plachanc73@gmail.com"

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Add the application's jar to the container
ADD /target/restws-campsite.jar app.jar

# Run the jar file 
ENTRYPOINT ["java", "-jar", "/app.jar"]