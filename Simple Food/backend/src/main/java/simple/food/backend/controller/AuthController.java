package simple.food.backend.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import simple.food.backend.dto.auth.AuthResponse;
import simple.food.backend.dto.auth.LoginRequest;
import simple.food.backend.dto.auth.RegisterRequest;
import simple.food.backend.model.usuario.UsuarioService;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Value("${security.jwt.expiration-hours}")
    private long expiration;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterRequest req) {
        usuarioService.register(req.getName(), req.getEmail(), req.getWhatsappNumber(), req.getPassword());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest req) {
        String token = usuarioService.login(req.getEmail(), req.getPassword());

        AuthResponse authResponse = new AuthResponse(token, LocalDateTime.now().plusHours(expiration));

        return ResponseEntity.ok().body(authResponse);
    }
}

