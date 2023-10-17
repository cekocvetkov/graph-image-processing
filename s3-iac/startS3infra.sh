#!/bin/bash

# Start LocalStack in detached mode
localstack start -d

# Wait for LocalStack to start (adjust the sleep duration as needed)
sleep 5

# Initialize Terraform in your project directory
terraform init

# Plan the Terraform changes
terraform plan

# Apply the Terraform changes (you can add `-auto-approve` to skip confirmation)
terraform apply -auto-approve
