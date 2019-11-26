package com.hdu.email.web.controller.email;

import com.hdu.email.common.util.transfer.PageView;
import com.hdu.emailservice.api.RecycleApi;
import com.hdu.emailservice.dto.RecycleDto;
import com.hdu.emailservice.dto.RecycleParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 回收站controller
 */
@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/recycle")
public class RecycleController {


    @Autowired
    private RecycleApi recycleApi;

    @RequestMapping("/query")
    private PageView<RecycleDto> query(@RequestHeader(value = "X-Token")String username, RecycleParam param){
        PageView<RecycleDto> pageView = new PageView<>();
        try{
            param.setUsername(username);
            pageView = recycleApi.queryRecycle(param);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return pageView;
    }
}
