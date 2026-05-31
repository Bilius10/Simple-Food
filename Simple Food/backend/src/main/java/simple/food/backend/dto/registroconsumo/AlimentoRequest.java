package simple.food.backend.dto.registroconsumo;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlimentoRequest {

    @NotNull(message = "{registro.consumo.tabelaNutricionalId.required}")
    private Long tabelaNutricionalId;

    @NotNull(message = "{registro.consumo.gordura.required}")
    @Digits(integer = 10, fraction = 2, message = "{registro.consumo.gordura.format}")
    private Double gordura;

    @NotNull(message = "{registro.consumo.proteina.required}")
    @Digits(integer = 10, fraction = 2, message = "{registro.consumo.proteina.format}")
    private Double proteina;

    @NotNull(message = "{registro.consumo.carboidrato.required}")
    @Digits(integer = 10, fraction = 2, message = "{registro.consumo.carboidrato.format}")
    private Double carboidrato;

    @NotNull(message = "{registro.consumo.caloriasTotais.required}")
    @Digits(integer = 10, fraction = 2, message = "{registro.consumo.caloriasTotais.format}")
    private Double calorias;

    @NotNull(message = "{registro.consumo.quantidade.required}")
    @DecimalMin(value = "0.01", inclusive = true, message = "{registro.consumo.quantidade.positive}")
    @Digits(integer = 10, fraction = 2, message = "{registro.consumo.quantidade.format}")
    private Double quantidadeConsumida;

    @Size(max = 2048, message = "{registro.consumo.fotoEnviadaUrl.size}")
    private String fotoEnviadaUrl;
}
