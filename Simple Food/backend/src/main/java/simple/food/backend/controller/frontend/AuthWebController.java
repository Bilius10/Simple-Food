package simple.food.backend.controller.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthWebController {

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/login/")
    public String loginPageSlash() {
        return "login";
    }

    @GetMapping("/registro")
    public String registroPage() {
        return "registro";
    }
}