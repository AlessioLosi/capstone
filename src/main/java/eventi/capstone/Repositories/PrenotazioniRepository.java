package eventi.capstone.Repositories;

import eventi.capstone.Entities.Eventi;
import eventi.capstone.Entities.Prenotazioni;
import eventi.capstone.Entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PrenotazioniRepository extends JpaRepository<Prenotazioni, UUID> {
    Prenotazioni findByPaymentIntentId(String paymentIntentId);

    boolean existsByUserAndEventi(User utente, Eventi evento);

    Optional<Prenotazioni> findByData(LocalDate data);


    Page<Prenotazioni> findByUser(User currentAuthenticatedUtente, Pageable pageable);
}
