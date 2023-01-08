package harmony.dev.harmonyserver.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import harmony.dev.harmonyserver.DTO.ResponseDTO;
import harmony.dev.harmonyserver.Exception.BusinessException;
import harmony.dev.harmonyserver.Exception.ExceptionSummary;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        BusinessException e = new BusinessException();
        e.add(ExceptionSummary.builder()
                .code("Unauthorized")
                .build());
        ObjectMapper mapper = new ObjectMapper();
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.getWriter().write(mapper.writeValueAsString(
                ResponseDTO.builder()
                        .errors(ExceptionSummary.of(e))
                        .build()));
    }
}
