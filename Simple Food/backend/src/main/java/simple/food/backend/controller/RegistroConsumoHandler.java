package simple.food.backend.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import simple.food.backend.dto.registroconsumo.RegistroConsumoRequest;
import simple.food.backend.dto.registroconsumo.RegistroConsumoResponse;
import simple.food.backend.model.registroconsumo.RegistroConsumoService;

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
}
