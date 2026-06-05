package simple.food.backend.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopFoodDTO {
    private Long numeroDoAlimento;
    private String categoria;
    private String descricao;
    private Double quantidadeTotal;
    private Double caloriasTotal;
    private Long registrosCount;

}

