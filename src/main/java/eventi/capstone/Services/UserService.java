package eventi.capstone.Services;

import com.cloudinary.Cloudinary;
import eventi.capstone.Entities.User;
import eventi.capstone.Exceptions.BadRequestException;
import eventi.capstone.Exceptions.NotFoundException;
import eventi.capstone.Payloads.UtentiDTO;
import eventi.capstone.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository uR;
    @Autowired
    private PasswordEncoder bcryptencoder;
    @Autowired
    private Cloudinary cloudinaryUploader;

    public User findByEmail(String email) {
        return this.uR.findByEmail(email).orElseThrow(() -> new NotFoundException("l'untente con la mail " + email + " non è stato trovato"));
    }

    public User findById(UUID userId) {
        return this.uR.findById(userId).orElseThrow(() -> new NotFoundException(userId));
    }

    public User save(UtentiDTO body) {
        this.uR.findByEmail(body.email()).ifPresent(dipendente -> {
                    throw new BadRequestException("Email " + body.email() + " già in uso");
                }
        );
        this.uR.findByUsername(body.username()).ifPresent(user -> {
            throw new BadRequestException("Username:" + body.username() + " già in uso");
        });
        User newUtente = new User(body.nome(), body.cognome(), body.email(), bcryptencoder.encode(body.password()), body.username());
        newUtente.setAvatar("https://ui-avatars.com/api/?name=" + body.nome() + "+" + body.cognome());
        return this.uR.save(newUtente);
    }

    public Page<User> findAll(int page, int size, String sortBy) {
        if (size > 20) size = 20;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.uR.findAll(pageable);
    }

    public User findByUsername(String username) {
        return this.uR.findByUsername(username).orElseThrow(() -> new NotFoundException(username));
    }

    public User findByIdAndUpdate(UUID id_utente, UtentiDTO payload) {
        User utente = this.findById(id_utente);
        if (!utente.getEmail().equals(payload.email())) {
            if (this.uR.existsByEmail(payload.email()))
                throw new BadRequestException("La mail è già in uso");
        }

        utente.setNome(payload.nome());
        utente.setCognome(payload.cognome());
        utente.setEmail(payload.email());
        utente.setUsername(payload.username());
        utente.setPassword(payload.password());
        return this.uR.save(utente);
    }

}
