package com.jega.models;

import jakarta.ws.rs.core.MediaType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.RestForm;

import java.io.File;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
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