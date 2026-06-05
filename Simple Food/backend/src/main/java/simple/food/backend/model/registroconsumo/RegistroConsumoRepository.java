package simple.food.backend.model.registroconsumo;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import simple.food.backend.dto.dashboard.MacrosSummary;
import simple.food.backend.dto.dashboard.TopFoodDTO;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RegistroConsumoRepository extends JpaRepository<RegistroConsumo, Long> {

    @Query("""
    SELECT new simple.food.backend.dto.dashboard.MacrosSummary(
        COALESCE(SUM(r.proteina), 0.0),
        COALESCE(SUM(r.carboidrato), 0.0),
        COALESCE(SUM(r.gordura), 0.0),
        COALESCE(SUM(r.caloriasTotais), 0.0),
        FUNCTION('date_trunc', 'month', r.dataHoraConsumo)
    )
    FROM RegistroConsumo r
    WHERE r.usuario.id = :userId
      AND r.dataHoraConsumo BETWEEN :start AND :end
    GROUP BY FUNCTION('date_trunc', 'month', r.dataHoraConsumo)
    ORDER BY FUNCTION('date_trunc', 'month', r.dataHoraConsumo)
    """)
    List<MacrosSummary> getSumMacrosByUserBetweenDates(
            @Param("userId") Long userId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    @Query("""
    SELECT new simple.food.backend.dto.dashboard.TopFoodDTO(
        r.tabelaNutricional.numeroDoAlimento,
        r.tabelaNutricional.categoriaDoAlimento,
        r.tabelaNutricional.descricaoDosAlimentos,
        COALESCE(SUM(r.quantidadeConsumida), 0.0),
        COALESCE(SUM(r.caloriasTotais), 0.0),
        COUNT(r)
    )
    FROM RegistroConsumo r
    WHERE r.usuario.id = :userId
      AND r.dataHoraConsumo BETWEEN :start AND :end
    GROUP BY r.tabelaNutricional.numeroDoAlimento, r.tabelaNutricional.categoriaDoAlimento, r.tabelaNutricional.descricaoDosAlimentos
    ORDER BY COALESCE(SUM(r.caloriasTotais), 0.0) DESC
    """)
    List<TopFoodDTO> findTopFoodsByUserIdBetweenDates(Long userId, LocalDateTime start, LocalDateTime end);
}
