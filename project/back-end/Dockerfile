FROM openjdk:11
VOLUME /tmp
RUN mkdir /work
COPY . /work
WORKDIR /work
RUN chmod +x gradlew
RUN /work/gradlew build
RUN mv /work/build/libs/*.jar /work/app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/work/app.jar"]