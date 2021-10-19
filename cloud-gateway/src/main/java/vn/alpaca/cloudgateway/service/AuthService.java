package vn.alpaca.cloudgateway.service;

import com.google.common.base.Strings;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.alpaca.cloudgateway.client.AuthenticationClient;
import vn.alpaca.common.object.wrapper.SuccessResponse;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationClient client;

    public Integer validateToken(String token) {
        if (Strings.isNullOrEmpty(token)) {
            return null;
        }

        if (!token.startsWith("Bearer ")) {
            return null;
        }

        SuccessResponse<Integer> response = client.verifyToken(token);

        if (response == null) {
            return null;
        }

        return response.getData();

    }

}
