package eventi.capstone.Services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import eventi.capstone.Entities.User;
import eventi.capstone.Exceptions.BadRequestException;
import eventi.capstone.Exceptions.NotFoundException;
import eventi.capstone.Payloads.UtentiDTO;
import eventi.capstone.Repositories.UserRepository;
import io.jsonwebtoken.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

        if (payload.email() != null && !utente.getEmail().equals(payload.email())) {
            if (this.uR.existsByEmail(payload.email()))
                throw new BadRequestException("La mail è già in uso");
            utente.setEmail(payload.email());
        }

        utente.setNome(payload.nome() != null ? payload.nome() : utente.getNome());
        utente.setCognome(payload.cognome() != null ? payload.cognome() : utente.getCognome());
        utente.setUsername(payload.username() != null ? payload.username() : utente.getUsername());

        return this.uR.save(utente);
    }


    public String uploadAvatar(MultipartFile file, User currentAuthenticatedUtente) {

        if (file.isEmpty()) {
            throw new BadRequestException("Il file dell'immagine non può essere vuoto");
        }

        String url = null;
        try {
            url = (String) cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        } catch (IOException | java.io.IOException e) {
            throw new BadRequestException("Errore nel caricamento dell'immagine");
        }

        User Found = this.findById(currentAuthenticatedUtente.getId());
        if (Found == null) {
            throw new BadRequestException("Utente non trovato con l'ID fornito");
        }

        Found.setAvatar(url);
        this.uR.save(Found);
        return url;
    }
}

