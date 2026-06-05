package simple.food.backend.model.registroconsumo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import simple.food.backend.dto.dashboard.MacrosSummary;
import simple.food.backend.dto.dashboard.TopFoodDTO;
import simple.food.backend.dto.registroconsumo.AlimentoRequest;
import simple.food.backend.dto.registroconsumo.RegistroConsumoRequest;
import simple.food.backend.dto.registroconsumo.RegistroConsumoResponse;
import simple.food.backend.model.tabelanutricional.TabelaNutricional;
import simple.food.backend.model.tabelanutricional.TabelaNutricionalService;
import simple.food.backend.model.usuario.Usuario;
import simple.food.backend.model.usuario.UsuarioService;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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

        List<RegistroConsumo> registroConsumos = mapToRegistroConsumoList(registroConsumoRequest.getAlimentos(),
                usuario, registroConsumoRequest.getDataHoraConsumo());

        registroConsumoRepository.saveAll(registroConsumos);
        return new RegistroConsumoResponse().fromRegistroConsumo(registroConsumos);
    }

    private List<RegistroConsumo> mapToRegistroConsumoList(List<AlimentoRequest> alimentoRequest, Usuario usuario,
                                                           LocalDateTime dataHoraConsumo) {

        List<RegistroConsumo> consumos = new ArrayList<>(List.of());

        alimentoRequest.forEach(alimento -> {
            TabelaNutricional tabelaNutricional =
                    tabelaNutricionalService.findById(alimento.getTabelaNutricionalId());

            consumos.add(new RegistroConsumo(alimento, usuario, tabelaNutricional, dataHoraConsumo));
        });

        return consumos;
    }

    public List<MacrosSummary> getSumMacrosByUserBetweenDates(Long userId, LocalDateTime start, LocalDateTime end) {
        usuarioService.hasRoleOrIsOwner(userId);
        return registroConsumoRepository.getSumMacrosByUserBetweenDates(userId, start.with(LocalTime.MIN),
                end.with(LocalTime.MAX));
    }

    public List<TopFoodDTO> findTopFoodsByUserIdBetweenDates(Long userId, LocalDateTime start, LocalDateTime end) {
        usuarioService.hasRoleOrIsOwner(userId);
        return registroConsumoRepository.findTopFoodsByUserIdBetweenDates(userId, start.with(LocalTime.MIN),
                end.with(LocalTime.MAX));
    }
}
