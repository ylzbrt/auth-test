package com.ylzinfo.brt.feign;

import com.ylzinfo.brt.constant.HttpHeaderEnum;
import com.ylzinfo.brt.service.ServiceAuthService;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;



@Component
@Slf4j
public class AuthMyFeignInterceptor implements RequestInterceptor {
    @Value("${spring.application.name}")
    private String serviceName;

    @Autowired
    ServiceAuthService authService;

    public void apply(RequestTemplate template) {
        final String timestamp = System.currentTimeMillis() + "";
        final String sign = authService.getSign(timestamp);

        template.header(HttpHeaderEnum.SERVICE_NAME.getCode(), serviceName);
        template.header(HttpHeaderEnum.TIMESTAMP.getCode(), timestamp);
        template.header(HttpHeaderEnum.SERVICE_SIGN.getCode(), sign);
        log.debug("feign注入,SERVICE_NAME={},SERVICE_SIGN={}", serviceName, sign);
    }

}
