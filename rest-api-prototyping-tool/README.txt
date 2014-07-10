This project contains the following modules:
- rest-api-v1: Public REST Api which serves as documentation for the service.
- rest-common: Common code for the API and its implementations.
- rest-demo-v1: Demo implementation of the API.
- rest-doc: Project that generates the documentation from the rest-api-v1 module.
- build-tools: Files used by the project build process such as checkstyle configuration.

TO BUILD PROJECT
=================
mvn clean install

TO START SERVERS
=================
mvn jetty:run (from within the rest-demo-v1 and rest-doc modules respectively)

TO BUID THE DISTRIBUTION PACKAGE
================================
mvn -P clean package (generates the rest-bank.zip in the rest-demo-v1/target folder for distribution)
