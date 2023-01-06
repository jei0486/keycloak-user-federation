#!/bin/bash

cd /mnt/c/_DEV/_edu/keycloak-user-federation

./gradlew clean bootJar

cp /mnt/c/_DEV/_edu/keycloak-user-federation/build/libs/keycloak-user-federation.jar /mnt/c/_DEV/_tool/keycloak/standalone/deployments/keycloak-user-federation.jar


# /mnt/c/_DEV/_tool/keycloak/standalone/deployments
# /mnt/c/_DEV/_edu/keycloak-user-federation

# docker container copy in local
# docker ps

# docker exec -it ea7791a19796 /bin/bash

# docker cp ea7791a19796:/opt/jboss/keycloak/standalone/configuration/standalone.xml .