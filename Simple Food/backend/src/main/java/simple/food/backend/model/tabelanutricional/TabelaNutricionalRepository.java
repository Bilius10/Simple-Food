package simple.food.backend.model.tabelanutricional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TabelaNutricionalRepository extends JpaRepository<TabelaNutricional, Long> {

    List<TabelaNutricional> findByDescricaoDosAlimentosContainingIgnoreCase(String descricaoDosAlimentos);
}

