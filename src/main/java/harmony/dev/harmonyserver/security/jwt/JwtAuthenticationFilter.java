package harmony.dev.harmonyserver.security.jwt;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(JwtProperties.HEADER_STRING);

        if (authHeader != null && authHeader.startsWith(JwtProperties.TOKEN_PREFIX)) {
            String token = authHeader.replace(JwtProperties.TOKEN_PREFIX, "");

            try {
                if (!token.equals("") && jwtTokenProvider.checkValidationToken(token)) {
                    request.setAttribute("userId", jwtTokenProvider.getUserIdFromToken(token));
                    Authentication authentication = jwtTokenProvider.getAuthentication(token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (IllegalStateException | MalformedJwtException | UnsupportedJwtException ex) {
                throw new JwtException("Invalid Token");
            } catch (SignatureException ex) {
                throw new JwtException("Signature failed");
            } catch (ExpiredJwtException ex) {
                throw new JwtException("Expired token given");
            } catch (UsernameNotFoundException ex) {
                throw new JwtException("Unavailable Token");
            }
        }
        filterChain.doFilter(request, response);
    }
}
