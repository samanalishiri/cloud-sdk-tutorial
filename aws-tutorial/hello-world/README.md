# <p align="center">Hello World</p>

When creating an AWS account, it is recommended to create another account to work alongside it. Consequently, you must
add the following policy or the other policies, depending on your requirements, to the second account to enable command
execution.

## Pipeline

### Build

```shell
mvn clean package -DskipTests=true
```

### Test

```shell
mvn test
```

## Create User via CLI

Add IAMFullAccess policy to create more accounts.

```shell
export USERNAME=
aws iam create-user --user-name $USERNAME

aws iam list-users
aws iam list-attached-user-policies --user-name $USERNAME

aws iam delete-user --user-name $USERNAME
```

## Create User via cloudformation

Add AWSCloudFormationFullAccess policy to be able to execute the Cloudformation templates.

```shell
# If the region already set then return the name of that.
aws configure get region
```

```shell
export REGION=
aws cloudformation --region $REGION validate-template --template-body file://create-user.yml

aws cloudformation --region $REGION create-stack --stack-name awstutorialusername-iam-resource --template-body file://create-user.yml --capabilities CAPABILITY_NAMED_IAM
aws cloudformation --region $REGION list-stacks

aws cloudformation --region $REGION delete-stack --stack-name awstutorialusername-iam-resource
```

##

**<p align="center"> [Top](#hello-world) </p>**
