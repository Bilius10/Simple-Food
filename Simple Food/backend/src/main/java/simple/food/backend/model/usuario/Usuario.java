package simple.food.backend.model.usuario;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import simple.food.backend.model.registroconsumo.RegistroConsumo;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "email", nullable = false, length = 150, unique = true)
    private String email;

    @Column(name = "senha_hash", length = 255)
    private String senhaHash;

    @Column(name = "numero_whatsapp", nullable = false, length = 20, unique = true)
    private String numeroWhatsapp;

    @Column(name = "meta_calorias_diarias")
    private Integer metaCaloriasDiarias;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @JsonIgnore
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RegistroConsumo> registros;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(String.format("ROLE_%s", this.role.name())));
    }

    public Usuario(String nome, String email, String senhaHash, String numeroWhatsapp) {
        this.nome = nome;
        this.email = email;
        this.senhaHash = senhaHash;
        this.numeroWhatsapp = numeroWhatsapp;
        this.setRole(UserRole.USER);
    }

    public Usuario update(String nome, String email, String numeroWhatsapp, Integer metaCaloriasDiarias) {
        this.nome = nome;
        this.email = email;
        this.numeroWhatsapp = numeroWhatsapp;
        this.metaCaloriasDiarias = metaCaloriasDiarias;
        return this;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return senhaHash;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return email;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
