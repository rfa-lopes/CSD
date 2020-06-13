package csd.wallet.Utils;

import io.jsonwebtoken.*;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

public class JWTUtil {

    private static final String KEY = "password_ultra_S$cret@--123456";

    private static final byte[] KEY_BYTES = KEY.getBytes();

    private static final int JWT_MINUTS_TIME = 10; //10 minutos

    private static final long JWT_EXPIRATION_TIME = JWT_MINUTS_TIME * 60 * 1000;

    private static final String SUB = "Authentication";
    private static final String JWT_TYPE = "JWT";

    public static String createJWT(Long id) {
        long current = System.currentTimeMillis();
        Date exp = new Date(current + JWT_EXPIRATION_TIME);
        Date iat = new Date(current);
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
        Key signingKey = new SecretKeySpec(KEY_BYTES, signatureAlgorithm.getJcaName());
        JwtBuilder builder = Jwts.builder().
                setId(id.toString()).
                setSubject(SUB).
                setHeaderParam("type", JWT_TYPE).
                setExpiration(exp).
                setIssuedAt(iat).
                signWith(signatureAlgorithm, signingKey);

        return builder.compact();
    }

    public static String parseJWT(String jwt) throws SignatureException, ExpiredJwtException{
    	
    	
        Claims claims = Jwts.parser()
                .setSigningKey(KEY_BYTES)
                .parseClaimsJws(jwt).getBody();
        
        return claims.getId();
    }
}

