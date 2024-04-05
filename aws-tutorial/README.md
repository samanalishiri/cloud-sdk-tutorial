## Description

This tutorial developed around the AWS cloud platform.

It is included:

- hello world
- bucket-tutorial
- sqs-tutorial

## Prerequisites

- Java 17
- Maven 3
- AWS/Localstack

## Set up AWS

### Setup Account

1- [Create account in AWS.](https://aws.amazon.com/)

2- Create a user (e.g. aws-tutorial) in order to develop, management, etc. (do not use root user)

Save the following parameters in the machine as an OS environment variable if you want to use`StaticCredentialsProvider`
class for credentials.

- windows

```shell
setx /M AWS_ACCESS_KEY_ID aws_access_key_id 
setx /M AWS_SECRET_ACCESS_KEY aws_secret_access_key
```

- Unix/Linux

```shell
export AWS_ACCESS_KEY_ID=aws_access_key_id >> ${HOME}/.bashrc
export AWS_SECRET_ACCESS_KEY=aws_secret_access_key >> ${HOME}/.bashrc 
source ${HOME}/.bashrc
```

### Setup CLI

Install AWS CLI from this [link](https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html) and
type the following commands in the terminal if you want to use `DefaultCredentialsProvider` class for credentials.

```shell
aws --version
```

```shell
aws configure
```

Then answer the prompts:

- AWS Access Key ID [None]: your aws_access_key_id
- AWS Secret Access Key [None]: your aws_secret_access_key
- Default region name [None]: your-region or leave it
- Default output format [None]: json

## Set up Localstack

[Install Localstack.](https://github.com/localstack/localstack)

### Install by Docker Compose

Install Localstack on docker via docker compose.

```yaml
version: "3.8"

services:
  localstack:
    container_name: localstack
    hostname: localstack
    image: localstack/localstack
    ports:
      - "127.0.0.1:4566:4566"            # LocalStack Gateway
      - "127.0.0.1:4510-4559:4510-4559"  # external services port range
    environment:
      # LocalStack configuration: https://docs.localstack.cloud/references/configuration/
      - DEBUG=${DEBUG:-0}
    volumes:
      - "${LOCALSTACK_VOLUME_DIR:-./volume}:/var/lib/localstack"
      - "/var/run/docker.sock:/var/run/docker.sock"
```

Execute the following command to install.

```shell
docker-compose -f ./localstack-docker-compose.yaml -p aws-tutorial up --build -d
```

Also, you can execute

* [for Windows](https://github.com/samanalishiri/cloud-sdk-tutorial/tree/main/aws-tutorial/install-localstack-on-docker.bat)
* [for Unix/Linux](https://github.com/samanalishiri/cloud-sdk-tutorial/tree/main/aws-tutorial/install-localstack-on-docker)

## Build

```shell
mvn clean package -DskipTests=true
```

### Test with Aws Env

```shell
mvn test -Dvendor=ENV
``` 

### Test with Aws CLI

```shell
mvn test -Dvendor=CLI
``` 

### Test with Localstack

```shell
mvn test -Dvendor=Localstack
```
