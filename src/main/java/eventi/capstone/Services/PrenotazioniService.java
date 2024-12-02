package eventi.capstone.Services;

import eventi.capstone.Entities.Eventi;
import eventi.capstone.Entities.Prenotazioni;
import eventi.capstone.Entities.User;
import eventi.capstone.Exceptions.BadRequestException;
import eventi.capstone.Exceptions.NotFoundException;
import eventi.capstone.Repositories.PrenotazioniRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class PrenotazioniService {
    @Autowired
    private PrenotazioniRepository prenoR;
    @Autowired
    private EventiService eventiService;
    @Autowired
    private UserService userService;

    public Prenotazioni savePrenotazione(Prenotazioni payload, User currentAuthenticatedUtente) {
        Eventi evento = this.eventiService.findById(payload.getEventi().getId());
        User utente = this.userService.findById(currentAuthenticatedUtente.getId());
        if (this.prenoR.existsByUserAndEventi(utente, evento)) {
            throw new BadRequestException("Hai già una prenotazione per questo evento.");
        }
        Prenotazioni newPrenotazione = new Prenotazioni(evento, utente);
        return this.prenoR.save(newPrenotazione);
    }

    public Page<Prenotazioni> findAll(int page, int size, String sortBy) {
        if (size > 20) size = 20;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.prenoR.findAll(pageable);
    }

    public Prenotazioni findById(UUID id) {
        return this.prenoR.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Prenotazioni findByData(LocalDate data) {
        return this.prenoR.findByData(data).orElseThrow(() -> new NotFoundException(String.valueOf(data)));
    }

    public void deletePrenotazione(UUID id_prenotazione) {
        Prenotazioni prenotazione = this.findById(id_prenotazione);
        this.prenoR.delete(prenotazione);
    }


    public boolean existsByUserAndEventi(User currentAuthenticatedUtente, Eventi evento) {
        return false;
    }


}
