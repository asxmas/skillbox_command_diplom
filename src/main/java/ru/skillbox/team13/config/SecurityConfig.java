package ru.skillbox.team13.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.skillbox.team13.security.Jwt.JwtConfigurer;
import ru.skillbox.team13.security.Jwt.JwtTokenProvider;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    private final JwtTokenProvider jwtTokenProvider;

    @Value("${server.base_url}")
    private String BASE_URL;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .authorizeRequests()
//                .antMatchers("/**").permitAll()
                .antMatchers("/api/v1/auth/**").permitAll()
                .antMatchers("/api/v1/platform/**").permitAll()
                .antMatchers("/api/v1/account/**").permitAll()
                .antMatchers("/api/v1/profile/**").hasAuthority("user")
                .antMatchers("/api/v1/post/**").hasAuthority("user")
                .antMatchers("/api/v1/likes/**").hasAuthority("user")
                .antMatchers("/api/v1/liked/**").hasAuthority("user")
                .antMatchers("/api/v1/friends/**").hasAuthority("user")
                .antMatchers("/api/v1/feeds/**").hasAuthority("user")
                .antMatchers("/api/v1/dialogs/**").hasAuthority("user")
                .antMatchers("/api/v1/notifications/**").hasAuthority("user")
                .antMatchers("/api/v1/storage/**").hasAuthority("user")
                .antMatchers("/api/v1/tags/**").hasAuthority("user")
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider))
                .and()
                .cors()
                .and()
                .sessionManagement()
                    .maximumSessions(-1)
                    .sessionRegistry(sessionRegistry());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(BASE_URL)
                .allowedHeaders("*")
                .allowCredentials(true)
                .allowedMethods("*");
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
}
