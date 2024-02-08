package uz.cosinus.thinkstore.service.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import uz.cosinus.thinkstore.exception.AuthException;

import java.io.IOException;

@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private JwtService jwtService;
    private AuthenticationService authenticationService;
    private HandlerExceptionResolver handlerExceptionResolver;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if(authorization == null || !authorization.startsWith("Bearer")) {
            filterChain.doFilter(request,response);
            return;
        }

        try{
            String token = authorization.substring(7);
            Jws<Claims> claimsJws = jwtService.extractToken(token);
            try {

                authenticationService.authenticate(claimsJws.getBody(),request);
                filterChain.doFilter(request,response);
            }catch (NullPointerException e) {
                throw new AccessDeniedException("Refresh token is not access token");
            }
        }catch (ExpiredJwtException e) {
            handlerExceptionResolver.resolveException(request,response,null,new AuthException("Token expired"));
        }
    }
}
