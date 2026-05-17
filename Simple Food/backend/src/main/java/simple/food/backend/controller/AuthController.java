package simple.food.backend.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import simple.food.backend.dto.AuthResponse;
import simple.food.backend.dto.LoginRequest;
import simple.food.backend.dto.RegisterRequest;
import simple.food.backend.model.usuario.UsuarioRepository;
import simple.food.backend.model.usuario.UsuarioService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterRequest req) {
        usuarioService.register(req.getName(), req.getEmail(), req.getWhatsappNumber(), req.getPassword());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest req) {
        return null;
    }
}

