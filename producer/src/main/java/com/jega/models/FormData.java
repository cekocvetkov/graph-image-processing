package com.jega.models;

import java.io.File;
import jakarta.ws.rs.core.MediaType;
import lombok.Getter;
import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.RestForm;

@Getter
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