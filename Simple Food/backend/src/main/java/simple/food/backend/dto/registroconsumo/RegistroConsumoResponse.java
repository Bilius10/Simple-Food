package simple.food.backend.dto.registroconsumo;

import lombok.*;
import simple.food.backend.model.registroconsumo.RegistroConsumo;
import simple.food.backend.model.tabelanutricional.TabelaNutricional;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroConsumoResponse {
    private Long id;
    private LocalDateTime dataHoraConsumo;
    private List<InformacoesNutricionaisAlimento> informacoesNutricionaisAlimento;

    public RegistroConsumoResponse(RegistroConsumo registroConsumo, TabelaNutricional tabelaNutricional) {
        this.id = registroConsumo.getId();
    }
}

