package simple.food.backend.dto.registroconsumo;

import jakarta.validation.constraints.*;
import simple.food.backend.model.tabelanutricional.TabelaNutricional;

import java.math.BigDecimal;

public class InformacoesNutricionaisAlimento {

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

    public InformacoesNutricionaisAlimento(TabelaNutricional tabelaNutricional, Double quantidadeConsumida,
                                           String fotoEnviadaUrl, Double calorias) {
        this.tabelaNutricionalId = tabelaNutricional.getNumeroDoAlimento();
        this.gordura = tabelaNutricional.getLipideosG();
        this.proteina = tabelaNutricional.getProteinaG();
        this.carboidrato = tabelaNutricional.getCarboidratoG();
        this.calorias = calorias;
        this.quantidadeConsumida = quantidadeConsumida;
        this.fotoEnviadaUrl = fotoEnviadaUrl;
    }
}
