package eventi.capstone.Services;

import eventi.capstone.Entities.Post;
import eventi.capstone.Entities.User;
import eventi.capstone.Exceptions.NotFoundException;
import eventi.capstone.Exceptions.UnauthorizedException;
import eventi.capstone.Payloads.PostDTO;
import eventi.capstone.Repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    @Autowired
    private PostRepository pR;
    @Autowired
    private UserService userService;

    public Post savePost(PostDTO payload, User currentUtente) {
        User utente = this.userService.findById(currentUtente.getId());
        Post newPost = new Post(payload.testo());
        newPost.setCreatore(utente);
        return this.pR.save(newPost);
    }

    public Post findById(Long id) {
        return this.pR.findById(id).orElseThrow(() -> new NotFoundException(String.valueOf(id)));
    }

    public Post findByIdAndUpdate(Long id, PostDTO payload, User currentAuthenticatedUtente) {
        Post post = this.findById(id);
        if (!post.getCreatore().getId().equals(currentAuthenticatedUtente.getId())) {
            throw new UnauthorizedException("Non hai i permessi per modificare questo evento!");
        }
        post.setTesto(payload.testo());

        return this.pR.save(post);
    }

    public void deletePost(Long id, User currentAuthenticatedUtente) {
        Post post = this.findById(id);
        if (!post.getCreatore().getId().equals(currentAuthenticatedUtente.getId())) {
            throw new UnauthorizedException("Non hai i permessi per modificare questo evento!");
        }
        this.pR.delete(post);
    }

    public Page<Post> findAllByCreatore(User currentAuthenticatedUtente, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return pR.findByCreatore(currentAuthenticatedUtente, pageable);
    }

}
