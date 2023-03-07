package dev.osmocode.codehub.security;

import dev.osmocode.codehub.entity.User;
import dev.osmocode.codehub.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    final static class CustomUserDetails extends User implements UserDetails {

        CustomUserDetails(User user) {
            super(user);
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return Collections.singleton(new SimpleGrantedAuthority(this.getAuthority().getAuthority()));
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
    
    
    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    /**
     * @param username the username account
     * @return The <code>UserDetails</code> corresponding to the username
     * @throws UsernameNotFoundException if username does not exist
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService
                .findUserByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException(""));
        return new CustomUserDetails(user);
    }

    public void saveUser(User user) {
        userService.saveUser(user);
    }
}
