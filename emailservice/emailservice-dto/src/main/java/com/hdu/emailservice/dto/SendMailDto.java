package com.hdu.emailservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class SendMailDto implements Serializable {
        private String urid;
        private List<FileDto> fileLists;
        private List<String> recipients;
        private List<String> copys;
        private String title;
        private String content;
        private byte[] blobContent;
        private byte[] blobRecipients;
        private byte[] blobCopy;
        private byte[] blobFileLists;
        private Date date;
        private Date lastUpdated;
        private String username;
}
