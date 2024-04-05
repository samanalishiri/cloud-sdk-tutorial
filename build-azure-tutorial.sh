mvn -f ./azure-tutorial/pom.xml clean package -DskipTests=true
mvn -f ./azure-tutorial/pom.xml test -Dcredentials=Azurite
mvn -f ./azure-tutorial/pom.xml test -Dcredentials=ENV

rm -rf ./azure-tutorial/storage-tutorial/src/test/resources/temp-test-result_*
