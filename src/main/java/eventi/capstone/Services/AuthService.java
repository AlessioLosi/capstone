package eventi.capstone.Services;

import eventi.capstone.Entities.User;
import eventi.capstone.Exceptions.UnauthorizedException;
import eventi.capstone.Payloads.NewUserLoginDTO;
import eventi.capstone.Tools.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserService userService;
    @Autowired
    private JWT jwt;
    @Autowired
    private PasswordEncoder bcryptencoder;


    public String checkCredenzialiAndToken(NewUserLoginDTO body) {
        User userFound = this.userService.findByEmail(body.email());
        if (bcryptencoder.matches(body.password(), userFound.getPassword())) {
            String accessToken = jwt.createToken(userFound);
            return accessToken;
        } else {
            throw new UnauthorizedException("Le credenziali inserite sono errate");
        }
    }


}
