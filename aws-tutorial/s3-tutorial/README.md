# <p align="center">S3 Tutorial</p>

This tutorial is about working with S3 service of AWS include create bucket, upload and download files, etc.

## Work with S3 via CLI

Add S3FullAccess policy to create more accounts.

```shell

```

## Create S3 stuff via cloudformation

Add AWSCloudFormationFullAccess policy to be able to execute the Cloudformation templates.

```shell

```

## Build

```shell
mvn clean package -DskipTests=true
```

### Test (CLI)

Get credentials from AWS CLI.

```shell
mvn test -Dcredentials=CLI
``` 

### Test (ENV)

Get credentials from OS environment variables.

```shell
mvn test -Dcredentials=ENV
``` 

### Test (Localstack)

Use fake credentials in order to connect to Localstack.

```shell
mvn test -Dcredentials=Localstack
``` 

##

**<p align="center"> [Top](#s3-tutorial) </p>**
