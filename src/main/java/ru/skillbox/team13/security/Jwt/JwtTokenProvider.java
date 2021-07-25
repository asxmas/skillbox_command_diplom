package ru.skillbox.team13.security.Jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.skillbox.team13.entity.User;
import ru.skillbox.team13.repository.BlacklistedTokenRepository;
import ru.skillbox.team13.repository.UserRepository;
import ru.skillbox.team13.security.JwtUserDetailsService;
import ru.skillbox.team13.security.TokenType;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {

    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.ordinary.expired}")
    private long ordinaryValidityInMillis;

    @Value("${jwt.token.recovery.expired}")
    private long recoveryValidityInMillis;

    @Value("${jwt.token.mail.expired}")
    private long mailValidityInMillis;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private BlacklistedTokenRepository blacklistedTokenRepo;

    @Autowired
    private UserRepository userRepository;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
        return bCryptPasswordEncoder;
    }

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken(String username, TokenType type) {

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("type", type.toString());

        //установка времени валидности токена для аутентификации, перехода по mail и ввода пароля
        long tokenValidity = 0;
        switch (type) {
            case ORDINARY:
                tokenValidity = ordinaryValidityInMillis;
                break;
            case MAIL_LINK:
                tokenValidity = mailValidityInMillis;
                break;
            case RECOVERY:
                tokenValidity = recoveryValidityInMillis;
                break;
        }

        Date now = new Date();
        Date validity = new Date(now.getTime() + tokenValidity);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        //Postman добавляет к токену "Bearer ", фронт - нет. Убираем префикс если он есть
        if (bearerToken != null) {
            if (bearerToken.startsWith("Bearer")) bearerToken = bearerToken.substring(7, bearerToken.length());
            return bearerToken;
        }
        return null;
    }

    public Date resolveTokenDate(String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        return claims.getBody().getExpiration();
    }

    public boolean validateToken(String token) {
        try {
            if (blacklistedTokenRepo.findByToken(token).isPresent() ||
                    userRepository.findByEmail(getUsername(token)).isEmpty()) {
                throw new JwtException("JWT token in blacklist");
            }
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
