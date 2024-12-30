# <p align="center">S3 Tutorial</p>

This tutorial is about working with S3 service of AWS include create bucket, upload and download files, etc.

## Pipeline

### Build

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
mvn test -P Localstack -Dcredentials=Localstack
``` 

### Test (Mock)

For testing with Mock when there is no real AWS account or any tools for simulating.

```shell
mvn test
```

## Work with S3 via CLI

Add S3FullAccess policy to create more accounts.

```shell
export BUCKET_NAME=$DOMAIN-$COMPANY-$GROUP-$ARTIFACT
```

```shell
aws s3api create-bucket --bucket $BUCKET_NAME
```

```shell
aws s3api list-buckets
```

```shell
aws s3api get-bucket-acl --bucket $BUCKET_NAME
```

```shell
aws s3api delete-bucket --bucket $BUCKET_NAME
```

## Create S3 stuff via cloudformation

Add AWSCloudFormationFullAccess policy to be able to execute the Cloudformation templates.

```shell
# If the region already set then return the name of that.
aws configure get region
```

```shell
export REGION=
aws cloudformation --region $REGION validate-template --template-body file://create-s3bucket.yml

aws cloudformation --region $REGION create-stack --stack-name awstutorialbucket-s3-resource --template-body file://create-s3bucket.yml --capabilities CAPABILITY_NAMED_IAM
aws cloudformation --region $REGION list-stacks

aws cloudformation --region $REGION delete-stack --stack-name awstutorialbucket-s3-resource
```

##

**<p align="center"> [Top](#s3-tutorial) </p>**
