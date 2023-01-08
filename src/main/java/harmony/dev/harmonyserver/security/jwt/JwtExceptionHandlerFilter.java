package harmony.dev.harmonyserver.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import harmony.dev.harmonyserver.DTO.ResponseDTO;
import harmony.dev.harmonyserver.Exception.BusinessException;
import harmony.dev.harmonyserver.Exception.ExceptionSummary;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (JwtException ex) {
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, ex);
        }
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse response, Throwable ex) {
        BusinessException e = new BusinessException();
        e.add(ExceptionSummary.builder()
                        .message(ex.getMessage())
                        .build());
        try {
            ObjectMapper mapper = new ObjectMapper();
            response.setStatus(status.value());
            response.setContentType("application/json");
            response.getWriter().write(mapper.writeValueAsString(
                    ResponseDTO.builder()
                            .errors(ExceptionSummary.of(e))
                            .build()
            ));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
