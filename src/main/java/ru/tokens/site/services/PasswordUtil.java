package ru.tokens.site.services;

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
