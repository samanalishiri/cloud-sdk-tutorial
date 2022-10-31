mvn -f ./aws-tutorial/pom.xml clean package -DskipTests=true
mvn -f ./aws-tutorial/pom.xml test -Dvendor=Localstack
mvn -f ./aws-tutorial/pom.xml test -Dvendor=AwsEnv
# mvn -f ./aws-tutorial/pom.xml test -Dvendor=AwsCli
rm -rf ./aws-tutorial/sqs-tutorial/src/test/resources/temp-test-result_*
rm -rf ./aws-tutorial/s3-tutorial/src/test/resources/temp-test-result_*

