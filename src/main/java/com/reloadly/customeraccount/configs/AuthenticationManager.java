package com.reloadly.customeraccount.configs;

import com.reloadly.customeraccount.helpers.AuthorisationHelper;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * To Authenticate User.
 * @see SecurityContextRepository
 */
@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

    private final AuthorisationHelper authorisationHelper;

    public AuthenticationManager(AuthorisationHelper authorisationHelper) {
        this.authorisationHelper = authorisationHelper;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {

        // Get Credential value(Jwt value).
        String authToken = authentication.getCredentials().toString();

        try {
            AuthorisationHelper.Payload payload = authorisationHelper.validateJwt(authToken);

            if (payload == null || !payload.isTokenValid())
                return Mono.empty();

            // Add Roles as Authorities
            List<GrantedAuthority> authorities = new ArrayList<>();
            for (var role : payload.getRoles())
                authorities.add(new SimpleGrantedAuthority(role));

            // Authenticate Customer roles  in Authorities
            var auth = new UsernamePasswordAuthenticationToken(payload.getUserName(), null, authorities);
            return Mono.just(auth);
        } catch (Exception e) {
            return Mono.empty();
        }
    }
}
