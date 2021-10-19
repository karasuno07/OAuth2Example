package vn.alpaca.common.security.interceptor;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import vn.alpaca.common.security.object.SecurityUserDetail;
import vn.alpaca.common.security.service.JwtTokenService;
import vn.alpaca.common.security.service.SecurityUserDetailService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenService tokenProvider;
    private final SecurityUserDetailService userService;
    private static final String AUTHENTICATION_SCHEME = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = extractTokenFromHeader(request);

            if (tokenProvider.validateToken(jwt)) {
                int id = tokenProvider.extractUserIdFromToken(jwt);

                SecurityUserDetail user = userService.findUserById(id);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(user, null,
                                user.getAuthorities());

                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(
                                request));

                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }

        filterChain.doFilter(request, response);
    }

    protected String extractTokenFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) &&
                bearerToken.startsWith(AUTHENTICATION_SCHEME)) {
            return bearerToken.substring(AUTHENTICATION_SCHEME.length());
        }
        return null;
    }
}
