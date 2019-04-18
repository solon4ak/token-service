package ru.tokens.site.utils;

import java.util.UUID;
import ru.tokens.site.entities.Token;

/**
 *
 * @author solon4ak
 */
class TokenUtilsImpl implements TokenUtils {

    private volatile long TOKEN_ID_SEQUENCE = 1;

    @Override
    public Token generateToken() {

        Token token = new Token();

        IdUtils idUtils = new IdUtilsImpl();
        UUID uuidString = idUtils.generateUUID();

        PasswordUtil passwordUtil = new PasswordUtilsImpl();
        String activationCode = passwordUtil.generateActivationString();

        token.setTokenId(this.getNextTokenId());
        token.setUuidString(uuidString.toString());
        token.setActivationCode(activationCode);

        return token;
    }

    private synchronized long getNextTokenId() {
        return this.TOKEN_ID_SEQUENCE ++;
    }

}
