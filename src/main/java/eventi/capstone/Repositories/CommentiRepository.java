package eventi.capstone.Repositories;

import eventi.capstone.Entities.Commenti;
import eventi.capstone.Entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentiRepository extends JpaRepository<Commenti, Long> {
    Page<Commenti> findByCreatore(User currentAuthenticatedUtente, Pageable pageable);
}
