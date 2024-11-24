package eventi.capstone.Services;

import com.cloudinary.Cloudinary;
import eventi.capstone.Entities.User;
import eventi.capstone.Exceptions.BadRequestException;
import eventi.capstone.Exceptions.NotFoundException;
import eventi.capstone.Payloads.UtentiDTO;
import eventi.capstone.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        User newUtente = new User(body.nome(), body.cognome(), body.email(), body.password(), body.username());
        newUtente.setAvatar("https://ui-avatars.com/api/?name=" + body.nome() + "+" + body.cognome());
        return this.uR.save(newUtente);
    }

}
