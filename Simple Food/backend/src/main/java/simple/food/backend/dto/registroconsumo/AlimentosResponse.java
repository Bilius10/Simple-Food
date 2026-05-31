package simple.food.backend.dto.registroconsumo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import simple.food.backend.model.registroconsumo.RegistroConsumo;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlimentosResponse {

        private Long registroConsumoId;
        private LocalDateTime dataConsumo;
        private Double gordura;
        private Double proteina;
        private Double carboidrato;
        private Double calorias;
        private Double quantidadeConsumida;
        private String fotoEnviadaUrl;

    public AlimentosResponse(RegistroConsumo registroConsumo) {
        this.registroConsumoId = registroConsumo.getId();
        this.dataConsumo = registroConsumo.getDataHoraConsumo();
        this.gordura = registroConsumo.getGordura();
        this.proteina = registroConsumo.getProteina();
        this.carboidrato = registroConsumo.getCarboidrato();
        this.calorias = registroConsumo.getCaloriasTotais();
        this.quantidadeConsumida = registroConsumo.getQuantidadeConsumida();
        this.fotoEnviadaUrl = registroConsumo.getFotoEnviadaUrl();
    }

    public List<AlimentosResponse> toList(List<RegistroConsumo> registroConsumo) {
        return registroConsumo.stream()
                .map(AlimentosResponse::new)
                .toList();
    }
}
