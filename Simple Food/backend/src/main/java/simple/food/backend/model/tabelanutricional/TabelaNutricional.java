package simple.food.backend.model.tabelanutricional;

import lombok.*;

import jakarta.persistence.*;

@Entity
@Table(name = "tabela_nutricional")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TabelaNutricional {
    @Id
    @Column(name = "n_mero_do_alimento")
    private Long numeroDoAlimento;

    @Column(name = "categoria_do_alimento")
    private String categoriaDoAlimento;

    @Column(name = "descri_o_dos_alimentos")
    private String descricaoDosAlimentos;

    @Column(name = "umidade")
    private Double umidade;

    @Column(name = "energia_kcal")
    private Double energiaKcal;

    @Column(name = "energia_kj")
    private Double energiaKj;

    @Column(name = "prote_na_g")
    private Double proteinaG;

    @Column(name = "lip_deos_g")
    private Double lipideosG;

    @Column(name = "colesterol_mg")
    private Double colesterolMg;

    @Column(name = "carboidrato_g")
    private Double carboidratoG;

    @Column(name = "fibra_alimentar_g")
    private Double fibraAlimentarG;

    @Column(name = "cinzas_g")
    private Double cinzasG;

    @Column(name = "c_lcio_mg")
    private Double calcioMg;

    @Column(name = "magn_sio_mg")
    private Double magnesioMg;

    @Column(name = "mangan_s_mg")
    private Double manganesMg;

    @Column(name = "f_sforo_mg")
    private Double fosforoMg;

    @Column(name = "ferro_mg")
    private Double ferroMg;

    @Column(name = "s_dio_mg")
    private Double sodioMg;

    @Column(name = "pot_ssio_mg")
    private Double potassioMg;

    @Column(name = "cobre_mg")
    private Double cobreMg;

    @Column(name = "zinco_mg")
    private Double zincoMg;

    @Column(name = "retinol_mcg")
    private Double retinolMcg;

    @Column(name = "re_mcg")
    private Double reMcg;

    @Column(name = "rae_mcg")
    private Double raeMcg;

    @Column(name = "tiamina_mg")
    private Double tiaminaMg;

    @Column(name = "riboflavina_mg")
    private Double riboflavinaMg;

    @Column(name = "piridoxina_mg")
    private Double piridoxinaMg;

    @Column(name = "niacina_mg")
    private Double niacinaMg;

    @Column(name = "vitamina_c_mg")
    private Double vitaminaCMg;

    @Column(name = "triptofano_g")
    private String triptofanoG;

    @Column(name = "treonina_g")
    private Double treoninaG;

    @Column(name = "isoleucina_g")
    private Double isoleucinaG;

    @Column(name = "leucina_g")
    private Double leucinaG;

    @Column(name = "lisina_g")
    private Double lisinaG;

    @Column(name = "metionina_g")
    private Double metioninaG;

    @Column(name = "cistina_g")
    private Double cistinaG;

    @Column(name = "fenilalanina_g")
    private Double fenilalaninaG;

    @Column(name = "tirosina_g")
    private Double tirosinaG;

    @Column(name = "valina_g")
    private Double valinaG;

    @Column(name = "arginina_g")
    private Double argininaG;

    @Column(name = "histidina_g")
    private Double histidinaG;

    @Column(name = "alanina_g")
    private Double alaninaG;

    @Column(name = "cido_asp_rtico_g")
    private Double acidoAsparticoG;

    @Column(name = "cido_glut_mico_g")
    private Double acidoGlutamicoG;

    @Column(name = "glicina_g")
    private Double glicinaG;

    @Column(name = "prolina_g")
    private Double prolinaG;

    @Column(name = "serina_g")
    private Double serinaG;

    @Column(name = "saturados_g")
    private Double saturadosG;

    @Column(name = "mono_insaturados_g")
    private Double monoInsaturadosG;

    @Column(name = "poli_insaturados_g")
    private Double poliInsaturadosG;

    @Column(name = "x12_0_g")
    private Double x12_0_g;

    @Column(name = "x14_0_g")
    private Double x14_0_g;

    @Column(name = "x16_0_g")
    private Double x16_0_g;

    @Column(name = "x18_0_g")
    private Double x18_0_g;

    @Column(name = "x20_0_g")
    private Double x20_0_g;

    @Column(name = "x22_0_g")
    private Double x22_0_g;

    @Column(name = "x24_0_g")
    private Double x24_0_g;

    @Column(name = "x14_1_g")
    private Double x14_1_g;

    @Column(name = "x16_1_g")
    private Double x16_1_g;

    @Column(name = "x18_1_g")
    private Double x18_1_g;

    @Column(name = "x20_1_g")
    private Double x20_1_g;

    @Column(name = "x18_2_n_6_g")
    private Double x18_2_n_6_g;

    @Column(name = "x18_3_n_3_g")
    private Double x18_3_n_3_g;

    @Column(name = "x20_4_g")
    private Double x20_4_g;

    @Column(name = "x20_5_g")
    private Double x20_5_g;

    @Column(name = "x22_5_g")
    private Double x22_5_g;

    @Column(name = "x22_6_g")
    private Double x22_6_g;

    @Column(name = "x18_1t_g")
    private Double x18_1t_g;

    @Column(name = "x18_2t_g")
    private Double x18_2t_g;
}
