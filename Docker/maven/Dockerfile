FROM java:8-jdk

ENV MAVEN_VERSION 3.5.2
RUN cd && \
    wget -q http://www-us.apache.org/dist/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz && \
    tar -xzf apache-maven-${MAVEN_VERSION}-bin.tar.gz && \
    mv apache-maven-${MAVEN_VERSION} /opt/mvn && \
    rm apache-maven-${MAVEN_VERSION}-bin.tar.gz

ENV M2_HOME /opt/mvn
ENV MAVEN_HOME /opt/mvn

ENV PATH ${PATH}:/opt/mvn/bin

RUN mkdir /code

WORKDIR /code
