package simple.food.backend.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import simple.food.backend.dto.dashboard.MacrosSummary;
import simple.food.backend.dto.dashboard.TopFoodDTO;
import simple.food.backend.dto.registroconsumo.RegistroConsumoRequest;
import simple.food.backend.dto.registroconsumo.RegistroConsumoResponse;
import simple.food.backend.model.registroconsumo.RegistroConsumoService;
import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/registro_consumo")
@RestController
public class RegistroConsumoHandler {

    @Autowired
    private RegistroConsumoService registroConsumoService;

    @PostMapping
    public ResponseEntity<RegistroConsumoResponse> create(@RequestBody @Valid RegistroConsumoRequest req) {
        var registroConsumoResponse = registroConsumoService.create(req);
        return ResponseEntity.ok(registroConsumoResponse);
    }

    @GetMapping("/macros")
    public ResponseEntity<List<MacrosSummary>> getSumMacrosByUserBetweenDates(@RequestParam Long userId,
                                                                        @RequestParam LocalDateTime start,
                                                                        @RequestParam LocalDateTime end) {
        List<MacrosSummary> macrosSummary = registroConsumoService.getSumMacrosByUserBetweenDates(userId, start, end);
        return ResponseEntity.ok(macrosSummary);
    }

    @GetMapping("/top-foods")
    public ResponseEntity<List<TopFoodDTO>> getTopFoodsBy(@RequestParam Long userId,
                                                        @RequestParam LocalDateTime start,
                                                        @RequestParam LocalDateTime end) {
        List<TopFoodDTO> topFoods = registroConsumoService.findTopFoodsByUserIdBetweenDates(userId, start, end);
        return ResponseEntity.ok(topFoods);
    }
}
