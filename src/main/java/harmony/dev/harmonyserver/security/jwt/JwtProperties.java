package harmony.dev.harmonyserver.security.jwt;

public interface JwtProperties {
    String SECRET = "SECRET";
    long EXPIRATION_NAME = 30 * 60 * 1000L;
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
