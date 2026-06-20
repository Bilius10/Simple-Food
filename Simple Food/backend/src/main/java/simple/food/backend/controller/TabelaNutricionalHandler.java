package simple.food.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import simple.food.backend.dto.tabelanutricional.TabelaNutricionalResponse;
import simple.food.backend.model.tabelanutricional.TabelaNutricionalService;

import java.util.List;

@RestController
@RequestMapping("/tabela_nutricional")
public class TabelaNutricionalHandler {

    @Autowired
    private TabelaNutricionalService tabelaNutricionalService;

    @GetMapping("/search")
    public ResponseEntity<List<TabelaNutricionalResponse>> searchFoodByName(String description) {
        List<TabelaNutricionalResponse> foodsByName = tabelaNutricionalService.findFoodsByName(description);
        return ResponseEntity.ok(foodsByName);
    }
}
