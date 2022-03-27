package com.example.supermarket.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtils {

    /**
     * 荷载中用户名的key
     */
    private static final String CLAIM_KEY_USERNAME = "sub";
    /**
     * 荷载中创建时间的key
     */
    private static final String CLAIM_KEY_CREATED = "created";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 根据用户信息获取token
     *
     * @param userDetails
     * @return
     */
    public String generateToken(UserDetails userDetails) {

        // 准备负载payload
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }
    
    /**
     * 根据荷载生成jwt token
     *
     * @param claims
     * @return
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
    
    /**
     * 生成token失效时间
     *
     * @return
     */
    private Date generateExpirationDate() {

        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
     * 从token中获取登录用户名
     *
     * @param token
     * @return
     */
    public String getUserNameFormToken(String token) {

        String username;
        // 从token中获取荷载
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }
    
    /**
     * 从token中获取荷载
     *
     * @param token
     * @return
     */
    private Claims getClaimsFromToken(String token) {

        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }

    /**
     * 判断token是否有效
     */
    public boolean validateToken(String token, UserDetails userDetails) {

        String userName = getUserNameFormToken(token);
        // 判断是否过期
        // 判断用户信息是否一致

        return userName.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * 判断token是否过期
     * @param token
     * @return
     */
    private boolean isTokenExpired(String token) {

        // 从token中获取失效时间
        Date expireDate = getExpiredDateFormToken(token);
        return expireDate.before(new Date()); // 过期返回true
    }
    
    /**
     * 判断token是否可以被刷新
     * @param token
     * @return
     */
    public boolean canRefresh(String token) {

        return !isTokenExpired(token);
    }
   

    /**
     * 获取刷新token
     * @param token
     * @return
     */
    public String refreshToken(String token) {
        Claims claimsFromToken = getClaimsFromToken(token);
        claimsFromToken.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claimsFromToken);
    }

    /**
     * 从token中获取失效时间
     * @param token
     * @return
     */
    private Date getExpiredDateFormToken(String token) {
        Claims claimsFromToken = getClaimsFromToken(token);
        Date expiration = claimsFromToken.getExpiration();
        return expiration;
    }

    
}