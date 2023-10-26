# Infrastructure as code for a localstack s3 bucket (create a s3 bucket locally) 

1. Start a localstack docker container (must install python pip beforehand and run "pip install localstack")
```
localstack start -d
```

2. Connect to container:
```
docker exec -it localstack_container_name sh
```
3. Set aws profile
```
aws configure --profile default
```
4. create bucket with terraform (see main.tf, provider.tf)
```
  terraform init
  terraform plan
  terraform apply
```
6. Verify bucket is created. Connect to container and try 
```
aws s3 ls --endpoint-url=http://localhost:4566 --recursive --human-readable
```
6. See bucket content:
```
aws s3 ls s3://s3-dev-bucket --endpoint=http://localhost:4566
```

# An easier approach without terraform (as terraform here is a little bit of overdoing, it's purpose is just for playing around and trying things out)

1. Same first step (Start a localstack docker container (must install python pip beforehand and run "pip install localstack"))
```
localstack start -d
```

2. Connect to container and create the bucket with the localstack aws cli
```
awslocal s3api create-bucket --bucket s3-dev-bucket
```

3. Check if bucket created (list all buckets)

```
awslocal s3api list-buckets
```
