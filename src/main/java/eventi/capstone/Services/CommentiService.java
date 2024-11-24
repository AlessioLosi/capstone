package eventi.capstone.Services;

import eventi.capstone.Repositories.CommentiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentiService {
    @Autowired
    private CommentiRepository cR;
}
