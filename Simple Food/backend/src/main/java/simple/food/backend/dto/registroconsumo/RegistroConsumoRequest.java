package simple.food.backend.dto.registroconsumo;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroConsumoRequest {

    @NotNull(message = "{registro.consumo.dataHora.required}")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataHoraConsumo;

    @NotNull(message = "{registro.consumo.usuarioId.required}")
    private Long usuarioId;

    private List<AlimentoRequest> alimentos;
}
