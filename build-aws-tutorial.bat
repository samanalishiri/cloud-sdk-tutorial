call mvn -f .\aws-tutorial\pom.xml clean package -DskipTests=true
call mvn -f .\aws-tutorial\pom.xml test -Dcredentials=Localstack
call mvn -f .\aws-tutorial\pom.xml test -Dcredentials=ENV
# timeout 60
# call mvn -f .\aws-tutorial\pom.xml test -Dcredentials=CLI
call del .\aws-tutorial\sqs-tutorial\src\test\resources\temp-test-result_*
call del .\aws-tutorial\s3-tutorial\src\test\resources\temp-test-result_*
