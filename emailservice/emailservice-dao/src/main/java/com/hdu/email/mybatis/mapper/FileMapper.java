package com.hdu.email.mybatis.mapper;

import com.hdu.emailservice.dto.FileDto;

import java.util.List;

public interface FileMapper {
    List<FileDto> selFile(String sender);
}
