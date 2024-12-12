package eventi.capstone.Services;

import eventi.capstone.Entities.Commenti;
import eventi.capstone.Entities.Post;
import eventi.capstone.Entities.User;
import eventi.capstone.Exceptions.NotFoundException;
import eventi.capstone.Exceptions.UnauthorizedException;
import eventi.capstone.Payloads.CommentiDTO;
import eventi.capstone.Repositories.CommentiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CommentiService {
    @Autowired
    private CommentiRepository cR;
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    public Commenti saveCommenti(CommentiDTO payload, User currentUtente, Long id) {
        User utente = this.userService.findById(currentUtente.getId());
        Post post = this.postService.findById(id);
        Commenti newCommento = new Commenti(payload.testo());
        newCommento.setCreatore(utente);
        newCommento.setPost(post);
        return this.cR.save(newCommento);
    }

    public Commenti findById(Long id) {
        return this.cR.findById(id).orElseThrow(() -> new NotFoundException(String.valueOf(id)));
    }

    public Commenti findByIdAndUpdate(Long id, CommentiDTO payload, User currentAuthenticatedUtente) {
        Commenti commento = this.findById(id);
        if (!commento.getCreatore().getId().equals(currentAuthenticatedUtente.getId())) {
            throw new UnauthorizedException("Non hai i permessi per modificare questo evento!");
        }
        commento.setTesto(payload.testo());

        return this.cR.save(commento);
    }

    public void deleteCommento(Long id, User currentAuthenticatedUtente) {
        Commenti commento = this.findById(id);
        if (!commento.getCreatore().getId().equals(currentAuthenticatedUtente.getId())) {
            throw new UnauthorizedException("Non hai i permessi per modificare questo evento!");
        }
        this.cR.delete(commento);
    }

    public Page<Commenti> findAllByCreatore(User currentAuthenticatedUtente, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return cR.findByCreatore(currentAuthenticatedUtente, pageable);
    }

    public Page<Commenti> findAllByPost(Long id, int page, int size) {

        Post post = postService.findById(id);
        Pageable pageable = PageRequest.of(page, size);
        return cR.findByPost(post, pageable);
    }
}
