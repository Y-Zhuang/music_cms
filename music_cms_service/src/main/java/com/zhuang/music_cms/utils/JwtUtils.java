package com.zhuang.music_cms.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * @Package     : com.zhuang.music_cms.config
 * @ClassName   : JwtConfig 
 * @Description : jwt配置类
 * @Author      : Zhuang
 * @Date        : 2020-04-21 23:23
 */

@Getter
@Setter
@ConfigurationProperties(prefix = "jwt.config")
public class JwtUtils {

    private String key;

    private Long expire;

    public String createJwt(Integer id, String userName, Map<String, Object> map){
        return Jwts.builder()
                .setClaims(map)
                .setId(id.toString())
                .setSubject(userName)
                .setIssuedAt(new Date())
                .setExpiration(new Date((expire + System.currentTimeMillis())))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public Claims parseJwt(String token){
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
    }
}
