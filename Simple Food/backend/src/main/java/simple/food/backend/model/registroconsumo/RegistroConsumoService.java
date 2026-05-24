package simple.food.backend.model.registroconsumo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import simple.food.backend.dto.registroconsumo.RegistroConsumoRequest;
import simple.food.backend.dto.registroconsumo.RegistroConsumoResponse;
import simple.food.backend.model.tabelanutricional.TabelaNutricionalService;
import simple.food.backend.model.usuario.UsuarioService;

@Service
public class RegistroConsumoService {

    @Autowired
    private RegistroConsumoRepository registroConsumoRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TabelaNutricionalService tabelaNutricionalService;

    public RegistroConsumoResponse create(RegistroConsumoRequest registroConsumoRequest) {
        var usuario = usuarioService.findById(registroConsumoRequest.getUsuarioId());
        var tabelaNutricional = tabelaNutricionalService.findById(registroConsumoRequest.getTabelaNutricionalId());

        RegistroConsumo registroConsumo = new RegistroConsumo(usuario, tabelaNutricional, registroConsumoRequest);

        registroConsumoRepository.save(registroConsumo);

        return new RegistroConsumoResponse(registroConsumo);
    }
}
