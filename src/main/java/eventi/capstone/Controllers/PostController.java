package eventi.capstone.Controllers;

import eventi.capstone.Entities.Post;
import eventi.capstone.Entities.User;
import eventi.capstone.Exceptions.BadRequestException;
import eventi.capstone.Payloads.PostDTO;
import eventi.capstone.Services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/me/post")
    @ResponseStatus(HttpStatus.CREATED)
    public Post post(@RequestBody @Validated PostDTO payload, @AuthenticationPrincipal User currentAuthenticatedUtente, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            validationResult.getAllErrors().forEach(System.out::println);
            throw new BadRequestException("Ci sono stati errori nel payload!");
        }
        return this.postService.savePost(payload, currentAuthenticatedUtente);
    }

    @GetMapping("/me/post")
    @ResponseStatus(HttpStatus.OK)
    public Page<Post> findPostByCreatore(
            @AuthenticationPrincipal User currentAuthenticatedUtente,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return this.postService.findAllByCreatore(currentAuthenticatedUtente, page, size);
    }


    @DeleteMapping("/me/post/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvento(@PathVariable Long id, @AuthenticationPrincipal User currentAuthenticatedUtente) {
        this.postService.deletePost(id, currentAuthenticatedUtente);
    }

    @GetMapping("/tuttipost")
    public Page<Post> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
                              @RequestParam(defaultValue = "id") String sortBy) {
        return this.postService.findAll(page, size, sortBy);
    }
}
