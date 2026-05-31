package simple.food.backend.infrastructur.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import simple.food.backend.infrastructur.exception.ErrorMessages;
import simple.food.backend.infrastructur.exception.ServiceException;
import simple.food.backend.model.usuario.UsuarioRepository;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter{

    private final UsuarioRepository repository;
    private final TokenService tokenService;

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = recoverToken(request);

        if (token != null) {
            String email = tokenService.getSubject(token);
            var user = repository.findByEmail(email)
                    .orElseThrow(() -> new ServiceException(ErrorMessages.USER_NOT_FOUND_BY_EMAIL, HttpStatus.INTERNAL_SERVER_ERROR));

            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        String authHeader = request.getHeader(AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith(BEARER)) {
            return authHeader.substring(BEARER.length());
        }

        if (request.getCookies() != null) {
            for (Cookie c : request.getCookies()) {
                if ("sf_token".equals(c.getName())) {
                    return c.getValue();
                }
            }
        }

        return null;
    }
}
