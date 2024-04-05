docker rm localstack --force
docker image rm localstack/localstack:latest
docker-compose -f ./localstack-docker-compose.yaml -p aws-tutorial up --build -d