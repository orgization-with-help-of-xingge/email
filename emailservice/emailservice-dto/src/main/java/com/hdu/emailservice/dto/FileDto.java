package com.hdu.emailservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
public class FileDto implements Serializable {

    private String name;
    private String uuid;
    private String url;
}
