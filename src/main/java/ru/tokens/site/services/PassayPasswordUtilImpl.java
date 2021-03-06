package ru.tokens.site.services;

import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import static org.passay.NumberRangeRule.ERROR_CODE;
import org.passay.PasswordGenerator;
import org.springframework.stereotype.Service;

/**
 *
 * @author solon4ak
 */
@Service(value = "passayPasswordUtil")
public class PassayPasswordUtilImpl implements PasswordUtil {

    private static final PasswordGenerator gen = new PasswordGenerator();
    private static final CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
    private static final CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
    private static final CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
    private static final CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
    private static final CharacterData digitChars = EnglishCharacterData.Digit;
    private static final CharacterRule digitRule = new CharacterRule(digitChars);
    private static final CharacterData specialChars = new CharacterData() {
        @Override
        public String getErrorCode() {
            return ERROR_CODE;
        }

        @Override
        public String getCharacters() {
            return "!@#$%&";
        }
    };
    private static final CharacterRule splCharRule = new CharacterRule(specialChars);

    @Override
    public String generatePassword() {
        lowerCaseRule.setNumberOfCharacters(2);
        upperCaseRule.setNumberOfCharacters(2);
        digitRule.setNumberOfCharacters(2);
        splCharRule.setNumberOfCharacters(2);
        String password = gen.generatePassword(10, splCharRule, lowerCaseRule,
                upperCaseRule, digitRule);
        return password;
    }

    @Override
    public String generateActivationString() {
        lowerCaseRule.setNumberOfCharacters(3);
        upperCaseRule.setNumberOfCharacters(3);
        digitRule.setNumberOfCharacters(3);
        splCharRule.setNumberOfCharacters(3);
        String activationString = gen.generatePassword(12, splCharRule, lowerCaseRule,
                upperCaseRule, digitRule);
        return activationString;
    }

    @Override
    public String generateUserDir() {
        lowerCaseRule.setNumberOfCharacters(2);
        upperCaseRule.setNumberOfCharacters(2);
        digitRule.setNumberOfCharacters(2);
        return gen.generatePassword(6, lowerCaseRule,
                upperCaseRule, digitRule);
    }
    
}
