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
    private double proteina;
    private double carboidrato;
    private double gordura;
    private double calorias;
}
