package eventi.capstone.Services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import eventi.capstone.Entities.Eventi;
import eventi.capstone.Entities.User;
import eventi.capstone.Exceptions.BadRequestException;
import eventi.capstone.Exceptions.NotFoundException;
import eventi.capstone.Exceptions.UnauthorizedException;
import eventi.capstone.Payloads.NewEventDTO;
import eventi.capstone.Repositories.EventiRepository;
import io.jsonwebtoken.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public class EventiService {
    @Autowired
    private EventiRepository eR;
    @Autowired
    private UserService userService;
    @Autowired
    private Cloudinary cloudinaryUploader;

    public Eventi findById(UUID id) {
        return this.eR.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Eventi saveEvento(NewEventDTO payload, User currentUtente) {
        User utente = this.userService.findById(currentUtente.getId());
        Eventi newEvento = new Eventi(payload.nome(), payload.artista(), payload.postiDisponibili(), payload.data(), payload.luogo(), payload.prezzo());
        newEvento.setOrganizzatore(utente);
        newEvento.setFoto("https://i.pinimg.com/474x/31/d0/66/31d066238f106380c7bfa0b59ce0b3b6.jpg");
        return this.eR.save(newEvento);

    }

    public String uploadfoto(MultipartFile file, User currentAuthenticatedUtente, UUID id) {

        if (file.isEmpty()) {
            throw new BadRequestException("Il file dell'immagine non può essere vuoto");
        }

        String url = null;
        try {
            url = (String) cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        } catch (IOException | java.io.IOException e) {
            throw new BadRequestException("Errore nel caricamento dell'immagine");
        }

        Eventi Found = this.findById(id);
        if (Found == null) {
            throw new BadRequestException("Utente non trovato con l'ID fornito");
        }

        Found.setFoto(url);
        this.eR.save(Found);
        return url;
    }

    public Page<Eventi> findAll(int page, int size, String sortBy) {
        if (size > 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.eR.findAll(pageable);
    }

    public void deleteEvento(UUID id_evento, User currentAuthenticatedUtente) {
        Eventi evento = this.findById(id_evento);

        if (evento == null) {
            throw new IllegalArgumentException("Evento non trovato con ID: " + id_evento);
        }

        if (!evento.getOrganizzatore().getId().equals(currentAuthenticatedUtente.getId())) {
            throw new UnauthorizedException("Non hai i permessi per modificare questo evento!");
        }

        this.eR.delete(evento);
    }


    public void updatePosti(UUID id_evento) {
        Eventi evento = this.findById(id_evento);
        evento.setPostiDisponibili(evento.getPostiDisponibili() - 1);
        this.eR.save(evento);

    }

    public Eventi updateEvento(UUID id_evento, NewEventDTO body) {
        Eventi evento = this.findById(id_evento);
        evento.setPrezzo(body.prezzo());
        evento.setArtista(body.artista());
        evento.setLuogo(body.luogo());
        evento.setNome(body.nome());
        evento.setData(body.data());
        evento.setPostiDisponibili(body.postiDisponibili());

        this.eR.save(evento);

        return null;
    }


    public Page<Eventi> findAllByArtista(String artista, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return eR.findByArtista(artista, pageable);
    }


}
