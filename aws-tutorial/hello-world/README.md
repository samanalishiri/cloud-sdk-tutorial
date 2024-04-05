When creating an AWS account, it is recommended to create another account to work alongside it. Consequently, you must
add the following policy or the other policies, depending on your requirements, to the second account to enable command
execution.

Add IAMFullAccess policy to create more accounts.

```shell
aws iam create-user --user-name ???

aws iam list-users
aws iam list-attached-user-policies --user-name ???

aws iam delete-user --user-name ???
```

Add AWSCloudFormationFullAccess policy to be able to execute the Cloudformation templates.

```shell
aws cloudformation --region ??? validate-template --template-body file://create-user.yaml

aws cloudformation --region ??? create-stack --stack-name John-iam-resource --template-body file://create-user.yaml --capabilities CAPABILITY_NAMED_IAM
aws cloudformation --region ??? list-stacks

aws cloudformation --region ??? delete-stack --stack-name John-iam-resource
```