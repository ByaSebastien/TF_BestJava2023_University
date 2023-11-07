package be.bstorm.controllers;

import be.bstorm.config.jwt.JwtUtil;
import be.bstorm.models.form.security.FSignIn;
import be.bstorm.models.security.SignInDTO;
import be.bstorm.models.security.UserEntity;
import be.bstorm.services.user.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public SecurityController(
            UserService userService,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping(path = {"/sign-in", "/login"})
    public SignInDTO signInAction(
            @RequestBody FSignIn form
    ) {
        UserDetails ud = this.userService.loadUserByUsername(form.username());

        if (passwordEncoder.matches(form.password(), ud.getPassword())) {
            String token = jwtUtil.generateToken(ud);
            return new SignInDTO(token, ud);
        }
        return null;
    }

    @PostMapping(path = {"/register"})
    public UserDetails registerAction(
            @RequestBody FSignIn form
    ) {
        UserEntity entity = new UserEntity();
        entity.setId(form.username());
        entity.setUsername(form.username());
        entity.setPassword(passwordEncoder.encode(form.password()));

        return this.userService.register(entity);
    }
}
