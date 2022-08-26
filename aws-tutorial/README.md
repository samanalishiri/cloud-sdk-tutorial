## Description

This tutorial developed around the AWS cloud platform.

It is included:

- aws-hello world
- bucket-tutorial
- sqs-tutorial

## Prerequisites

- Java 17
- Maven 3
- AWS/Localstack

## Set up AWS

1- [Create account in AWS.](https://aws.amazon.com/)

2- Create a user in order to develop. (do not use root user)

Save the following parameters in the machine as an OS environment variable if you want to use
`StaticCredentialsProvider` class for credentials.

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

3- Install AWS CLI and type the following commands in the terminal if you want to use
`DefaultCredentialsProvider` class for credentials.

```shell
aws --version
aws configure
```

Then answer the prompts:

- AWS Access Key ID [None]: your aws_access_key_id
- AWS Secret Access Key [None]: your aws_secret_access_key
- Default region name [None]: your-region or leave it
- Default output format [None]: json

## Set up Localstack

1- [Install Localstack.](https://github.com/localstack/localstack)

- also you can execute
    - [install-localstack-on-docker.bat](https://github.com/samanalishiri/cloud-sdk-tutorial/tree/main/aws-tutorial/install-localstack-on-docker.bat)
      for Windows
    - [install-localstack-on-docker](https://github.com/samanalishiri/cloud-sdk-tutorial/tree/main/aws-tutorial/install-localstack-on-docker)
      for Unix/Linux

## Build

```shell
mvn clean package -DskipTests=true
```

### Test with Aws Env

```shell
mvn test -Dvendor=AwsEnv
``` 

### Test with Aws CLI

```shell
mvn test -Dvendor=AwsCli
``` 

### Test with Localstack

```shell
mvn test -Dvendor=Localstack
```
