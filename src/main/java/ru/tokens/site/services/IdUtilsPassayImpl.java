package ru.tokens.site.services;

import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import static org.passay.NumberRangeRule.ERROR_CODE;
import org.passay.PasswordGenerator;
import org.springframework.stereotype.Component;

/**
 *
 * @author solon4ak
 */
@Component(value = "PassayTokenUtil")
public class IdUtilsPassayImpl implements IdUtils {
    
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
    public String generateUUID() {
        lowerCaseRule.setNumberOfCharacters(12);
        upperCaseRule.setNumberOfCharacters(12);
        digitRule.setNumberOfCharacters(12);
        String activationString = gen.generatePassword(36, lowerCaseRule,
                upperCaseRule, digitRule);
        return activationString;
    }
    
}
