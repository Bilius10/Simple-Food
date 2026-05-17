package simple.food.backend.model.registroconsumo;

import lombok.*;

import jakarta.persistence.*;
import simple.food.backend.model.tabelanutricional.TabelaNutricional;
import simple.food.backend.model.usuario.Usuario;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "registros_consumo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroConsumo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantidade_consumida", nullable = false, precision = 10, scale = 2)
    private BigDecimal quantidadeConsumida;

    @Column(name = "calorias_totais", nullable = false, precision = 10, scale = 2)
    private BigDecimal caloriasTotais;

    @Column(name = "data_hora_consumo", nullable = false)
    private LocalDateTime dataHoraConsumo;

    @Column(name = "foto_enviada_url")
    private String fotoEnviadaUrl;

    @Column(name = "gordura", nullable = false, precision = 10, scale = 2)
    private BigDecimal gordura;

    @Column(name = "proteina", nullable = false, precision = 10, scale = 2)
    private BigDecimal proteina;

    @Column(name = "carboidrato", nullable = false, precision = 10, scale = 2)
    private BigDecimal carboidrato;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_alimento", referencedColumnName = "n_mero_do_alimento", nullable = false)
    private TabelaNutricional tabelaNutricional;
}
