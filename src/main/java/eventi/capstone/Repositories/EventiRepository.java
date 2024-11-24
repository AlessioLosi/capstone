package eventi.capstone.Repositories;

import eventi.capstone.Entities.Eventi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EventiRepository extends JpaRepository<Eventi, UUID> {
}
