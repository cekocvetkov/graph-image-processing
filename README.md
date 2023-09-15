# graph-image-processing
(work in progress)
This repository contains a full-stack solution for uploading an image of a graph and getting the code (in any programming language) to initialize a data structure with the data from the image (Nodes and Edges of the graph in the uploaded image)

## Start a rabbitmq docker container locally
```
docker run -d --hostname rmq --name rabbitmq -e RABBITMQ_DEFAULT_USER=gipuser -e RABBITMQ_DEFAULT_PASS=gippass -p 8089:15672 -p 5672:5672 rabbitmq:3-management
```

### After setting up a S3 bucket, use the following environmental variables to start the producer project:
```
AWS_BUCKET_NAME=somebucketname;
AWS_BUCKET_REGION=somebucketregion;
AWS_ACCESS_KEY=someaccesskey;
AWS_SECRET_ACCESS_KEY=somesecretaccesskey
```

## Solution Diagram

![rabbitmw drawio](https://github.com/cekocvetkov/graph-image-processing/assets/7689051/1fc624bd-c301-47a5-bf17-7222fbd9c99a)
