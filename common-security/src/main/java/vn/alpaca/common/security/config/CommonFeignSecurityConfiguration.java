package vn.alpaca.common.security.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import vn.alpaca.common.object.constant.CustomHttpHeader;
import vn.alpaca.common.security.object.SecurityUserDetail;

@Configuration
public class CommonFeignSecurityConfiguration {

    @Value("${spring.application.name}")
    String serviceName;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            try {
                Object principal =
                        SecurityContextHolder.getContext().getAuthentication()
                                .getPrincipal();

                if (principal instanceof SecurityUserDetail) {
                    SecurityUserDetail user = (SecurityUserDetail) principal;
                    requestTemplate.header(CustomHttpHeader.X_AUTHORIZED_USER,
                            Integer.toString(user.getId()));
                } else {
                    requestTemplate.header(
                            CustomHttpHeader.X_AUTHORIZED_SERVICE, serviceName);
                }
            } catch (Exception e) {
                requestTemplate.header(CustomHttpHeader.X_AUTHORIZED_SERVICE,
                        serviceName);
            }
        };
    }
}
