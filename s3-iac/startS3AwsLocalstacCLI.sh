localstack start -d

docker exec -it -d localstack_main awslocal s3api create-bucket --bucket s3-dev-bucket
