call mvn -f .\aws-tutorial\pom.xml clean package
call mvn -f .\aws-tutorial\pom.xml test -Dvendor=Localstack
call mvn -f .\aws-tutorial\pom.xml test -Dvendor=AwsEnv
# timeout 60
# call mvn -f .\aws-tutorial\pom.xml test -Dvendor=AwsCli
call del .\aws-tutorial\sqs-tutorial\src\test\resources\temp-test-result_*
call del .\aws-tutorial\s3-tutorial\src\test\resources\temp-test-result_*
