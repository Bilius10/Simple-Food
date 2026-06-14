package simple.food.backend.dto.usuario;

import lombok.*;
import simple.food.backend.model.usuario.Usuario;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsuarioDto {
    String nome;
    String email;
    String numeroWhatsapp;
    Integer metaCaloriasDiarias;

    public UsuarioDto(Usuario usuario) {
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.numeroWhatsapp = usuario.getNumeroWhatsapp();
        this.metaCaloriasDiarias = usuario.getMetaCaloriasDiarias();
    }
}
