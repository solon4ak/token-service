package ru.tokens.site.services;

import com.fasterxml.uuid.Generators;
import java.util.Random;
import org.springframework.stereotype.Component;

/**
 *
 * @author solon4ak
 */
@Component(value = "UUIDTokenUtil")
final class IdUtilsUUIDImpl implements IdUtils {
    
    @Override
    public String generateUUID() {
        return Generators
                .randomBasedGenerator(new Random(System.currentTimeMillis()))
                .generate().toString();
    }
}
