package ru.tokens.site.utils;

import com.fasterxml.uuid.Generators;
import java.util.Random;
import java.util.UUID;
import org.springframework.stereotype.Component;

/**
 *
 * @author solon4ak
 */
@Component
final class IdUtilsImpl implements IdUtils {
    
    @Override
    public UUID generateUUID() {
        return Generators
                .randomBasedGenerator(new Random(System.currentTimeMillis()))
                .generate();
    }
}
