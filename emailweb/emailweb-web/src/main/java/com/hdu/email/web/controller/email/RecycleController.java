package com.hdu.email.web.controller.email;

import com.hdu.email.common.util.transfer.PageView;
import com.hdu.emailservice.dto.RecycleDto;
import com.hdu.emailservice.dto.RecycleParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
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


    @RequestMapping("query")
    private PageView<RecycleDto> query(RecycleParam param){
        return null;
    }
}
