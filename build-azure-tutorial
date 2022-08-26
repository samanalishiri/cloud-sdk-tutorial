mvn -f ./azure-tutorial/pom.xml clean package -DskipTests=true
mvn -f ./azure-tutorial/pom.xml test -Dvendor=Azurite

rm -rf ./azure-tutorial/storage-tutorial/src/test/resources/temp-test-result_*
