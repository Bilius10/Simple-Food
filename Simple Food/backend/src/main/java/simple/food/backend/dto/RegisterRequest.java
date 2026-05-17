package simple.food.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "{validation.required.nome}")
    @Size(max = 100, message = "{validation.size.nome}")
    private String name;

    @NotBlank(message = "{validation.required.email}")
    @Email(message = "{validation.invalid.email}")
    @Size(max = 100, message = "{validation.size.email}")
    private String email;

    @NotBlank(message = "{validation.required.senha}")
    @Size(min = 6, max = 50, message = "{validation.size.senha}")
    private String password;

    @NotBlank(message = "{validation.required.numeroWhatsapp}")
    @Size(max = 20, message = "{validation.size.numeroWhatsapp}")
    @Pattern(regexp = "^[+]?([0-9\\s().-]){8,20}$", message = "{validation.invalid.numeroWhatsapp}")
    private String whatsappNumber;
}
