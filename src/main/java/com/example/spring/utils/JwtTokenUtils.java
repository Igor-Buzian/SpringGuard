package com.example.spring.utils;


import com.example.spring.dto.RegisterDtoValues;
import com.example.spring.entity.User;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtils {
   @Value("${jwt.secret}")
    private String secret;

   @Value("${jwt.expiration}")
    private Long expiration;

public String generateToken(User user){
    Map<String, Object> claims = new HashMap<>();
    claims.put("username", user.getEmail());
    claims.put("enabled", true);
    claims.put("role","Role_User");
    claims.put("updateAt", new Date());
    return Jwts
            .builder()
            .setClaims(claims)
            .setSubject(user.getEmail())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis()+expiration*1000))
            .signWith(SignatureAlgorithm.HS512,secret)
            .compact();
}
    public String getRoleFromToken(String token){
        return Jwts
                .parser()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }

public String getLogin(String token){
    return Jwts
            .parser()
            .setSigningKey(secret)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
}
public String getRole(String token){
    return Jwts
            .parser()
            .setSigningKey(secret)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .get("enabled", String.class);
}

public boolean validateToken(String token){
  try {
      Jwts.parser().setSigningKey(secret).build().parseClaimsJws(token);
      return true;
  }
  catch (JwtException | IllegalArgumentException  e){
      return false;
  }
}

}
