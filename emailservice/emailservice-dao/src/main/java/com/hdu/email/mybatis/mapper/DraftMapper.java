package com.hdu.email.mybatis.mapper;

import com.hdu.emailservice.dto.DraftMailDto;
import com.hdu.emailservice.dto.DraftMailParam;
import com.hdu.emailservice.dto.SendMailDto;

import java.util.List;

/**
 * 草稿箱
 */
public interface DraftMapper {
    int insDraft(SendMailDto sendMailDto);
    List<DraftMailDto> selDrafts(DraftMailParam param);
    int countDrafts(DraftMailParam param);
    DraftMailDto selById(String urid);
    int updDraft(SendMailDto sendMailDto);
    int delDraft(String urid);
}
