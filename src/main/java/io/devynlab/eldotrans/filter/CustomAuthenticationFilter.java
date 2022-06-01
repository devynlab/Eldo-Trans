package io.devynlab.eldotrans.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  private final AuthenticationManager authManager;

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
    return authManager.authenticate(authToken);
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
    User user = (User) authentication.getPrincipal();
    Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
    String accessToken = JWT.create()
        .withSubject(user.getUsername())
        .withExpiresAt(new Date((long) (System.currentTimeMillis() + 4.32e+7)))
        .withIssuer(request.getRequestURL().toString())
        .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
        .sign(algorithm);
    String refreshToken = JWT.create()
        .withSubject(user.getUsername())
        .withExpiresAt(new Date((long) (System.currentTimeMillis() + 8.64e+7)))
        .withIssuer(request.getRequestURL().toString())
        .sign(algorithm);
        /*
            response.setHeader("accessToken", accessToken);
            response.setHeader("refreshToken", refreshToken);
        */
    Map<String, Map> hashMap = new HashMap<>();
    Map<String, String> tokens = new HashMap<>();
    Map<String, String> userDetails = new HashMap<>();
    tokens.put("access_token", accessToken);
    tokens.put("refresh_token", refreshToken);
    userDetails.put("username", user.getUsername());
    hashMap.put("tokens", tokens);
    hashMap.put("user", userDetails);
    response.setContentType(APPLICATION_JSON_VALUE);
    new ObjectMapper().writeValue(response.getOutputStream(), hashMap);
  }
}
