FROM openjdk:22
COPY target/prometheus-k8s-0.0.1-SNAPSHOT.jar prometheusk8sapp.jar
EXPOSE 8787
ENTRYPOINT ["java", "-jar", "/prometheusk8sapp.jar"]