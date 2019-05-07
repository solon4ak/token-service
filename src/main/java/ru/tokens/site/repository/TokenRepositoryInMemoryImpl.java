package ru.tokens.site.repository;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import ru.tokens.site.entities.Token;

/**
 *
 * @author solon4ak
 */
@Repository
public class TokenRepositoryInMemoryImpl implements TokenRepository {
    
    private volatile long TOKEN_ID_SEQUENCE = 1L;
    
    private synchronized long getNextTokenId() {
        return this.TOKEN_ID_SEQUENCE ++;
    }
    
    private final Map<Long, Token> tokenRepository = new Hashtable<>();

    @Override
    public List<Token> getAll() {
        return new ArrayList<>(this.tokenRepository.values());
    }

    @Override
    public Token get(long id) {
        return this.tokenRepository.get(id);
    }

    @Override
    public void add(Token token) {
        token.setTokenId(this.getNextTokenId());
        this.tokenRepository.put(token.getTokenId(), token);
    }

    @Override
    public void update(Token token) {
        this.tokenRepository.put(token.getTokenId(), token);
    }

    @Override
    public void delete(long id) {
        this.tokenRepository.remove(id);
    }
    
}
