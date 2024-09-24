ARG NEXUS_VERSION=3.66.0

FROM maven:3-jdk-8-alpine AS build

COPY . /nexus3-repository-artifact-usage-plugin/
RUN cd /nexus3-repository-artifact-usage-plugin/; \
    mvn clean package -PbuildKar;

FROM sonatype/nexus3:$NEXUS_VERSION

ARG DEPLOY_DIR=/opt/sonatype/nexus/deploy/
USER root
COPY --from=build /nexus3-repository-artifact-usage-plugin/nexus3-repository-artifact-usage-plugin/target/nexus3-repository-artifact-usage-plugin-*-bundle.kar ${DEPLOY_DIR}
USER nexus
