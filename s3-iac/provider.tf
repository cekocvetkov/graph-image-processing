provider "aws" {
  access_key = "dev"
  secret_key = "dev"
  region = "eu-west-1"

  skip_credentials_validation = true
  skip_requesting_account_id = true
  skip_region_validation = true
  s3_use_path_style = true

  endpoints {
    s3 = "http://localhost:4566"
  }
}
