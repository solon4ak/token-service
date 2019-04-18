package ru.tokens.site.utils;

/**
 *
 * @author solon4ak
 */
public class UtilActivation {
    
    public IdUtils getIdUtils() {
        return new IdUtilsImpl();
    }
    
    public PasswordUtil getPasswordUtil() {
        return new PasswordUtilsImpl();
    }
    
    public TokenUtils getTokenUtils() {
        return new TokenUtilsImpl();
    }
}
