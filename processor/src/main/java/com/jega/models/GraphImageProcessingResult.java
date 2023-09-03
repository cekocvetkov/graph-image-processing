package com.jega.models;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@RegisterForReflection
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GraphImageProcessingResult
{
    
    public String id;
    public String code;

}