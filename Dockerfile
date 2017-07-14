FROM registry.cn-hangzhou.aliyuncs.com/shangzbei/alpine-oraclejdk8-dockerize
VOLUME /tmp
ADD target/*.jar app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS="--server.port=3000"
ENV SPRING_BOOT_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar $SPRING_BOOT_OPTS" ]
