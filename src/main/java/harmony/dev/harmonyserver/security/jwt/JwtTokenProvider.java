package harmony.dev.harmonyserver.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private final UserDetailsService userDetailsService;

    public Map<String, String> createToken(String userId) {
        Date now = new Date();
        Map<String, String> tokenInfo =new HashMap<>();

        Claims claims = Jwts.claims().setSubject(userId);
        tokenInfo.put("type", JwtProperties.TOKEN_PREFIX.trim());
        tokenInfo.put("key", Jwts.builder()
                        .setClaims(claims)
                        .setIssuedAt(now)
                        .setExpiration(new Date(now.getTime() + JwtProperties.EXPIRATION_NAME))
                        .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(JwtProperties.SECRET.getBytes()))
                        .compact());
        return tokenInfo;
    }

    private Claims getAllClaims(String jwtToken) {
        return Jwts.parser()
                .setSigningKey(JwtProperties.SECRET.getBytes())
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    public String getUserIdFromToken(String jwtToken) {
        return getAllClaims(jwtToken).getSubject();
    }

    public Date getExpirationDate(String jwtToken) {
        Claims claims = getAllClaims(jwtToken);
        return claims.getExpiration();
    }

    private boolean isTokenExpired(String jwtToken) {
        return getExpirationDate(jwtToken).before(new Date());
    }

    public Authentication getAuthentication(String jwtToken) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserIdFromToken(jwtToken));
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    public boolean checkValidationToken(String jwtToken) {
        Claims allClaims = this.getAllClaims(jwtToken);
        return allClaims != null && !isTokenExpired(jwtToken);
    }
}
