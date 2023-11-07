package be.bstorm.models.security;

import org.springframework.security.core.userdetails.UserDetails;

public record SignInDTO(
        String token,
        UserDetails user
) {
}
