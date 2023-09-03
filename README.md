# graph-image-processing
This repository contains a full-stack solution for uploading an image of a graph and getting the code (in any programming language) to initialize a data structure with the data from the image (Nodes and Edges of the graph in the uploaded image)

### Start a rabbitmq docker container locally
```
docker run -d --hostname rmq --name rabbitmq -e RABBITMQ_DEFAULT_USER=gipuser -e RABBITMQ_DEFAULT_PASS=gippass -p 8089:15672 -p 5672:5672 rabbitmq:3-management
```
