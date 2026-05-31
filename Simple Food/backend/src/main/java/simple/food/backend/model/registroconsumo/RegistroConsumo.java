package simple.food.backend.model.registroconsumo;

import lombok.*;

import jakarta.persistence.*;
import simple.food.backend.dto.registroconsumo.AlimentoRequest;
import simple.food.backend.model.tabelanutricional.TabelaNutricional;
import simple.food.backend.model.usuario.Usuario;

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

    @Column(name = "quantidade_consumida", nullable = false)
    private Double quantidadeConsumida;

    @Column(name = "calorias_totais", nullable = false)
    private Double caloriasTotais;

    @Column(name = "data_hora_consumo", nullable = false)
    private LocalDateTime dataHoraConsumo;

    @Column(name = "foto_enviada_url")
    private String fotoEnviadaUrl;

    @Column(name = "gordura", nullable = false)
    private Double gordura;

    @Column(name = "proteina", nullable = false)
    private Double proteina;

    @Column(name = "carboidrato", nullable = false)
    private Double carboidrato;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_alimento", referencedColumnName = "n_mero_do_alimento", nullable = false)
    private TabelaNutricional tabelaNutricional;

    public RegistroConsumo(AlimentoRequest alimentoRequest, Usuario usuario,
                           TabelaNutricional tabelaNutricional, LocalDateTime dataHoraConsumo) {
        this.usuario = usuario;
        this.tabelaNutricional = tabelaNutricional;
        this.quantidadeConsumida = alimentoRequest.getQuantidadeConsumida();
        this.caloriasTotais = alimentoRequest.getCalorias();
        this.dataHoraConsumo = dataHoraConsumo;
        this.gordura = alimentoRequest.getGordura();
        this.proteina = alimentoRequest.getProteina();
        this.carboidrato = alimentoRequest.getCarboidrato();
        this.fotoEnviadaUrl = alimentoRequest.getFotoEnviadaUrl();
    }
}
