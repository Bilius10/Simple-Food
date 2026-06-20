package simple.food.backend.dto.tabelanutricional;

import simple.food.backend.model.tabelanutricional.TabelaNutricional;

public class TabelaNutricionalResponse {

    private Long tabelaNutricionalId;

    private String categoriaDoAlimento;

    private String descricaoDosAlimentos;

    private Double gordura;

    private Double proteina;

    private Double carboidrato;

    private Double calorias;

    private Double umidade;

    public TabelaNutricionalResponse(TabelaNutricional tabelaNutricional) {
        this.tabelaNutricionalId = tabelaNutricional.getNumeroDoAlimento();
        this.categoriaDoAlimento = tabelaNutricional.getCategoriaDoAlimento();
        this.descricaoDosAlimentos = tabelaNutricional.getDescricaoDosAlimentos();
        this.gordura = tabelaNutricional.getLipideosG();
        this.proteina = tabelaNutricional.getProteinaG();
        this.carboidrato = tabelaNutricional.getCarboidratoG();
        this.calorias = tabelaNutricional.getEnergiaKcal();
        this.umidade = tabelaNutricional.getUmidade();
    }
}
