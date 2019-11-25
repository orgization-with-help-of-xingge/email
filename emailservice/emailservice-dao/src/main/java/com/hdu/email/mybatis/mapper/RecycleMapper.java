package com.hdu.email.mybatis.mapper;

import com.hdu.emailservice.dto.RecycleDto;

/**
 * 功能描述: 草稿箱mapper
 * @Author: sixl
 * @Date: 2019/11/25 19:01
 */
public interface RecycleMapper {
    int insRecycle(RecycleDto recycleDto);
}
