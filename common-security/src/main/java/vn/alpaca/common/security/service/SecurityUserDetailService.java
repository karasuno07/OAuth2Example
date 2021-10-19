package vn.alpaca.common.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.alpaca.common.object.wrapper.SuccessResponse;
import vn.alpaca.common.security.client.SecurityUserClient;
import vn.alpaca.common.security.object.SecurityUserDetail;

@Service
@RequiredArgsConstructor
public class SecurityUserDetailService implements UserDetailsService {

    private final SecurityUserClient securityUserClient;

    @Override
    public SecurityUserDetail loadUserByUsername(String s)
            throws UsernameNotFoundException {
        SuccessResponse<SecurityUserDetail> successResponse =
                securityUserClient.findByUserName(s);
        return successResponse.getData();
    }

    public SecurityUserDetail findUserById(int id) {
        SuccessResponse<SecurityUserDetail> user =
                securityUserClient.findById(id);
        return user.getData();
    }
}
