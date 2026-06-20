package simple.food.backend.controller.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class UsuarioWebController {

    @GetMapping("/usuarios/{id}/editar")
    public String editUsuario(@PathVariable Long id, Model model) {
        model.addAttribute("userId", id);
        return "usuario-edit";
    }
}
