package com.hdu.email.web.controller.email;

import com.hdu.email.common.util.transfer.BaseReturnResult;
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
            param.setUsername(username+"@sixl.xyz");
            pageView = recycleApi.queryRecycle(param);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return pageView;
    }

    @RequestMapping("/delrecycle")
    private BaseReturnResult delrecycle(@RequestHeader(value = "X-Token")String username, RecycleParam param){
        BaseReturnResult result = BaseReturnResult.getFailResult();
        try{
            param.setUsername(username+"@sixl.com");
            result = recycleApi.delRecycle(param);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return result;
    }

    @RequestMapping("/revoke")
    private BaseReturnResult revoke(@RequestHeader(value = "X-Token")String username, RecycleParam param){
        BaseReturnResult result = BaseReturnResult.getFailResult();
        try{
            param.setUsername(username+"@sixl.com");
            result = recycleApi.revokeRecycle(param);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return result;
    }


}
