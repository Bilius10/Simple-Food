package simple.food.backend.model.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import simple.food.backend.infrastructur.email.SpringMailSenderService;
import simple.food.backend.infrastructur.exception.ErrorMessages;
import simple.food.backend.infrastructur.exception.ServiceException;
import simple.food.backend.infrastructur.security.TokenService;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SpringMailSenderService springMailSenderService;

    @Autowired
    private TokenService tokenService;

    public void register(String nome, String email, String whatsappNumber, String password) {
        validateExistsByEmailOrWhatsappNumber(email, whatsappNumber);

        String encode = passwordEncoder.encode(password);

        Usuario usuario = new Usuario(nome, email, encode, whatsappNumber);

        springMailSenderService.sendWelcomeEmail(email, nome, "teste");
        usuarioRepository.save(usuario);
    }

    public String login(String email, String password){
        Usuario user = findByEmail(email);

        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new ServiceException(ErrorMessages.INVALID_CREDENTIALS, HttpStatus.UNAUTHORIZED);
        }

        return tokenService.generateToken(user);
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
}
