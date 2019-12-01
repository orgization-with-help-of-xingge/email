package com.hdu.email.web.controller.email;

import com.hdu.email.common.util.transfer.BaseReturnResult;
import com.hdu.email.common.util.transfer.PageView;
import com.hdu.emailservice.api.DraftApi;
import com.hdu.emailservice.dto.DraftMailDto;
import com.hdu.emailservice.dto.DraftMailParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 草稿箱controller
 */
@RequestMapping("/draft")
@RestController
@Slf4j
@CrossOrigin
public class DraftController {
    @Autowired
    private DraftApi draftApi;


    @RequestMapping("/queryall")
    private PageView<DraftMailDto> queryAll(@RequestHeader("X-Token")String username, DraftMailParam param){
        PageView<DraftMailDto> pageView = new PageView<>();
        try{
            param.setUsername(username+"@sixl.xyz");
            pageView = draftApi.queryAll(param);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return pageView;
    }

    @RequestMapping("/detail")
    private BaseReturnResult detail(DraftMailParam param){
        BaseReturnResult result = BaseReturnResult.getFailResult();
        try{
            result = draftApi.queryDetail(param);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return result;
    }

    @RequestMapping("/delete")
    private BaseReturnResult delete(List<String> uridin){
        BaseReturnResult result = BaseReturnResult.getFailResult();
        try{
            result = draftApi.deleteMail(uridin);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return result;

    }
}
