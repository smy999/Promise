package com.pjt3.promise.common.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureGenerationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;

/**
 * jwt 토큰 유틸 정의.
 */
@Component
public class JwtTokenUtil {
    private static String secretKey;
    private static Integer expirationTime;

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String ISSUER = "ssafy.com";
    
    @Autowired
	public JwtTokenUtil(@Value("${jwt.secret}") String secretKey, @Value("${jwt.expiration}") Integer expirationTime) {
		this.secretKey = secretKey;
		this.expirationTime = expirationTime;
	}
    
	public void setExpirationTime() {
    		JwtTokenUtil.expirationTime = expirationTime;
	}

	public static JWTVerifier getVerifier() {
        return JWT
                .require(Algorithm.HMAC512(secretKey.getBytes()))
                .withIssuer(ISSUER)
                .build();
    }
    
    public static String getToken(String userEmail) {
    	// access_Token 유효시간 : 30분
    	Date expires = JwtTokenUtil.getTokenExpiration(expirationTime);
        return JWT.create()
                .withSubject(userEmail)
                .withExpiresAt(expires)
                .withIssuer(ISSUER)
                .withIssuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                .sign(Algorithm.HMAC512(secretKey.getBytes()));
    }
    
    public static String getRefreshToken() {
    	// refresh_Token 유효시간 : 14일 (2주)
    	Date expires = JwtTokenUtil.getTokenExpiration(expirationTime * 2 * 24 * 14);
    	return JWT.create()
    			.withExpiresAt(expires)
    			.withIssuer(ISSUER)
    			.withIssuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
    			.sign(Algorithm.HMAC512(secretKey.getBytes()));    	
    }
    
    public static Date getTokenExpiration(int expirationTime) {
    		Date now = new Date();
    		return new Date(now.getTime() + expirationTime);
    }
    
    
    public static void handleError(String token) {
        JWTVerifier verifier = JWT
                .require(Algorithm.HMAC512(secretKey.getBytes()))
                .withIssuer(ISSUER)
                .build();

        try {
            verifier.verify(token.replace(TOKEN_PREFIX, ""));
        } catch (AlgorithmMismatchException ex) {
            throw ex;
        } catch (InvalidClaimException ex) {
            throw ex;
        } catch (SignatureGenerationException ex) {
            throw ex;
        } catch (SignatureVerificationException ex) {
            throw ex;
        } catch (TokenExpiredException ex) {
            throw ex;
        } catch (JWTCreationException ex) {
            throw ex;
        } catch (JWTDecodeException ex) {
            throw ex;
        } catch (JWTVerificationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        }
    }
    
    // 토큰 Validation Check
    public static boolean validateToken(String token) {
    	JWTVerifier verifier = JWT
    			.require(Algorithm.HMAC512(secretKey.getBytes()))
    			.withIssuer(ISSUER)
    			.build();
    	
    	try {
    		verifier.verify(token.replace(TOKEN_PREFIX, ""));
    		return true;
    	} catch (AlgorithmMismatchException ex) {
    		return false;
    	} catch (InvalidClaimException ex) {
    		return false;
    	} catch (SignatureGenerationException ex) {
    		return false;
    	} catch (SignatureVerificationException ex) {
    		return false;
    	} catch (TokenExpiredException ex) {
    		return false;
    	} catch (JWTCreationException ex) {
    		return false;
    	} catch (JWTDecodeException ex) {
    		return false;
    	} catch (JWTVerificationException ex) {
    		return false;
    	} catch (Exception ex) {
    		throw ex;
    	}
    }

    public static void handleError(JWTVerifier verifier, String token) {
        try {
            verifier.verify(token.replace(TOKEN_PREFIX, ""));
        } catch (AlgorithmMismatchException ex) {
            throw ex;
        } catch (InvalidClaimException ex) {
            throw ex;
        } catch (SignatureGenerationException ex) {
            throw ex;
        } catch (SignatureVerificationException ex) {
            throw ex;
        } catch (TokenExpiredException ex) {
            throw ex;
        } catch (JWTCreationException ex) {
            throw ex;
        } catch (JWTDecodeException ex) {
            throw ex;
        } catch (JWTVerificationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        }
    }
}
