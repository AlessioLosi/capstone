package eventi.capstone.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties({"password", "accountNonLocked", "credentialsNonExpired", "accountNonExpired", "authorities", "enabled"})
public class User implements UserDetails {
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @JsonBackReference
    List<Prenotazioni> prenotazioni;
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private String username;
    @Enumerated(EnumType.STRING)
    private TipologiaUtente tipologiaUtente;
    private String avatar;

    public User(String nome, String cognome, String email, String password, String username) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.username = username;
        this.tipologiaUtente = TipologiaUtente.USER;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return List.of(new SimpleGrantedAuthority(this.tipologiaUtente.name()));
    }


    @Override
    public String getUsername() {
        return this.getEmail();
    }
}
