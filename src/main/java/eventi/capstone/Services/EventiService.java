package eventi.capstone.Services;

import eventi.capstone.Entities.Eventi;
import eventi.capstone.Entities.User;
import eventi.capstone.Exceptions.NotFoundException;
import eventi.capstone.Exceptions.UnauthorizedException;
import eventi.capstone.Payloads.NewEventDTO;
import eventi.capstone.Repositories.EventiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EventiService {
    @Autowired
    private EventiRepository eR;
    @Autowired
    private UserService userService;

    public Eventi findById(UUID id) {
        return this.eR.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Eventi saveEvento(NewEventDTO payload, User currentUtente) {
        User utente = this.userService.findById(currentUtente.getId());
        Eventi newEvento = new Eventi(payload.nome(), payload.artista(), payload.postiDisponibili(), payload.data(), payload.luogo());
        newEvento.setOrganizzatore(utente);
        return this.eR.save(newEvento);
    }

    public Page<Eventi> findAll(int page, int size, String sortBy) {
        if (size > 20) size = 20;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.eR.findAll(pageable);
    }

    public void deleteEvento(UUID id_evento, User currentAuthenticatedUtente) {
        Eventi evento = this.findById(id_evento);
        if (!evento.getOrganizzatore().getId().equals(currentAuthenticatedUtente.getId())) {
            throw new UnauthorizedException("Non hai i permessi per modificare questo evento!");
        }
        this.eR.delete(evento);
    }

    public Page<Eventi> findAllByArtista(String artista, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return eR.findByArtista(artista, pageable);
    }


}
