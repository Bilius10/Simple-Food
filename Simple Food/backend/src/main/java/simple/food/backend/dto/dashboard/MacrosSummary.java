package simple.food.backend.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MacrosSummary {
    private Double proteina;
    private Double carboidrato;
    private Double gordura;
    private Double caloriasTotais;
    private Object dataHoraConsumo;
}
