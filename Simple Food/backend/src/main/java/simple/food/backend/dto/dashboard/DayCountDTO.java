package simple.food.backend.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DayCountDTO {
    private Integer year;
    private Integer month;
    private Integer day;
    private Long count;
}
