package io.agileinteligence.ppmtool.security;

public class SecurityConstants {

    public static final String SIGN_UP_URLS = "/api/users/**";
    public static final String H2_URL = "h2-console/**";
    public static final String SECRET = "SecretKeyToGenJWTS";
    public static final String TOKEN_PREFIX = "Bearer "; // one space after "Bearer" is very important
    public static final String HEADER_STRING = "Authorization";
    public static final long EXPIRATION_TIME = 600_000; // 600 seconds


}
