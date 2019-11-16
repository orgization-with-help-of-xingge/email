package com.hdu.emailservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Recipients implements Serializable {
    private String recipients;
    private String recipientsName;
}
