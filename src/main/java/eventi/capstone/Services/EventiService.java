package eventi.capstone.Services;

import eventi.capstone.Repositories.EventiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventiService {
    @Autowired
    private EventiRepository eR;
}
