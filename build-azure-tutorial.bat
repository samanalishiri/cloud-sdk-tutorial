call mvn -f .\azure-tutorial\pom.xml clean package -DskipTests=true
call mvn -f .\azure-tutorial\pom.xml test -Dcredentials=Azurite
call mvn -f .\azure-tutorial\pom.xml test -Dcredentials=ENV

call del .\azure-tutorial\storage-tutorial\src\test\resources\temp-test-result_*
