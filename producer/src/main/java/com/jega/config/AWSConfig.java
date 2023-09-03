package com.jega.config;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithName;

@ConfigMapping( prefix = "aws" )
public interface AWSConfig
{
    @WithName( "bucket-name" )
    String getBucketName();
    
    @WithName( "bucket-region" )
    String getBucketRegion();
    
    
    @WithName( "access-key" )
    String getAccessKey();
    
    
    @WithName( "secret-access-key" )
    String getSecretAccessKey();
    
}
