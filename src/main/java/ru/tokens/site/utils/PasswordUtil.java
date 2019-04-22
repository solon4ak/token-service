package ru.tokens.site.utils;

/**
 *
 * @author solon4ak
 */
public interface PasswordUtil {
    String generatePassword();
    String generateActivationString();
    String generateUserDir();
//    String generateRandomString(int length, String dictionary);
}
