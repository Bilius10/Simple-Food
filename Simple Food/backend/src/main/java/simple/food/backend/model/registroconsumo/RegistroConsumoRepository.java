package simple.food.backend.model.registroconsumo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import simple.food.backend.dto.dashboard.MacrosSummary;

import java.time.LocalDateTime;

@Repository
public interface RegistroConsumoRepository extends JpaRepository<RegistroConsumo, Long> {

    Page<RegistroConsumo> findByUsuarioIdAndDataHoraConsumoBetweenOrderByDataHoraConsumoDesc(
            Long usuarioId, LocalDateTime start, LocalDateTime end, Pageable pageable
    );

    @Query("SELECT new simple.food.backend.dto.dashboard.MacrosSummary(" +
            "COALESCE(SUM(r.proteina), 0.0), " +
            "COALESCE(SUM(r.carboidrato), 0.0), " +
            "COALESCE(SUM(r.gordura), 0.0), " +
            "COALESCE(SUM(r.caloriasTotais), 0.0)" +
            ") " +
            "FROM RegistroConsumo r " +
            "WHERE r.usuario.id = :userId " +
            "AND r.dataHoraConsumo BETWEEN :start AND :end")
    MacrosSummary sumMacrosByUserBetweenDates(@Param("userId") Long userId,
                                              @Param("start") LocalDateTime start,
                                              @Param("end") LocalDateTime end);
}
