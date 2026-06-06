package simple.food.backend.dto.dashboard;

import lombok.*;
import simple.food.backend.model.registroconsumo.RegistroConsumo;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FoodsConsumed {
    private Long numeroDoAlimento;
    private String categoria;
    private String descricao;
    private LocalDateTime dataConsumo;
    private Double gordura;
    private Double proteina;
    private Double carboidrato;
    private Double calorias;
    private Double quantidadeConsumida;

    public FoodsConsumed(RegistroConsumo registroConsumo) {
        numeroDoAlimento = registroConsumo.getTabelaNutricional().getNumeroDoAlimento();
        categoria = registroConsumo.getTabelaNutricional().getCategoriaDoAlimento();
        descricao = registroConsumo.getTabelaNutricional().getDescricaoDosAlimentos();
        dataConsumo = registroConsumo.getDataHoraConsumo();
        gordura = registroConsumo.getGordura();
        proteina = registroConsumo.getProteina();
        carboidrato = registroConsumo.getCarboidrato();
        calorias = registroConsumo.getCaloriasTotais();
        quantidadeConsumida = registroConsumo.getQuantidadeConsumida();
    }
}
