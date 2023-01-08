package harmony.dev.harmonyserver.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import harmony.dev.harmonyserver.DTO.ResponseDTO;
import harmony.dev.harmonyserver.Exception.BusinessException;
import harmony.dev.harmonyserver.Exception.ExceptionSummary;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        BusinessException e = new BusinessException();
        e.add(ExceptionSummary.builder()
                .code("FORBIDDEN")
                .build());

        ObjectMapper mapper = new ObjectMapper();
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");
        response.getWriter().write(mapper.writeValueAsString(
                ResponseDTO.builder()
                        .errors(ExceptionSummary.of(e))
                        .build()));
    }
}
