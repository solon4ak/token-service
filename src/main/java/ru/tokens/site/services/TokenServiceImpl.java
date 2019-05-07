package ru.tokens.site.services;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.tokens.site.entities.Token;
import ru.tokens.site.entities.User;
import ru.tokens.site.repository.TokenRepository;
import ru.tokens.site.utils.IdUtils;
import ru.tokens.site.utils.PasswordUtil;

/**
 *
 * @author solon4ak
 */
@Service
public class TokenServiceImpl implements TokenService {    
    
    private static final Logger log = LogManager.getLogger("Token Service");

    @Autowired
    private TokenRepository tokenRepository;
    
    @Autowired
    private IdUtils idUtils;
    
    @Autowired
    @Qualifier("passayPasswordUtil")
    private PasswordUtil passwordUtil;
    
    @PostConstruct
    public void init() {
        Token t1 = this.generateToken();
        log.info("Token #1 generated. Token UUIDString: {}, activationString: {}", 
                t1.getUuidString(), t1.getActivationCode());
        
        Token t2 = this.generateToken();
        log.info("Token #2 generated. Token UUIDString: {}, activationString: {}", 
                t2.getUuidString(), t2.getActivationCode());
    }
    
    @Override
    public List<Token> getAllTokens() {
        return this.tokenRepository.getAll();
    }

    @Override
    public List<Token> getActivatedTokens() {
        List<Token> activatedTokens = new ArrayList<>();
        for (Token token : this.getAllTokens()) {
            if (token.isActivated()) {
                activatedTokens.add(token);
            }
        }
        return activatedTokens;
    }

    @Override
    public Token findTokenById(long tokenId) {
        return this.tokenRepository.get(tokenId);
    }

    @Override
    public Token findTokenByUser(User user) {
        for (Token token : this.getAllTokens()) {
            if (user.equals(token.getUser())) {
                return token;
            }
        }
        return null;
    }

    @Override
    public Token findTokenByUUIDString(String uuidString) {
        for (Token token : this.getAllTokens()) {
            if (uuidString.equals(token.getUuidString())) {
                return token;
            }
        }
        return null;
    }

    @Override
    public void saveToken(Token token) {
        if (token.getTokenId() < 1) {
            this.tokenRepository.add(token);
        } else {
            this.tokenRepository.update(token);
        }
    }

    @Override
    public Token generateToken() {
        Token token = new Token();
        token.setUuidString(idUtils.generateUUID().toString());
        token.setActivationCode(passwordUtil.generateActivationString());
        this.saveToken(token);
        
        return token;
    }

    @Override
    public List<Token> generateTokens(int quantity) {
        List<Token> generated = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            generated.add(this.generateToken());
        }
        return generated;
    }

    @Override
    public void deleteToken(Token token) {
        this.tokenRepository.delete(token.getTokenId());
    }

    @Override
    public void deleteToken(long id) {
        this.tokenRepository.delete(id);
    }
    
}
