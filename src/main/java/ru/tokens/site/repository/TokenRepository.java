package ru.tokens.site.repository;

import java.util.List;
import ru.tokens.site.entities.Token;

/**
 *
 * @author solon4ak
 */
public interface TokenRepository {
    List<Token> getAll();
    Token get(long id);
    void add(Token token);
    void update(Token token);
    void delete(long id);
}
