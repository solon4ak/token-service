package ru.tokens.site.services;

import java.security.SecureRandom;
import org.springframework.stereotype.Service;

/**
 *
 * @author solon4ak
 */
@Service(value = "defaultPasswordUtil")
final class PasswordUtilsImpl implements PasswordUtil {

    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * different dictionaries used
     */
    private static final String ALPHA_CAPS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String ALPHA = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMERIC = "0123456789";
//    private static final String SPECIAL_CHARS = "!@#$%^&*_=+-/";
    
    public String generateRandomString(int length, String dictionary) {        
        String result = "";
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(dictionary.length());
            result += dictionary.charAt(index);
        }
        return result;
    }

    /**
     * Method will generate random string based on the parameters
     *
     * @return the random password
     */
    @Override
    public String generatePassword() {
        int len = 10;
        String dictionary = String.join(ALPHA_CAPS, ALPHA, NUMERIC);
        return generateRandomString(len, dictionary);
    }

    @Override
    public String generateActivationString() {
        int len = 12;
        String dictionary = String.join(ALPHA_CAPS, ALPHA, NUMERIC);
        return generateRandomString(len, dictionary);
    }

    @Override
    public String generateUserDir() {
        int len = 6;
        String dictionary = String.join(ALPHA_CAPS, ALPHA, NUMERIC);
        return generateRandomString(len, dictionary);
    }
    
    

}
