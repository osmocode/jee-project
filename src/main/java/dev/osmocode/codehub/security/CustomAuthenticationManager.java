package dev.osmocode.codehub.security;

import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {

    private final CustomUserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    public CustomAuthenticationManager(CustomUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Attempts to authenticate the passed {@link Authentication} object, returning a
     * fully populated <code>Authentication</code> object (including granted authorities)
     * if successful.
     *
     * @param authentication the authentication request object
     * @return a fully authenticated object including credentials
     * @throws AuthenticationException if authentication fails
     * @see AuthenticationManager
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        var user = userDetailsService.loadUserByUsername(authentication.getName());
        if (null == user) {
            throw new BadCredentialsException("Username or password is incorrect.");
        }
        if (!user.isEnabled()) {
            throw new DisabledException("This account is disable.");
        }
        if (!user.isAccountNonLocked()) {
            throw new LockedException("This account is locked.");
        }
        var password = authentication.getCredentials();
        if (!passwordEncoder.matches(password.toString(), user.getPassword())) {
            throw new BadCredentialsException("Username or password is incorrect.");
        }
        return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
    }
}
