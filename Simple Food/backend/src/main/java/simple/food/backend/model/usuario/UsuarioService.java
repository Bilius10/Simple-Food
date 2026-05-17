package simple.food.backend.model.usuario;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import simple.food.backend.infrastructur.email.SpringMailSenderService;
import simple.food.backend.infrastructur.exception.ErrorMessages;
import simple.food.backend.infrastructur.exception.ServiceException;

@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;
    private PasswordEncoder passwordEncoder;
    private SpringMailSenderService springMailSenderService;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, SpringMailSenderService springMailSenderService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.springMailSenderService = springMailSenderService;
    }

    public void register(String nome, String email, String whatsappNumber, String password) {
        validateExistsByEmailOrWhatsappNumber(email, whatsappNumber);

        String encode = passwordEncoder.encode(password);

        Usuario usuario = new Usuario(nome, email, encode, whatsappNumber);

        springMailSenderService.sendWelcomeEmail(email, nome, "teste");
        usuarioRepository.save(usuario);
    }

    private void validateExistsByEmailOrWhatsappNumber(String email, String whatsappNumber) {
        if(usuarioRepository.existsByEmailOrNumeroWhatsapp(email, whatsappNumber)) {
            throw new ServiceException(ErrorMessages.EMAIL_OR_WHATSAPP_NUMBER_ALREADY_EXISTS
                    ,HttpStatus.BAD_REQUEST);
        }
    }
}
