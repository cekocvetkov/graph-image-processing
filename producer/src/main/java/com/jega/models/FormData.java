package com.jega.models;

import jakarta.ws.rs.core.MediaType;
import lombok.Getter;
import lombok.ToString;
import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.RestForm;

import java.io.File;

@Getter
@ToString
public class FormData {
    
    @RestForm("file")
    File data;
    
    @RestForm
    @PartType(MediaType.TEXT_PLAIN)
    String fileName;
    
    @RestForm
    @PartType(MediaType.TEXT_PLAIN)
    String mimeType;
    
}