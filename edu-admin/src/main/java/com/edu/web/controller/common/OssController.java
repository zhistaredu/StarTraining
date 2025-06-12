package com.edu.web.controller.common;

import com.edu.common.core.controller.BaseController;
import com.edu.common.core.domain.AjaxResult;
import com.edu.config.OssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/aliyun/oss")
@Component
public class OssController  extends BaseController {

    @Autowired
    private OssUtil ossUtil;

    @GetMapping("/getSignature")
    public AjaxResult getSignature() throws ServletException, IOException {
        ossUtil.ossClient();
        Map<String,String> result = ossUtil.getSignature();
        result.put("companyId",getLoginUser().getCompanyId());
        return AjaxResult.success(result);
    }
}
