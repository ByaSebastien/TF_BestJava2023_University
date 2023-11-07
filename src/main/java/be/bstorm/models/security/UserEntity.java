package be.bstorm.models.security;

import be.bstorm.models.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Entity(name = "User")
@Table(
        name = "security_user",
        uniqueConstraints = {
                @UniqueConstraint(name = "UK_User_username",  columnNames = { "username" })
        }
)
@Data
public class UserEntity extends BaseEntity<String> implements UserDetails {

    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;

    @ManyToMany(targetEntity = RoleEntity.class, fetch = FetchType.EAGER)
    private List<RoleEntity> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
