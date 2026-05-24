package simple.food.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "{validation.required.email}")
    @Email(message = "{validation.invalid.email}")
    @Size(max = 100, message = "{validation.size.email}")
    private String email;

    @NotBlank(message = "{validation.required.senha}")
    @Size(min = 6, max = 50, message = "{validation.size.senha}")
    private String password;
}

