package simple.food.backend.model.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import simple.food.backend.dto.auth.AuthResponse;
import simple.food.backend.infrastructur.email.SpringMailSenderService;
import simple.food.backend.infrastructur.exception.ErrorMessages;
import simple.food.backend.infrastructur.exception.ServiceException;
import simple.food.backend.infrastructur.security.SecurityFilter;
import simple.food.backend.infrastructur.security.TokenService;

import java.time.LocalDateTime;

@Service
public class UsuarioService {

    @Value("${security.jwt.expiration-hours}")
    private long expiration;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SpringMailSenderService springMailSenderService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private SecurityFilter securityFilter;

    public void register(String nome, String email, String whatsappNumber, String password) {
        validateExistsByEmailOrWhatsappNumber(email, whatsappNumber);

        String encode = passwordEncoder.encode(password);

        Usuario usuario = new Usuario(nome, email, encode, whatsappNumber);

        springMailSenderService.sendWelcomeEmail(email, nome, "teste");
        usuarioRepository.save(usuario);
    }

    public AuthResponse login(String email, String password){
        Usuario user = findByEmail(email);

        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new ServiceException(ErrorMessages.INVALID_CREDENTIALS, HttpStatus.UNAUTHORIZED);
        }

        return new AuthResponse(tokenService.generateToken(user), LocalDateTime.now().plusHours(expiration), user.getId());
    }

    private void validateExistsByEmailOrWhatsappNumber(String email, String whatsappNumber) {
        if(usuarioRepository.existsByEmailOrNumeroWhatsapp(email, whatsappNumber)) {
            throw new ServiceException(ErrorMessages.EMAIL_OR_WHATSAPP_NUMBER_ALREADY_EXISTS
                    ,HttpStatus.BAD_REQUEST);
        }
    }

    private Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ServiceException(ErrorMessages.INVALID_CREDENTIALS, HttpStatus.NOT_FOUND));
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorMessages.USER_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    public void hasRoleOrIsOwner(Long userId) {
        Usuario authenticatedUser = securityFilter.getAuthenticatedUser();

        if(authenticatedUser.getRole().equals(UserRole.ADMIN) ||
                authenticatedUser.getRole().equals(UserRole.NUTRITIONIST)) {
            return;
        }

        if(authenticatedUser.getId().equals(userId)) {
            return;
        }

        throw new ServiceException(ErrorMessages.USER_NOT_AUTHORIZED, HttpStatus.UNAUTHORIZED);
    }
}
