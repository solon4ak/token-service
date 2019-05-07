package ru.tokens.site.services;

import java.util.List;
import ru.tokens.site.entities.Token;
import ru.tokens.site.entities.User;

/**
 *
 * @author solon4ak
 */
public interface TokenService {
    
    List<Token> getAllTokens();
    
    List<Token> getActivatedTokens();
    
    Token findTokenById(long tokenId);
    
    Token findTokenByUser(User user);
    
    Token findTokenByUUIDString(String uuidString);
    
    void saveToken(Token token);
    
    Token generateToken();
    
    List<Token> generateTokens(int quantity);
    
    void deleteToken(Token token);
    
    void deleteToken(long id);
}
