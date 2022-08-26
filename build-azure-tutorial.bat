call mvn -f .\azure-tutorial\pom.xml clean package -DskipTests=true
call mvn -f .\azure-tutorial\pom.xml test -Dvendor=Azurite

call del .\azure-tutorial\storage-tutorial\src\test\resources\temp-test-result_*
