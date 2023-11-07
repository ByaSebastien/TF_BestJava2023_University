package be.bstorm.services.user;

import be.bstorm.models.security.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserEntity register(UserEntity entity);
}
