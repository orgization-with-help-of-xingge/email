package com.hdu.email.mybatis.mapper;

import com.hdu.emailservice.dto.DeletedDto;
import com.hdu.emailservice.dto.RecycleParam;

import java.util.List;

/**
 * 功能描述: 删除邮件
 * @Author: sixl
 * @Date: 2019/11/25 18:29
 */
public interface DeletedMapper {
    //记录用户删除邮件
    int insDeleted(DeletedDto deletedDto);
    int delDeleted(RecycleParam param);
}
