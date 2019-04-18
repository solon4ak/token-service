package ru.tokens.site.utils;

import com.fasterxml.uuid.Generators;
import java.util.Random;
import java.util.UUID;

/**
 *
 * @author solon4ak
 */
final class IdUtilsImpl implements IdUtils {
    
    @Override
    public UUID generateUUID() {
        return Generators
                .randomBasedGenerator(new Random(System.currentTimeMillis()))
                .generate();
    }
}
