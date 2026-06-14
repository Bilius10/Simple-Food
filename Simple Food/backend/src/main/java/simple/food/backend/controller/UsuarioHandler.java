package simple.food.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import simple.food.backend.dto.usuario.UsuarioDto;
import simple.food.backend.model.usuario.Usuario;
import simple.food.backend.model.usuario.UsuarioService;

@RequestMapping("/usuarios")
@RestController
public class UsuarioHandler {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> findById(@PathVariable Long id) {
        Usuario usuario = usuarioService.findById(id);
        UsuarioDto usuarioResponse = new UsuarioDto(usuario);
        return ResponseEntity.ok(usuarioResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDto> update(@PathVariable Long id, @RequestBody UsuarioDto usuarioRequest) {
        Usuario updatedUsuario = usuarioService.update(id, usuarioRequest);
        UsuarioDto usuarioResponse = new UsuarioDto(updatedUsuario);
        return ResponseEntity.ok(usuarioResponse);
    }
}
