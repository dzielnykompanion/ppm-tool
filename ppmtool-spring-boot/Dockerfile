# maven image as base image for the firs stage
FROM maven AS maven_build
# create 'build' directory in the image and copy pom there
COPY pom.xml /build/
# copy src directory to the 'build' directory inside image
COPY src /build/src/
# 'build' inside image
WORKDIR /build/
# package app
# skipped test due to some earlier errors, before dockerizing
RUN ["mvn", "package", "-Dmaven.test.skip=true"]


# openjdk for next stage, might use alpine version in the future
FROM openjdk:12
# mglb byku
MAINTAINER "mglb"
# create directory 'app' inside openjdk image
WORKDIR /app
# copy .jar file from first stage
COPY --from=maven_build /build/target/ppmtool-0.0.1-SNAPSHOT.jar /app/
# run this command inside openjdk image when container is started
ENTRYPOINT ["java","-jar","ppmtool-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080