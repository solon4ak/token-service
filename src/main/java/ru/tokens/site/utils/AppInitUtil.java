package ru.tokens.site.utils;

/**
 *
 * @author solon4ak
 */
public class AppInitUtil {
    
    public IdUtils getIdUtils() {
        return new IdUtilsImpl();
    }
    
    public PasswordUtil getPasswordUtil() {
        return new PassayPasswordUtilImpl();
    }
    
    public TokenUtils getTokenUtils() {
        return new TokenUtilsImpl();
    }
}
