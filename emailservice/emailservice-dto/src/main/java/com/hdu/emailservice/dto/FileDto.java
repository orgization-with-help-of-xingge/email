package com.hdu.emailservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
public class FileDto implements Serializable {
    private String urid;
    private String messageName;
    private String ftpfilename;
    private String filename;
    private String filepath;
}
