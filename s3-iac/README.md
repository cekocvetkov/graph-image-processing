Infrastructure as code for a localstack s3 bucket 

1. Start a localstack docker container

localstack start -d

2. Connect to container:

docker exec -it localstack_container_name sh

3. Set aws profile

aws configure --profile default

4. create bucket with terraform (see main.tf, provider.tf)

5. Verify bucket is created. Connect to container and try 

aws s3 ls --endpoint-url=http://localhost:4566 --recursive --human-readable

6. See bucket content:

aws s3 ls s3://s3-dev-bucket --endpoint=http://localhost:4566
