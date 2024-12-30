# <p align="center">Java Cloud Tutorial</p>

This tutorial is included cloud SDKs such as AWS, Azure, GCP, ... samples that implement by Java. Therefore, you can see
the following links for more details as official tutorials.

* For AWS follow [aws-doc-sdk-examples](https://github.com/awsdocs/aws-doc-sdk-examples)

## Table of content

- [AWS SDK Java Tutorial](aws-tutorial)
    - [Hello World](aws-tutorial/hello-world)
    - [Bucket Tutorial](aws-tutorial/s3-tutorial)
    - [SQS Tutorial](aws-tutorial/sqs-tutorial)
- [Azure SDK Java Tutorial](azure-tutorial)
    - [Hello World](azure-tutorial/azure-hello-world)
    - [Storage Tutorial](azure-tutorial/storage-tutorial)
    - [Queue Tutorial](azure-tutorial/queue-tutorial)

## Prerequisites

- Java 21
- Maven 3
- AWS/Localstack
- Azure/Azurite

## Build

```shell
mvn -f ./aws-tutorial/pom.xml clean package -DskipTests=true
mvn -f ./azure-tutorial/pom.xml clean package -DskipTests=true
```

## Test

Localstack and Azurite are mandatory for the test.

```shell
mvn -f ./aws-tutorial/pom.xml test -Dcredentials=Localstack
mvn -f ./azure-tutorial/pom.xml test -Dcredentials=Azurite
```

##

**<p align="center"> [Top](#java-cloud-tutorial) </p>**

