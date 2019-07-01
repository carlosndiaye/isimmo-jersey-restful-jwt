package com.kraken.isimmo.utils;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


import javax.crypto.SecretKey;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author carlosndiaye
 */


public class JwTokenHelper {

    private static JwTokenHelper jwTokenHelper = null;
    private static final long EXPIRATION_LIMIT = 30;
    private Date date = new Date();
    SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private JwTokenHelper() {

    }

    public static JwTokenHelper getInstance() {
        if (jwTokenHelper == null)
            jwTokenHelper = new JwTokenHelper();
        return jwTokenHelper;
    }

    public String generatePrivateKey(String username, String password) {
        return Jwts
                .builder()
                .setSubject(username)
                .setSubject(password)
                .setIssuedAt(date)
                .setExpiration(getExpirationDate())
                .signWith(key)
                .compact();
    }

    public void claimKey(String privateKey) throws ExpiredJwtException, MalformedJwtException {
        Jwts
                .parser()
                .setSigningKey(key)
                .parseClaimsJws(privateKey);
    }


    private Date getExpirationDate() {
        long currentTimeMillis = System.currentTimeMillis();
        long expMilliSeconds = TimeUnit.MINUTES.toMillis(EXPIRATION_LIMIT);
        return new Date(currentTimeMillis + expMilliSeconds);
    }
}
