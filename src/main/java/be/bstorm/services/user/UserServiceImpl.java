package be.bstorm.services.user;

import be.bstorm.models.security.UserEntity;
import be.bstorm.repositories.security.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findOneByUsername(username).orElseThrow(() -> new UsernameNotFoundException(""));
    }

    @Override
    public UserEntity register(UserEntity entity) {
        return this.userRepository.save(entity);
    }
}
