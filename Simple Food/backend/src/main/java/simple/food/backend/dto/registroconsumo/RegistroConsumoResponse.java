package simple.food.backend.dto.registroconsumo;

import lombok.*;
import simple.food.backend.model.registroconsumo.RegistroConsumo;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroConsumoResponse {

    private List<AlimentosResponse> alimentoRequest;

    public RegistroConsumoResponse fromRegistroConsumo(List<RegistroConsumo> registros) {
        return new RegistroConsumoResponse(new AlimentosResponse().toList(registros));
    }
}

