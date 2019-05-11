package ru.tokens.site.services;

import java.security.Principal;

/**
 *
 * @author solon4ak
 */
public interface AuthenticationService {
    
    Principal authenticate(String email, String password);
}
