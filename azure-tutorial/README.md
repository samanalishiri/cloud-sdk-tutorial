## Description

This tutorial developed around the Azure cloud platform.

It is included:

- azure-hello world
- storage-tutorial
- queue-tutorial

## Prerequisites

- Java 17
- Maven 3
- Azure/Azurite

## Setup Azure

1- [Create account in Azure.](https://portal.azure.com/)

2- Create a Storage account in order to develop.

Save the following parameters in the machine as an OS environment variable.

- windows

```shell
setx /M ACCOUNT_NAME "account-name";
setx /M ACCOUNT_KEY "account-key";
setx /M AZURE_STORAGE_CONNECTION_STRING "connection-string"
```

- Unix/Linux

```shell
export ACCOUNT_NAME=account-name >> ${HOME}/.bashrc
export ACCOUNT_KEY=account-key >> ${HOME}/.bashrc 
export AZURE_STORAGE_CONNECTION_STRING=connection-string >> ${HOME}/.bashrc 
source ${HOME}/.bashrc
```

## Setup Azurite

1- [Install Azurite.](https://github.com/azure/azurite)

- also you can execute
    - [install-azurite-on-docker.bat](https://github.com/samanalishiri/cloud-sdk-tutorial/tree/main/azure-tutorial/install-azurite-on-docker.bat)
      for Windows
    - [install-azurite-on-docker](https://github.com/samanalishiri/cloud-sdk-tutorial/tree/main/azure-tutorial/install-azurite-on-docker)
      for Unix/Linux


## Setup Azure Storage Explorer

Download and setup [Azure storage explorer.](https://azure.microsoft.com/en-us/products/storage/storage-explorer/#overview)

## Build

```shell
mvn clean package -DskipTests=true
```

### Test with Azure Env

```shell
mvn test -Dcredentials=ENV
```

### Test with Azurite

```shell
mvn test -Dcredentials=Azurite
```
