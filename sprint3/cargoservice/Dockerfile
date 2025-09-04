#### Dockerfile in project cargoservice
FROM eclipse-temurin:17.0.5_8-jre-focal as builder
## ADD extracts the tar
ADD ./build/distributions/cargoservice-1.0.tar /
WORKDIR /cargoservice-1.0/bin
COPY ./*.pl ./
COPY ./*.json ./
COPY ./*.bin ./
COPY ./*.txt ./
RUN mkdir logs

CMD ["bash", "cargoservice"]


## 1) gradlew distTar

## Caso di life locale
## docker rmi conwayguilifelocal:1.0   	 
## docker  build -t conwayguilifelocal:2.0 .   //La version 2.0 ha ip local
## docker run -it --rm --name conwayguilife -p7110:7110 --env "HOST_IP=192.168.1.132" conwayguilifelocal:1.1   
## docker run -it --rm --name conwayguilife -p7110:7110 --env "HOST_IP=192.168.1.9" conwayguilifelocal:1.1  

## Register the image on DockerHub
## docker tag conwayguilifelocal:2.0 natbodocker/conwayguilifelocal:2.0
## type docker_password.txt | docker login --username natbodocker --password-stdin
## docker login --username natbodocker
## docker push natbodocker/conwayguilifelocal:2.0



## Caso di GUI standalone
## docker rmi conwayguialone:1.0    
## docker  build -t conwayguialone:1.0 .
## docker run -it --rm --name conwayguialone -p7110:7110 --network cargo-network --env "MQTTBROKER_URL=tcp://mosquitto:1883" conwayguialone:1.0   
