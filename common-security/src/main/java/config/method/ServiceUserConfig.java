package config.method;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.alpaca.common.object.wrapper.SuccessResponse;
import vn.alpaca.common.security.client.SecurityUserClient;
import vn.alpaca.common.security.object.SecurityAuthorityDetail;
import vn.alpaca.common.security.object.ServiceUserDetail;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class ServiceUserConfig {

    private final SecurityUserClient userClient;

    @Bean
    public ServiceUserDetail getUserClient() {
        try {
            SuccessResponse<List<SecurityAuthorityDetail>> authorityPage =
                    userClient.getAuthorities();
            List<SecurityAuthorityDetail> securityAuthorityDetails =
                    authorityPage.getData();
            List<String> authorities = securityAuthorityDetails.stream()
                    .map(SecurityAuthorityDetail::getPermissionName)
                    .collect(Collectors.toList());

            ServiceUserDetail serviceUserDetail = new ServiceUserDetail();
            serviceUserDetail.setPermissions(authorities);

            return serviceUserDetail;
        } catch (Exception e) {
            e.printStackTrace();
            return new ServiceUserDetail();
        }
    }
}
