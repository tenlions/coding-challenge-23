FROM adoptopenjdk/openjdk11:alpine
MAINTAINER nloewes
COPY ./target/filemanagement.jar /usr/src/filemanagement/
COPY docker-entrypoint.sh /docker-entrypoint.sh
WORKDIR /usr/src/filemanagement
EXPOSE 8050
ENTRYPOINT ["/docker-entrypoint.sh"]
CMD ["java", "-jar", "filemanagement.jar"]
