package eventi.capstone.Repositories;

import eventi.capstone.Entities.Commenti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentiRepository extends JpaRepository<Commenti, Long> {
}
