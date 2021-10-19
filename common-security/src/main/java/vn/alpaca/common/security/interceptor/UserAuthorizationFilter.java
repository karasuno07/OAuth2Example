package vn.alpaca.common.security.interceptor;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import vn.alpaca.common.object.constant.CustomHttpHeader;
import vn.alpaca.common.object.wrapper.SuccessResponse;
import vn.alpaca.common.security.client.SecurityUserClient;
import vn.alpaca.common.security.object.SecurityAuthorityDetail;
import vn.alpaca.common.security.object.SecurityRoleDetail;
import vn.alpaca.common.security.object.SecurityUserDetail;
import vn.alpaca.common.security.object.ServiceUserDetail;
import vn.alpaca.common.security.service.SecurityUserDetailService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class UserAuthorizationFilter extends OncePerRequestFilter {

    private final SecurityUserDetailService userDetailService;
    private final ServiceUserDetail serviceUserDetail;
    private final SecurityUserClient userClient;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        try {
            Integer id = extractUserIdFromHeader(request);
            String serverName = extractServiceNameFromHeader(request);

            if (id != null) {
                SecurityUserDetail user = userDetailService.findUserById(id);

                if (user != null) {
                    SuccessResponse<SecurityRoleDetail>
                            roleDetailSuccessResponse =
                            userClient.getRoleDetail(user.getRoleId());
                    Set<SecurityAuthorityDetail> authorities =
                            roleDetailSuccessResponse.getData()
                                    .getAuthorities();

                    user.setPermissions(authorities.stream()
                            .map(SecurityAuthorityDetail::getPermissionName)
                            .collect(Collectors.toList()));

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(user, null,
                                    user.getAuthorities());

                    authentication.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(
                                    request));

                    SecurityContextHolder.getContext()
                            .setAuthentication(authentication);
                }
            } else if (Strings.isNotEmpty(serverName)) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                serviceUserDetail, null,
                                serviceUserDetail.getAuthorities());

                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(
                                request));

                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        filterChain.doFilter(request, response);
    }

    private String extractServiceNameFromHeader(HttpServletRequest request) {
        return request.getHeader(CustomHttpHeader.X_AUTHORIZED_SERVICE);
    }

    protected Integer extractUserIdFromHeader(HttpServletRequest request) {
        String userId = request.getHeader(CustomHttpHeader.X_AUTHORIZED_USER);
        if (StringUtils.hasText(userId)) {
            return Integer.parseInt(userId);
        }
        return null;
    }
}
