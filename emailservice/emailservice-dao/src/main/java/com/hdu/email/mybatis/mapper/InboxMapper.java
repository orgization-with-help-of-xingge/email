package com.hdu.email.mybatis.mapper;

import com.hdu.emailservice.dto.Inbox;
import com.hdu.emailservice.dto.InboxParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InboxMapper {
    /*
    *@Description: 根据messageName查询此邮件
    *@Param:
    *@return:
    */
    Inbox selById(InboxParam param);

    /**
     * 收件箱
     * @param param
     * @return
     */
    List<Inbox> selByRecipNonRead(InboxParam param);
    Integer countByRecipNonRead(InboxParam param);
    List<Inbox> selByRecip(InboxParam param);
    Integer countByRecip(InboxParam param);

    /**
     * 更新状态,已读取等等
     * @param inbox
     * @return
     */
    int insRead(Inbox inbox);
    int updRead(Inbox inbox);

    /**
     * 发件箱
     */
    List<Inbox> selBySender(InboxParam param);
    Integer countBySender(InboxParam param);


    /**
     * 根据messageName和username查询出当前star
     * @param param
     * @return
     */
    Inbox selStar(InboxParam param);
    int insStar(InboxParam param);
    int updStar(InboxParam param);
}
